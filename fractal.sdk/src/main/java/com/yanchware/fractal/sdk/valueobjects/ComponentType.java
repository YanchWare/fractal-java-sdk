package com.yanchware.fractal.sdk.valueobjects;

public enum ComponentType {
    UNKNOWN("Unknown"),
    CONTAINER_PLATFORM("NetworkAndCompute.CaaS.ContainerPlatform"),
    KAFKA("Messaging.CaaS.Broker"),
    KAFKA_TOPIC("Messaging.CaaS.KafkaTopic"),
    KAFKA_USER("Messaging.CaaS.KafkaUser"),
    KUBERNETES("NetworkAndCompute.CaaS.Kubernetes"),
    POSTGRESQL("DataStorage.PaaS.PostgreSQL"),
    POSTGRESQLDB("DataStorage.PaaS.PostgreSQLDatabase");

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
