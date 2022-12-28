
package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.TestWithFixture;
import com.yanchware.fractal.sdk.valueobjects.ComponentType;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class CosmosEntityTest<T extends AzureCosmosEntityBuilder<?,?>> extends TestWithFixture {

  abstract T getBuilder();
  abstract ComponentType getExpectedType();

  @Test
  public void exceptionThrown_when_componentBuiltWithEmptyValues() {
    assertThatThrownBy(() -> getBuilder().withId("a-legal-id").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("defined no connection to a Cosmos Account, and it is required");
  }

  @Test
  public void exceptionThrown_when_throughputLargerThanMaxThroughput() {
    var throughput = a(Integer.class);
    assertThatThrownBy(() -> getBuilder()
      .withId("a-legal-id")
      .withMaxThroughput(throughput - 1)
      .withThroughput(throughput)
    .build())
    .isInstanceOf(IllegalArgumentException.class)
    .hasMessageContaining("has max throughput defined, but it is less than base throughput");
  }

  @Test
  public void typeIsAsExpected_when_BuiltWithAllRequiredValues() {
    var cosmosAccount = a(String.class);
    var throughput = a(Integer.class);
    var builder = getBuilder()
      .withId("a-legal-id")
      .withMaxThroughput(throughput + 1)
      .withThroughput(throughput)
      .withCosmosAccount(cosmosAccount);

    var component = builder.build();

    assertThat(component.getType()).isEqualTo(getExpectedType());
    assertThat(component)
      .asInstanceOf(InstanceOfAssertFactories.type(AzureCosmosEntity.class))
      .extracting(AzureCosmosEntity::getCosmosAccount, AzureCosmosEntity::getMaxThroughput, AzureCosmosEntity::getThroughput)
      .containsExactly(cosmosAccount, throughput + 1, throughput);
  }
}