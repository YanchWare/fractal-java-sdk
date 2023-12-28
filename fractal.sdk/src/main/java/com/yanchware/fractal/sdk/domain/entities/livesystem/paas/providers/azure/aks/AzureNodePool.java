package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.aks;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.NodeTaint;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureMachineType;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureOsSku;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureOsType;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static com.yanchware.fractal.sdk.utils.ValidationUtils.validateIntegerInRange;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureNodePool implements Validatable {
  private final static String NAME_REGEX = "^[a-z\\d]+$";
  private final static String NAME_IS_BLANK = "[AzureNodePool Validation] Name has not been defined and it is required";
  private final static String NAME_ONLY_LOWERCASE_ALPHANUMERIC = "[AzureNodePool Validation] Name should only contain lowercase alphanumeric characters";
  private final static String LINUX_NAME_IS_TOO_LONG = "[AzureNodePool Validation] Name for node with Linux OS Type should be between 1 and 12 characters";
  private final static String WINDOWS_NAME_IS_TOO_LONG = "[AzureNodePool Validation] Name for node with Windows OS Type should be between 1 and 6 characters";
  private final static String DISK_SIZE_UNDER_30GB = "[AzureNodePool Validation] Disk size must be at least 30GB";
  private final static String SYSTEM_POOL_MODE_WINDOWS = "[AzureNodePool Validation] Pool Mode is set to SYSTEM but that is not supported for a Windows node pool";
  private final static String MAX_NODE_COUNT = "[AzureNodePool Validation] Max node count must be a positive integer (> 0)";
  private final static String MIN_NODE_COUNT_IS_NULL = "[AzureNodePool Validation] MinNodeCount has not been defined and it is required when autoscaling is enabled";
  private final static String MAX_NODE_COUNT_IS_NULL = "[AzureNodePool Validation] MaxNodeCount has not been defined and it is required when autoscaling is enabled";
  private final static String MACHINE_TYPE_IS_NULL = "[AzureNodePool Validation] Machine Type has not been defined and it is required";
  private final static Integer MIN_NUMBER_OF_USER_NODE_POOLS = 0;
  private final static Integer MIN_NUMBER_OF_SYSTEM_NODE_POOLS = 1;
  private final static Integer MAX_NUMBER_OF_NODE_POOLS = 1000;
  private final static Integer MIN_NUMBER_OF_PODS_PER_NODE = 0;
  private final static Integer MAX_NUMBER_OF_PODS_PER_NODE = 255;

  private Integer diskSizeGb;
  private Integer initialNodeCount;
  private AzureMachineType machineType;
  private Integer maxNodeCount;
  private Integer maxSurge;
  private Integer minNodeCount;
  private String name;
  private Integer maxPodsPerNode;
  private AzureOsType osType;
  private AzureOsSku osSku;
  private AzureAgentPoolMode agentPoolMode;
  private SortedSet<String> nodeTaints;
  private boolean autoscalingEnabled;

  public static AzureNodePoolBuilder builder() {
    return new AzureNodePoolBuilder();
  }


  public static class AzureNodePoolBuilder {
    private final AzureNodePool nodePool;
    private final AzureNodePoolBuilder builder;

    public AzureNodePoolBuilder() {
      this.nodePool = new AzureNodePool();
      this.builder = this;
    }

    public AzureNodePoolBuilder withDiskSizeGb(Integer diskSizeGb) {
      nodePool.setDiskSizeGb(diskSizeGb);
      return builder;
    }

    public AzureNodePoolBuilder withInitialNodeCount(Integer initialNodeCount) {
      nodePool.setInitialNodeCount(initialNodeCount);
      return builder;
    }

    public AzureNodePoolBuilder withMachineType(AzureMachineType machineType) {
      nodePool.setMachineType(machineType);
      return builder;
    }

    public AzureNodePoolBuilder withMaxNodeCount(Integer maxNodeCount) {
      nodePool.setMaxNodeCount(maxNodeCount);
      return builder;
    }

    public AzureNodePoolBuilder withMaxSurge(Integer maxSurge) {
      nodePool.setMaxSurge(maxSurge);
      return builder;
    }

    public AzureNodePoolBuilder withMinNodeCount(Integer minNodeCount) {
      nodePool.setMinNodeCount(minNodeCount);
      return builder;
    }

    public AzureNodePoolBuilder withName(String name) {
      nodePool.setName(name);
      return builder;
    }

    public AzureNodePoolBuilder withMaxPodsPerNode(Integer maxPodsPerNode) {
      nodePool.setMaxPodsPerNode(maxPodsPerNode);
      return builder;
    }

    public AzureNodePoolBuilder withOsType(AzureOsType osType) {
      nodePool.setOsType(osType);
      return builder;
    }

    public AzureNodePoolBuilder withOsSku(AzureOsSku osSku) {
      nodePool.setOsSku(osSku);
      return builder;
    }

    public AzureNodePoolBuilder withAgentPoolMode(AzureAgentPoolMode agentPoolMode) {
      nodePool.setAgentPoolMode(agentPoolMode);
      return builder;
    }

    public AzureNodePoolBuilder withNodeTaint(NodeTaint nodeTaint) {
      return withNodeTaints(List.of(nodeTaint));
    }

    public AzureNodePoolBuilder withNodeTaints(List<NodeTaint> nodeTaints) {
      if (CollectionUtils.isBlank(nodeTaints)) {
        return builder;
      }

      if (nodePool.getNodeTaints() == null) {
        nodePool.setNodeTaints(new TreeSet<>());
      }

      nodeTaints.forEach(nodeTaint -> nodePool.getNodeTaints()
          .add(nodeTaint.toString()));

      return builder;
    }

    public AzureNodePoolBuilder withAutoscalingEnabled(boolean autoscalingEnabled) {
      nodePool.setAutoscalingEnabled(autoscalingEnabled);
      return builder;
    }

    public AzureNodePool build() {

      if (nodePool.getOsType() == null) {
        nodePool.setOsType(AzureOsType.LINUX);
      }

      if (nodePool.getAgentPoolMode() == null) {
        nodePool.setAgentPoolMode(AzureAgentPoolMode.SYSTEM);
      }

      var errors = nodePool.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format("AzureNodePool validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return nodePool;
    }
  }

  @Override
  public Collection<String> validate() {

    Collection<String> errors = new ArrayList<>();

    if (isBlank(this.name)) {
      errors.add(NAME_IS_BLANK);
    }

    if (!isBlank(this.name) && !this.name.matches(NAME_REGEX)) {
      errors.add(NAME_ONLY_LOWERCASE_ALPHANUMERIC);
    }

    if (!isBlank(this.name) && this.osType == AzureOsType.LINUX && this.name.length() > 12) {
      errors.add(LINUX_NAME_IS_TOO_LONG);
    }

    if (!isBlank(this.name) && this.osType == AzureOsType.WINDOWS && this.name.length() > 6) {
      errors.add(WINDOWS_NAME_IS_TOO_LONG);
    }

    if (this.machineType == null) {
      errors.add(MACHINE_TYPE_IS_NULL);
    }

    if (this.diskSizeGb != null && this.diskSizeGb < 30) {
      errors.add(DISK_SIZE_UNDER_30GB);
    }

    if (this.agentPoolMode == AzureAgentPoolMode.SYSTEM && this.osType == AzureOsType.WINDOWS) {
      errors.add(SYSTEM_POOL_MODE_WINDOWS);
    }

    if (this.initialNodeCount != null && this.agentPoolMode == AzureAgentPoolMode.USER) {
      validateIntegerInRange("InitialNodeCount", this.initialNodeCount, MIN_NUMBER_OF_USER_NODE_POOLS, MAX_NUMBER_OF_NODE_POOLS, errors);
    }

    if (this.initialNodeCount != null && this.agentPoolMode == AzureAgentPoolMode.SYSTEM) {
      validateIntegerInRange("InitialNodeCount", this.initialNodeCount, MIN_NUMBER_OF_SYSTEM_NODE_POOLS, MAX_NUMBER_OF_NODE_POOLS, errors);
    }

    if (this.maxNodeCount != null && this.agentPoolMode == AzureAgentPoolMode.USER) {
      validateIntegerInRange("MaxNodeCount", this.maxNodeCount, MIN_NUMBER_OF_USER_NODE_POOLS, MAX_NUMBER_OF_NODE_POOLS, errors);
    }

    if (this.maxNodeCount != null && this.agentPoolMode == AzureAgentPoolMode.SYSTEM) {
      validateIntegerInRange("MaxNodeCount", this.maxNodeCount, MIN_NUMBER_OF_SYSTEM_NODE_POOLS, MAX_NUMBER_OF_NODE_POOLS, errors);
    }

    if (this.minNodeCount != null && this.agentPoolMode == AzureAgentPoolMode.USER) {
      validateIntegerInRange("MinNodeCount", this.minNodeCount, MIN_NUMBER_OF_USER_NODE_POOLS, MAX_NUMBER_OF_NODE_POOLS, errors);
    }

    if (this.minNodeCount != null && this.agentPoolMode == AzureAgentPoolMode.SYSTEM) {
      validateIntegerInRange("MinNodeCount", this.minNodeCount, MIN_NUMBER_OF_SYSTEM_NODE_POOLS, MAX_NUMBER_OF_NODE_POOLS, errors);
    }

    if (this.maxPodsPerNode != null) {
      validateIntegerInRange("MaxPodsPerNode", this.maxPodsPerNode, MIN_NUMBER_OF_PODS_PER_NODE, MAX_NUMBER_OF_PODS_PER_NODE, errors);
    }

    if (this.autoscalingEnabled) {
      if (this.maxNodeCount == null) {
        errors.add(MAX_NODE_COUNT_IS_NULL);
      }

      if (this.minNodeCount == null) {
        errors.add(MIN_NODE_COUNT_IS_NULL);
      }
    }

    if (this.osType == AzureOsType.LINUX &&
        this.osSku != null && (
        this.osSku.equals(AzureOsSku.WINDOWS2019) ||
            this.osSku.equals(AzureOsSku.WINDOWS2022))) {
      errors.add("Windows OS SKU cannot be used with Linux OS Type");
    }

    if (this.osType == AzureOsType.WINDOWS &&
        this.osSku != null &&
        (this.osSku.equals(AzureOsSku.UBUNTU) ||
            this.osSku.equals(AzureOsSku.AZURE_LINUX) ||
            this.osSku.equals(AzureOsSku.CBLMARINER))) {
      errors.add("Linux OS SKU cannot be used with Windows OS Type");
    }

    return errors;
  }
}