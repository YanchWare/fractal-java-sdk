package com.yanchware.fractal.sdk.domain.livesystem.operationalservicewindow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.stream.Stream;

/** One-off concrete window on a specific local date. */
public class OneOffRule implements Rule {
  final LocalDate date;
  final LocalTime start;
  final LocalTime end; // if end <= start, roll over into next day
  OneOffRule(LocalDate date, LocalTime start, LocalTime end) {
    this.date = Objects.requireNonNull(date);
    this.start = Objects.requireNonNull(start);
    this.end = Objects.requireNonNull(end);
  }
  @Override
  public Stream<Window> enumerate(ZonedDateTime fromIncl, ZonedDateTime toExcl, ZoneId zone) {
    ZonedDateTime s = date.atTime(start).atZone(zone);
    ZonedDateTime e = date.atTime(end).atZone(zone);

    if (!e.isAfter(s)) {
      e = e.plusDays(1);
    }

    if (e.isAfter(fromIncl) && s.isBefore(toExcl)) {
      return Stream.of(new Window(s, e));
    }

    return Stream.empty();
  }

}
