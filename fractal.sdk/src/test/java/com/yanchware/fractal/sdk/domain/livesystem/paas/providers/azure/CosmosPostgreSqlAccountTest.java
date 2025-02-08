
package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.jayway.jsonpath.JsonPath;
import com.yanchware.fractal.sdk.TestWithFixture;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.*;
import com.yanchware.fractal.sdk.domain.values.ComponentId;
import com.yanchware.fractal.sdk.domain.values.ComponentType;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_ACCOUNT;
import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_POSTGRESQL_CLUSTER;
import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CosmosPostgreSqlAccountTest extends TestWithFixture {

  AzureCosmosPostgreSqlDbms.AzureCosmosPostgreSqlDbmsBuilder getBuilder() {
    return new AzureCosmosPostgreSqlDbms.AzureCosmosPostgreSqlDbmsBuilder();
  }

  ComponentType getExpectedType() {
    return PAAS_COSMOS_POSTGRESQL_CLUSTER;
  }

  AzureCosmosPostgreSqlDatabase getInvalidCosmosEntity() {
    return new AzureCosmosPostgreSqlDatabase();
  }

  Collection<AzureCosmosPostgreSqlDatabase> getValidCosmosEntities() {
    return List.of(
      aPostgreSqlDb("db-a"),
      aPostgreSqlDb("db-b"),
      aPostgreSqlDb("db-c"));
  }

  @Test
  public void exceptionThrown_when_componentBuiltWithEmptyValues() {
    assertThatThrownBy(() -> getBuilder().withId("a-legal-id").build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Region has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_entityIsInvalid() {
    assertThatThrownBy(() -> getBuilder()
      .withId("a-legal-id")
      .withDatabase(getInvalidCosmosEntity())
      .build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Region has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_bothRegionAndResourceGroupAreNotDefined() {
    var accountId = "a-legal-id";
    var entities = getValidCosmosEntities();
    var builder = getBuilder()
      .withId(accountId)
      .withDatabases(entities);

    assertThatThrownBy(builder::build)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Region has not been defined and it is required");
  }

  @Test
  public void typeIsAsExpected_when_BuiltWithoutEntitiesInRegion() {
    var throughput = a(Integer.class);
    var builder = getBuilder()
      .withId("a-legal-id")
      .withRegion(AzureRegion.EAST_ASIA);

    var component = builder.build();

    assertThat(component.getType()).isEqualTo(getExpectedType());
    assertThat(component)
      .asInstanceOf(InstanceOfAssertFactories.type(AzureCosmosAccount.class))
      .extracting(AzureCosmosAccount::getCosmosEntities, AzureCosmosAccount::getMaxTotalThroughput)
      .containsExactly(Collections.EMPTY_LIST, throughput);
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

    assertThat(component.getType()).isEqualTo(getExpectedType());
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

    assertThat(component.getType()).isEqualTo(getExpectedType());
    assert(component.getTags()).equals(Map.ofEntries(
      entry("a", "tag A")
    ));
  }

  @Test
  public void typeIsAsExpected_when_BuiltWithoutEntitiesInResourceGroup() {
    var throughput = a(Integer.class);
    var builder = getBuilder()
      .withId("a-legal-id")
      .withAzureResourceGroup(
        AzureResourceGroup.builder()
          .withName(a(String.class))
          .withRegion(AzureRegion.SOUTHEAST_ASIA)
          .build());

    var component = builder.build();

    assertThat(component.getType()).isEqualTo(getExpectedType());
    assertThat(component)
      .asInstanceOf(InstanceOfAssertFactories.type(AzureCosmosAccount.class))
      .extracting(AzureCosmosAccount::getCosmosEntities, AzureCosmosAccount::getMaxTotalThroughput)
      .containsExactly(Collections.EMPTY_LIST, throughput);
  }


  @Test
  public void typeIsAsExpected_when_BuiltWithoutEntitiesInRegionAndResourceGroup() {
    var throughput = a(Integer.class);
    var builder = getBuilder()
      .withId("a-legal-id")
      .withRegion(AzureRegion.EAST_ASIA)
      .withAzureResourceGroup(
        AzureResourceGroup.builder()
          .withName(a(String.class))
          .withRegion(AzureRegion.SOUTHEAST_ASIA)
          .build());

    var component = builder.build();

    assertThat(component.getType()).isEqualTo(getExpectedType());
    assertThat(component)
      .asInstanceOf(InstanceOfAssertFactories.type(AzureCosmosAccount.class))
      .extracting(AzureCosmosAccount::getCosmosEntities, AzureCosmosAccount::getMaxTotalThroughput)
      .containsExactly(Collections.EMPTY_LIST, throughput);
  }

  @Test
  public void typeIsAsExpected_when_BuiltWithEntities() {
    var accountId = "a-legal-id";
    var entities = getValidCosmosEntities();
    var throughput = a(Integer.class);
    var builder = getBuilder()
      .withId(accountId)
      .withRegion(AzureRegion.EAST_ASIA)
      .withDatabases(entities);

    var component = builder.build();

    assertThat(component.getType()).isEqualTo(getExpectedType());
    assertThat(component)
      .asInstanceOf(InstanceOfAssertFactories.type(AzureCosmosAccount.class))
      .extracting(AzureCosmosAccount::getCosmosEntities, AzureCosmosAccount::getMaxTotalThroughput)
      .containsExactly(entities, throughput);

    assertThat(component.getDatabases().stream().allMatch(x -> x.getDependencies().contains(ComponentId.from(accountId))))
      .isTrue();
  }

  @Test
  public void typeIsAsExpected_when_BuiltWithSingleEntity() {
    var entity = getValidCosmosEntities().stream().findFirst().orElse(null);
    assertThat(entity).isNotNull();
    var throughput = a(Integer.class);
    var builder = getBuilder()
      .withId("a-legal-id")
      .withRegion(AzureRegion.EAST_ASIA)
      .withDatabase(entity);

    var component = builder.build();

    assertThat(component.getType()).isEqualTo(getExpectedType());
    assertThat(component)
      .asInstanceOf(InstanceOfAssertFactories.type(AzureCosmosAccount.class))
      .extracting(AzureCosmosAccount::getCosmosEntities, AzureCosmosAccount::getMaxTotalThroughput)
      .containsExactly(List.of(entity), throughput);
  }

  private AzureCosmosPostgreSqlDatabase aPostgreSqlDb(String id) {
    return AzureCosmosPostgreSqlDatabase.builder()
      .withId(id).build();
  }
}