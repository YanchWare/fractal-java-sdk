package com.yanchware.fractal.sdk.valueobjects;

public enum ComponentType {
    UNKNOWN("Unknown"),
    API_GATEWAY("APIManagement.CaaS.APIGateway"),
    AMBASSADOR("APIManagement.CaaS.Ambassador"),
    CAAS_WORKLOAD("NetworkAndCompute.CaaS.Workload"),
    K8S_WORKLOAD("NetworkAndCompute.CaaS.K8sWorkload"),
    CONTAINER_PLATFORM("NetworkAndCompute.CaaS.ContainerPlatform"),
    CONTAINERIZED_MESSAGE_BROKER("Messaging.CaaS.Broker"),
    KAFKA("Messaging.CaaS.Kafka"),
    KAFKA_TOPIC("Messaging.CaaS.KafkaTopic"),
    KAFKA_USER("Messaging.CaaS.KafkaUser"),
    KUBERNETES("NetworkAndCompute.CaaS.Kubernetes"),
    POSTGRESQL("DataStorage.PaaS.PostgreSQL"),
    POSTGRESQLDB("DataStorage.PaaS.PostgreSQLDatabase"),
    CAAS_MONITORING("Observability.CaaS.Monitoring"),
    PROMETHEUS("Observability.CaaS.Prometheus");

    private String id;

    ComponentType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

}
