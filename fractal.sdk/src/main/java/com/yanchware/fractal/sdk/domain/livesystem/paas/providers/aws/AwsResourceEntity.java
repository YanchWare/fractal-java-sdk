package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws;

import com.yanchware.fractal.sdk.domain.livesystem.paas.ResourceEntity;

import java.util.ArrayList;
import java.util.Collection;

public interface AwsResourceEntity extends ResourceEntity {
  static Collection<String> validateAwsResourceEntity(AwsResourceEntity resourceEntity, String entityName) {
    final var REGION_IS_NULL_TEMPLATE = "[AWS %s Validation] Region has not been defined and it is required";

    var errors = new ArrayList<String>();

    if (resourceEntity.getAwsRegion() == null) {
      errors.add(String.format(REGION_IS_NULL_TEMPLATE, entityName));
    }

    return errors;
  }

  AwsRegion getAwsRegion();

  void setAwsRegion(AwsRegion region);
}
