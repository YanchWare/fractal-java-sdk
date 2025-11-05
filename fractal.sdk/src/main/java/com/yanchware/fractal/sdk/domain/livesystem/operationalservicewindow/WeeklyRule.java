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

public class WeeklyRule implements Rule {
  final EnumSet<DayOfWeek> days;
  final LocalTime start;
  final LocalTime end; // if end is before or equal start, it rolls into next day

  public WeeklyRule(EnumSet<DayOfWeek> days, LocalTime start, LocalTime end) {
    if (days.isEmpty()) throw new IllegalArgumentException("days required");
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
      DayOfWeek dow = cursor.getDayOfWeek();
      if (days.contains(dow)) {
        var s = cursor.with(start);
        var e = cursor.with(end);
        if (!e.isAfter(s))
        {
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
