
package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.TestWithFixture;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.*;
import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

public class CosmosPostgreSqlAccountTest extends TestWithFixture {

  AzureCosmosPostgreSqlDbms.AzureCosmosPostgreSqlDbmsBuilder getBuilder() {
    return new AzureCosmosPostgreSqlDbms.AzureCosmosPostgreSqlDbmsBuilder();
  }

  Collection<AzureCosmosPostgreSqlDatabase> getValidCosmosEntities() {
    return List.of(
      aPostgreSqlDb("db-a"),
      aPostgreSqlDb("db-b"),
      aPostgreSqlDb("db-c"));
  }

  @Test
  public void tagsAreAsExpected_when_TagsAreSet() {
    var builder = getBuilder()
      .withId("a-legal-id")
      .withRegion(AzureRegion.EAST_ASIA)
      .withTags(Map.ofEntries(
        entry("a", "tag A"),
        entry("b", "tag B")
      ));

    var component = builder.build();

    assert(component.getTags()).equals(Map.ofEntries(
      entry("a", "tag A"),
      entry("b", "tag B")
    ));
  }

  @Test
  public void tagsAreAsExpected_when_ASingleTagIsSet() {
    var builder = getBuilder()
      .withId("a-legal-id")
      .withRegion(AzureRegion.EAST_ASIA)
      .withTag("a", "tag A");

    var component = builder.build();

    assert(component.getTags()).equals(Map.ofEntries(
      entry("a", "tag A")
    ));
  }

  @Test
  public void typeIsAsExpected_when_BuiltWithSingleEntity() {
    var entity = getValidCosmosEntities().stream().findFirst().orElse(null);
    assertThat(entity).isNotNull();
    var builder = getBuilder()
      .withId("a-legal-id")
      .withRegion(AzureRegion.EAST_ASIA)
      .withDatabase(entity);

    var component = builder.build();

    assertThat(component.getType()).isEqualTo(PAAS_COSMOS_POSTGRESQL_CLUSTER);
    assertThat(component.getDatabases().stream().findFirst().get().getType()).isEqualTo(PAAS_COSMOS_POSTGRESQL_DATABASE);
  }

  @Test
  public void propertiesAreAsExpected() {
    var entity = getValidCosmosEntities().stream().findFirst().orElse(null);
    assertThat(entity).isNotNull();
    var backupRetentionDays = a(Integer.class);
    var coordinatorCores = a(Integer.class);
    var coordinatorStorageGb = a(Integer.class);
    var coordinatorServerEdition = a(String.class);
    var highAvailability = HighAvailabilityMode.SAME_ZONE;
    var nodeCount = a(Integer.class);
    var isPrivate = a(Boolean.class);
    var rootUser = a(String.class);
    var skuName = AzureCosmosPostgreSqlDbmsSkuName.STANDARD_D8DS_V5;
    var nodeServerEdition = a(String.class);
    var storageGb = a(Integer.class);
    var replicationRole = ReplicationRole.ASYNC_REPLICA;
    var storageAutoGrow = AzureStorageAutoGrow.ENABLED;
    var subnetAddressCidr = a(String.class);
    var id = "a-legal-id";

    var builder = getBuilder()
      .withId(id)
      .withRegion(AzureRegion.EAST_ASIA)
      .withBackupRetentionDays(backupRetentionDays)
      .withCoordinatorCores(coordinatorCores)
      .withCoordinatorStorageGb(coordinatorStorageGb)
      .withCoordinatorServerEdition(coordinatorServerEdition)
      .withHighAvailabilityMode(highAvailability)
      .withNodeCount(nodeCount)
      .withIsPrivate(isPrivate)
      .withRootUser(rootUser)
      .withSkuName(skuName)
      .withNodeServerEdition(nodeServerEdition)
      .withStorageGb(storageGb)
      .withReplicationRole(replicationRole)
      .withStorageAutoGrow(storageAutoGrow)
      .withSubnetAddressCidr(subnetAddressCidr)
      .withDatabase(entity);

    var component = builder.build();
    assertThat(component).extracting(
      "id",
        "backupRetentionDays",
        "coordinatorCores",
        "coordinatorStorageGb",
        "coordinatorServerEdition",
        "highAvailabilityMode",
        "nodeCount",
        "isPrivate",
        "rootUser",
        "skuName",
        "nodeServerEdition",
        "storageGb",
        "replicationRole",
        "storageAutoGrow",
        "subnetAddressCidr")
      .contains(
        component.getId(),
        backupRetentionDays,
        coordinatorCores,
        coordinatorStorageGb,
        coordinatorServerEdition,
        highAvailability,
        nodeCount,
        isPrivate,
        rootUser,
        skuName,
        nodeServerEdition,
        storageGb,
        replicationRole,
        storageAutoGrow,
        subnetAddressCidr);
  }

  private AzureCosmosPostgreSqlDatabase aPostgreSqlDb(String id) {
    return AzureCosmosPostgreSqlDatabase.builder()
      .withId(id).build();
  }
}