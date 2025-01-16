package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.jayway.jsonpath.JsonPath;
import com.yanchware.fractal.sdk.TestWithFixture;
import com.yanchware.fractal.sdk.domain.Component;
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

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class CosmosAccountTest<T extends AzureCosmosAccountBuilder<?, ?>> extends TestWithFixture {

  abstract T getBuilder();

  abstract ComponentType getExpectedType();

  abstract <C extends Component & AzureCosmosEntity> C getInvalidCosmosEntity();

  abstract <C extends Component & AzureCosmosEntity> Collection<C> getValidCosmosEntities();

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
      .withMaxTotalThroughput(a(Integer.class))
      .withCosmosEntity(getInvalidCosmosEntity())
      .build())
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Region has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_bothRegionAndResourceGroupAreNotDefined() {
    var accountId = "a-legal-id";
    var entities = getValidCosmosEntities();
    var throughput = a(Integer.class);
    var builder = getBuilder()
      .withId(accountId)
      .withMaxTotalThroughput(throughput)
      .withCosmosEntities(entities);

    assertThatThrownBy(builder::build)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageContaining("Region has not been defined and it is required");
  }

  @Test
  public void typeIsAsExpected_when_BuiltWithoutEntitiesInRegion() {
    var throughput = a(Integer.class);
    var builder = getBuilder()
      .withId("a-legal-id")
      .withRegion(AzureRegion.EAST_ASIA)
      .withMaxTotalThroughput(throughput);

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
    assert (component.getTags()).equals(Map.ofEntries(
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
    assert (component.getTags()).equals(Map.ofEntries(
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
          .build())
      .withMaxTotalThroughput(throughput)
      .withBackupPolicy(AzureCosmosBackupPolicy.builder()
        .withBackupPolicyType(AzureCosmosBackupPolicyType.PERIODIC)
        .withBackupIntervalInMinutes(1440)
        .withBackupRetentionIntervalInHours(720)
        .withBackupStorageRedundancy(BackupStorageRedundancy.GEO)
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
      .withBackupPolicy(AzureCosmosBackupPolicy.builder()
        .withBackupPolicyType(AzureCosmosBackupPolicyType.PERIODIC)
        .withBackupIntervalInMinutes(60)
        .withBackupRetentionIntervalInHours(8)
        .withBackupStorageRedundancy(BackupStorageRedundancy.GEO)
        .build())
      .withRegion(AzureRegion.EAST_ASIA)
      .withAzureResourceGroup(
        AzureResourceGroup.builder()
          .withName(a(String.class))
          .withRegion(AzureRegion.SOUTHEAST_ASIA)
          .build())
      .withMaxTotalThroughput(throughput);

    var component = builder.build();

    assertThat(component.getType()).isEqualTo(getExpectedType());
    assertThat(component)
      .asInstanceOf(InstanceOfAssertFactories.type(AzureCosmosAccount.class))
      .extracting(AzureCosmosAccount::getCosmosEntities, AzureCosmosAccount::getMaxTotalThroughput)
      .containsExactly(Collections.EMPTY_LIST, throughput);
  }

  @Test
  public void typeIsAsExpected_when_BuiltWithoutEntitiesAndMaxTotalThroughputInRegionAndResourceGroup() {
    var builder = getBuilder()
      .withId("a-legal-id")
      .withBackupPolicy(AzureCosmosBackupPolicy.builder()
        .withBackupPolicyType(AzureCosmosBackupPolicyType.PERIODIC)
        .withBackupIntervalInMinutes(60)
        .withBackupRetentionIntervalInHours(8)
        .withBackupStorageRedundancy(BackupStorageRedundancy.GEO)
        .build())
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
      .containsExactly(Collections.EMPTY_LIST, null);
  }

  @Test
  public void typeIsAsExpected_when_BuiltWithEntities() {
    var accountId = "a-legal-id";
    var entities = getValidCosmosEntities();
    var throughput = a(Integer.class);
    var builder = getBuilder()
      .withId(accountId)
      .withRegion(AzureRegion.EAST_ASIA)
      .withMaxTotalThroughput(throughput)
      .withCosmosEntities(entities);

    var component = builder.build();

    assertThat(component.getType()).isEqualTo(getExpectedType());
    assertThat(component)
      .asInstanceOf(InstanceOfAssertFactories.type(AzureCosmosAccount.class))
      .extracting(AzureCosmosAccount::getCosmosEntities, AzureCosmosAccount::getMaxTotalThroughput)
      .containsExactly(entities, throughput);

    assertThat(component.getCosmosEntities().stream().allMatch(x -> x.getDependencies().contains(ComponentId.from(accountId))))
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
      .withMaxTotalThroughput(throughput)
      .withCosmosEntity(entity);

    var component = builder.build();

    assertThat(component.getType()).isEqualTo(getExpectedType());
    assertThat(component)
      .asInstanceOf(InstanceOfAssertFactories.type(AzureCosmosAccount.class))
      .extracting(AzureCosmosAccount::getCosmosEntities, AzureCosmosAccount::getMaxTotalThroughput)
      .containsExactly(List.of(entity), throughput);
  }

  @Test
  public void typeIsAsExpected_when_BuiltWithBackupPolicyAndSingleEntity() {
    var entity = getValidCosmosEntities().stream().findFirst().orElse(null);
    assertThat(entity).isNotNull();
    var throughput = a(Integer.class);
    var backupPolicy = AzureCosmosBackupPolicy.builder()
      .withBackupPolicyType(AzureCosmosBackupPolicyType.PERIODIC)
      .withBackupStorageRedundancy(BackupStorageRedundancy.GEO)
      .withBackupIntervalInMinutes(1440)
      .withBackupRetentionIntervalInHours(720)
      .build();

    var builder = getBuilder()
      .withId("a-legal-id")
      .withRegion(AzureRegion.EAST_ASIA)
      .withMaxTotalThroughput(throughput)
      .withBackupPolicy(backupPolicy)
      .withCosmosEntity(entity);

    var component = builder.build();

    Map<String, Object> jsonMap = JsonPath.read(TestUtils.getJsonRepresentation(component), "$");

    assertThat(jsonMap.keySet()).contains("backupPolicy");
    assertThat(component.getType()).isEqualTo(getExpectedType());
    assertThat(component)
      .asInstanceOf(InstanceOfAssertFactories.type(AzureCosmosAccount.class))
      .extracting(AzureCosmosAccount::getCosmosEntities,
        AzureCosmosAccount::getMaxTotalThroughput,
        AzureCosmosAccount::getBackupPolicy)
      .containsExactly(List.of(entity), throughput, backupPolicy);
  }
}