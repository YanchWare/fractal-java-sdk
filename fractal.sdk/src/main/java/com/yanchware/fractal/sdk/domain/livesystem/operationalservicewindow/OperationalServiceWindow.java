package com.yanchware.fractal.sdk.domain.livesystem.operationalservicewindow;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Operational Service Window (OSW)
 * --------------------------------
 * Immutable definition of temporal windows during which complex infra operations are allowed.
 * &nbsp;
 * Supports:
 *  - Weekly recurring windows (e.g., Mon–Thu 22:00–02:00).
 *  - One-off ad-hoc windows on specific dates/times.
 *  - Blackout ranges (e.g., change freeze).
 *  - Zone-aware evaluation and iteration helpers.
 */
public class OperationalServiceWindow {
  private final String name;
  private final ZoneId zone;
  private final List<Rule> rules;
  private final List<Blackout> blackouts;
  private final ZonedDateTime activeFrom; // inclusive
  private final ZonedDateTime activeUntil; // exclusive
  private final Predicate<ZonedDateTime> extraExclusion; // e.g., public holidays

  private OperationalServiceWindow(
    String name,
    ZoneId zone,
    List<Rule> rules,
    List<Blackout> blackouts,
    ZonedDateTime activeFrom,
    ZonedDateTime activeUntil,
    Predicate<ZonedDateTime> extraExclusion)
  {
    this.name = name;
    this.zone = zone;
    this.rules = List.copyOf(rules);
    this.blackouts = List.copyOf(blackouts);
    this.activeFrom = activeFrom;
    this.activeUntil = activeUntil;
    this.extraExclusion = extraExclusion;
  }

  public String name() { return name; }
  public ZoneId zone() { return zone; }

  public static Builder builder(String name, ZoneId zone) {
    return new Builder(name, zone);
  }

  /** Is the given instant inside any allowed window (after applying blackouts & exclusions)? */
  public boolean isAllowed(Instant instant) {
    var zonedDateTime = instant.atZone(zone);
    return currentWindow(zonedDateTime).isPresent();
  }

  /** If now is inside a window, return its concrete bounds. */
  public Optional<Window> currentWindow(Instant instant) {
    ZonedDateTime t = instant.atZone(zone);
    return currentWindow(t);
  }

  public Optional<Window> currentWindow(ZonedDateTime zonedDateTime) {
    if (!isWithinActiveBounds(zonedDateTime) || extraExclusion.test(zonedDateTime)) {
      return Optional.empty();
    }
    return windowsBetween(zonedDateTime.minusDays(1), zonedDateTime.plusDays(1))
      .filter(w -> w.contains(zonedDateTime))
      .findFirst();
  }

  /** Next allowed window starting strictly after the given instant (search horizon configurable). */
  public Optional<Window> nextWindow(Instant after, Duration searchHorizon) {
    var from = after.atZone(zone).plusNanos(1);
    var to = from.plus(searchHorizon);
    return windowsBetween(from, to).findFirst();
  }

  /** Convenience: search the next 180 days. */
  public Optional<Window> nextWindow(Instant after) {
    return nextWindow(after, Duration.ofDays(180));
  }

  /** Enumerate concrete windows (sorted, de-duplicated, blackout-adjusted) between two instants. */
  public Stream<Window> windowsBetween(Instant fromIncl, Instant toExcl) {
    return windowsBetween(fromIncl.atZone(zone), toExcl.atZone(zone));
  }

  public Stream<Window> windowsBetween(ZonedDateTime fromIncl, ZonedDateTime toExcl) {
    // Respect active range
    if (activeFrom != null && toExcl.isBefore(activeFrom)) {
      return Stream.empty();
    }
    if (activeUntil != null && fromIncl.isAfter(activeUntil)) {
      return Stream.empty();
    }

    var clampFrom = activeFrom == null
      ? fromIncl
      : fromIncl.isBefore(activeFrom)
        ? activeFrom
        : fromIncl;
    var clampTo = activeUntil == null
      ? toExcl
      : toExcl.isAfter(activeUntil)
        ? activeUntil
        : toExcl;

    // Generate, sort, merge overlaps from all rules
    List<Window> generated = rules.stream()
      .flatMap(r -> r.enumerate(clampFrom, clampTo, zone))
      .filter(w -> w.end().isAfter(clampFrom) && w.start().isBefore(clampTo))
      .sorted(Comparator.comparing(Window::start))
      .toList();

    if (generated.isEmpty()) {
      return Stream.empty();
    }

    // Merge overlapping windows (from multiple rules)
    var mergedWindows = new ArrayList<Window>();
    var currentWindow = generated.getFirst();
    for (int i = 1; i < generated.size(); i++) {
      var nextWindow = generated.get(i);
      if (!nextWindow.start().isAfter(currentWindow.end())) {
        var newEndZonedTime = nextWindow.end().isAfter(currentWindow.end()) ? nextWindow.end() : currentWindow.end();
        currentWindow = new Window(currentWindow.start(), newEndZonedTime);
      } else {
        mergedWindows.add(currentWindow);
        currentWindow = nextWindow;
      }
    }
    mergedWindows.add(currentWindow);

    // Apply extra predicate exclusion & blackouts
    var filteredWindows = mergedWindows.stream()
      .flatMap(w -> splitOnPredicateExclusion(w, extraExclusion))
      .filter(w -> blackouts.stream().noneMatch(b -> b.overlaps(w)))
      .filter(w -> w.end().isAfter(w.start()))
      .toList();

    return filteredWindows.stream()
      .filter(w -> w.end().isAfter(clampFrom) && w.start().isBefore(clampTo))
      .sorted(Comparator.comparing(Window::start));
  }

