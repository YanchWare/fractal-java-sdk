package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.livesystem.caas.NodeTaint;
import com.yanchware.fractal.sdk.domain.livesystem.caas.TaintEffect;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks.AzureAgentPoolMode;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks.AzureNodePool;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureNodePoolTest {
  @Test
  public void validationError_when_azureNodePoolWithNullName() {
    assertThatThrownBy(() -> getAzureNodePoolBuilder(null, 30, false).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Name has not been defined and it is required");
  }

  @Test
  public void validationError_when_azureNodePoolWithEmptyName() {
    assertThatThrownBy(() -> getAzureNodePoolBuilder("", 30, false).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Name has not been defined and it is required");
  }

  @Test
  public void validationError_when_azureNodePoolWithBlankName() {
    assertThatThrownBy(() -> getAzureNodePoolBuilder("   ", 30, false).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Name has not been defined and it is required");
  }

  @Test
  public void validationError_when_azureNodePoolWithNullMachineType() {
    assertThatThrownBy(() -> getAzureNodePoolBuilder("test", 30, false).withMachineType(null).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Machine Type has not been defined and it is required");
  }

  @Test
  public void validationError_when_azureNodePoolWithNameThatContainOtherCharacters() {
    assertThatThrownBy(() -> getAzureNodePoolBuilder("name-name", 30, false).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Name should only contain lowercase alphanumeric characters");
  }

  @Test
  public void validationError_when_azureNodePoolWithNameThatContainUpperCase() {
    assertThatThrownBy(() -> getAzureNodePoolBuilder("Name", 30, false).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Name should only contain lowercase alphanumeric characters");
  }

  @Test
  public void validationError_when_azureNodePoolWithNameTooLongForWindows() {
    var builder = getAzureNodePoolBuilder("nametoolong", 30, false);
    builder.withOsType(AzureOsType.WINDOWS);
    assertThatThrownBy(builder::build)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Name for node with Windows OS Type should be between 1 and 6 characters");
  }

  @Test
  public void validationError_when_azureNodePoolWithNameTooLongForLinux() {
    var builder = getAzureNodePoolBuilder("namewaytoooooolong", 30, false);
    builder.withOsType(AzureOsType.LINUX);
    assertThatThrownBy(builder::build)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Name for node with Linux OS Type should be between 1 and 12 characters");
  }

  @Test
  public void validationError_when_azureNodePoolWithDiskSizeLessThan30Gb() {
    assertThatThrownBy(() -> getAzureNodePoolBuilder("name", 29, false).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Disk size must be at least 30GB");
  }

  @Test
  public void validationError_when_SystemNodePoolWithMinNodeCountEqualTo0() {
    assertThatThrownBy(() ->
        AzureNodePool.builder()
            .withName("name")
            .withDiskSizeGb(30)
            .withMachineType(AzureMachineType.STANDARD_B2S)
            .withAutoscalingEnabled(true)
            .withMinNodeCount(0)
            .withMaxNodeCount(3)
            .build()
    )
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("MinNodeCount must be between 1 and 1000, the value entered is: 0");
  }

  @Test
  public void noValidationErrors_when_UserNodePoolWithMinNodeCountEqualTo0() {
    assertThat(AzureNodePool.builder()
        .withName("name")
        .withDiskSizeGb(30)
        .withMachineType(AzureMachineType.STANDARD_B2S)
        .withAgentPoolMode(AzureAgentPoolMode.USER)
        .withAutoscalingEnabled(true)
        .withMinNodeCount(0)
        .withMaxNodeCount(3)
        .build()
        .validate()).isEmpty();
  }

  @Test
  public void noValidationErrors_when_azureNodePoolWithValidFields() {
    assertThat(getAzureNodePoolBuilder("azurenode", 30, false).build().validate()).isEmpty();
  }

  @Test
  public void noValidationErrors_when_azureNodePoolMinimalFields() {
    var builder = AzureNodePool.builder()
        .withName("azurenode")
        .withMachineType(AzureMachineType.STANDARD_B2S);
    assertThat(builder.build().validate()).isEmpty();
  }

  @Test
  public void returns_3_nodeTaints_when_azureNodePoolWithValidFields() {
    var azureNodePool = getAzureNodePoolBuilder("linuxdynamic", 30, false).build();
    assertThat(azureNodePool.getNodeTaints().size()).isEqualTo(3);
  }

  @Test
  public void validationError_when_azureNodePoolWithMaxNodeCountNullAndAutoscalingEnabled() {
    assertThatThrownBy(() -> getAzureNodePoolBuilder("name", 30, true).build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("MinNodeCount has not been defined and it is required when autoscaling is enabled");
  }

  @Test
  public void correctValues_when_azureNodePoolBuilt() {
    var nodePool = getAzureNodePoolBuilder("linux", 50, true)
        .withMinNodeCount(1)
        .withMaxNodeCount(2)
        .build();
    assertThat(nodePool)
        .extracting("name", "diskSizeGb", "autoscalingEnabled", "minNodeCount", "maxNodeCount")
        .contains("linux", 50, true, 1, 2);
  }

  private AzureNodePool.AzureNodePoolBuilder getAzureNodePoolBuilder(String name, int diskSizeGb, boolean autoscalingEnabled) {
    return AzureNodePool.builder()
        .withName(name)
        .withDiskSizeGb(diskSizeGb)
        .withMachineType(AzureMachineType.STANDARD_B2S)
        .withInitialNodeCount(1)
        .withMaxNodeCount(3)
        .withNodeTaint(NodeTaint.builder()
            .withKey("testKey")
            .withValue("testValue")
            .withEffect(TaintEffect.NO_EXECUTE)
            .build())
        .withNodeTaints(List.of(
            NodeTaint.builder().withKey("key1").withValue("value1").withEffect(TaintEffect.NO_SCHEDULE).build(),
            NodeTaint.builder().withKey("key2").withValue("value3").withEffect(TaintEffect.PREFER_NO_SCHEDULE).build()
        ))
        .withAutoscalingEnabled(autoscalingEnabled);
  }

  @Test
  public void correctValues_when_azureNodePoolWithAzureLinuxOsSkuBuilt() {
    var nodePoolName = "azlinuxpool";
    var nodePool = AzureNodePool.builder()
        .withName(nodePoolName)
        .withMachineType(AzureMachineType.STANDARD_B2S)
        .withOsType(AzureOsType.LINUX)
        .withOsSku(AzureOsSku.AZURE_LINUX)
        .build();
    
    
    assertThat(nodePool)
        .extracting("name", "osType", "osSku", "agentPoolMode")
        .contains(nodePoolName, AzureOsType.LINUX, AzureOsSku.AZURE_LINUX, AzureAgentPoolMode.SYSTEM);
  }

  @Test
  public void validationError_when_azureNodePoolWithLinuxOsTypeAndWindowsOsSku() {
    assertThatThrownBy(() -> AzureNodePool.builder()
        .withName("azlinuxpool")
        .withMachineType(AzureMachineType.STANDARD_B2S)
        .withOsType(AzureOsType.LINUX)
        .withOsSku(AzureOsSku.WINDOWS2019)
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Windows OS SKU cannot be used with Linux OS Type");
  }

  @Test
  public void validationError_when_azureNodePoolWithWindowsOsTypeAndAzureLinuxOsSku() {
    assertThatThrownBy(() -> AzureNodePool.builder()
        .withName("windowspool")
        .withMachineType(AzureMachineType.STANDARD_B2S)
        .withOsType(AzureOsType.WINDOWS)
        .withOsSku(AzureOsSku.AZURE_LINUX)
        .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Linux OS SKU cannot be used with Windows OS Type");
  }

  @Test
  public void useGen2VirtualMachineIsTrue_when_defaultConfiguration() {
    var nodePool = getAzureNodePoolBuilder("linux", 50, true)
        .withMinNodeCount(1)
        .withMaxNodeCount(2)
        .build();

    assertThat(nodePool.isUseGen2VirtualMachine())
        .isEqualTo(true);
  }

  @Test
  public void useGen2VirtualMachineIsFalse_when_properlyConfigured() {
    var nodePool = getAzureNodePoolBuilder("linux", 50, true)
        .withUseGen2VirtualMachine(false)
        .withMinNodeCount(1)
        .withMaxNodeCount(2)
        .build();

    assertThat(nodePool.isUseGen2VirtualMachine())
        .isEqualTo(false);
  }
}