package com.yanchware.fractal.sdk.domain.entities.environment;

import java.time.Duration;
import java.util.List;

public record PtrRecord(String name, List<String> domainNames, Duration ttl) implements DnsDataRecord { }
