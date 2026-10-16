package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_OPENSEARCH;

/**
 * <p>
 * Builder class to represent an OpenSearch cluster running on Kubernetes (<code>Storage.CaaS.OpenSearch</code>).
 * </p>
 * <br>
 * <p>
 * The security plugin is always on: the agent generates the admin password and the certificates and keeps them in
 * Kubernetes Secrets in the component's namespace. Consumers do not use the admin credential: they link to this
 * component (e.g. {@link CaaSJaeger.JaegerBuilder#linkToOpenSearch(CaaSOpenSearch)} or
 * {@link CaaSKubernetesWorkload.KubernetesWorkloadBuilder#linkToSearch(CaaSOpenSearch, SearchAccess)}) and each gets
 * its own user, scoped to its own index prefix.
 * </p>
 * <p>
 * For more details check the <a href="https://opensearch.org/docs/latest/">OpenSearch documentation</a>.
 * </p>
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class CaaSOpenSearch extends CaaSSearchImpl implements LiveSystemComponent {
  private final static String DEFAULT_NAMESPACE = "opensearch";
  private final static String REPLICAS_NOT_POSITIVE = "[CaaSOpenSearch Validation] Replicas defined was either 0 or negative and it needs to be greater than 0";

  private Integer replicas;
  private Boolean dashboards;
  private Map<String, Object> values;

  protected CaaSOpenSearch() {
    setNamespace(DEFAULT_NAMESPACE);
  }

  public static OpenSearchBuilder builder() {
    return new OpenSearchBuilder();
  }

  public static class OpenSearchBuilder extends Builder<CaaSOpenSearch, OpenSearchBuilder> {
    @Override
    protected CaaSOpenSearch createComponent() {
      return new CaaSOpenSearch();
    }

    @Override
    protected OpenSearchBuilder getBuilder() {
      return this;
    }

    /**
     * The id of the container platform where OpenSearch will be instantiated.
     * Set automatically when the component is added through <code>KubernetesCluster.withDocumentDB(...)</code>.
     *
     * @param containerPlatform id of the Kubernetes cluster
     */
    public OpenSearchBuilder withContainerPlatform(String containerPlatform) {
      component.setContainerPlatform(containerPlatform);
      return builder;
    }

    /**
     * Namespace where OpenSearch will be instantiated.
     * <p>
     * Default: <code>opensearch</code>
     *
     * @param namespace Kubernetes namespace
     */
    public OpenSearchBuilder withNamespace(String namespace) {
      component.setNamespace(namespace);
      return builder;
    }

    /**
     * Number of OpenSearch nodes. Must be greater than 0.
     * <p>
     * Default: <code>3</code>
     *
     * @param replicas number of nodes
     */
    public OpenSearchBuilder withReplicas(int replicas) {
      component.setReplicas(replicas);
      return builder;
    }

    /**
     * Size of the persistent volume of each node, as a Kubernetes quantity (e.g. <code>100Gi</code>).
     * <p>
     * Default: <code>100Gi</code>
     *
     * @param storage volume size per node
     */
    public OpenSearchBuilder withStorage(String storage) {
      component.setStorage(storage);
      return builder;
    }

    /**
     * Storage class of the persistent volumes. When unset the agent resolves the cluster's storage class.
     *
     * @param storageClassName Kubernetes storage class name
     */
    public OpenSearchBuilder withStorageClassName(String storageClassName) {
      component.setStorageClassName(storageClassName);
      return builder;
    }

    /**
     * Memory request and limit of each node, as a Kubernetes quantity (e.g. <code>4Gi</code>).
     *
     * @param memory memory quantity
     */
    public OpenSearchBuilder withMemory(String memory) {
      component.setMemory(memory);
      return builder;
    }

    /**
     * CPU request and limit of each node, as a Kubernetes quantity (e.g. <code>2</code> or <code>500m</code>).
     *
     * @param cpu CPU quantity
     */
    public OpenSearchBuilder withCpu(String cpu) {
      component.setCpu(cpu);
      return builder;
    }

    /**
     * Whether OpenSearch Dashboards is installed next to the cluster.
     * <p>
     * Default: <code>false</code>
     *
     * @param dashboards true to install OpenSearch Dashboards
     */
    public OpenSearchBuilder withDashboards(boolean dashboards) {
      component.setDashboards(dashboards);
      return builder;
    }

    /**
     * Helm values deep-merged into the values the agent computes.
     *
     * @param values Helm values
     * @important.note <strong>Overriding chart values is an operator escape hatch. Fractal Cloud cannot guarantee the
     * functionality of the deployment when these values conflict with the ones the agent sets.</strong>
     */
    public OpenSearchBuilder withValues(Map<String, Object> values) {
      if (values == null) {
        return builder;
      }

      if (component.getValues() == null) {
        component.setValues(new HashMap<>());
      }

      component.getValues().putAll(values);
      return builder;
    }

    @Override
    public CaaSOpenSearch build() {
      component.setType(CAAS_OPENSEARCH);
      return super.build();
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (replicas != null && replicas <= 0) {
      errors.add(REPLICAS_NOT_POSITIVE);
    }

    return errors;
  }
}
