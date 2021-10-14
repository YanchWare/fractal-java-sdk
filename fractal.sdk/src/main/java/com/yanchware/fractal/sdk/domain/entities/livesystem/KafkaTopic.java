package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSKafkaTopic;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.*;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;
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

        //TODO if we agree/enforce these to be passed from KafkaCluster, I would remove them.
        public KafkaTopicBuilder containerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        public KafkaTopicBuilder namespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public KafkaTopicBuilder clusterName(String clusterName) {
            component.setClusterName(clusterName);
            return builder;
        }

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
            component.setVersion(DEFAULT_VERSION);
            return super.build();
        }
    }
}
