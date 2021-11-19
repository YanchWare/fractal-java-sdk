package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CONTAINERIZED_MESSAGE_BROKER;

@ToString(callSuper = true)
public class CaaSMessageBroker extends Component implements BlueprintComponent {
    public static final String TYPE = CONTAINERIZED_MESSAGE_BROKER.getId();
}
