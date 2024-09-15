package com.yanchware.fractal.sdk.aggregates;

import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemAggregate;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemsFactory;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;

import static com.yanchware.fractal.sdk.utils.TestUtils.getDefaultAks;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LiveSystemTest {

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
    assertThatThrownBy(() -> factory.builder().build()).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll("Id has not been defined", "ResourceGroupId has not been defined and it is required");
  }

  @Test
  public void multipleValidationErrors_when_liveSystemHasNullId() {
    assertThatThrownBy(() -> factory.builder().withName(null).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Id has not been defined");
  }

  @Test
  public void multipleValidationErrors_when_liveSystemHasEmptyId() {
    assertThatThrownBy(() -> factory.builder().withName("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Id has not been defined");
  }

  @Test
  public void multipleValidationErrors_when_liveSystemHasBlankId() {
    assertThatThrownBy(() -> factory.builder().withName("   ").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Id has not been defined");
  }

  @Test
  public void multipleValidationErrors_when_liveSystemHasNullResourceGroupId() {
    assertThatThrownBy(() -> factory.builder().withResourceGroupId(null).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("ResourceGroupId has not been defined and it is required");
  }

  @Test
  public void multipleValidationErrors_when_liveSystemHasEmptyResourceGroupId() {
    assertThatThrownBy(() -> factory.builder().withResourceGroupId("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("ResourceGroupId has not been defined and it is required");
  }

  @Test
  public void multipleValidationErrors_when_liveSystemHasBlankResourceGroupId() {
    assertThatThrownBy(() -> factory.builder().withResourceGroupId("   ").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("ResourceGroupId has not been defined and it is required");
  }

  @Test
  public void multipleValidationErrors_when_liveSystemHasNoComponents() {
    assertThatThrownBy(() -> factory.builder()
            .withResourceGroupId("res/group")
            .withName("ls")
            .build().instantiate()).isInstanceOf(InstantiatorException.class).hasMessageContaining("Components list is null or empty and at least one component is required");
  }

  @Test
  public void noValidationErrors_when_liveSystemWithValidFields() {
    assertThat(generateBuilder().validate()).isEmpty();
  }

  private LiveSystemAggregate generateBuilder() {
    return factory.builder()
        .withResourceGroupId("res/group")
        .withName("ls")
        .withComponent(
            getDefaultAks()
                .build())
        .build();
  }
}