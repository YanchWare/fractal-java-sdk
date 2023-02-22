package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

public final class AzureManagedPipelineMode extends ExtendableEnum<AzureManagedPipelineMode> {

  public static final AzureManagedPipelineMode CLASSIC = fromString("Classic");
  public static final AzureManagedPipelineMode INTEGRATED = fromString("Integrated");

  public AzureManagedPipelineMode() {
  }

  @JsonCreator
  public static AzureManagedPipelineMode fromString(String name) {
    return fromString(name, AzureManagedPipelineMode.class);
  }

  public static Collection<AzureManagedPipelineMode> values() {

    return values(AzureManagedPipelineMode.class);
  }
}
