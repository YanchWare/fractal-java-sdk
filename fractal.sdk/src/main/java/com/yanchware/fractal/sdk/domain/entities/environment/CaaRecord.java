package com.yanchware.fractal.sdk.domain.entities.environment;

import java.time.Duration;
import java.util.List;

public record CaaRecord(String name, List<CaaRecordData> recordData, Duration ttl) implements DnsDataRecord { }
