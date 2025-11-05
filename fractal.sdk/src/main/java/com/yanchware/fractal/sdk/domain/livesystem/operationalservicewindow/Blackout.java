package com.yanchware.fractal.sdk.domain.livesystem.operationalservicewindow;

import java.time.ZonedDateTime;

/***
 * A blackout interval to exclude from otherwise-allowed time.
 * 
 * @param start
 * @param end
 */
public record Blackout(ZonedDateTime start, ZonedDateTime end) {
  public Blackout {
    if (!end.isAfter(start)) throw new IllegalArgumentException("Blackout end must be after start");
  }
  boolean overlaps(Window w) {
    return !(w.end().isBefore(start) || w.start().isAfter(end));
  }
}
