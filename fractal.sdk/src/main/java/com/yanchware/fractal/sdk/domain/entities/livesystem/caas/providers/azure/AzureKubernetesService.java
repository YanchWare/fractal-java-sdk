package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.KubernetesCluster;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.PriorityClass;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class AzureKubernetesService extends KubernetesCluster {
  private final static String EMPTY_NODE_POOL = "[AzureKubernetesService Validation] Node pool list is null or empty and at least one node pool is required";

  private AzureRegion region;
  private Collection<AzureNodePool> nodePools;

  protected AzureKubernetesService() {
    nodePools = new ArrayList<>();
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static AzureKubernetesServiceBuilder builder() {
    return new AzureKubernetesServiceBuilder();
  }

  public static class AzureKubernetesServiceBuilder extends Builder<AzureKubernetesService, AzureKubernetesServiceBuilder> {

    @Override
    protected AzureKubernetesService createComponent() {
      return new AzureKubernetesService();
    }

    @Override
    protected AzureKubernetesServiceBuilder getBuilder() {
      return this;
    }

    public AzureKubernetesServiceBuilder withRegion(AzureRegion region) {
      component.setRegion(region);
      return builder;
    }

    public AzureKubernetesServiceBuilder withNodePool(AzureNodePool nodePool) {
      return withNodePools(List.of(nodePool));
    }

    public AzureKubernetesServiceBuilder withNodePools(Collection<? extends AzureNodePool> nodePools) {
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
    if (nodePools.isEmpty()) {
      errors.add(EMPTY_NODE_POOL);
    }

    nodePools.stream()
        .map(AzureNodePool::validate)
        .forEach(errors::addAll);

    return errors;
  }

}
