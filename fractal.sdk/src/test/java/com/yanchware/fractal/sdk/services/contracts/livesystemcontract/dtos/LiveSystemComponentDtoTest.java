package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.Component;
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
    public void liveSystemComponentDto_matches_liveSystemComponents_withCorrectTypeForLiveSystem() {
        var aks = TestUtils.getAksExample();
        var postgres = TestUtils.getAzurePostgresExample();
        Map<String, LiveSystemComponentDto> lsDtoMap = LiveSystemComponentDto.fromLiveSystemComponents(List.of(aks, postgres));

        assertLsComponent(lsDtoMap, aks, KUBERNETES, aks.getProvider());

        aks.getKafkaClusters().forEach(component -> assertLsComponent(lsDtoMap, component, KAFKA, aks.getProvider()));
        aks.getPrometheusInstances().forEach(component -> assertLsComponent(lsDtoMap, component, PROMETHEUS, aks.getProvider()));
        aks.getAmbassadorInstances().forEach(component -> assertLsComponent(lsDtoMap, component, AMBASSADOR, aks.getProvider()));
        aks.getKubernetesWorkloads().forEach(component -> assertLsComponent(lsDtoMap, component, K8S_WORKLOAD, aks.getProvider()));

        assertLsComponent(lsDtoMap, postgres, POSTGRESQL, postgres.getProvider());

        postgres.getDatabases().forEach(component -> assertLsComponent(lsDtoMap, component, POSTGRESQLDB, postgres.getProvider()));
    }

    private void assertLsComponent(Map<String, LiveSystemComponentDto> lsDtoMap, Component component, ComponentType type, ProviderType provider) {
        var dto = lsDtoMap.get(component.getId().getValue());
        assertGenericComponent(dto, component, type.getId());
        assertThat(dto.getProvider()).isEqualTo(provider);
    }
}