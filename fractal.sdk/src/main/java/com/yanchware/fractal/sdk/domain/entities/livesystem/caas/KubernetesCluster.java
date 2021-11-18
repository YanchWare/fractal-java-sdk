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
    private List<KubernetesWorkload> kubernetesWorkloads;
    private List<KafkaCluster> kafkaClusters;
    private List<Prometheus> prometheusInstances;
    private List<Ambassador> ambassadorInstances;
    private List<Ocelot> ocelotInstances;
    private List<Jaeger> jaegerInstances;
    private List<ElasticLogging> elasticLoggingInstances;
    private List<ElasticDataStore> elasticDataStoreInstances;

    public KubernetesCluster() {
        super();
        kubernetesWorkloads = new ArrayList<>();
        kafkaClusters = new ArrayList<>();
        prometheusInstances = new ArrayList<>();
        ambassadorInstances = new ArrayList<>();
        ocelotInstances = new ArrayList<>();
        jaegerInstances = new ArrayList<>();
        elasticLoggingInstances = new ArrayList<>();
        elasticDataStoreInstances = new ArrayList<>();
    }

    public static abstract class Builder<T extends KubernetesCluster, B extends Builder<T, B>> extends Component.Builder<T, B> {

        public B withWorkload(KubernetesWorkload workload) {
            return withWorkloads(List.of(workload));
        }

        public B withWorkloads(Collection<? extends KubernetesWorkload> workloads) {
            if (isBlank(workloads)) {
                return builder;
            }

            if (component.getKubernetesWorkloads() == null) {
                component.setKubernetesWorkloads(new ArrayList<>());
            }

            workloads.forEach(workload -> {
                workload.setProvider(component.getProvider());
                workload.setContainerPlatform(component.getId().getValue());
                workload.getDependencies().add(component.getId());
            });
            component.getKubernetesWorkloads().addAll(workloads);
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

        public B withOcelot(Ocelot ocelot) {
            return withOcelot(List.of(ocelot));
        }

        public B withOcelot(Collection<? extends Ocelot> ocelotInstances) {
            if (isBlank(ocelotInstances)) {
                return builder;
            }
            if (component.getOcelotInstances() == null) {
                component.setOcelotInstances(new ArrayList<>());
            }
            ocelotInstances.forEach(ocelot -> {
                ocelot.setProvider(component.getProvider());
                ocelot.setContainerPlatform(component.getId().getValue());
                ocelot.getDependencies().add(component.getId());
            });
            component.getOcelotInstances().addAll(ocelotInstances);
            return builder;
        }

        public B withJaeger(Jaeger jaeger) {
            return withJaeger(List.of(jaeger));
        }

        public B withJaeger(Collection<? extends Jaeger> jaegerInstances) {
            if (isBlank(jaegerInstances)) {
                return builder;
            }
            if (component.getJaegerInstances() == null) {
                component.setJaegerInstances(new ArrayList<>());
            }
            jaegerInstances.forEach(jaeger -> {
                jaeger.setProvider(component.getProvider());
                jaeger.setContainerPlatform(component.getId().getValue());
                jaeger.getDependencies().add(component.getId());
            });
            component.getJaegerInstances().addAll(jaegerInstances);
            return builder;
        }

        public B withElasticLogging(ElasticLogging elasticLogging) {
            return withElasticLogging(List.of(elasticLogging));
        }

        public B withElasticLogging(Collection<? extends ElasticLogging> elasticLoggingInstances) {
            if (isBlank(elasticLoggingInstances)) {
                return builder;
            }
            if (component.getElasticLoggingInstances() == null) {
                component.setElasticLoggingInstances(new ArrayList<>());
            }
            elasticLoggingInstances.forEach(jaeger -> {
                jaeger.setProvider(component.getProvider());
                jaeger.setContainerPlatform(component.getId().getValue());
                jaeger.getDependencies().add(component.getId());
            });
            component.getElasticLoggingInstances().addAll(elasticLoggingInstances);
            return builder;
        }

        public B withElasticDataStore(ElasticDataStore elasticDataStore) {
            return withElasticDataStore(List.of(elasticDataStore));
        }

        public B withElasticDataStore(Collection<? extends ElasticDataStore> elasticDataStoreInstances) {
            if (isBlank(elasticDataStoreInstances)) {
                return builder;
            }
            if (component.getElasticDataStoreInstances() == null) {
                component.setElasticDataStoreInstances(new ArrayList<>());
            }
            elasticDataStoreInstances.forEach(jaeger -> {
                jaeger.setProvider(component.getProvider());
                jaeger.setContainerPlatform(component.getId().getValue());
                jaeger.getDependencies().add(component.getId());
            });
            component.getElasticDataStoreInstances().addAll(elasticDataStoreInstances);
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
        kubernetesWorkloads.stream()
                .map(CaaSWorkload::validate)
                .forEach(errors::addAll);
        kafkaClusters.stream()
                .map(KafkaCluster::validate)
                .forEach(errors::addAll);
        prometheusInstances.stream()
                .map(CaaSMonitoring::validate)
                .forEach(errors::addAll);
        ambassadorInstances.stream()
                .map(CaaSAPIGateway::validate)
                .forEach(errors::addAll);
        ocelotInstances.stream()
                .map(CaaSServiceMeshSecurity::validate)
                .forEach(errors::addAll);
        jaegerInstances.stream()
                .map(CaaSTracing::validate)
                .forEach(errors::addAll);
        elasticLoggingInstances.stream()
                .map(CaaSLogging::validate)
                .forEach(errors::addAll);
        elasticDataStoreInstances.stream()
                .map(CaaSDocumentDB::validate)
                .forEach(errors::addAll);
        return errors;
    }

}
