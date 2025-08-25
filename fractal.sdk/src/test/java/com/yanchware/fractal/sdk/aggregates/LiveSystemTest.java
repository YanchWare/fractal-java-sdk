package com.yanchware.fractal.sdk.aggregates;

import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemAggregate;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemIdValue;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemsFactory;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupType;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.UUID;

import static com.yanchware.fractal.sdk.utils.TestUtils.getDefaultAks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LiveSystemTest {

  private static ResourceGroupId validResourceGroupId = new ResourceGroupId(ResourceGroupType.PERSONAL, UUID.randomUUID(), "rg");
  LiveSystemsFactory factory;

  @BeforeEach
  public void setup() {
    factory = new LiveSystemsFactory(
      HttpClient.newBuilder().build(),
      new LocalSdkConfiguration(""),
      RetryRegistry.ofDefaults());
  }

  @Test
  public void multipleValidationErrors_when_liveSystemHasNoFields() {
    assertThatThrownBy(() -> factory.builder().build()).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll("Id has not been defined");
  }

  @Test
  public void multipleValidationErrors_when_liveSystemHasNullId() {
    assertThatThrownBy(() -> factory.builder().withId(null).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Id has not been defined");
  }

  @Test
  public void multipleValidationErrors_when_liveSystemHasNullName() {
    assertThatThrownBy(() -> factory.builder().withId(new LiveSystemIdValue(validResourceGroupId, null)).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Name has not been defined");
  }

  @Test
  public void multipleValidationErrors_when_liveSystemHasEmptyId() {
    assertThatThrownBy(() -> factory.builder().withId(new LiveSystemIdValue(validResourceGroupId, "")).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Name has not been defined");
  }

  @Test
  public void multipleValidationErrors_when_liveSystemHasBlankId() {
    assertThatThrownBy(() -> factory.builder().withId(new LiveSystemIdValue(validResourceGroupId, "    ")).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Name has not been defined");
  }

  @Test
  public void multipleValidationErrors_when_liveSystemHasNullResourceGroupId() {
    assertThatThrownBy(() -> factory.builder().withId(new LiveSystemIdValue(null, "xxx")).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("ResourceGroupId has not been defined and it is required");
  }

  @Test
  public void multipleValidationErrors_when_liveSystemHasNoComponents() {
    assertThatThrownBy(() -> factory.builder()
      .withId(new LiveSystemIdValue(validResourceGroupId, "ls"))
      .withStandardProvider(ProviderType.AWS)
      .build().instantiate()).isInstanceOf(InstantiatorException.class)
      .hasMessageContaining("Components list is null or empty and at least one component is required");
  }

  @Test
  public void noValidationErrors_when_liveSystemWithValidFields() {
    assertThat(generateBuilder().validate()).isEmpty();
  }

  private LiveSystemAggregate generateBuilder() {
    return factory.builder()
      .withId(new LiveSystemIdValue(validResourceGroupId, "ls"))
      .withStandardProvider(ProviderType.AZURE)
      .withComponent(
        getDefaultAks()
          .build())
      .build();
  }
}