package com.yanchware.fractal.sdk.domain.livesystem;

import com.yanchware.fractal.sdk.domain.livesystem.operationalservicewindow.OperationalServiceWindow;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class OperationalServiceWindowTest {

  private static final ZoneId ZONE_ID = ZoneId.of("Europe/Copenhagen");

  private static OperationalServiceWindow nightly() {
    return OperationalServiceWindow.builder("Nightly", ZONE_ID)
      .withWeeklyRule(EnumSet.of(
          DayOfWeek.MONDAY, DayOfWeek.TUESDAY,
          DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY),
        LocalTime.of(22, 0), LocalTime.of(2, 0)) // cross-midnight
      .build();
  }

  @Test
  void weeklyRule_allowsInsideSameDayAndPastMidnight() {
    var osw = nightly();

    // Tue 2025-06-03 23:00 should be allowed (inside Tue 22:00 → Wed 02:00)
    var t1 = ZonedDateTime.of(2025, 6, 3, 23, 0, 0, 0, ZONE_ID).toInstant();
    assertTrue(osw.isAllowed(t1));

    // Wed 2025-06-04 01:30 still in Tuesday's window (cross-midnight)
    var t2 = ZonedDateTime.of(2025, 6, 4, 1, 30, 0, 0, ZONE_ID).toInstant();
    assertTrue(osw.isAllowed(t2));

    // Wed 2025-06-04 03:00 should be outside
    var t3 = ZonedDateTime.of(2025, 6, 4, 3, 0, 0, 0, ZONE_ID).toInstant();
    assertFalse(osw.isAllowed(t3));
  }

  @Test
  void oneOffRule_allowsOnlyThatDateRange() {
    var osw = OperationalServiceWindow.builder("OneOff", ZONE_ID)
      .withOneOffRule(LocalDate.of(2025, 12, 27), LocalTime.MIDNIGHT, LocalTime.NOON)
      .build();

    var inside = ZonedDateTime.of(2025, 12, 27, 8, 0, 0, 0, ZONE_ID).toInstant();
    var before = ZonedDateTime.of(2025, 12, 26, 23, 59, 0, 0, ZONE_ID).toInstant();
    var after = ZonedDateTime.of(2025, 12, 27, 12, 0, 0, 0, ZONE_ID).toInstant();

    assertTrue(osw.isAllowed(inside));
    assertFalse(osw.isAllowed(before));
    assertFalse(osw.isAllowed(after));
  }

  @Test
  void blackout_excludesEvenIfRuleAllows() {
    var base = nightly();
    var withFreeze = OperationalServiceWindow.builder("Nightly+Freeze", ZONE_ID)
      .withWeeklyRule(EnumSet.of(DayOfWeek.SATURDAY), LocalTime.of(20, 0), LocalTime.of(6, 0))
      .withBlackoutRule(
        ZonedDateTime.of(2025, 12, 20, 0, 0, 0, 0, ZONE_ID),
        ZonedDateTime.of(2025, 12, 27, 0, 0, 0, 0, ZONE_ID))
      .build();

    // Sat 2025-12-20 21:00 would normally be allowed by Sat rule, but is in blackout
    var satInFreeze = ZonedDateTime.of(2025, 12, 20, 21, 0, 0, 0, ZONE_ID).toInstant();
    assertFalse(withFreeze.isAllowed(satInFreeze));

    // Sanity: a normal nightly time outside the freeze is allowed
    var ok = ZonedDateTime.of(2025, 6, 3, 23, 0, 0, 0, ZONE_ID).toInstant();
    assertTrue(base.isAllowed(ok));
  }

  @Test
  void activeBetween_limitsOverallLifetime() {
    var osw = OperationalServiceWindow.builder("ActiveRange", ZONE_ID)
      .withWeeklyRule(EnumSet.of(DayOfWeek.MONDAY), LocalTime.of(22, 0), LocalTime.of(2, 0))
      .activeBetween(
        ZonedDateTime.of(2025, 1, 10, 0, 0, 0, 0, ZONE_ID),
        ZonedDateTime.of(2025, 1, 20, 0, 0, 0, 0, ZONE_ID))
      .build();

    // Mon Jan 06 2025 would match rule, but before activeFrom → not allowed
    var before = ZonedDateTime.of(2025, 1, 6, 23, 0, 0, 0, ZONE_ID).toInstant();
    assertFalse(osw.isAllowed(before));

    // Mon Jan 13 2025 within active range → allowed
    var inside = ZonedDateTime.of(2025, 1, 13, 23, 0, 0, 0, ZONE_ID).toInstant();
    assertTrue(osw.isAllowed(inside));

    // Mon Jan 20 2025 00:00 is at activeUntil (exclusive) → not allowed
    var atUntil = ZonedDateTime.of(2025, 1, 20, 0, 0, 0, 0, ZONE_ID).toInstant();
    assertFalse(osw.isAllowed(atUntil));
  }

  @Test
  void nextWindow_returnsNextConcreteBounds() {
    var osw = nightly();

    // After Wed 2025-06-04 03:00, the next window should start Wed 2025-06-04 22:00
    var after = ZonedDateTime.of(2025, 6, 4, 3, 0, 0, 0, ZONE_ID).toInstant();

    var next = osw.nextWindow(after).orElseThrow();
    assertEquals(ZonedDateTime.of(2025, 6, 4, 22, 0, 0, 0, ZONE_ID), next.start());
    assertEquals(ZonedDateTime.of(2025, 6, 5, 2, 0, 0, 0, ZONE_ID), next.end());
  }

  @Test
  void windowsBetween_mergesOverlappingRules() {
    var osw = OperationalServiceWindow.builder("Overlap", ZONE_ID)
      // Overlapping windows on Tuesdays:
      .withWeeklyRule(EnumSet.of(DayOfWeek.TUESDAY), LocalTime.of(21, 0), LocalTime.of(1, 0))
      .withWeeklyRule(EnumSet.of(DayOfWeek.TUESDAY), LocalTime.of(23, 0), LocalTime.of(2, 0))
      .build();

    var from = ZonedDateTime.of(2025, 6, 3, 0, 0, 0, 0, ZONE_ID); // Tue
    var to = from.plusDays(1);

    var list = osw.windowsBetween(from, to).toList();
    assertEquals(1, list.size(), "overlapping rules should merge into one window");
    var w = list.getFirst();
    assertEquals(ZonedDateTime.of(2025, 6, 3, 21, 0, 0, 0, ZONE_ID), w.start());
    assertEquals(ZonedDateTime.of(2025, 6, 4, 2, 0, 0, 0, ZONE_ID), w.end());
  }

  @Test
  void dstSpringForward_isHandledByZonedDateTime() {
    // EU DST in 2025 starts Sun 2025-03-30 at 02:00 → 03:00 (one hour skipped)
    var osw = OperationalServiceWindow.builder("DST", ZONE_ID)
      .withWeeklyRule(EnumSet.of(DayOfWeek.SUNDAY), LocalTime.of(1, 0), LocalTime.of(4, 0))
      .build();

    var from = ZonedDateTime.of(2025, 3, 30, 0, 0, 0, 0, ZONE_ID);
    var to = from.plusDays(1);

    var list = osw.windowsBetween(from, to).toList();
    assertEquals(1, list.size());
    var w = list.getFirst();

    assertEquals(ZonedDateTime.of(2025, 3, 30, 1, 0, 0, 0, ZONE_ID), w.start());
    assertEquals(ZonedDateTime.of(2025, 3, 30, 4, 0, 0, 0, ZONE_ID), w.end());

    // Duration should be 2 hours because 02:00–03:00 doesn't exist
    assertEquals(Duration.ofHours(2), w.duration());
  }

  @Test
  void currentWindow_presentOnlyWhenInside() {
    var osw = nightly();

    var inside = ZonedDateTime.of(2025, 6, 5, 0, 30, 0, 0, ZONE_ID); // Thu 00:30 (from Wed 22→Thu 02)
    assertTrue(osw.currentWindow(inside).isPresent());

    var outside = ZonedDateTime.of(2025, 6, 5, 3, 0, 0, 0, ZONE_ID);
    assertTrue(osw.currentWindow(outside).isEmpty());
  }

  @Test
  void windowsBetween_iteratesWithinBounds() {
    var osw = nightly();

    var start = ZonedDateTime.of(2025, 6, 2, 0, 0, 0, 0, ZONE_ID); // Monday
    var end = start.plusDays(7);

    var windows = osw.windowsBetween(start, end).toList();
    assertFalse(windows.isEmpty());
    // Expect 4 nightly windows Mon–Thu in that week
    assertEquals(4, windows.size());
  }
}
