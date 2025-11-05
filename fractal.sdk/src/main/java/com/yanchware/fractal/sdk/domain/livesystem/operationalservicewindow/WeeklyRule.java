package com.yanchware.fractal.sdk.domain.livesystem.operationalservicewindow;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Represents a recurring rule for generating time intervals on specific days of the week.
 * Each instance of this class defines a weekly schedule where windows of time are specified
 * for a set of days, along with start and end times. The rule generates time intervals
 * falling within a given range when enumerated.
 * <p/>
 * This class is immutable and ensures that the specified days, start time, and end time
 * are valid. If the end time is before or equal to the start time, the interval rolls
 * into the next day.
 * <p/>
 * Note: This class implements the {@code Rule} interface, providing functionality
 * for listing windows of time within a specified range.
 */
public class WeeklyRule implements Rule {
  final EnumSet<DayOfWeek> days;
  final LocalTime start;
  final LocalTime end; // if end is before or equal start, it rolls into next day

  /**
   * Constructs a new {@code WeeklyRule} that defines a recurring schedule based on the specified parameters.
   * This rule generates time intervals on the specified days of the week, with start and end times defining
   * the duration of each interval. The end time can roll over into the next day if it is earlier than or equal
   * to the start time.
   *
   * @param days the days of the week when the rule applies; must not be empty
   * @param start the start time of the interval; must not be {@code null}
   * @param end the end time of the interval; must not be {@code null}
   * @throws IllegalArgumentException if {@code days} is empty
   * @throws NullPointerException if {@code start} or {@code end} is {@code null}
   */
  public WeeklyRule(EnumSet<DayOfWeek> days, LocalTime start, LocalTime end) {
    if (days.isEmpty()) {
      throw new IllegalArgumentException("days required");
    }
    this.days = EnumSet.copyOf(days);
    this.start = Objects.requireNonNull(start);
    this.end = Objects.requireNonNull(end);
  }

  @Override
  public Stream<Window> enumerate(ZonedDateTime fromIncl, ZonedDateTime toExcl, ZoneId zone) {
    // Align to start-of-day for the week-span loop
    var cursor = fromIncl.truncatedTo(ChronoUnit.DAYS);
    var out = new ArrayList<Window>();
    while (cursor.isBefore(toExcl)) {
      var dow = cursor.getDayOfWeek();
      if (days.contains(dow)) {
        var s = cursor.with(start);
        var e = cursor.with(end);
        if (!e.isAfter(s)) {
          e = e.plusDays(1); // cross midnight
        }

        // Clamp to range:
        if (e.isAfter(fromIncl) && s.isBefore(toExcl)) {
          out.add(new Window(s, e));
        }
      }
      cursor = cursor.plusDays(1);
    }
    return out.stream();
  }

}
