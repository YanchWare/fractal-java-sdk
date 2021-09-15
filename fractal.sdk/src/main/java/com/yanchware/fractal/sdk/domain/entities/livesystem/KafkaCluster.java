package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSKafka;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KAFKA;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class KafkaCluster extends CaaSKafka implements LiveSystemComponent {
    public static final String TYPE = KAFKA.getId();
    private final static String NAMESPACE_IS_NULL_OR_EMPTY = "KafkaCluster namespace has not been defined and it is required";
    private final static String CONTAINER_PLATFORM_IS_NULL_OR_EMPTY = "KafkaCluster containerPlatform has not been defined and it is required";

    private String namespace;
    private String containerPlatform;

    public static abstract class Builder<T extends KafkaCluster, B extends Builder<T, B>> extends Component.Builder<T, B> {

        public B namespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public B containerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (StringUtils.isEmpty(namespace) || StringUtils.isBlank(namespace)) {
            errors.add(NAMESPACE_IS_NULL_OR_EMPTY);
        }

        if (StringUtils.isEmpty(containerPlatform) || StringUtils.isBlank(containerPlatform)) {
            errors.add(CONTAINER_PLATFORM_IS_NULL_OR_EMPTY);
        }
        return errors;
    }
}
