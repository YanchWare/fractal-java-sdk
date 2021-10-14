package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.gcp;

import com.yanchware.fractal.sdk.domain.entities.livesystem.KubernetesCluster;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType.GCP;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KUBERNETES;

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

    @Override
    public ProviderType getProvider() {
        return GCP;
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

        public GoogleKubernetesEngineBuilder withNodePool(GcpNodePool nodePool) {
            return withNodePools(List.of(nodePool));
        }

        public GoogleKubernetesEngineBuilder withNodePools(Collection<? extends GcpNodePool> nodePools) {
            if (nodePools == null || nodePools.isEmpty()) {
                return builder;
            }

            if (component.getNodePools() == null) {
                component.setNodePools(new ArrayList<>());
            }

            component.getNodePools().addAll(nodePools);
            return builder;
        }

        @Override
        public GoogleKubernetesEngine build() {
            component.setType(KUBERNETES);
            return super.build();
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
