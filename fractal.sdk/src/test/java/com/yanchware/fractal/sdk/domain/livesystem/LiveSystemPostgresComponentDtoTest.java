package com.yanchware.fractal.sdk.domain.livesystem;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzurePostgreSqlDatabase;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzurePostgreSqlDbms;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.LiveSystemComponentDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.domain.values.ComponentType;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import com.yanchware.fractal.sdk.utils.TestUtils;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.TestUtils.assertGenericComponent;
import static org.assertj.core.api.Assertions.assertThat;

class LiveSystemPostgresComponentDtoTest {

    @Test
    public void liveSystemComponentDto_matches_liveSystemComponents_withCorrectTypeForLiveSystem_forAks() {
        var factory = new LiveSystemsFactory(
                HttpClient.newBuilder().build(),
                new LocalSdkConfiguration(""),
                RetryRegistry.ofDefaults());
        var postgres = TestUtils.getAzurePostgresExample();
        var liveSystem = factory.builder()
                .withId(new LiveSystemIdValue("test", "test"))
                .withStandardProvider(ProviderType.AZURE)
                .withComponent(postgres)
                .build();
        assertPostgres(postgres, liveSystem.blueprintMapFromLiveSystemComponents());
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
                "storageGb")
            .containsExactly(
                postgres.getAzureRegion(),
                postgres.getBackupRetentionDays(),
                postgres.getName(),
                postgres.getRootUser(),
                postgres.getSkuName().getId(),
                postgres.getStorageAutoGrow().getId(),
                postgres.getStorageGb());
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