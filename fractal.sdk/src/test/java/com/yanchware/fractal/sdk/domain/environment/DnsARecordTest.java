package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsARecord;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsARecord.A_DNS_RECORD_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DnsARecordTest {

  @Test
  public void validationError_when_NameIsNull() {
    assertThatThrownBy(() -> DnsARecord.builder().withName(null).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("[DnsARecord Validation] The Name must contain between 1 and 63 characters");
  }

  @Test
  public void dnsARecordWithAtSign() {
    var recordName = "@";
    var duration = Duration.ofMinutes(1);
    var ipV4Address = "1.1.1.1";
    var ipV4Address2 = "2.2.2.2";

    var record = DnsARecord.builder()
        .withName(recordName)
        .withTtl(duration)
        .withIpV4Address(ipV4Address)
        .withIpV4Address(ipV4Address2)
        .build();

    assertThat(record.getName()).isEqualTo(recordName);
    assertThat(record.getTtl()).isEqualTo(duration);
    assertThat(record.getIpV4Addresses().size()).isEqualTo(2);
    assertThat(record.getIpV4Addresses())
        .containsExactly(ipV4Address, ipV4Address2);

    var jsonRecord = TestUtils.getJsonNodeRepresentation(record);
    assertThat(jsonRecord).isNotNull();
    assertThat(jsonRecord.has("@type")).isNotNull();
    assertEquals(A_DNS_RECORD_TYPE, jsonRecord.get("@type").textValue());
  }
  
  @Test
  public void dnsARecordHasAllFields() {
    var recordName = "ARecord";
    var duration = Duration.ofMinutes(1);
    var ipV4Address = "1.1.1.1";
    var ipV4Address2 = "2.2.2.2";

    var record = DnsARecord.builder()
        .withName(recordName)
        .withTtl(duration)
        .withIpV4Address(ipV4Address)
        .withIpV4Address(ipV4Address2)
        .build();

    assertThat(record.getName()).isEqualTo(recordName);
    assertThat(record.getTtl()).isEqualTo(duration);
    assertThat(record.getIpV4Addresses().size()).isEqualTo(2);
    assertThat(record.getIpV4Addresses())
        .containsExactly(ipV4Address, ipV4Address2);

    var jsonRecord = TestUtils.getJsonNodeRepresentation(record);
    assertThat(jsonRecord).isNotNull();
    assertThat(jsonRecord.has("@type")).isNotNull();
    assertEquals(A_DNS_RECORD_TYPE, jsonRecord.get("@type").textValue());
  }
}