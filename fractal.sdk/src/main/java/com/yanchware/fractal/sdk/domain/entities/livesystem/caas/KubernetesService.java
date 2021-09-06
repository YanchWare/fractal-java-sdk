package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSService;

public class KubernetesService extends CaaSService {

  protected KubernetesService() {

  }

  public static KubernetesServiceBuilder builder() {
    return new KubernetesServiceBuilder();
  }

  public static class KubernetesServiceBuilder extends Component.Builder<KubernetesService, KubernetesService.KubernetesServiceBuilder> {

    @Override
    protected KubernetesService createComponent() {
      return new KubernetesService();
    }

    @Override
    protected KubernetesServiceBuilder getBuilder() {
      return this;
    }
  }

}
