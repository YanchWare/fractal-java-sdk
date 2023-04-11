package com.yanchware.fractal.sdk.domain.entities.environment;

import java.util.List;
import java.util.Map;

public record DnsZone(
  String name,
  boolean isPrivate,
  List<DnsRecord> records,
  Map<String, Object> parameters) { }
