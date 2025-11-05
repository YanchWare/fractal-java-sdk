package com.yanchware.fractal.sdk.domain.livesystem.operationalservicewindow;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Represents a time interval defined by a start and an end time. The start time is inclusive,
 * and the end time is exclusive.
 * <p/>
 * Instances of this class are immutable and represent a contiguous block of time.
 */
public record Window(ZonedDateTime start, ZonedDateTime end) {

  /**
   * Checks if a given {@link ZonedDateTime} is within the interval represented by this {@code Window}.
   * The start time of the interval is inclusive, and the end time is exclusive.
   *
   * @param t the {@code ZonedDateTime} to check
   * @return {@code true} if the given time is within the interval; {@code false} otherwise
   */
  public boolean contains(ZonedDateTime t) {
    return !t.isBefore(start) && t.isBefore(end);
  }

  /**
   * Calculates the duration of the time interval represented by this {@code Window}.
   * The duration is computed as the amount of time between the start time (inclusive)
   * and the end time (exclusive) of the interval.
   *
   * @return a {@link Duration} representing the length of the time interval
   */
  public Duration duration() {
    return Duration.between(start, end);
  }

}
