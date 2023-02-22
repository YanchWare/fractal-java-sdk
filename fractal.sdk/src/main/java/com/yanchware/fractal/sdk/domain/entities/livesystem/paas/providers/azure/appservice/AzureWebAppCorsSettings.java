package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;


@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureWebAppCorsSettings {
  private Collection<String> allowedOrigins;
  private Boolean supportCredentials;
}
