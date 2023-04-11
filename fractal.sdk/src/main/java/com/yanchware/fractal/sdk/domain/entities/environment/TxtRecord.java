package com.yanchware.fractal.sdk.domain.entities.environment;

import java.time.Duration;
import java.util.List;

public record TxtRecord(String name, List<String> values, Duration ttl) implements DnsDataRecord { }
