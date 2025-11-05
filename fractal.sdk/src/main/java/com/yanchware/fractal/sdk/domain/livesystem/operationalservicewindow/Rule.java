package com.yanchware.fractal.sdk.domain.livesystem.operationalservicewindow;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

public interface Rule {
  Stream<Window> enumerate(ZonedDateTime fromIncl, ZonedDateTime toExcl, ZoneId zone);
}
