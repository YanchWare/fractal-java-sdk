package com.yanchware.fractal.sdk.domain.livesystem.operationalservicewindow;

import java.time.Duration;
import java.time.ZonedDateTime;

/** A concrete window occurrence in time. */
public record Window(ZonedDateTime start, ZonedDateTime end) {

  public boolean contains(ZonedDateTime t) {
    return !t.isBefore(start) && t.isBefore(end);
  }

  public Duration duration() {
    return Duration.between(start, end);
  }
  
}
