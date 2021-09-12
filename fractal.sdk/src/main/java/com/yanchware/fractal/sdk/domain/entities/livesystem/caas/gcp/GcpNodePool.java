package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.gcp;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Collection;

@Builder
public class GcpNodePool implements Validatable {
  private final static String NAME_IS_NULL_OR_EMPTY = "GcpNodePool name has not been defined and it is required";

  private int diskSizeGb;
  private int initialNodeCount;
  private GcpMachine machineType;
  private int maxNodeCount;
  private int maxSurge;
  private int minNodeCount;
  private String name;

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (name == null || name.isEmpty() || name.isBlank()) {
      errors.add(NAME_IS_NULL_OR_EMPTY);
    }

    return errors;
  }
}