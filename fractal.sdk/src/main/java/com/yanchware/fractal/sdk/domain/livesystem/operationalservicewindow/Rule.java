package com.yanchware.fractal.sdk.domain.livesystem.operationalservicewindow;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

/**
 * A Rule defines a contract for generating a stream of time intervals (windows)
 * within a specified time range based on specific scheduling logic.
 * Implementing classes can define custom behaviors for how these time intervals
 * are generated and returned.
 * <p/>
 * The time intervals are formed as {@link Window} objects, each representing
 * a start and end time. The start time is inclusive, and the end time is exclusive.
 * <p/>
 * Implementations of this interface should support enumeration of windows within
 * a specified range, ensuring that the generated windows align with the defined
 * scheduling constraints.
 */
public interface Rule {

  /**
   * Enumerates the time intervals (windows) defined by the rule within a specified time range
   * and time zone. The method returns a stream of {@code Window} objects that fall within
   * the given range, adhering to the scheduling logic of the implemented rule.
   *
   * @param fromIncl the start of the range, inclusive; must not be {@code null}
   * @param toExcl the end of the range, exclusive; must not be {@code null}
   * @param zone the time zone in which the windows should be calculated; must not be {@code null}
   * @return a stream of {@code Window} objects representing the enumerated time intervals
   *         within the specified range; an empty stream is returned if no intervals match
   */
  Stream<Window> enumerate(ZonedDateTime fromIncl, ZonedDateTime toExcl, ZoneId zone);
}
