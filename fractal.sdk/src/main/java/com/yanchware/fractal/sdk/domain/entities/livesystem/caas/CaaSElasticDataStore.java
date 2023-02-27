package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CAAS_ELASTIC_DATASTORE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class CaaSElasticDataStore extends CaaSSearchImpl implements LiveSystemComponent {
  private final static String ELASTIC_INSTANCES_NEGATIVE_OR_ZERO = "[CaaSElasticDataStore Validation] Elastic Instances defined was either 0 or negative and it needs to be greater than 0";
  private final static String VERSION_IS_BLANK = "[CaaSElasticDataStore Validation] Elastic Version has not been defined and it is required";

  private boolean isKibanaRequired;
  private String elasticVersion;
  private int elasticInstances; //create task in linear

  protected CaaSElasticDataStore() {
    isKibanaRequired = true;
  }

  public static ElasticDataStoreBuilder builder() {
    return new ElasticDataStoreBuilder();
  }

  public static class ElasticDataStoreBuilder extends Builder<CaaSElasticDataStore, ElasticDataStoreBuilder> {
    @Override
    protected CaaSElasticDataStore createComponent() {
      return new CaaSElasticDataStore();
    }

    @Override
    protected ElasticDataStoreBuilder getBuilder() {
      return this;
    }

    /**
     * The name of the container platform where the elastic datastore will be instantiated
     *
     * @param containerPlatform
     */
    public ElasticDataStoreBuilder withContainerPlatform(String containerPlatform) {
      component.setContainerPlatform(containerPlatform);
      return builder;
    }

    /**
     * Namespace where the elastic datastore will be instantiated
     *
     * @param namespace
     */
    public ElasticDataStoreBuilder withNamespace(String namespace) {
      component.setNamespace(namespace);
      return builder;
    }

    /**
     * If true, Kibana will be instantiated part of the elastic datastore solution
     *
     * @param isKibanaRequired
     */
    public ElasticDataStoreBuilder withKibana(boolean isKibanaRequired) {
      component.setKibanaRequired(isKibanaRequired);
      return builder;
    }

    /**
     * Elastic version to be used
     *
     * @param elasticVersion
     */
    public ElasticDataStoreBuilder withElasticVersion(String elasticVersion) {
      component.setElasticVersion(elasticVersion);
      return builder;
    }

    /**
     * Number of elastic datastore instances
     * Must be a value greater than 0
     *
     * @param elasticInstances
     */
    public ElasticDataStoreBuilder withInstances(int elasticInstances) {
      component.setElasticInstances(elasticInstances);
      return builder;
    }

    /**
     * Elastic Datastore storage
     *
     * @param storage
     */
    public ElasticDataStoreBuilder withStorage(String storage) {
      component.setStorage(storage);
      return builder;
    }

    /**
     * Storage class name
     * Default: 'standard'
     *
     * @param storageClassName
     */
    public ElasticDataStoreBuilder withStorageClassName(String storageClassName) {
      component.setStorageClassName(storageClassName);
      return builder;
    }

    /**
     * Number of memory units
     * Default: '8'
     *
     * @param memory
     */
    public ElasticDataStoreBuilder withMemory(int memory) {
      component.setMemory(memory);
      return builder;
    }

    /**
     * Number of CPU units
     * Default: '8'
     *
     * @param cpu
     */
    public ElasticDataStoreBuilder withCpu(int cpu) {
      component.setCpu(cpu);
      return builder;
    }

    @Override
    public CaaSElasticDataStore build() {
      component.setType(CAAS_ELASTIC_DATASTORE);
      return super.build();
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (elasticInstances <= 0) {
      errors.add(ELASTIC_INSTANCES_NEGATIVE_OR_ZERO);
    }

    if (isBlank(elasticVersion)) {
      errors.add(VERSION_IS_BLANK);
    }

    return errors;
  }
}
