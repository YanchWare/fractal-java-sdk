package com.yanchware.fractal.sdk.domain.entities.environment;

import java.time.Duration;
import java.util.List;

public record MxRecord(String name, List<MxRecordData> recordData, Duration ttl) implements DnsDataRecord { }
