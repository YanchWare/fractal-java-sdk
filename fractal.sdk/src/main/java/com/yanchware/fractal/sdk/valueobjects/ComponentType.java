package com.yanchware.fractal.sdk.valueobjects;

public enum ComponentType {
    UNKNOWN("Unknown"),

    CAAS_WORKLOAD("CustomWorkloads.CaaS.Workload"),
    CAAS_K8S_WORKLOAD("CustomWorkloads.CaaS.K8sWorkload"),
    CAAS_DOCUMENT_DB("Storage.CaaS.DocumentDB"),
    CAAS_ELASTIC_DATASTORE("Storage.CaaS.Elastic"),
    CAAS_MESSAGE_BROKER("Messaging.CaaS.Broker"),
    CAAS_KAFKA("Messaging.CaaS.Kafka"),
    CAAS_KAFKA_TOPIC("Messaging.CaaS.KafkaTopic"),
    CAAS_KAFKA_USER("Messaging.CaaS.KafkaUser"),
    CAAS_API_GATEWAY("APIManagement.CaaS.APIGateway"),
    CAAS_AMBASSADOR("APIManagement.CaaS.Ambassador"),
    CAAS_TRAEFIK("APIManagement.CaaS.Traefik"),
    CAAS_MONITORING("Observability.CaaS.Monitoring"),
    CAAS_PROMETHEUS("Observability.CaaS.Prometheus"),
    CAAS_TRACING("Observability.CaaS.Tracing"),
    CAAS_JAEGER("Observability.CaaS.Jaeger"),
    CAAS_LOGGING("Observability.CaaS.Logging"),
    CAAS_ELASTIC_LOGGING("Observability.CaaS.Elastic"),
    CAAS_SERVICE_MESH_SECURITY("Security.CaaS.ServiceMeshSecurity"),
    CAAS_OCELOT("Security.CaaS.Ocelot"),

    PAAS_CONTAINER_PLATFORM("NetworkAndCompute.PaaS.ContainerPlatform"),
    PAAS_KUBERNETES("NetworkAndCompute.PaaS.Kubernetes"),
    PAAS_POSTGRESQL("Storage.PaaS.PostgreSQL"),
    PAAS_POSTGRESQLDB("Storage.PaaS.PostgreSQLDatabase"),
    PAAS_COSMOS_POSTGRESQLDB("Storage.PaaS.CosmosDBPostgreDB"),

    SAAS_UNMANAGED_STORAGE("Storage.SaaS.Unmanaged"),
    SAAS_UNMANAGED_BROKER("Messaging.SaaS.Unmanaged");

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
