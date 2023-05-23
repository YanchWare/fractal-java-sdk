package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsARecord;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsCNameRecord;
import com.yanchware.fractal.sdk.utils.SerializationUtils;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;

class DnsZoneConfigTest {

 


  @Test
  public void validationError_when_NameIsNull() throws JsonProcessingException {
    
    var dnsZoneConfig = DnsZoneConfig.builder()
        .withDnsZoneName("targit.cloud")
        .withRecord(DnsARecord.builder()
            .withName("targit-development4.reserved")
            .withTtl(Duration.ofMinutes(5))
            .build())
        .withRecord(DnsCNameRecord.builder()
            .withName("development4")
            .withAlias("targit-development4.reserved.targit.cloud")
            .withTtl(Duration.ofSeconds(30))
            .build())
        .build();
    
    var json = TestUtils.getJsonRepresentation(dnsZoneConfig);
    
    var test = SerializationUtils.deserialize(json, DnsZoneConfig.class);
    
    var dnsZoneName = test.getDnsZoneName();
  }
}