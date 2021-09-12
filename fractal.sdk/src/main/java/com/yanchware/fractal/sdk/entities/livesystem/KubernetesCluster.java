package com.yanchware.fractal.sdk.entities.livesystem;

import com.yanchware.fractal.sdk.entities.Component;
import com.yanchware.fractal.sdk.entities.blueprint.caas.CaaSContainerPlatform;
import com.yanchware.fractal.sdk.entities.blueprint.caas.CaaSService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class KubernetesCluster extends CaaSContainerPlatform implements LiveSystemComponent {
  protected List<CaaSService> services;

  public KubernetesCluster() {
    super();
    services = new ArrayList<>();
  }

  public static abstract class Builder<T extends KubernetesCluster, B extends Builder<T, B>> extends Component.Builder<T, B> {

    public B service(CaaSService service) {
      if (component.getServices() == null ){
        component.setServices(new ArrayList<>());
      }
      component.getServices().add(service);
      return builder;
    }

    public B services(Collection<? extends CaaSService> services) {
      if (component.getServices() == null ){
        component.setServices(new ArrayList<>());
      }
      component.getServices().addAll(services);
      return builder;
    }

  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    services.stream()
      .map(CaaSService::validate)
      .forEach(errors::addAll);
    return errors;
  }

}