package com.yanchware.fractal.sdk.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ComponentType {
  UNKNOWN("Unknown"),

  CAAS_WORKLOAD("CustomWorkloads.CaaS.Workload"),
  CAAS_K8S_WORKLOAD("CustomWorkloads.CaaS.KubernetesWorkload"),
  CAAS_SEARCH("Storage.CaaS.Search"),
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
  PAAS_COLUMN_ORIENTED_DBMS("Storage.PaaS.ColumnOrientedDbms"),
  PAAS_KUBERNETES("NetworkAndCompute.PaaS.Kubernetes"),
  PAAS_POSTGRESQL_DBMS("Storage.PaaS.PostgreSqlDbms"),
  PAAS_CASANDRA_DBMS("Storage.PaaS.Cassandra"),
  PAAS_POSTGRESQL_DATABASE("Storage.PaaS.PostgreSqlDatabase"),
  PAAS_RELATIONAL_DATABASE("Storage.PaaS.RelationalDatabase"),
  PAAS_RELATIONAL_DBMS("Storage.PaaS.RelationalDbms"),
  PAAS_DATA_STORAGE("Storage.PaaS.DataStorage"),
  PAAS_COSMOS_POSTGRESQL_DATABASE("Storage.PaaS.CosmosDbPostgreSqlDatabase"),
  PAAS_GRAPH_DATABASE("Storage.PaaS.GraphDatabase"),
  PAAS_GRAPH_DBMS("Storage.PaaS.GraphDbms"),
  PAAS_COSMOS_GREMLIN_DATABASE("Storage.PaaS.CosmosDbGremlinDatabase"),
  PAAS_COSMOS_MONGO_DATABASE("Storage.PaaS.CosmosDbMongoDatabase"),
  PAAS_COSMOS_NOSQL_DATABASE("Storage.PaaS.CosmosDbNoSqlDatabase"),
  PAAS_COSMOS_TABLE("Storage.PaaS.CosmosDbTable"),
  PAAS_COSMOS_ACCOUNT("Storage.PaaS.CosmosDbAccount"),
  PAAS_DOCUMENT_DBMS("Storage.PaaS.DocumentDbms"),
  PAAS_DOCUMENT_DATABASE("Storage.PaaS.DocumentDatabase"),
  PAAS_KEY_VALUE_DBMS("Storage.PaaS.KeyValueDbms"),
  PAAS_KEY_VALUE_ENTITY("Storage.PaaS.KeyValueEntity"),
  PAAS_WORKLOAD("CustomWorkloads.PaaS.Workload"),
  PAAS_WORKLOAD_DEPLOYMENT_SLOT("CustomWorkloads.PaaS.WorkloadDeploymentSlot"),
  PAAS_WEBAPP("CustomWorkloads.PaaS.WebApp"),
  PAAS_STORAGE_ACCOUNT("Storage.PaaS.StorageAccount"),
  PAAS_STORAGE_CONTAINER("Storage.PaaS.StorageContainer"),
  PAAS_FILE_SHARE("Storage.PaaS.FileShare"),
  PAAS_MESSAGE_BROKER("Messaging.PaaS.Broker"),
  PAAS_MESSAGE_ENTITY("Messaging.PaaS.Entity"),
  PAAS_SERVICE_BUS_NAMESPACE("Messaging.PaaS.ServiceBus"),
  PAAS_SERVICE_BUS_RELAY("Messaging.PaaS.Relay"),
  PAAS_SERVICE_BUS_QUEUE("Messaging.PaaS.ServiceBusQueue"),
  PAAS_SERVICE_BUS_TOPIC("Messaging.PaaS.ServiceBusTopic"),
  SAAS_UNMANAGED_STORAGE("Storage.SaaS.Unmanaged"),
  SAAS_UNMANAGED_BROKER("Messaging.SaaS.Unmanaged"),
  PAAS_CDN_PROFILE("Network.PaaS.CdnProfile");

  private final String id;

  ComponentType(String id) {
    this.id = id;
  }

  @JsonValue
  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return id;
  }

}
