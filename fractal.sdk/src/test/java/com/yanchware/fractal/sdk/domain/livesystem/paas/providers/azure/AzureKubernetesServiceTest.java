package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.livesystem.caas.PreemptionPolicy;
import com.yanchware.fractal.sdk.domain.livesystem.caas.PriorityClass;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks.AzureAgentPoolMode;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks.AzureNodePool;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks.ManagedClusterSkuTier;
import com.yanchware.fractal.sdk.utils.TestUtils;
import com.yanchware.fractal.sdk.domain.values.ComponentId;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion.WEST_EUROPE;
import static com.yanchware.fractal.sdk.utils.TestUtils.getAksBuilder;
import static com.yanchware.fractal.sdk.utils.TestUtils.getDefaultAks;
import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_KUBERNETES;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AzureKubernetesServiceTest {

  @Test
  public void noValidationErrors_when_aksHasRequiredFields() {
    var aks = getDefaultAks().build();
    assertThat(aks.validate()).isEmpty();
    assertThat(aks.getManagedClusterSkuTier()).isEqualTo(ManagedClusterSkuTier.FREE);
    assertThat(aks.getNodePools()).first()
        .extracting(AzureNodePool::getAgentPoolMode, AzureNodePool::getOsType)
        .containsExactly(AzureAgentPoolMode.SYSTEM, AzureOsType.LINUX);
  }
  
  @Test
  public void noValidationErrors_when_aksHasRequiredFieldsAndManagedClusterSkuTierChanged() {
    var aks = getDefaultAks()
        .withManagedClusterSkuTier(ManagedClusterSkuTier.PREMIUM)
        .build();
    assertThat(aks.validate()).isEmpty();
    
    assertThat(aks.getManagedClusterSkuTier()).isEqualTo(ManagedClusterSkuTier.PREMIUM);
  }

  @Test
  public void noValidationErrors_when_getAksBuilderHasRequiredFields() {
    var aks = getAksBuilder().build();
    var json = TestUtils.getJsonRepresentation(aks);
    assertThat(aks.validate()).isEmpty();
    assertThat(json).isNotBlank();
    assertThat(aks.getNodePools()).first()
        .extracting(AzureNodePool::getAgentPoolMode, AzureNodePool::getOsType)
        .containsExactly(AzureAgentPoolMode.SYSTEM, AzureOsType.LINUX);
  }

  @Test
  public void noValidationErrors_when_aksHasRequiredFieldsAndMultipleNodePool() {
    var aks = getDefaultAks().withNodePool(
            AzureNodePool.builder()
                .withName("winds")
                .withDiskSizeGb(30)
                .withMachineType(AzureMachineType.STANDARD_B2S)
                .withOsType(AzureOsType.WINDOWS)
                .withAgentPoolMode(AzureAgentPoolMode.USER)
                .withInitialNodeCount(1)
                .withAutoscalingEnabled(false)
                .build())
        .withPriorityClass(PriorityClass.builder()
            .withName("priority-class")
            .withDescription("description")
            .withPreemptionPolicy(PreemptionPolicy.PREEMPT_LOWER_PRIORITY)
            .withValue(1)
            .build())
        .build();

    assertThat(aks.validate()).isEmpty();
    assertThat(aks.getNodePools()).first()
        .extracting(AzureNodePool::getAgentPoolMode)
        .isEqualTo(AzureAgentPoolMode.SYSTEM);
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithNullId() {
    assertThatThrownBy(() -> getDefaultAks().withId("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("A valid component id cannot be null, empty or contain spaces");
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithNullRegion() {
    assertThatThrownBy(() -> getDefaultAks().withRegion(null).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Region is not specified and it is required");
  }

  @Test
  public void typeIsKubernetes_when_aksIsBuiltWithoutSpecifyType() {
    var aksBuilder = getDefaultAks();
    assertThat(aksBuilder.build().getType()).isEqualTo(PAAS_KUBERNETES);
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
    var priorityClass = PriorityClass.builder().withValue(-123);
    assertThatThrownBy(priorityClass::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Value must be between 1 and 1_000_000_000");
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithPriorityClassValueOverMax() {
    var priorityClass = PriorityClass.builder().withValue(2_000_000_001);
    assertThatThrownBy(priorityClass::build).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Value must be between 1 and 1_000_000_000");
  }

  @Test
  public void exceptionThrown_when_aksCreatedWithWindowsSystemNodePools() {
    assertThatThrownBy(() -> getDefaultAks().withNodePool(
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

  @Test
  public void noValidationErrors_when_aksHasRequiredFieldsAndMultipleNodePoolWithCustomMachineType() {
    var machineType = "This_Not_Exist_For_Sure";
    var aks = AzureKubernetesService.builder()
        .withId(ComponentId.from("test"))
        .withRegion(WEST_EUROPE)
        .withResourceGroup(AzureResourceGroup.builder()
            .withRegion(WEST_EUROPE)
            .withName("rg-test")
            .build())
        .withNodePool(
            AzureNodePool.builder()
                .withName("winds")
                .withDiskSizeGb(30)
                .withMachineType(AzureMachineType.fromString(machineType))
                .withOsType(AzureOsType.WINDOWS)
                .withAgentPoolMode(AzureAgentPoolMode.USER)
                .withInitialNodeCount(1)
                .withAutoscalingEnabled(false)
                .build())
        .withPriorityClass(PriorityClass.builder()
            .withName("priority-class")
            .withDescription("description")
            .withPreemptionPolicy(PreemptionPolicy.PREEMPT_LOWER_PRIORITY)
            .withValue(1)
            .build())
        .build();

    var json = TestUtils.getJsonRepresentation(aks);
    assertThat(aks.validate()).isEmpty();
    assertThat(json).isNotBlank();
    assertThat(aks.getNodePools()).first()
        .extracting(a -> a.getMachineType().toString())
        .isEqualTo(machineType);
  }
}