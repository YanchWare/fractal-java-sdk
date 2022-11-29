package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PUBLIC)
public abstract class CaaSComponent extends Component implements BlueprintComponent {

  private String containerPlatform;
  private String namespace;

  private String getNamespaceIsNullOrEmptyErrorMessage() {
    return String.format("[%s Validation] Namespace has not been defined and it is required", this.getClass().getSimpleName());
  }

  private String getContainerPlatformIsNullOrEmptyErrorMessage() {
    return String.format("[%s Validation] ContainerPlatform defined was either empty or blank and it is required", this.getClass().getSimpleName());
  }
  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (isBlank(namespace)) {
      errors.add(getNamespaceIsNullOrEmptyErrorMessage());
    }

    if (containerPlatform != null && isBlank(containerPlatform)) {
      errors.add(getContainerPlatformIsNullOrEmptyErrorMessage());
    }
    return errors;
  }
}
