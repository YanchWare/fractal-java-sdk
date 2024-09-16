package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_ELASTIC_LOGGING;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * <p>
 * Builder class to represent an Elastic Logging component.
 * </p>
 * <br>
 * <p>
 * For more details about creating an Elastic Logging component using Fractal Cloud check out
 * our <a href="https://fractal.cloud/docs/docs-ht-create-elastic-logging">documentation page</a>
 * </p>
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class CaaSElasticLogging extends CaaSLoggingImpl implements LiveSystemComponent {
  private final static String ELASTIC_INSTANCES_NEGATIVE_OR_ZERO = "[CaaSElasticLogging Validation] Elastic Instances defined was either 0 or negative and it needs to be greater than 0";
  private final static String VERSION_IS_BLANK = "[CaaSElasticLogging Validation] Elastic Version has not been defined and it is required";

  private boolean isApmRequired;
  private boolean isKibanaRequired;

  private String elasticVersion;
  private int elasticInstances;

  protected CaaSElasticLogging() {
    isApmRequired = false;
    isKibanaRequired = true;
  }

  public static ElasticLoggingBuilder builder() {
    return new ElasticLoggingBuilder();
  }

  public static class ElasticLoggingBuilder extends Builder<CaaSElasticLogging, ElasticLoggingBuilder> {
    @Override
    protected CaaSElasticLogging createComponent() {
      return new CaaSElasticLogging();
    }

    @Override
    protected ElasticLoggingBuilder getBuilder() {
      return this;
    }

    /**
     * The id of the container platform where the elastic logging will be instantiated
     *
     * @param containerPlatform
     */
    public ElasticLoggingBuilder withContainerPlatform(String containerPlatform) {
      component.setContainerPlatform(containerPlatform);
      return builder;
    }

    /**
     * Namespace where the elastic logging will be instantiated
     *
     * @param namespace
     */
    public ElasticLoggingBuilder withNamespace(String namespace) {
      component.setNamespace(namespace);
      return builder;
    }

    /**
     * If true, APM will be instantiated part of the elastic logging solution
     * <p>
     * For more details check <a href="https://www.elastic.co/guide/en/apm/guide/current/index.html">APM documentation</a>
     *
     * @param isApmRequired
     */
    public ElasticLoggingBuilder withAPM(boolean isApmRequired) {
      component.setApmRequired(isApmRequired);
      return builder;
    }

    /**
     * If true, Kibana will be instantiated part of the elastic logging solution
     * <p>
     * For more details check <a href="https://www.elastic.co/guide/en/kibana/index.html">Kibana documentation</a>
     *
     * @param isKibanaRequired
     */
    public ElasticLoggingBuilder withKibana(boolean isKibanaRequired) {
      component.setKibanaRequired(isKibanaRequired);
      return builder;
    }

    /**
     * Elastic version to be used
     *
     * @param elasticVersion
     */
    public ElasticLoggingBuilder withElasticVersion(String elasticVersion) {
      component.setElasticVersion(elasticVersion);
      return builder;
    }

    /**
     * Number of elastic datastore instances
     * Must be a value greater than 0
     *
     * @param elasticInstances
     */
    public ElasticLoggingBuilder withInstances(int elasticInstances) {
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
    public ElasticLoggingBuilder withStorage(String storage) {
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
    public ElasticLoggingBuilder withStorageClassName(String storageClassName) {
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
    public ElasticLoggingBuilder withMemory(int memory) {
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
    public ElasticLoggingBuilder withCpu(int cpu) {
      component.setCpu(cpu);
      return builder;
    }

    @Override
    public CaaSElasticLogging build() {
      component.setType(CAAS_ELASTIC_LOGGING);
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
