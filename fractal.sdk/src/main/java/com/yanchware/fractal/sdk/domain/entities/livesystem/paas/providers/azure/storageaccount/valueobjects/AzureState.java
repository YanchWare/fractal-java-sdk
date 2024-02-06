package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

/**
 * <pre>
 * The state of virtual network rule.
 * </pre>
 */
public final class AzureState extends ExtendableEnum<AzureState> {

  public static final AzureState PROVISIONING = fromString("Provisioning");
  
  public static final AzureState DEPROVISIONING = fromString("Deprovisioning");
  
  public static final AzureState SUCCEEDED = fromString("Succeeded");
  
  public static final AzureState FAILED = fromString("Failed");
  
  public static final AzureState NETWORK_SOURCE_DELETED = fromString("NetworkSourceDeleted");
  

  /**
   * Creates or finds a State from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding State.
   */
  @JsonCreator
  public static AzureState fromString(String name) {
    return fromString(name, AzureState.class);
  }

  /**
   * Gets known State values.
   *
   * @return known State values.
   */
  public static Collection<AzureState> values() {
    return values(AzureState.class);
  }
}
