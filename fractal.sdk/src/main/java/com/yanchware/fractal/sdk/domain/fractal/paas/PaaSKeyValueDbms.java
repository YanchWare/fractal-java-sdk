package com.yanchware.fractal.sdk.domain.fractal.paas;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.fractal.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_KEY_VALUE_DBMS;

@ToString(callSuper = true)
public class PaaSKeyValueDbms extends Component implements BlueprintComponent {
  public static final String TYPE = PAAS_KEY_VALUE_DBMS.getId();
}
