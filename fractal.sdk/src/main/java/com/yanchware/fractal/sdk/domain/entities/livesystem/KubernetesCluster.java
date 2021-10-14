package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSContainerPlatform;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSKafka;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KUBERNETES;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class KubernetesCluster extends CaaSContainerPlatform implements LiveSystemComponent {
    public static final String TYPE = KUBERNETES.getId();
    private List<CaaSService> services;
    private List<KafkaCluster> kafkaClusters;

    public KubernetesCluster() {
        super();
        services = new ArrayList<>();
        kafkaClusters = new ArrayList<>();
    }

    public static abstract class Builder<T extends KubernetesCluster, B extends Builder<T, B>> extends Component.Builder<T, B> {

        public B withService(CaaSService service) {
            return withServices(List.of(service));
        }

        public B withServices(Collection<? extends CaaSService> services) {
            if (services == null || services.isEmpty()) {
                return builder;
            }

            if (component.getServices() == null) {
                component.setServices(new ArrayList<>());
            }

            component.getServices().addAll(services);
            return builder;
        }

        public B withKafkaCluster(KafkaCluster kafka) {
            return withKafkaClusters(List.of(kafka));
        }

        public B withKafkaClusters(Collection<? extends KafkaCluster> kafkaClusters) {
            if (kafkaClusters == null || kafkaClusters.isEmpty()) {
                return builder;
            }

            if (component.getKafkaClusters() == null) {
                component.setKafkaClusters(new ArrayList<>());
            }

            kafkaClusters.forEach(cluster -> {
                cluster.setProvider(component.getProvider());
                cluster.setContainerPlatform(component.getId().getValue());
                cluster.getKafkaUsers().forEach(user -> {
                    user.setContainerPlatform(component.getId().getValue());
                    user.setProvider(component.getProvider());
                });
                cluster.getKafkaTopics().forEach(topic -> {
                    topic.setContainerPlatform(component.getId().getValue());
                    topic.setProvider(component.getProvider());
                });
            });
            component.getKafkaClusters().addAll(kafkaClusters);
            return builder;
        }

    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();
        services.stream()
                .map(CaaSService::validate)
                .forEach(errors::addAll);
        kafkaClusters.stream()
                .map(CaaSKafka::validate)
                .forEach(errors::addAll);
        return errors;
    }

}
