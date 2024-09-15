package com.yanchware.fractal.sdk.domain.entities.environment;

import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsSrvRecord;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DnsSrvRecordTest {
  @Test
  public void properName_when_NameIsNull() {
    var record = DnsSrvRecord.builder()
        .withProtocolName("_tls")
        .withService("_sip")
        .withName(null)
        .build();
    
    assertThat(record.getName()).isEqualTo("_sip._tls");
  }

  @Test
  public void properName_when_NameIsSet() {
    var record = DnsSrvRecord.builder()
        .withProtocolName("_tls")
        .withService("_sip")
        .withName("name")
        .build();

    assertThat(record.getName()).isEqualTo("_sip._tls.name");
  }
  
  @Test
  public void validationError_when_ServiceIsNull() {
    assertThatThrownBy(() -> DnsSrvRecord.builder()
        .withService(null)
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Service has not been defined and it is required");
  }

  @Test
  public void validationError_when_ProtocolNameIsNull() {
    assertThatThrownBy(() -> DnsSrvRecord.builder()
        .withService("service")
        .withProtocolName(null)
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("ProtocolName has not been defined and it is required");
  }
}