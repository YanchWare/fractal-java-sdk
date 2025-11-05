package com.yanchware.fractal.sdk.domain.livesystem.operationalservicewindow;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Represents a configurable operational service window, which defines a schedule of active time periods
 * based on rules, blackouts, and optional exclusions. These windows are defined within a specified time zone
 * and applicable for determining whether a given point in time is allowed or excluded.
 * &nbsp;
 * Instances of this class are immutable and can be created via the static {@code builder} method.
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

  public String name() {
    return name;
  }

  public ZoneId zone() {
    return zone;
  }

  /**
   * Creates a new {@code Builder} instance with the provided name and time zone.
   *
   * @param name the name of the operational service window
   * @param zone the time zone associated with the operational service window
   * @return a new {@code Builder} instance for configuring and creating an {@code OperationalServiceWindow}
   */
  public static Builder builder(String name, ZoneId zone) {
    return new Builder(name, zone);
  }


  /**
   * Determines if the given instant falls within any of the currently defined operational service windows.
   * &nbsp;
   * The method evaluates whether the provided instant, when converted to the defined time zone,
   * is within any current active window.
   *
   * @param instant the instant in time to check, represented as an {@code Instant}
   * @return {@code true} if the instant is allowed (i.e., it falls within an active operational service window),
   * {@code false} otherwise
   */
  public boolean isAllowed(Instant instant) {
    var zonedDateTime = instant.atZone(zone);
    return currentWindow(zonedDateTime).isPresent();
  }

  /**
   * Determines the current operational service window for the provided instant.
   * &nbsp;
   * This method converts the input {@code Instant} to the configured time zone
   * and identifies the {@code Window} in which the given time falls, if any.
   * If no window is active at the given instant, an empty {@code Optional} is returned.
   *
   * @param instant the instant of time to evaluate, represented as an {@code Instant}
   * @return an {@code Optional<Window>} containing the active {@code Window} if one exists,
   * or an empty {@code Optional} if no active window is found for the given instant
   */
  public Optional<Window> currentWindow(Instant instant) {
    var zonedDateTime = instant.atZone(zone);
    return currentWindow(zonedDateTime);
  }

  /**
   * Determines the current operational service window for the provided time.
   * &nbsp;
   * The method evaluates whether the given {@code ZonedDateTime} is within the active bounds
   * of the operational service window and not excluded by additional conditions. It searches
   * for windows that overlap with the specified time, returning the first matching window
   * if one is found.
   *
   * @param zonedDateTime the point in time to evaluate, represented as a {@code ZonedDateTime}
   * @return an {@code Optional<Window>} containing the active {@code Window} if one exists,
   * or an empty {@code Optional} if no active window is found for the given time
   */
  public Optional<Window> currentWindow(ZonedDateTime zonedDateTime) {
    if (!isWithinActiveBounds(zonedDateTime) || extraExclusion.test(zonedDateTime)) {
      return Optional.empty();
    }
    return windowsBetween(zonedDateTime.minusDays(1), zonedDateTime.plusDays(1))
      .filter(w -> w.contains(zonedDateTime))
      .findFirst();
  }

  /**
   * Searches for the next available operational service window starting after the given instant and within the specified search horizon.
   * &nbsp;
   * The search is conducted in the configured time zone of the service window, considering any applicable blackouts,
   * active periods, and exclusions defined in the rules of the operational service window.
   *
   * @param after         the {@code Instant} after which the search should begin
   * @param searchHorizon the maximum {@code Duration} in which to search for the next available window
   * @return an {@code Optional<Window>} containing the next available operational service window if one exists,
   * or an empty {@code Optional} if no window is found within the search parameters
   */
  public Optional<Window> nextWindow(Instant after, Duration searchHorizon) {
    var from = after.atZone(zone).plusNanos(1);
    var to = from.plus(searchHorizon);
    return windowsBetween(from, to).findFirst();
  }

  /**
   * Searches for the next available operational service window starting after the given instant.
   * The method performs the search with a default search horizon of 180 days.
   *
   * @param after the {@code Instant} after which the search should begin
   * @return an {@code Optional<Window>} containing the next available operational service window if one exists,
   * or an empty {@code Optional} if no window is found within the default search horizon
   */
  public Optional<Window> nextWindow(Instant after) {
    return nextWindow(after, Duration.ofDays(180));
  }

  /**
   * Retrieves a stream of operational service windows within the specified time range.
   * The time range is defined by the given start and end instants, inclusive and exclusive respectively,
   * and is converted to the configured time zone of the operational service window.
   *
   * @param fromIncl the inclusive start of the time range, represented as an {@code Instant}
   * @param toExcl   the exclusive end of the time range, represented as an {@code Instant}
   * @return a {@code Stream<Window>} representing the operational service windows within the specified range
   */
  public Stream<Window> windowsBetween(Instant fromIncl, Instant toExcl) {
    return windowsBetween(fromIncl.atZone(zone), toExcl.atZone(zone));
  }

  /**
   * Retrieves a stream of operational service windows that overlap with the specified time range.
   * The range is defined by the inclusive start time and the exclusive end time in the configured time zone,
   * and is constrained by the active bounds, rules, and blackout periods. Overlapping windows from multiple rules
   * are merged, and additional predicates or exclusions are applied to refine the results.
   *
   * @param fromIncl the inclusive start of the time range to evaluate, represented as a {@code ZonedDateTime}
   * @param toExcl   the exclusive end of the time range to evaluate, represented as a {@code ZonedDateTime}
   * @return a {@code Stream<Window>} containing the resulting operational service windows that match the specified criteria
   */
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

    /**
     * Specifies the active time interval for the builder. The interval is defined
     * by a starting `ZonedDateTime` (inclusive) and an ending `ZonedDateTime` (exclusive).
     * The specified times are adjusted to match the time zone of the builder.
     *
     * @param fromIncl  the starting `ZonedDateTime` of the active interval (inclusive); must not be null
     * @param untilExcl the ending `ZonedDateTime` of the active interval (exclusive); must not be null
     *                  and must be after `fromIncl`
     * @return the current `Builder` instance for method chaining
     * @throws IllegalArgumentException if `untilExcl` is not after `fromIncl`
     * @throws NullPointerException     if either `fromIncl` or `untilExcl` is null
     */
    public Builder activeBetween(ZonedDateTime fromIncl, ZonedDateTime untilExcl) {
      if (!untilExcl.isAfter(fromIncl)) {
        throw new IllegalArgumentException("untilExcl must be after fromIncl");
      }
      this.activeFrom = fromIncl.withZoneSameInstant(zone);
      this.activeUntil = untilExcl.withZoneSameInstant(zone);
      return this;
    }

    /**
     * Adds a weekly rule to specify the allowed time windows for one or more days of the week.
     * The specified time window is defined by the start and end times. If the end time is earlier
     * than or equal to the start time, the window will wrap into the next day.
     *
     * @param days  the set of days of the week for which the rule applies; must not be empty
     * @param start the starting local time for the rule; must not be null
     * @param end   the ending local time for the rule; must not be null
     * @return the current Builder instance for method chaining
     * @throws IllegalArgumentException if the days set is empty
     * @throws NullPointerException     if the start or end time is null
     */
    public Builder withWeeklyRule(EnumSet<DayOfWeek> days, LocalTime start, LocalTime end) {
      this.rules.add(new WeeklyRule(days, start, end));
      return this;
    }

    /**
     * Adds a one-off rule that specifies a time window on a specific date.
     * The time window starts and ends on the provided local time, and if the
     * end time is earlier than or equal to the start time, the window is treated
     * as spanning into the following day.
     *
     * @param date  the specific local date for the one-off rule
     * @param start the starting local time of the one-off rule
     * @param end   the ending local time of the one-off rule; treated as wrapping
     *              into the next day if it is earlier than or equal to the start time
     * @return the current Builder instance for method chaining
     */
    public Builder withOneOffRule(LocalDate date, LocalTime start, LocalTime end) {
      this.rules.add(new OneOffRule(date, start, end));
      return this;
    }

    /**
     * Adds a blackout rule to exclude a specific time interval. The blackout is defined
     * by a start and end ZonedDateTime, both of which are adjusted to the zone of the builder.
     *
     * @param start the starting ZonedDateTime of the blackout interval
     * @param end   the ending ZonedDateTime of the blackout interval, which must be after the start
     * @return the current Builder instance for method chaining
     */
    public Builder withBlackoutRule(ZonedDateTime start, ZonedDateTime end) {
      this.blackouts.add(new Blackout(start.withZoneSameInstant(zone), end.withZoneSameInstant(zone)));
      return this;
    }

    /**
     * Adds an exclusion condition using the given predicate. The predicate is evaluated
     * against a ZonedDateTime to determine whether it should be excluded.
     *
     * @param predicate the condition to exclude specific times, represented as a Predicate<ZonedDateTime>
     * @return the current Builder instance for method chaining
     */
    public Builder excludeWhen(Predicate<ZonedDateTime> predicate) {
      this.extraExclusion = this.extraExclusion.or(predicate);
      return this;
    }

    public OperationalServiceWindow build() {
      return new OperationalServiceWindow(name, zone, rules, blackouts, activeFrom, activeUntil, extraExclusion);
    }
  }

  // Split a window by minute if any instant inside matches extraExclusion. (Cheap & robust)
  private static Stream<Window> splitOnPredicateExclusion(Window w, Predicate<ZonedDateTime> exclude) {
    if (isWindowClean(w, exclude)) {
      return Stream.of(w);
    }

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

      if (ok && segStart == null) {
        segStart = cursor;
      }
      if (segStart != null && (!nextOk || next.isAfter(end))) {
        var segEnd = next.isAfter(end) ? end : next;
        if (segEnd.isAfter(segStart)) {
          parts.add(new Window(segStart, segEnd));
        }
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
}
