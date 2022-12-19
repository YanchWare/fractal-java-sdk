package com.yanchware.fractal.sdk.domain.entities.blueprint.paas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_RELATIONAL_DATABASE;

@ToString(callSuper = true)
public class PaaSRelationalDatabase extends Component implements BlueprintComponent {
  public static final String TYPE = PAAS_RELATIONAL_DATABASE.getId();

}
