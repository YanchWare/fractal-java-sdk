package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public class CleanupPolicies extends ExtendableEnum<CleanupPolicies> {
  public static final CleanupPolicies DELETE = fromString("Delete");
  public static final CleanupPolicies COMPACT = fromString("Compact");

  public CleanupPolicies() {
  }

  @JsonCreator
  public static CleanupPolicies fromString(String name) {
    return fromString(name, CleanupPolicies.class);
  }

  public static Collection<CleanupPolicies> values() {
    return values(CleanupPolicies.class);
  }
}
