package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzurePostgreSqlDatabase;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzurePostgreSqlDbms;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.LiveSystemComponentDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.TestUtils;
import com.yanchware.fractal.sdk.domain.values.ComponentType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.TestUtils.assertGenericComponent;
import static org.assertj.core.api.Assertions.assertThat;

class LiveSystemPostgresComponentDtoTest {

    @Test
    public void liveSystemComponentDto_matches_liveSystemComponents_withCorrectTypeForLiveSystem_forAks() {
        var postgres = TestUtils.getAzurePostgresExample();
        Map<String, LiveSystemComponentDto> lsDtoMap = LiveSystemComponentDto.fromLiveSystemComponents(List.of(postgres));
        assertPostgres(postgres, lsDtoMap);
    }

    private void assertPostgres(AzurePostgreSqlDbms postgres, Map<String, LiveSystemComponentDto> lsDtoMap) {
        assertPostgres(lsDtoMap, postgres);
        postgres.getDatabases().forEach(component -> assertPostgresDb(lsDtoMap, (AzurePostgreSqlDatabase) component));
    }

    private void assertPostgres(Map<String, LiveSystemComponentDto> lsDtoMap, AzurePostgreSqlDbms postgres) {
        var dto = lsDtoMap.get(postgres.getId().getValue());
        assertGenericComponent(dto, postgres, ComponentType.PAAS_POSTGRESQL_DBMS.getId());
        assertThat(dto.getProvider()).isEqualTo(ProviderType.AZURE);
        assertThat(dto.getParameters())
            .extracting(
                "azureRegion",
                "backupRetentionDays",
                "name",
                "rootUser",
                "skuName",
                "storageAutoGrow",
                "storageMB")
            .containsExactly(
                postgres.getAzureRegion(),
                postgres.getBackupRetentionDays(),
                postgres.getName(),
                postgres.getRootUser(),
                postgres.getSkuName().getId(),
                postgres.getStorageAutoGrow().getId(),
                postgres.getStorageMB());
    }

    private void assertPostgresDb(Map<String, LiveSystemComponentDto> lsDtoMap, AzurePostgreSqlDatabase database) {
        var dto = lsDtoMap.get(database.getId().getValue());
        assertGenericComponent(dto, database, ComponentType.PAAS_POSTGRESQL_DATABASE.getId());
        assertThat(dto.getParameters())
            .extracting(
                "azureRegion",
                "name",
                "schema")
            .containsExactly(
                database.getAzureRegion(),
                database.getName(),
                database.getSchema());
    }
}