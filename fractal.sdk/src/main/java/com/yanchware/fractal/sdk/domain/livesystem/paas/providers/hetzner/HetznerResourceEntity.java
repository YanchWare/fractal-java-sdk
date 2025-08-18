package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.hetzner;

import com.yanchware.fractal.sdk.domain.livesystem.paas.ResourceEntity;

import java.util.ArrayList;
import java.util.Collection;

public interface HetznerResourceEntity extends ResourceEntity {
  HetznerRegion getHetznerRegion();
  void setHetznerRegion(HetznerRegion region);

  static Collection<String> validateHetznerResourceEntity(HetznerResourceEntity resourceEntity, String entityName) {
    final var REGION_IS_NULL_TEMPLATE = "[Hetzner %s Validation] Region has not been defined and it is required";

    var errors = new ArrayList<String>();

    if (resourceEntity.getHetznerRegion() == null) {
      errors.add(String.format(REGION_IS_NULL_TEMPLATE, entityName));
    }

    return errors;
  }
}
