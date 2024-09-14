package com.yanchware.fractal.sdk.domain.entities.blueprint.paas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COLUMN_ORIENTED_DBMS;

@ToString(callSuper = true)
public class PaaSColumnOrientedDbms extends Component implements BlueprintComponent {
  public static final String TYPE = PAAS_COLUMN_ORIENTED_DBMS.getId();
}
