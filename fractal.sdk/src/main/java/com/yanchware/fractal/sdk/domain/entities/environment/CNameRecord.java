package com.yanchware.fractal.sdk.domain.entities.environment;

import java.time.Duration;

public record CNameRecord(String name, String alias, Duration ttl) implements DnsRecord {
  protected static final String DNS_RECORD_ALIAS_PARAM_KEY = "alias";
}
