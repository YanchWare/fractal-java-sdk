package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KUBERNETES;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class KubernetesCluster extends CaaSContainerPlatform implements LiveSystemComponent {
    private List<KubernetesWorkload> services;
    private List<KafkaCluster> kafkaClusters;
    private List<Prometheus> prometheusInstances;
    private List<Ambassador> ambassadorInstances;

    public KubernetesCluster() {
        super();
        services = new ArrayList<>();
        kafkaClusters = new ArrayList<>();
        prometheusInstances = new ArrayList<>();
        ambassadorInstances = new ArrayList<>();
    }

    public static abstract class Builder<T extends KubernetesCluster, B extends Builder<T, B>> extends Component.Builder<T, B> {

        public B withService(KubernetesWorkload service) {
            return withServices(List.of(service));
        }

        public B withServices(Collection<? extends KubernetesWorkload> services) {
            if (isBlank(services)) {
                return builder;
            }

            if (component.getServices() == null) {
                component.setServices(new ArrayList<>());
            }

            services.forEach(svc -> {
                svc.setProvider(component.getProvider());
                svc.setContainerPlatform(component.getId().getValue());
                svc.getDependencies().add(component.getId());
            });
            component.getServices().addAll(services);
            return builder;
        }

        public B withKafkaCluster(KafkaCluster kafka) {
            return withKafkaClusters(List.of(kafka));
        }

        public B withKafkaClusters(Collection<? extends KafkaCluster> kafkaClusters) {
            if (isBlank(kafkaClusters)) {
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
                cluster.getDependencies().add(component.getId());
            });
            component.getKafkaClusters().addAll(kafkaClusters);
            return builder;
        }

        public B withPrometheus(Prometheus prometheus) {
            return withPrometheusInstances(List.of(prometheus));
        }

        public B withPrometheusInstances(Collection<? extends Prometheus> prometheusInstances) {
            if (isBlank(prometheusInstances)) {
                return builder;
            }

            if (component.getPrometheusInstances() == null) {
                component.setPrometheusInstances(new ArrayList<>());
            }

            prometheusInstances.forEach(prometheus -> {
                prometheus.setProvider(component.getProvider());
                prometheus.setContainerPlatform(component.getId().getValue());
                prometheus.getDependencies().add(component.getId());
            });
            component.getPrometheusInstances().addAll(prometheusInstances);
            return builder;
        }

        public B withAmbassador(Ambassador ambassador) {
            return withAmbassadorInstances(List.of(ambassador));
        }

        public B withAmbassadorInstances(Collection<? extends Ambassador> ambassadorInstances) {
            if (isBlank(ambassadorInstances)) {
                return builder;
            }

            if (component.getAmbassadorInstances() == null) {
                component.setAmbassadorInstances(new ArrayList<>());
            }

            ambassadorInstances.forEach(ambassador -> {
                ambassador.setProvider(component.getProvider());
                ambassador.setContainerPlatform(component.getId().getValue());
                ambassador.getDependencies().add(component.getId());
            });
            component.getAmbassadorInstances().addAll(ambassadorInstances);
            return builder;
        }

        @Override
        public T build() {
            component.setType(KUBERNETES);
            return super.build();
        }

    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();
        services.stream()
                .map(CaaSWorkload::validate)
                .forEach(errors::addAll);
        kafkaClusters.stream()
                .map(CaaSKafka::validate)
                .forEach(errors::addAll);
        prometheusInstances.stream()
                .map(CaaSPrometheus::validate)
                .forEach(errors::addAll);
        ambassadorInstances.stream()
                .map(CaaSAPIGateway::validate)
                .forEach(errors::addAll);
        return errors;
    }

}
