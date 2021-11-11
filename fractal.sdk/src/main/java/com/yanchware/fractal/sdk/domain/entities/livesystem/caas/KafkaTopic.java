package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSKafkaTopic;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KAFKA_TOPIC;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class KafkaTopic extends CaaSKafkaTopic implements LiveSystemComponent {

    private String containerPlatform;
    private String namespace;
    private String clusterName;
    private ProviderType provider;

    protected KafkaTopic() {
    }

    @Override
    public ProviderType getProvider() {
        return provider;
    }

    public static KafkaTopicBuilder builder() {
        return new KafkaTopicBuilder();
    }

    public static class KafkaTopicBuilder extends Component.Builder<KafkaTopic, KafkaTopicBuilder> {

        @Override
        protected KafkaTopic createComponent() {
            return new KafkaTopic();
        }

        @Override
        protected KafkaTopicBuilder getBuilder() {
            return this;
        }

        @Override
        public KafkaTopic build() {
            component.setType(KAFKA_TOPIC);
            return super.build();
        }
    }
}
