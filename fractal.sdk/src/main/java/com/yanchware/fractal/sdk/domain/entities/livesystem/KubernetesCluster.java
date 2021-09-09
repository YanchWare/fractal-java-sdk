package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSContainerPlatform;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KUBERNETES;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class KubernetesCluster extends CaaSContainerPlatform implements LiveSystemComponent {
  public static final String TYPE = KUBERNETES.getId();
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
