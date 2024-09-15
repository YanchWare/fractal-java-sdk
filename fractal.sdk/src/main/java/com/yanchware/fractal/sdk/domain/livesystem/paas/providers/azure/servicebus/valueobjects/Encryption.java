package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.servicebus.valueobjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class Encryption {
  private List<KeyVaultProperties> keyVaultProperties;
  private KeySource keySource;
  private Boolean requireInfrastructureEncryption;
}
