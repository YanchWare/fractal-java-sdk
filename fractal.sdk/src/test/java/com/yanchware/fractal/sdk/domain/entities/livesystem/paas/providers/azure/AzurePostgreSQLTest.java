package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlDbImpl;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PostgreSQLCharset.UTF8;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion.EUROPE_WEST;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureSkuName.B_GEN5_1;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureStorageAutoGrow.ENABLED;
import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType.AZURE;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_POSTGRESQL;
import static org.assertj.core.api.Assertions.*;

public class AzurePostgreSQLTest {

  @Test
  public void exceptionThrown_when_azurePgCreatedWithNoIdNoRegionNoNetwork() {
    var azurePg = AzurePostgreSql.builder();
    assertThatThrownBy(azurePg::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
        "Component id has not been defined and it is required",
        "[AzurePostgreSQL Validation] Region has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_azurePgCreatedWithJustId() {
    var azurePg = AzurePostgreSql.builder().withId(ComponentId.from("azure-pg"));
    assertThatThrownBy(azurePg::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
        "[AzurePostgreSQL Validation] Region has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_azurePgCreatedWithBackUpRetentionDaysLessThan7() {
    var azurePg = AzurePostgreSql.builder()
        .withId(ComponentId.from("azure-pg"))
        .withRegion(EUROPE_WEST)
        .withStorageMB(1234)
        .withBackupRetentionDays(6);
    assertThatThrownBy(azurePg::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
        "[AzurePostgreSQL Validation] Backup Retention Days must be between 7 and 35 days");
  }

  @Test
  public void exceptionThrown_when_azurePgCreatedWithBackUpRetentionDaysHigherThan35() {
    var azurePg = AzurePostgreSql.builder()
        .withId(ComponentId.from("azure-pg"))
        .withRegion(EUROPE_WEST)
        .withStorageMB(1234)
        .withBackupRetentionDays(36);
    assertThatThrownBy(azurePg::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
        "[AzurePostgreSQL Validation] Backup Retention Days must be between 7 and 35 days");
  }

  @Test
  public void propertiesAreSet_when_azurePgCreatedWithJustIdAndRegion() {
    var azurePg = AzurePostgreSql.builder().withId(ComponentId.from("azure-pg")).withRegion(EUROPE_WEST);
    assertThat(azurePg.build().validate()).isEmpty();
  }

  @Test
  public void propertiesAreSet_when_azurePostgresIsCreated() {
    var azurePostgreSQL = AzurePostgreSql.builder()
        .withId(ComponentId.from("azure-psg"))
        .withRegion(EUROPE_WEST)
        .withRootUser("rootUser")
        .withSkuName(B_GEN5_1)
        .withStorageAutoGrow(ENABLED)
        .withStorageMB(5 * 1024)
        .withBackupRetentionDays(12)
        .withDatabase(AzurePostgreSqlDb.builder()
            .withId(ComponentId.from("db-1"))
            .withName("db")
            .withCharset(UTF8)
            .withCollation("collation")
            .withSchema("schema")
            .build())
        .build();
    assertThat(azurePostgreSQL)
        .returns(PAAS_POSTGRESQL, from(AzurePostgreSql::getType))
        .returns(AZURE, from(AzurePostgreSql::getProvider))
        .returns(EUROPE_WEST, from(AzurePostgreSql::getRegion))
        .returns("rootUser", from(AzurePostgreSql::getRootUser))
        .returns(B_GEN5_1, from(AzurePostgreSql::getSkuName))
        .returns(ENABLED, from(AzurePostgreSql::getStorageAutoGrow))
        .returns(5 * 1024, from(AzurePostgreSql::getStorageMB))
        .returns(12, from(AzurePostgreSql::getBackupRetentionDays))
        .returns(1, from(x -> x.getDatabases().size()));
    Optional<PaaSPostgreSqlDbImpl> postgreSqlDbOptional = azurePostgreSQL.getDatabases().stream().findFirst();
    assertThat(postgreSqlDbOptional).isPresent();
    assertThat(postgreSqlDbOptional.get())
        .returns("db", from(PaaSPostgreSqlDbImpl::getName))
        .returns(UTF8, from(PaaSPostgreSqlDbImpl::getCharset))
        .returns("collation", from(PaaSPostgreSqlDbImpl::getCollation))
        .returns("schema", from(PaaSPostgreSqlDbImpl::getSchema))
        .returns(AZURE, from(PaaSPostgreSqlDbImpl::getProvider));
  }

}