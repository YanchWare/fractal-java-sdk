package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.servicebus.valueobjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class KeyVaultProperties {
  private String keyName;
  private String keyVaultUri;
  private String keyVersion;
  private String identity;
}
