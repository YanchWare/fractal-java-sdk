package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CAAS_DOCUMENT_DB;

@ToString(callSuper = true)
public class CaaSDocumentDB extends CaaSComponent implements BlueprintComponent {
    public static final String TYPE = CAAS_DOCUMENT_DB.getId();
}
