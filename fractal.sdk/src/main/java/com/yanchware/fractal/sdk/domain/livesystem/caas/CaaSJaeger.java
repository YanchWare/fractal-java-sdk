package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.values.ComponentId;
import lombok.ToString;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_JAEGER;

/**
 * <p>
 * Builder class to represent a Jaeger tracing component running on Kubernetes (<code>Observability.CaaS.Jaeger</code>).
 * </p>
 * <br>
 * <p>
 * Jaeger has no datastore of its own: it stores traces in an OpenSearch component it links to. Exactly one
 * OpenSearch link is required, declared with {@link JaegerBuilder#linkToOpenSearch(CaaSOpenSearch)} (or one of its
 * overloads). The link asks for <code>read-write</code> access on the index prefix
 * <code>&lt;jaegerId&gt;-jaeger-</code> unless another prefix is given, and Jaeger also depends on the OpenSearch
 * component so that it is reconciled after it.
 * </p>
 * <p>
 * For more details check the <a href="https://www.jaegertracing.io/docs/latest/">Jaeger documentation</a>.
 * </p>
 */
@ToString(callSuper = true)
public class CaaSJaeger extends CaaSTracingImpl implements LiveSystemComponent {
  private final static String INDEX_PREFIX_SUFFIX = "-jaeger-";
  private final static String OPENSEARCH_LINK_MISSING = "[CaaSJaeger Validation] Jaeger stores its traces in OpenSearch and needs exactly one OpenSearch link: call linkToOpenSearch(...)";
  private final static String OPENSEARCH_LINK_DUPLICATED = "[CaaSJaeger Validation] Jaeger needs exactly one OpenSearch link, but linkToOpenSearch(...) was called more than once";

  protected CaaSJaeger() {
  }

  public static JaegerBuilder builder() {
    return new JaegerBuilder();
  }

  public static class JaegerBuilder extends Builder<CaaSJaeger, JaegerBuilder> {
    private ComponentId openSearchId;
    private String openSearchIndexPrefix;
    private int openSearchLinkCount;

    @Override
    protected CaaSJaeger createComponent() {
      return new CaaSJaeger();
    }

    @Override
    protected JaegerBuilder getBuilder() {
      return this;
    }

    /**
     * The id of the container platform where Jaeger will be instantiated.
     * Set automatically when the component is added through <code>KubernetesCluster.withTracing(...)</code>.
     *
     * @param containerPlatform id of the Kubernetes cluster
     */
    public JaegerBuilder withContainerPlatform(String containerPlatform) {
      component.setContainerPlatform(containerPlatform);
      return builder;
    }

    /**
     * Namespace where Jaeger will be instantiated.
     *
     * @param namespace Kubernetes namespace
     */
    public JaegerBuilder withNamespace(String namespace) {
      component.setNamespace(namespace);
      return builder;
    }

    /**
     * Stores the traces in the given OpenSearch component, on the index prefix <code>&lt;jaegerId&gt;-jaeger-</code>.
     *
     * @param openSearch the OpenSearch component
     */
    public JaegerBuilder linkToOpenSearch(CaaSOpenSearch openSearch) {
      return linkToOpenSearch(openSearch, null);
    }

    /**
     * Stores the traces in the given OpenSearch component, on the given index prefix.
     *
     * @param openSearch  the OpenSearch component
     * @param indexPrefix prefix of Jaeger's indices; <code>null</code> for <code>&lt;jaegerId&gt;-jaeger-</code>
     */
    public JaegerBuilder linkToOpenSearch(CaaSOpenSearch openSearch, String indexPrefix) {
      if (openSearch == null) {
        throw new IllegalArgumentException("[CaaSJaeger Validation] OpenSearch component has not been defined and it is required");
      }

      return linkToOpenSearch(openSearch.getId(), indexPrefix);
    }

    /**
     * Stores the traces in the OpenSearch component with the given id, on the given index prefix.
     * Use it when the OpenSearch component is not built in the same code, e.g. a local reference to the
     * OpenSearch component of another Live System.
     *
     * @param openSearchId id of the OpenSearch component (or of a local reference to one)
     * @param indexPrefix  prefix of Jaeger's indices; <code>null</code> for <code>&lt;jaegerId&gt;-jaeger-</code>
     */
    public JaegerBuilder linkToOpenSearch(ComponentId openSearchId, String indexPrefix) {
      if (openSearchId == null) {
        throw new IllegalArgumentException("[CaaSJaeger Validation] OpenSearch component id has not been defined and it is required");
      }

      if (indexPrefix != null) {
        SearchLink.requireValidIndexPrefix(indexPrefix);
      }

      this.openSearchId = openSearchId;
      this.openSearchIndexPrefix = indexPrefix;
      this.openSearchLinkCount++;
      return builder;
    }

    @Override
    public CaaSJaeger build() {
      component.setType(CAAS_JAEGER);

      if (openSearchLinkCount == 0) {
        throw new IllegalArgumentException(OPENSEARCH_LINK_MISSING);
      }

      if (openSearchLinkCount > 1) {
        throw new IllegalArgumentException(OPENSEARCH_LINK_DUPLICATED);
      }

      if (component.getId() != null) {
        var indexPrefix = openSearchIndexPrefix != null
            ? openSearchIndexPrefix
            : component.getId().getValue() + INDEX_PREFIX_SUFFIX;
        withLink(SearchLink.to(openSearchId, SearchAccess.READ_WRITE, indexPrefix));
        withDependency(openSearchId);
      }

      return super.build();
    }
  }
}
