package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.servicebus.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public final class EndPointProvisioningState extends ExtendableEnum<EndPointProvisioningState> {
  public static final EndPointProvisioningState CREATING = fromString("Creating");
  public static final EndPointProvisioningState UPDATING = fromString("Updating");
  public static final EndPointProvisioningState DELETING = fromString("Deleting");
  public static final EndPointProvisioningState SUCCEEDED = fromString("Succeeded");
  public static final EndPointProvisioningState CANCELED = fromString("Canceled");
  public static final EndPointProvisioningState FAILED = fromString("Failed");

  public EndPointProvisioningState() {
  }

  @JsonCreator
  public static EndPointProvisioningState fromString(String name) {
    return fromString(name, EndPointProvisioningState.class);
  }

  public static Collection<EndPointProvisioningState> values() {
    return values(EndPointProvisioningState.class);
  }
}
