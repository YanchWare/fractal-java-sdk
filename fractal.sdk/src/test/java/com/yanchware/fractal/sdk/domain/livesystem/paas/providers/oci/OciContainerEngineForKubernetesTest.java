package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci;

import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.utils.TestUtils.getDefaultOke;
import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_KUBERNETES;
import static org.assertj.core.api.Assertions.*;

public class OciContainerEngineForKubernetesTest {

  @Test
  public void noValidationErrors_when_okeHasRequiredFields() {
    var oke = getDefaultOke().build();
    assertThat(oke.validate()).isEmpty();
  }

  @Test
  public void exceptionThrown_when_okeCreatedWithNullId() {
    assertThatThrownBy(() -> getDefaultOke().withId("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("A valid component id cannot be null, empty or contain spaces");
  }

  @Test
  public void exceptionThrown_when_okeCreatedWithNullRegion() {
    assertThatThrownBy(() -> getDefaultOke().withRegion(null).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Region is not specified and it is required");
  }

  @Test
  public void typeIsKubernetes_when_okeIsBuiltWithoutSpecifyType() {
    var okeBuilder = getDefaultOke();
    assertThat(okeBuilder.build().getType()).isEqualTo(PAAS_KUBERNETES);
    assertThatCode(okeBuilder::build).doesNotThrowAnyException();
  }
}