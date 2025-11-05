package com.yanchware.fractal.sdk.domain.livesystem.operationalservicewindow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Represents a one-time rule for generating a single operational service window
 * on a specified date and time range. The rule generates a window only if the specified
 * date and time fall within the provided search range.
 * <p/>
 * The start and end times define the bounds of the window for the given date. If the end time
 * is earlier than or equal to the start time, the window rolls over into the next day.
 * <p/>
 * This rule is designed for one-off scheduling scenarios and does not repeat
 * across multiple dates.
 */
public class OneOffRule implements Rule {
  final LocalDate date;
  final LocalTime start;
  final LocalTime end; // if end <= start, roll over into next day

  /**
   * Constructs a new instance of {@code OneOffRule} that defines a single operational
   * service window for a specific date and time range.
   *
   * @param date the specific date for the one-off rule; must not be {@code null}
   * @param start the start time of the service window on the specified date; must not be {@code null}
   * @param end the end time of the service window on the specified date; must not be {@code null}
   *            (if {@code end} is earlier than or equal to {@code start}, the window rolls over into the next day)
   */
  public OneOffRule(LocalDate date, LocalTime start, LocalTime end) {
    this.date = Objects.requireNonNull(date);
    this.start = Objects.requireNonNull(start);
    this.end = Objects.requireNonNull(end);
  }

  @Override
  public Stream<Window> enumerate(ZonedDateTime fromIncl, ZonedDateTime toExcl, ZoneId zone) {
    var start = date.atTime(this.start).atZone(zone);
    var end = date.atTime(this.end).atZone(zone);

    if (!end.isAfter(start)) {
      end = end.plusDays(1);
    }

    if (end.isAfter(fromIncl) && start.isBefore(toExcl)) {
      return Stream.of(new Window(start, end));
    }

    return Stream.empty();
  }

}
