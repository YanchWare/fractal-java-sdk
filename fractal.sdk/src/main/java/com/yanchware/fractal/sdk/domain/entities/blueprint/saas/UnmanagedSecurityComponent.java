package com.yanchware.fractal.sdk.domain.entities.blueprint.saas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.SAAS_UNMANAGED_SECURITY;

@ToString(callSuper = true)
public class UnmanagedSecurityComponent extends Component implements BlueprintComponent {
  public static final String TYPE = SAAS_UNMANAGED_SECURITY.getId();
}
