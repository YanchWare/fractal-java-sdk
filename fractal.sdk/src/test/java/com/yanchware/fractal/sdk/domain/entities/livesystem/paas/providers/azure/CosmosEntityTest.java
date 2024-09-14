
package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.TestWithFixture;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosEntityBuilder;
import com.yanchware.fractal.sdk.domain.values.ComponentId;
import com.yanchware.fractal.sdk.domain.values.ComponentType;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class CosmosEntityTest<T extends AzureCosmosEntityBuilder<? extends AzureCosmosEntity, ?>> extends TestWithFixture {

  abstract T getBuilder();

  abstract ComponentType getExpectedType();

  @Test
  public void exceptionThrown_when_componentBuiltWithEmptyValues() {
    assertThat(AzureCosmosEntity.validateCosmosEntity(getBuilder().withId("a-legal-id").build()))
        .isNotEmpty()
        .anyMatch(x -> x.contains("Region has not been defined and it is required"));
  }

  @Test
  public void exceptionThrown_when_throughputLargerThanMaxThroughput() {
    assertThat(AzureCosmosEntity.validateCosmosEntity(getBuilder()
        .withId("a-legal-id")
        .withMaxThroughput(a(Integer.class))
        .withThroughput(a(Integer.class))
        .build()))
        .isNotEmpty()
        .anyMatch(x -> x.contains("Defined both throughput and max throughput. Only one of them can be defined and not both"));
  }

  @Test
  public void typeIsAsExpected_when_BuiltWithAllRequiredValues() {
    var cosmosAccount = String.format("comp-%s", a(String.class));
    var throughput = a(Integer.class);
    var builder = getBuilder()
        .withId("a-legal-id")
        .withName("a-legal-name")
        .withCosmosAccount(ComponentId.from(cosmosAccount))
        .withMaxThroughput(throughput + 1)
        .withThroughput(throughput);

    var component = builder.build();

    assertThat(component.getType()).isEqualTo(getExpectedType());
    assertThat(component)
        .asInstanceOf(InstanceOfAssertFactories.type(AzureCosmosEntity.class))
        .extracting(AzureCosmosEntity::getMaxThroughput, AzureCosmosEntity::getThroughput)
        .containsExactly(throughput + 1, throughput);
  }

  @Test
  public void exceptionThrown_when_nameIsToLong() {
    var throughput = a(Integer.class);
    assertThat(AzureCosmosEntity.validateCosmosEntity(getBuilder()
        .withId("a-legal-id")
        .withName("MaximumLengthOfDatabaseOrContainerName_MaximumLengthOfDatabaseOrContainerName_MaximumLengthOfDatabaseOrContainerName_MaximumLengthOfDatabaseOrContainerName_MaximumLengthOfDatabaseOrContainerName_MaximumLengthOfDatabaseOrContainerName_MaximumLengthOfDatabaseOrContainerName")
        .withMaxThroughput(throughput + 1)
        .withThroughput(throughput)
        .build()))
        .isNotEmpty()
        .anyMatch(x -> x.contains("The Name is invalid. Ensure to provide a unique non-empty string less than '255' characters"));
  }
}