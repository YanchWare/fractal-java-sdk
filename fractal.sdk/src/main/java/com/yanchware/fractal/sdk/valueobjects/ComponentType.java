package com.yanchware.fractal.sdk.valueobjects;

public enum ComponentType {
    UNKNOWN("Unknown"),
    CAAS_WORKLOAD("NetworkAndCompute.CaaS.Workload"),
    CONTAINER_PLATFORM("NetworkAndCompute.CaaS.ContainerPlatform"),
    K8S_WORKLOAD("NetworkAndCompute.CaaS.K8sWorkload"),
    KUBERNETES("NetworkAndCompute.PaaS.Kubernetes"),
    POSTGRESQL("Storage.PaaS.PostgreSQL"),
    POSTGRESQLDB("Storage.PaaS.PostgreSQLDatabase"),
    CAAS_DOCUMENT_DB("Storage.CaaS.DocumentDB"),
    ELASTIC_DATASTORE("Storage.CaaS.Elastic"),
    CONTAINERIZED_MESSAGE_BROKER("Messaging.CaaS.Broker"),
    KAFKA("Messaging.CaaS.Kafka"),
    KAFKA_TOPIC("Messaging.CaaS.KafkaTopic"),
    KAFKA_USER("Messaging.CaaS.KafkaUser"),
    API_GATEWAY("APIManagement.CaaS.APIGateway"),
    AMBASSADOR("APIManagement.CaaS.Ambassador"),
    TRAEFIK("APIManagement.CaaS.Traefik"),
    CAAS_MONITORING("Observability.CaaS.Monitoring"),
    PROMETHEUS("Observability.CaaS.Prometheus"),
    CAAS_TRACING("Observability.CaaS.Tracing"),
    JAEGER("Observability.CaaS.Jaeger"),
    CAAS_LOGGING("Observability.CaaS.Logging"),
    ELASTIC_LOGGING("Observability.CaaS.Elastic"),
    SERVICE_MESH_SECURITY("Security.CaaS.ServiceMeshSecurity"),
    OCELOT("Security.CaaS.Ocelot");

    private final String id;

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
