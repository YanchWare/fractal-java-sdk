package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlDatabase;
import com.yanchware.fractal.sdk.domain.values.ComponentId;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PostgreSqlCharset.UTF8;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion.WEST_EUROPE;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureStorageAutoGrow.ENABLED;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.valueobjects.AzureSkuName.B_GEN5_1;
import static com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType.AZURE;
import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_POSTGRESQL_DBMS;
import static org.assertj.core.api.Assertions.*;

public class AzurePostgreSqlTest {

  @Test
  public void exceptionThrown_when_azurePgCreatedWithNoIdNoRegionNoNetwork() {
    var azurePg = AzurePostgreSqlDbms.builder();
    assertThatThrownBy(azurePg::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
        "Component id has not been defined and it is required",
        "[AzurePostgreSQL Validation] Region has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_azurePgCreatedWithJustId() {
    var azurePg = AzurePostgreSqlDbms.builder().withId(ComponentId.from("azure-pg"));
    assertThatThrownBy(azurePg::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
        "[AzurePostgreSQL Validation] Region has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_azurePgCreatedWithBackUpRetentionDaysLessThan7() {
    var azurePg = AzurePostgreSqlDbms.builder()
        .withId(ComponentId.from("azure-pg"))
        .withRegion(WEST_EUROPE)
        .withStorageMB(1234)
        .withBackupRetentionDays(6);
    assertThatThrownBy(azurePg::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
        "[AzurePostgreSQL Validation] Backup Retention Days must be between 7 and 35 days");
  }

  @Test
  public void exceptionThrown_when_azurePgCreatedWithBackUpRetentionDaysHigherThan35() {
    var azurePg = AzurePostgreSqlDbms.builder()
        .withId(ComponentId.from("azure-pg"))
        .withRegion(WEST_EUROPE)
        .withStorageMB(1234)
        .withBackupRetentionDays(36);
    assertThatThrownBy(azurePg::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
        "[AzurePostgreSQL Validation] Backup Retention Days must be between 7 and 35 days");
  }

  @Test
  public void propertiesAreSet_when_azurePgCreatedWithJustIdAndRegion() {
    var azurePg = AzurePostgreSqlDbms.builder().withId(ComponentId.from("azure-pg")).withRegion(WEST_EUROPE);
    assertThat(azurePg.build().validate()).isEmpty();
  }

  @Test
  public void propertiesAreSet_when_azurePostgresIsCreated() {
    var azurePostgreSql = AzurePostgreSqlDbms.builder()
        .withId(ComponentId.from("azure-psg"))
        .withRegion(WEST_EUROPE)
        .withRootUser("rootUser")
        .withSkuName(B_GEN5_1)
        .withStorageAutoGrow(ENABLED)
        .withStorageMB(5 * 1024)
        .withBackupRetentionDays(12)
        .withDatabase(AzurePostgreSqlDatabase.builder()
            .withId(ComponentId.from("db-1"))
            .withName("db")
            .withCharset(UTF8)
            .withCollation("collation")
            .withSchema("schema")
            .build())
        .build();
    assertThat(azurePostgreSql)
        .returns(PAAS_POSTGRESQL_DBMS, from(AzurePostgreSqlDbms::getType))
        .returns(AZURE, from(AzurePostgreSqlDbms::getProvider))
        .returns(WEST_EUROPE, from(AzurePostgreSqlDbms::getAzureRegion))
        .returns("rootUser", from(AzurePostgreSqlDbms::getRootUser))
        .returns(B_GEN5_1, from(AzurePostgreSqlDbms::getSkuName))
        .returns(ENABLED, from(AzurePostgreSqlDbms::getStorageAutoGrow))
        .returns(5 * 1024, from(AzurePostgreSqlDbms::getStorageMB))
        .returns(12, from(AzurePostgreSqlDbms::getBackupRetentionDays))
        .returns(1, from(x -> x.getDatabases().size()));
    Optional<PaaSPostgreSqlDatabase> postgreSqlDbOptional = azurePostgreSql.getDatabases().stream().findFirst();
    assertThat(postgreSqlDbOptional).isPresent();
    assertThat(postgreSqlDbOptional.get())
        .returns("db", from(PaaSPostgreSqlDatabase::getName))
        .returns(UTF8, from(PaaSPostgreSqlDatabase::getCharset))
        .returns("collation", from(PaaSPostgreSqlDatabase::getCollation))
        .returns("schema", from(PaaSPostgreSqlDatabase::getSchema))
        .returns(AZURE, from(PaaSPostgreSqlDatabase::getProvider));
  }

}