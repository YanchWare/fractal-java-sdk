package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.SERVICE_MESH_SECURITY;

public class CaaSServiceMeshSecurity extends Component implements BlueprintComponent {
    public static final String TYPE = SERVICE_MESH_SECURITY.getId();

}
