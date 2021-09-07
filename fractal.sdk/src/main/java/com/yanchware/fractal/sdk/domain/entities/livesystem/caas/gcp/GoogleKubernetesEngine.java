package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.gcp;

import com.yanchware.fractal.sdk.domain.entities.livesystem.KubernetesCluster;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter(AccessLevel.PRIVATE)
public class GoogleKubernetesEngine extends KubernetesCluster {
  private final static String EMPTY_NODE_POOL = "[GoogleKubernetesEngine Validation] Node pool list is null or empty and at least one node pool is required";

  public static GoogleKubernetesEngineBuilder builder() {
    return new GoogleKubernetesEngineBuilder();
  }

  private GcpRegion region;
  private String network;
  private String subNetwork;
  private String podsRange;
  private String serviceRange;
  private Collection<GcpNodePool> nodePools;

  protected GoogleKubernetesEngine() {
    nodePools = new ArrayList<>();
  }

  public static class GoogleKubernetesEngineBuilder extends Builder<GoogleKubernetesEngine, GoogleKubernetesEngineBuilder> {

    @Override
    protected GoogleKubernetesEngine createComponent() {
      return new GoogleKubernetesEngine();
    }

    @Override
    protected GoogleKubernetesEngineBuilder getBuilder() {
      return this;
    }

    public GoogleKubernetesEngineBuilder region(GcpRegion region) {
      component.setRegion(region);
      return builder;
    }

    public GoogleKubernetesEngineBuilder network(String network) {
      component.setNetwork(network);
      return builder;
    }

    public GoogleKubernetesEngineBuilder subNetwork(String subNetwork) {
      component.setSubNetwork(subNetwork);
      return builder;
    }

    public GoogleKubernetesEngineBuilder podsRange(String podsRange) {
      component.setPodsRange(podsRange);
      return builder;
    }

    public GoogleKubernetesEngineBuilder serviceRange(String serviceRange) {
      component.setServiceRange(serviceRange);
      return builder;
    }

    public GoogleKubernetesEngineBuilder nodePool(GcpNodePool nodePool) {
      if (component.getNodePools() == null) {
        component.setNodePools(new ArrayList<>());
      }

      if(nodePool != null) {
        component.getNodePools().add(nodePool);
      }
      return builder;
    }

    public GoogleKubernetesEngineBuilder nodePools(Collection<? extends GcpNodePool> nodePools) {
      if (component.getNodePools() == null) {
        component.setNodePools(new ArrayList<>());
      }

      if(nodePools == null) {
          nodePools = new ArrayList<>();
      }

      component.getNodePools().addAll(nodePools);
      return builder;
    }

  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    if (nodePools == null || nodePools.isEmpty()) {
      errors.add(EMPTY_NODE_POOL);
    }

    nodePools.stream()
      .map(GcpNodePool::validate)
      .forEach(errors::addAll);

    return errors;
  }

}