  // Split a window by minute if any instant inside matches extraExclusion. (Cheap & robust)
  private static Stream<Window> splitOnPredicateExclusion(Window w, Predicate<ZonedDateTime> exclude) {
    if (isWindowClean(w, exclude)) return Stream.of(w);

    // Coarse splitting strategy: iterate minute-by-minute to build contiguous allowed segments.
    List<Window> parts = new ArrayList<>();
    var cursor = w.start().truncatedTo(ChronoUnit.MINUTES);
    var end = w.end().truncatedTo(ChronoUnit.MINUTES);
    ZonedDateTime segStart = null;

    while (!cursor.isAfter(end)) {
      boolean ok = !exclude.test(cursor);
      boolean nextOk;
      var next = cursor.plusMinutes(1);
      nextOk = !exclude.test(next.minusNanos(1)); // inclusive of the minute

      if (ok && segStart == null) segStart = cursor;
      if (segStart != null && (!nextOk || next.isAfter(end))) {
        var segEnd = next.isAfter(end) ? end : next;
        if (segEnd.isAfter(segStart)) parts.add(new Window(segStart, segEnd));
        segStart = null;
      }
      cursor = next;
    }
    return parts.stream();
  }

  private static boolean isWindowClean(Window w, Predicate<ZonedDateTime> exclude) {
    // Fast checks at edges & midpoint
    if (exclude.test(w.start())) {
      return false;
    }
    if (exclude.test(w.end().minusNanos(1))) {
      return false;
    }
    ZonedDateTime mid = w.start().plus(Duration.between(w.start(), w.end()).dividedBy(2));
    return !exclude.test(mid);
  }

  private boolean isWithinActiveBounds(ZonedDateTime t) {
    if (activeFrom != null && t.isBefore(activeFrom)) {
      return false;
    }
    return activeUntil == null || t.isBefore(activeUntil);
  }

  public static final class Builder {
    private final String name;
    private final ZoneId zone;
    private final List<Rule> rules = new ArrayList<>();
    private final List<Blackout> blackouts = new ArrayList<>();
    private ZonedDateTime activeFrom = null;
    private ZonedDateTime activeUntil = null;
    private Predicate<ZonedDateTime> extraExclusion = t -> false;

    private Builder(String name, ZoneId zone) {
      this.name = Objects.requireNonNull(name);
      this.zone = Objects.requireNonNull(zone);
    }

    /** Limit the OSW to operate only within this active interval (inclusive/exclusive). */
    public Builder activeBetween(ZonedDateTime fromIncl, ZonedDateTime untilExcl) {
      if (!untilExcl.isAfter(fromIncl)) throw new IllegalArgumentException("untilExcl must be after fromIncl");
      this.activeFrom = fromIncl.withZoneSameInstant(zone);
      this.activeUntil = untilExcl.withZoneSameInstant(zone);
      return this;
    }

    /** Add a weekly window; if end <= start, it rolls into next day. */
    public Builder withWeeklyRule(EnumSet<DayOfWeek> days, LocalTime start, LocalTime end) {
      this.rules.add(new WeeklyRule(days, start, end));
      return this;
    }

    /** Add a one-off window on a specific local date in this OSW's zone. */
    public Builder withOneOffRule(LocalDate date, LocalTime start, LocalTime end) {
      this.rules.add(new OneOffRule(date, start, end));
      return this;
    }

    /** Add a blackout period (excluded even if a rule would allow it). */
    public Builder withBlackoutRule(ZonedDateTime start, ZonedDateTime end) {
      this.blackouts.add(new Blackout(start.withZoneSameInstant(zone), end.withZoneSameInstant(zone)));
      return this;
    }

    /** Provide an additional exclusion predicate, e.g., a holiday calendar. */
    public Builder excludeWhen(Predicate<ZonedDateTime> predicate) {
      this.extraExclusion = this.extraExclusion.or(predicate);
      return this;
    }

    public OperationalServiceWindow build() {
      return new OperationalServiceWindow(name, zone, rules, blackouts, activeFrom, activeUntil, extraExclusion);
    }
  }
}
