package com.yanchware.fractal.sdk.domain.livesystem.operationalservicewindow;

import java.time.ZonedDateTime;

/**
 * Represents a blackout period defined by a start and an end time,
 * indicating a time range during which operations or services may be unavailable.
 * Instances of this class are immutable and represent a contiguous time interval.
 * The start time is inclusive, while the end time is exclusive.
 *
 * @param start the start time of the blackout period, inclusive; must not be {@code null}
 * @param end   the end time of the blackout period, exclusive; must not be {@code null};
 *              must be strictly after {@code start}
 * @throws IllegalArgumentException if {@code end} is not after {@code start}
 */
public record Blackout(ZonedDateTime start, ZonedDateTime end) {
  public Blackout {
    if (!end.isAfter(start)) {
      throw new IllegalArgumentException("Blackout end must be after start");
    }
  }

  boolean overlaps(Window w) {
    return !(w.end().isBefore(start) || w.start().isAfter(end));
  }
}
