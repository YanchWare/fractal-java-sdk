package com.yanchware.fractal.sdk.entities.livesystem.caas;

import com.yanchware.fractal.sdk.entities.Validatable;
import lombok.Builder;

import java.util.Collection;
import java.util.List;

@Builder
public class GcpNodePool implements Validatable {
  private int diskSizeGb;
  private int initialNodeCount;
  private GcpMachine machineType;
  private int maxNodeCount;
  private int maxSurge;
  private int minNodeCount;
  private String name;

  @Override
  public Collection<String> validate() {
    return List.of("GKE Node Pool validation has not been implemented");
  }
}
