package com.yanchware.fractal.sdk.domain.entities.environment;

import java.time.Duration;
import java.util.List;

public record SrvRecord(String name, List<SrvRecordData> recordData, Duration ttl) implements DnsDataRecord { }
