package com.yanchware.fractal.sdk.entities.livesystem.caas;

import com.yanchware.fractal.sdk.entities.livesystem.KubernetesCluster;

public class GoogleKubernetesEngine extends KubernetesCluster {

  public static GoogleKubernetesEngineBuilder builder() {
    return new GoogleKubernetesEngineBuilder();
  }

  public static class GoogleKubernetesEngineBuilder extends ComponentBuilder {

    public GoogleKubernetesEngineBuilder service() {
      return this;
    }

  }

}
