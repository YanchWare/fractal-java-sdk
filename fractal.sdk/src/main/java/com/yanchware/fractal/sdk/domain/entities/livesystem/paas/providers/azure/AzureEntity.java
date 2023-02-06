package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public interface AzureEntity {
  AzureResourceGroup getAzureResourceGroup();
  void setAzureResourceGroup(AzureResourceGroup azureResourceGroup);
  AzureRegion getAzureRegion();
  void setAzureRegion(AzureRegion region);

  Map<String, String> getTags();
  void setTags(Map<String, String> tags);

  String getName();
  void setName(String Name);

  static Collection<String> validateAzureEntity(AzureEntity azureEntity, String entityName) {
    final var REGION_IS_NULL_TEMPLATE = "[Azure %s Validation] Region has not been defined and it is required";

    var errors = new ArrayList<String>();

    if (azureEntity.getAzureRegion() == null && azureEntity.getAzureResourceGroup() == null) {
      errors.add(String.format(REGION_IS_NULL_TEMPLATE, entityName));
    }

    return errors;
  }
}
