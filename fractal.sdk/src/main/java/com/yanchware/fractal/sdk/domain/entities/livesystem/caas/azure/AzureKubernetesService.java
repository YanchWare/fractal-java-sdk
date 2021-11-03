package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.KubernetesCluster;
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

    public static AzureKubernetesServiceBuilder builder() {
        return new AzureKubernetesServiceBuilder();
    }

    private AzureRegion region;
    private String network;
    private String subNetwork;
    private String podsRange;
    private String serviceRange;
    private Collection<AzureNodePool> nodePools;

    protected AzureKubernetesService() {
        nodePools = new ArrayList<>();
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.AZURE;
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

        public AzureKubernetesServiceBuilder region(AzureRegion region) {
            component.setRegion(region);
            return builder;
        }

        public AzureKubernetesServiceBuilder network(String network) {
            component.setNetwork(network);
            return builder;
        }

        public AzureKubernetesServiceBuilder subNetwork(String subNetwork) {
            component.setSubNetwork(subNetwork);
            return builder;
        }

        public AzureKubernetesServiceBuilder podsRange(String podsRange) {
            component.setPodsRange(podsRange);
            return builder;
        }

        public AzureKubernetesServiceBuilder serviceRange(String serviceRange) {
            component.setServiceRange(serviceRange);
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
