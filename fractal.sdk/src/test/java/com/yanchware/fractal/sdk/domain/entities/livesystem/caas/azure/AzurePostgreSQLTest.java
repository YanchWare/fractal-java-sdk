package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.PostgreSQLDB;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzurePostgreSQL;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureRegion.EUROPE_WEST;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureSkuName.B_GEN5_1;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureStorageAutoGrow.ENABLED;
import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType.AZURE;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.POSTGRESQL;
import static org.assertj.core.api.Assertions.*;

public class AzurePostgreSQLTest {

    @Test
    public void exceptionThrown_when_azurePgCreatedWithNoIdNoRegionNoNetwork() {
        var azurePg = AzurePostgreSQL.builder();
        assertThatThrownBy(azurePg::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "Component id has not been defined and it is required",
                "[AzurePostgreSQL Validation] Region has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_azurePgCreatedWithJustId() {
        var azurePg = AzurePostgreSQL.builder().withId(ComponentId.from("azure-pg"));
        assertThatThrownBy(azurePg::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[AzurePostgreSQL Validation] Region has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_azurePgCreatedWithBackUpRetentionDaysLessThan7() {
        var azurePg = AzurePostgreSQL.builder()
                .withId(ComponentId.from("azure-pg"))
                .withRegion(EUROPE_WEST)
                .withStorageMB(1234)
                .withBackupRetentionDays(6);
        assertThatThrownBy(azurePg::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[AzurePostgreSQL Validation] Backup Retention Days must be between 7 and 35 days");
    }

    @Test
    public void exceptionThrown_when_azurePgCreatedWithBackUpRetentionDaysHigherThan35() {
        var azurePg = AzurePostgreSQL.builder()
                .withId(ComponentId.from("azure-pg"))
                .withRegion(EUROPE_WEST)
                .withStorageMB(1234)
                .withBackupRetentionDays(36);
        assertThatThrownBy(azurePg::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[AzurePostgreSQL Validation] Backup Retention Days must be between 7 and 35 days");
    }

    @Test
    public void propertiesAreSet_when_azurePgCreatedWithJustIdAndRegion() {
        var azurePg = AzurePostgreSQL.builder().withId(ComponentId.from("azure-pg")).withRegion(EUROPE_WEST);
        assertThat(azurePg.build().validate()).isEmpty();
    }

    @Test
    public void propertiesAreSet_when_azurePostgresIsCreated() {
        var azurePgBuilder = AzurePostgreSQL.builder()
                .withId(ComponentId.from("azure-psg"))
                .withRegion(EUROPE_WEST)
                .withRootUser("rootUser")
                .withSkuName(B_GEN5_1)
                .withStorageAutoGrow(ENABLED)
                .withStorageMB(5 * 1024)
                .withBackupRetentionDays(12)
                .withDatabase(PostgreSQLDB.builder().withId(ComponentId.from("db-1")).withName("db").build());
        assertThat(azurePgBuilder.build())
                .returns(POSTGRESQL, from(AzurePostgreSQL::getType))
                .returns(AZURE, from(AzurePostgreSQL::getProvider))
                .returns(EUROPE_WEST, from(AzurePostgreSQL::getRegion))
                .returns("rootUser", from(AzurePostgreSQL::getRootUser))
                .returns(B_GEN5_1, from(AzurePostgreSQL::getSkuName))
                .returns(ENABLED, from(AzurePostgreSQL::getStorageAutoGrow))
                .returns(5 * 1024, from(AzurePostgreSQL::getStorageMB))
                .returns(12, from(AzurePostgreSQL::getBackupRetentionDays))
                .returns(1, from(x -> x.getDatabases().size()))
                .returns("db", from(x -> x.getDatabases().stream().findFirst().get().getName()))
                .returns(AZURE, from(x -> x.getDatabases().stream().findFirst().get().getProvider()));
    }

}