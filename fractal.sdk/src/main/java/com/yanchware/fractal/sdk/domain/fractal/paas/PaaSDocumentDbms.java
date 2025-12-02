package com.yanchware.fractal.sdk.domain.fractal.paas;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.fractal.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_DOCUMENT_DBMS;

@ToString(callSuper = true)
public class PaaSDocumentDbms extends Component implements BlueprintComponent {
  public static final String TYPE = PAAS_DOCUMENT_DBMS.getId();

}
