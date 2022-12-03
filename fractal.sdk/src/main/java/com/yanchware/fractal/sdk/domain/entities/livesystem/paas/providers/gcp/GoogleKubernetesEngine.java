package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.gcp;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.KubernetesCluster;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType.GCP;
import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
public class GoogleKubernetesEngine extends KubernetesCluster {
  private final static String EMPTY_NODE_POOL = "[GoogleKubernetesEngine Validation] Node pool list is null or empty and at least one node pool is required";

  private String networkName;
  private String subnetworkName;
  private String podsRangeName;
  private String servicesRangeName;
  private String subnetworkIpRange;

  private GcpRegion region;
  private Collection<GcpNodePool> nodePools;

  protected GoogleKubernetesEngine() {
    nodePools = new ArrayList<>();
  }

  @Override
  public ProviderType getProvider() {
    return GCP;
  }

  public static GoogleKubernetesEngineBuilder builder() {
    return new GoogleKubernetesEngineBuilder();
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

    public GoogleKubernetesEngineBuilder withNetworkName(String networkName) {
      component.setNetworkName(networkName);
      return builder;
    }

    public GoogleKubernetesEngineBuilder withSubnetworkName(String subnetworkName) {
      component.setSubnetworkName(subnetworkName);
      return builder;
    }

    public GoogleKubernetesEngineBuilder withPodsRangeName(String podsRangeName) {
      component.setPodsRangeName(podsRangeName);
      return builder;
    }

    public GoogleKubernetesEngineBuilder withServicesRangeName(String servicesRangeName) {
      component.setServicesRangeName(servicesRangeName);
      return builder;
    }

    public GoogleKubernetesEngineBuilder withSubnetworkIpRange(String subnetworkIpRange) {
      component.setSubnetworkIpRange(subnetworkIpRange);
      return builder;
    }

    public GoogleKubernetesEngineBuilder withRegion(GcpRegion region) {
      component.setRegion(region);
      return builder;
    }

    public GoogleKubernetesEngineBuilder withNodePool(GcpNodePool nodePool) {
      return withNodePools(List.of(nodePool));
    }

    public GoogleKubernetesEngineBuilder withNodePools(Collection<? extends GcpNodePool> nodePools) {
      if (isBlank(nodePools)) {
        return builder;
      }

      if (component.getNodePools() == null) {
        component.setNodePools(new ArrayList<>());
      }

      component.getNodePools().addAll(nodePools);
      return builder;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    if (isBlank(nodePools)) {
      errors.add(EMPTY_NODE_POOL);
    }

    nodePools.stream()
        .map(GcpNodePool::validate)
        .forEach(errors::addAll);

    return errors;
  }

}
