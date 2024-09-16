package com.yanchware.fractal.sdk.domain.blueprint.saas;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.SAAS_UNMANAGED_SECURITY;

@ToString(callSuper = true)
public class UnmanagedSecurityComponent extends Component implements BlueprintComponent {
  public static final String TYPE = SAAS_UNMANAGED_SECURITY.getId();
}
