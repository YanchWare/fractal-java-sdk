package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;


import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.NodeTaint;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.TaintEffect;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureNodePool;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureOsType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureMachineType.STANDARD_B2S;
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
  public void noValidationErrors_when_azureNodePoolWithValidFields() {
    assertThat(getAzureNodePoolBuilder("azurenode", 30, false).build().validate()).isEmpty();
  }

  @Test
  public void noValidationErrors_when_azureNodePoolMinimalFields() {
    var builder = AzureNodePool.builder()
        .withName("azurenode")
        .withMachineType(STANDARD_B2S);
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
        .extracting("name", "diskSizeGb", "autoscalingEnabled", "kubernetesVersion", "minNodeCount", "maxNodeCount")
        .contains("linux", 50, true, "1.1.1", 1, 2);
  }

  private AzureNodePool.AzureNodePoolBuilder getAzureNodePoolBuilder(String name, int diskSizeGb, boolean autoscalingEnabled) {
    return AzureNodePool.builder()
        .withName(name)
        .withDiskSizeGb(diskSizeGb)
        .withMachineType(STANDARD_B2S)
        .withInitialNodeCount(1)
        .withMaxNodeCount(3)
        .withKubernetesVersion("1.1.1")
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
}