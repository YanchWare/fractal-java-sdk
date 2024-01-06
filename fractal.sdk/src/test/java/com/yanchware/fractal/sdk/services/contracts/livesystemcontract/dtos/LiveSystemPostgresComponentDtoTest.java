package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlDbms;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzurePostgreSqlDbms;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.utils.TestUtils;
import com.yanchware.fractal.sdk.valueobjects.ComponentType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.TestUtils.assertGenericComponent;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_POSTGRESQL_DATABASE;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_POSTGRESQL_DBMS;
import static org.assertj.core.api.Assertions.assertThat;

class LiveSystemPostgresComponentDtoTest {

    @Test
    public void liveSystemComponentDto_matches_liveSystemComponents_withCorrectTypeForLiveSystem_forAks() {
        var postgres = TestUtils.getAzurePostgresExample();
        Map<String, LiveSystemComponentDto> lsDtoMap = LiveSystemComponentDto.fromLiveSystemComponents(List.of(postgres));
        assertPostgres(postgres, lsDtoMap);
    }

    private void assertPostgres(AzurePostgreSqlDbms postgres, Map<String, LiveSystemComponentDto> lsDtoMap) {
        assertPostgres(lsDtoMap, postgres, PAAS_POSTGRESQL_DBMS);
        postgres.getDatabases().forEach(component -> assertPostgresDb(lsDtoMap, component, PAAS_POSTGRESQL_DATABASE, postgres.getProvider()));
    }

    private void assertPostgres(Map<String, LiveSystemComponentDto> lsDtoMap, AzurePostgreSqlDbms postgres, ComponentType type) {
        var dto = lsDtoMap.get(postgres.getId().getValue());
        assertGenericComponent(dto, postgres, type.getId());
        assertThat(dto.getParameters())
            .extracting(
                    AzurePostgreSqlDbms::getAzureRegion,
            )
            .containsExactly(
                postgres.getAzureRegion().getName(),
                postgres.getBackupRetentionDays(),
                postgres.getName(),
                postgres.getRootUser(),
                postgres.getSkuName(),
                postgres.getStorageAutoGrow(),
                postgres.getStorageMB()
        );
    }

    private void assertPostgresDb(Map<String, LiveSystemComponentDto> lsDtoMap, Component component, ComponentType type, ProviderType provider) {
        var dto = lsDtoMap.get(component.getId().getValue());
        assertGenericComponent(dto, component, type.getId());
        assertThat(dto.getParameters()).isEmpty();
    }
}