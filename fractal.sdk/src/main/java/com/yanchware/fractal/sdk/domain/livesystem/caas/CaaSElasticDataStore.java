package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_ELASTIC_DATASTORE;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * <p>
 * Builder class to represent an Elastic Datastore component.
 * </p>
 * <br>
 * <p>
 * For more details about creating an Elastic Datastore component using Fractal Cloud check out
 * our <a href="https://fractal.cloud/docs/docs-ht-create-elastic-datastore">documentation page</a>
 * </p>
 */
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
     * The id of the container platform where the elastic datastore will be instantiated
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
     * Storage that will be used for PersistentVolumeClaim
     * <p>
     * For more details check <a href="https://www.elastic.co/guide/en/cloud-on-k8s/master/k8s-volume-claim-templates.html">Elastic documentation</a>
     *
     * @param storage
     */
    public ElasticDataStoreBuilder withStorage(String storage) {
      component.setStorage(storage);
      return builder;
    }

    /**
     * Storage class name that will be used for PersistentVolumeClaim
     * <p>
     * Default: 'standard'
     * <p>
     * For more details check <a href="https://www.elastic.co/guide/en/cloud-on-k8s/master/k8s-volume-claim-templates.html">Elastic documentation</a>
     *
     * @param storageClassName
     */
    public ElasticDataStoreBuilder withStorageClassName(String storageClassName) {
      component.setStorageClassName(storageClassName);
      return builder;
    }

    /**
     * Amount of memory, in gigabytes, that will be used for requests and limits
     * Default: '8'
     * <p>
     * For more details check <a href="https://www.elastic.co/guide/en/cloud-on-k8s/current/k8s-managing-compute-resources.html#k8s-compute-resources-elasticsearch">Elastic documentation</a>
     *
     * @param memory
     */
    public ElasticDataStoreBuilder withMemory(int memory) {
      component.setMemory(memory);
      return builder;
    }

    /**
     * CPU resources that will be used for requests and limits
     * Default: '8'
     * <p>
     * For more details check <a href="https://www.elastic.co/guide/en/cloud-on-k8s/current/k8s-managing-compute-resources.html#k8s-compute-resources-elasticsearch">Elastic documentation</a>
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
