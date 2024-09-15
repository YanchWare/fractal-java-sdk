package com.yanchware.fractal.sdk.domain.entities.environment;

import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsAaaaRecord;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.yanchware.fractal.sdk.domain.entities.environment.DnsRecordConstants.AAAA_DNS_RECORD_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DnsAaaaRecordTest {
  @Test
  public void validationError_when_NameIsNull() {
    assertThatThrownBy(() -> DnsAaaaRecord.builder().withName(null).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("[DnsAaaaRecord Validation] The Name must contain between 1 and 63 characters");
  }

  @Test
  public void dnsAaaaRecordHasAllFields() {
    var recordName = "AaaaRecord";
    var duration = Duration.ofMinutes(10);
    var ipV6Address = "2001:db8:3333:4444:CCCC:DDDD:EEEE:FFFF";

    var record = DnsAaaaRecord.builder()
        .withName(recordName)
        .withTtl(duration)
        .withIpV6Address(ipV6Address)
            .build();

    assertThat(record.getName()).isEqualTo(recordName);
    assertThat(record.getTtl()).isEqualTo(duration);
    var optionalIpAddress = record.getIpV6Addresses().stream().findFirst();
    assertTrue(optionalIpAddress.isPresent());
    assertEquals(ipV6Address, optionalIpAddress.get());

    var jsonRecord = TestUtils.getJsonNodeRepresentation(record);
    assertThat(jsonRecord).isNotNull();
    assertThat(jsonRecord.has("@type")).isNotNull();
    assertEquals(AAAA_DNS_RECORD_TYPE, jsonRecord.get("@type").textValue());
  }
}