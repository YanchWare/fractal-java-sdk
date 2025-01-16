package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws;

import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_KUBERNETES;
import static com.yanchware.fractal.sdk.utils.TestUtils.getDefaultEks;
import static org.assertj.core.api.Assertions.*;

public class AwsElasticKubernetesServiceTest {

  @Test
  public void noValidationErrors_when_eksHasRequiredFields() {
    var eks = getDefaultEks().build();
    assertThat(eks.validate()).isEmpty();
  }

  @Test
  public void exceptionThrown_when_eksCreatedWithNullId() {
    assertThatThrownBy(() -> getDefaultEks().withId("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("A valid component id cannot be null, empty or contain spaces");
  }

  @Test
  public void exceptionThrown_when_eksCreatedWithNullRegion() {
    assertThatThrownBy(() -> getDefaultEks().withRegion(null).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Region is not specified and it is required");
  }

  @Test
  public void typeIsKubernetes_when_eksIsBuiltWithoutSpecifyType() {
    var eksBuilder = getDefaultEks();
    assertThat(eksBuilder.build().getType()).isEqualTo(PAAS_KUBERNETES);
    assertThatCode(eksBuilder::build).doesNotThrowAnyException();
  }
}