package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import lombok.ToString;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KAFKA_TOPIC;

@ToString(callSuper = true)
public class CaaSKafkaTopic extends Component implements BlueprintComponent {
    public static final String TYPE = KAFKA_TOPIC.getId();

}
