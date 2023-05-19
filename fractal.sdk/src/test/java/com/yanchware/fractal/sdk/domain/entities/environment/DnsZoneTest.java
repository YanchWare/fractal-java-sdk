package com.yanchware.fractal.sdk.domain.entities.environment;

import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DnsZoneTest {
  @Test
  public void validationError_when_NameIsNull() {
    assertThatThrownBy(() -> DnsZone.builder().withName(null).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("[DnsZone Validation] The name must contain no more than 253 characters");
  }

  @Test
  public void validationError_when_NameWithoutPeriod() {
    assertThatThrownBy(() -> DnsZone.builder().withName("NameWithoutPeriod").build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("[DnsZone Validation] The name must contain no more than 253 characters");
  }

  @Test
  public void validationError_when_NameTooLong() {
    assertThatThrownBy(() -> DnsZone.builder()
        .withName("NameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong")
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("[DnsZone Validation] The name must contain no more than 253 characters");
  }

  @Test
  public void noValidationErrors_when_NameIs254CharsWithTrailingPeriod() {
    assertThat(DnsZone.builder()
        .withName("NameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLongNameTooLong.NameTooLong.")
        .build()
        .validate()).isEmpty();
  }

  @Test
  public void noValidationErrors_when_NameEndsWithPeriod() {
    assertThat(DnsZone.builder()
        .withName("fractal.cloud.")
        .build()
        .validate()).isEmpty();
  }

  @Test
  public void dnsZoneHasAllFields() {
    var dnsZoneName = "fractal.cloud";

    var isDnsZonePrivate = true;

    var dnsRecords = List.of(
        new AaaaRecord("aaaaRecord", "1.2.3.4", Duration.ofMinutes(1)),
        new PtrRecord("name", List.of(""), Duration.ofMinutes(1))
    );

    var parametersMap = new HashMap<String, Object>();
    parametersMap.put("resourceGroup", "rg-dns-zone");
    parametersMap.put("subscriptionId", "/subscription/id");

    var dnsZone = DnsZone
        .builder()
        .withName(dnsZoneName)
        .withRecords(dnsRecords)
        .withParameters(parametersMap)
        .isPrivate(isDnsZonePrivate)
        .build();

    assertThat(dnsZone.getName()).isEqualTo(dnsZoneName);
    assertThat(dnsZone.isPrivate()).isEqualTo(isDnsZonePrivate);
    assertThat(dnsZone.getRecords()).isEqualTo(dnsRecords);
    assertThat(dnsZone.getParameters()).isEqualTo(parametersMap);

    var jsonEnvironment = TestUtils.getJsonRepresentation(dnsZone);
    assertThat(jsonEnvironment).isNotBlank();
  }

}