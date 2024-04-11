package com.yanchware.fractal.sdk.domain.entities.blueprint.paas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_CDN_PROFILE;

@ToString(callSuper = true)
public class PaaSNetworkCdnProfile extends Component implements BlueprintComponent {
  public static final String TYPE = PAAS_CDN_PROFILE.getId();
}
