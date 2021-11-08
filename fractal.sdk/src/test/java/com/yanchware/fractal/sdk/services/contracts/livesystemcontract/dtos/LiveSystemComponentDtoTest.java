package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.utils.TestUtils;
import com.yanchware.fractal.sdk.valueobjects.ComponentType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.TestUtils.assertGenericComponent;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.*;

class LiveSystemComponentDtoTest {

    @Test
    public void liveSystemComponentDto_matches_liveSystemComponents_withCorrectTypeForLiveSystem() {
        var aks = TestUtils.getAksExample();
        var postgres = TestUtils.getAzurePostgresExample();
        Map<String, LiveSystemComponentDto> lsDtoMap = LiveSystemComponentDto.fromLiveSystemComponents(List.of(aks, postgres));

        LiveSystemComponentDto aksDto = lsDtoMap.get(aks.getId().getValue());
        assertGenericComponent(aksDto, aks, KUBERNETES.getId());

        aks.getKafkaClusters().forEach(component -> assertLsComponent(lsDtoMap, component, KAFKA));
        aks.getPrometheusInstances().forEach(component -> assertLsComponent(lsDtoMap, component, PROMETHEUS));
        aks.getAmbassadorInstances().forEach(component -> assertLsComponent(lsDtoMap, component, AMBASSADOR));

        LiveSystemComponentDto pgDto = lsDtoMap.get(postgres.getId().getValue());
        assertGenericComponent(pgDto, postgres, POSTGRESQL.getId());
        postgres.getDatabases().forEach(component -> assertLsComponent(lsDtoMap, component, POSTGRESQLDB));

    }

    private void assertLsComponent(Map<String, LiveSystemComponentDto> lsDtoMap, Component component, ComponentType type) {
        var dto = lsDtoMap.get(component.getId().getValue());
        assertGenericComponent(dto, component, type.getId());
    }
}