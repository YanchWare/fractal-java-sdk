package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.KubernetesCluster;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.PostgreSQL;
import com.yanchware.fractal.sdk.utils.TestUtils;
import com.yanchware.fractal.sdk.valueobjects.ComponentType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.TestUtils.assertGenericComponent;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.*;
import static org.assertj.core.api.Assertions.assertThat;

class LiveSystemComponentDtoTest {

    @Test
    public void liveSystemComponentDto_matches_liveSystemComponents_withCorrectTypeForLiveSystem_forAks() {
        var aks = TestUtils.getAksExample();
        var postgres = TestUtils.getAzurePostgresExample();
        Map<String, LiveSystemComponentDto> lsDtoMap = LiveSystemComponentDto.fromLiveSystemComponents(List.of(aks, postgres));

        assertCaaSComponents(aks, lsDtoMap);
        assertPostgres(postgres, lsDtoMap);
    }

    @Test
    public void liveSystemComponentDto_matches_liveSystemComponents_withCorrectTypeForLiveSystem_forGke() {
        var gke = TestUtils.getGkeExample();
        var postgres = TestUtils.getGcpPostgresExample();
        Map<String, LiveSystemComponentDto> lsDtoMap = LiveSystemComponentDto.fromLiveSystemComponents(List.of(gke, postgres));

        assertCaaSComponents(gke, lsDtoMap);
        assertPostgres(postgres, lsDtoMap);
    }

    private void assertCaaSComponents(KubernetesCluster k8sCluster, Map<String, LiveSystemComponentDto> lsDtoMap) {
        ProviderType provider = k8sCluster.getProvider();
        assertLsComponent(lsDtoMap, k8sCluster, KUBERNETES, provider);
        k8sCluster.getMessageBrokerInstances().forEach(component -> assertLsComponent(lsDtoMap, component, KAFKA, provider));
        k8sCluster.getMonitoringInstances().forEach(component -> assertLsComponent(lsDtoMap, component, PROMETHEUS, provider));
        k8sCluster.getApiGatewayInstances().forEach(component -> assertLsComponent(lsDtoMap, component, AMBASSADOR, provider));
        k8sCluster.getK8sWorkloadInstances().forEach(component -> assertLsComponent(lsDtoMap, component, K8S_WORKLOAD, provider));
        k8sCluster.getServiceMeshSecurityInstances().forEach(component -> assertLsComponent(lsDtoMap, component, OCELOT, provider));
        k8sCluster.getTracingInstances().forEach(component -> assertLsComponent(lsDtoMap, component, JAEGER, provider));
        k8sCluster.getLoggingInstances().forEach(component -> assertLsComponent(lsDtoMap, component, ELASTIC_LOGGING, provider));
    }

    private void assertPostgres(PostgreSQL postgres, Map<String, LiveSystemComponentDto> lsDtoMap) {
        assertLsComponent(lsDtoMap, postgres, POSTGRESQL, postgres.getProvider());
        postgres.getDatabases().forEach(component -> assertLsComponent(lsDtoMap, component, POSTGRESQLDB, postgres.getProvider()));
    }

    private void assertLsComponent(Map<String, LiveSystemComponentDto> lsDtoMap, Component component, ComponentType type, ProviderType provider) {
        var dto = lsDtoMap.get(component.getId().getValue());
        assertGenericComponent(dto, component, type.getId());
        assertThat(dto.getProvider()).isEqualTo(provider);
    }
}