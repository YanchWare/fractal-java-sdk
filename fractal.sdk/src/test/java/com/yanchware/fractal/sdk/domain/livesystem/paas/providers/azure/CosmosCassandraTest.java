package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.TestWithFixture;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.AzureCosmosCassandraCluster;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_CASANDRA_DBMS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CosmosCassandraTest extends TestWithFixture {

  @Test
  public void exceptionThrown_when_componentBuiltWithEmptyValues() {
    assertThatThrownBy(() -> AzureCosmosCassandraCluster.builder().withId("a-legal-id").build()).
      isInstanceOf(IllegalArgumentException.class).
      hasMessageContaining("Cosmos Cassandra Cluster periodic backup feature needs a value of hours between backups that is larger or equal to 1");
  }

  @Test
  public void typeIsAsExpected_when_BuiltWithAllRequiredValues() {
    var isDeallocated = a(Boolean.class);
    var useCassandraAuth = a(Boolean.class);
    var enableAuditLogging = a(Boolean.class);
    var cassandraVersion = a(String.class);
    var subnetId = a(String.class);
    var hoursBetweenBackups = a(Integer.class);

    var component = AzureCosmosCassandraCluster.builder()
      .withId("a-legal-id")
      .withRegion(AzureRegion.AUSTRALIA_CENTRAL)
      .withDeallocated(isDeallocated)
      .withCassandraAuthentication(useCassandraAuth)
      .withCassandraVersion(cassandraVersion)
      .withDelegatedManagementSubnetId(subnetId)
      .withAuditLoggingEnabled(enableAuditLogging)
      .withHoursBetweenBackups(hoursBetweenBackups)
    .build();

    assertThat(component.getType()).isEqualTo(PAAS_CASANDRA_DBMS);
    assertThat(component)
      .asInstanceOf(InstanceOfAssertFactories.type(AzureCosmosCassandraCluster.class))
      .extracting(
        AzureCosmosCassandraCluster::getCassandraVersion,
        AzureCosmosCassandraCluster::getHoursBetweenBackups,
        AzureCosmosCassandraCluster::getDelegatedManagementSubnetId,
        AzureCosmosCassandraCluster::isUseCassandraAuthentication,
        AzureCosmosCassandraCluster::isDeallocated,
        AzureCosmosCassandraCluster::isCassandraAuditLoggingEnabled)
      .containsExactly(
        cassandraVersion,
        hoursBetweenBackups,
        subnetId,
        useCassandraAuth,
        isDeallocated,
        enableAuditLogging);
  }
}
