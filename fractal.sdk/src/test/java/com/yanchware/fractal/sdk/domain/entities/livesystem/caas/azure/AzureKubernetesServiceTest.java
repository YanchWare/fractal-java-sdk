package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.PriorityClass;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureAgentPoolMode;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureNodePool;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure.AzureOsType;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KUBERNETES;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;

public class AzureKubernetesServiceTest {

  @Test
  public void noValidationErrors_when_aksHasRequiredFields() {
    var aks = generateBuilder().build();
    assertThat(aks.validate()).isEmpty();
    assertThat(aks.getNodePools()).first()
        .extracting(AzureNodePool::getAgentPoolMode, AzureNodePool::getOsType)
        .containsExactly(AzureAgentPoolMode.SYSTEM, AzureOsType.LINUX);
  }

  @Test
  public void noValidationErrors_when_aksHasRequiredFieldsAndMultipleNodePool() {
    var aks = generateBuilder().withNodePool(
            AzureNodePool.builder()
                .withName("windows-node-pool-name")
                .withDiskSizeGb(30)
                .withOsType(AzureOsType.WINDOWS)
                .withAgentPoolMode(AzureAgentPoolMode.USER)
                .withInitialNodeCount(1)
                .withAutoscalingEnabled(false)
                .build())
        .build();
    assertThat(aks.validate()).isEmpty();
    assertThat(aks.getNodePools()).first()
        .extracting(AzureNodePool::getAgentPoolMode)
        .isEqualTo(AzureAgentPoolMode.SYSTEM);
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithNullId() {
    assertThatThrownBy(() -> generateBuilder().withId("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("A valid component id cannot be null, empty or contain spaces");
  }

  @Test
  public void typeIsKubernetes_when_aksIsBuiltWithoutSpecifyType() {
    var aksBuilder = generateBuilder();
    assertThat(aksBuilder.build().getType()).isEqualTo(KUBERNETES);
    assertThatCode(aksBuilder::build).doesNotThrowAnyException();
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithNullNodePools() {
    var aks = AzureKubernetesService.builder()
        .withId(ComponentId.from("test"));
    assertThatThrownBy(aks::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Node pool list is null or empty");
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithEmptyNodePools() {
    var aks = AzureKubernetesService.builder()
        .withId(ComponentId.from("test"))
        .withNodePools(emptyList());
    assertThatThrownBy(aks::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Node pool list is null or empty");
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithPriorityClassValueNegative() {
    var aks = generateBuilder()
        .withPriorityClass(PriorityClass.builder().withValue(-123).build());
    assertThatThrownBy(aks::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Value must be between 0 and 2_000_000_000");
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithPriorityClassValueOverMax() {
    var aks = generateBuilder()
        .withPriorityClass(PriorityClass.builder().withValue(2_000_000_001).build());
    assertThatThrownBy(aks::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Value must be between 0 and 2_000_000_000");
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithWindowsSystemNodePools() {
    assertThatThrownBy(() -> generateBuilder().withNodePool(
            AzureNodePool.builder()
                .withName("broken-node-pool-name")
                .withDiskSizeGb(30)
                .withOsType(AzureOsType.WINDOWS)
                .build())
        .build()
        .validate())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Pool Mode is set to SYSTEM");
  }

  private AzureKubernetesService.AzureKubernetesServiceBuilder generateBuilder() {
    return AzureKubernetesService.builder()
        .withId(ComponentId.from("test"))
        .withNodePool(AzureNodePool.builder()
            .withName("azure-node-pool-name")
            .withDiskSizeGb(30)
            .withInitialNodeCount(1)
            .withAutoscalingEnabled(false).build());
  }
}