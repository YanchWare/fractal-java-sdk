package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.oci;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.ResourceEntity;

import java.util.ArrayList;
import java.util.Collection;

public interface OciResourceEntity extends ResourceEntity {
  Compartment getCompartment();
  void setCompartment(Compartment compartment);
  OciRegion getOciRegion();
  void setOciRegion(OciRegion region);

  static Collection<String> validateOciResourceEntity(OciResourceEntity resourceEntity, String entityName) {
    final var REGION_IS_NULL_TEMPLATE = "[OCI %s Validation] Region has not been defined and it is required";

    var errors = new ArrayList<String>();

    if (resourceEntity.getOciRegion() == null) {
      errors.add(String.format(REGION_IS_NULL_TEMPLATE, entityName));
    }

    return errors;
  }
}
