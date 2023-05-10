package com.yanchware.fractal.sdk.domain.entities.environment;

import java.time.Duration;
import java.util.List;

public record NsRecord(String name, List<String> nameServers, Duration ttl) implements DnsDataRecord { }
