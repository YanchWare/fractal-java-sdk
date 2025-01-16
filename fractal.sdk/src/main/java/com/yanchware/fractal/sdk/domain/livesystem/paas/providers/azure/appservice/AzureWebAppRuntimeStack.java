package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  property = "operatingSystem"
)
@JsonSubTypes({
  @JsonSubTypes.Type(value = AzureWebAppLinuxRuntimeStack.class, name = "Linux"),
  @JsonSubTypes.Type(value = AzureWebAppWindowsRuntimeStack.class, name = "Windows")
})
public interface AzureWebAppRuntimeStack {
}
