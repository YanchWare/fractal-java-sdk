package com.yanchware.fractal.sdk.domain.entities.blueprint.paas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_DOCUMENT_DBMS;

@ToString(callSuper = true)
public class PaaSDocumentDbms extends Component implements BlueprintComponent {
  public static final String TYPE = PAAS_DOCUMENT_DBMS.getId();

}
