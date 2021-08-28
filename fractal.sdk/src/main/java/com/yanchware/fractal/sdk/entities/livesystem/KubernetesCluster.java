package com.yanchware.fractal.sdk.entities.livesystem;

import com.yanchware.fractal.sdk.entities.blueprint.caas.CaaSContainerPlatform;
import com.yanchware.fractal.sdk.entities.blueprint.caas.CaaSService;

import java.util.List;

public abstract class KubernetesCluster extends CaaSContainerPlatform implements LiveSystemComponent {
  protected List<CaaSService> services;

}
