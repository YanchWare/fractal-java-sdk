package com.yanchware.fractal.sdk.domain.entities.blueprint.saas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.SAAS_EXTERNAL_SECURITY;

@ToString(callSuper = true)
public class ExternalSecurityComponent extends Component implements BlueprintComponent {
  public static final String TYPE = SAAS_EXTERNAL_SECURITY.getId();
}
