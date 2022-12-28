package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import java.util.ArrayList;
import java.util.Collection;

public interface AzureEntity {
  AzureResourceGroup getAzureResourceGroup();
  void setAzureResourceGroup(AzureResourceGroup azureResourceGroup);
  AzureRegion getRegion();
  void setRegion(AzureRegion region);
  Collection<String> validate();

  static Collection<String> validateAzureEntity(AzureEntity azureEntity, String entityName) {
    final var REGION_IS_NULL_TEMPLATE = "[Azure %s Validation] Region has not been defined and it is required";

    var errors = new ArrayList<String>();

    if (azureEntity.getRegion() == null && azureEntity.getAzureResourceGroup() == null) {
      errors.add(String.format(REGION_IS_NULL_TEMPLATE, entityName));
    }

    return errors;
  }
}
