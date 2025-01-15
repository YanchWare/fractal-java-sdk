package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.livesystem.paas.ResourceEntity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Generic Azure Resource
 * <p>
 * Important documentation when modifying or implementing this interface:
 * </p>
 * <a href="https://learn.microsoft.com/en-us/azure/azure-resource-manager/management/resource-name-rules">Azure naming limitations</a>
 */
public interface AzureResourceEntity extends ResourceEntity {
  AzureResourceGroup getAzureResourceGroup();
  void setAzureResourceGroup(AzureResourceGroup azureResourceGroup);
  AzureRegion getAzureRegion();
  void setAzureRegion(AzureRegion region);

  static Collection<String> validateAzureResourceEntity(AzureResourceEntity resourceEntity, String entityName) {
    final var REGION_IS_NULL_TEMPLATE = "[Azure %s Validation] Region has not been defined and it is required";

    var errors = new ArrayList<String>();

    if (resourceEntity.getAzureRegion() == null && resourceEntity.getAzureResourceGroup() == null) {
      errors.add(String.format(REGION_IS_NULL_TEMPLATE, entityName));
    }

    return errors;
  }
}
