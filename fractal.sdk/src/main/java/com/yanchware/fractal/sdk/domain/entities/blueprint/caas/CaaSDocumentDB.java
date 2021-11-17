package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CAAS_DOCUMENT_DB;

public class CaaSDocumentDB extends Component implements BlueprintComponent {
    public static final String TYPE = CAAS_DOCUMENT_DB.getId();

}
