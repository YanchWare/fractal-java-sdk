package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.NodeTaint;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
@Builder(setterPrefix = "with")
public class AzureNodePool implements Validatable {
  private final static String NAME_IS_BLANK = "[AzureNodePool Validation] Name has not been defined and it is required";
  private final static String DISK_SIZE_UNDER_30GB = "[AzureNodePool Validation] Disk size must be at least 30GB";
  private final static String SYSTEM_POOL_MODE_WINDOWS = "[AzureNodePool Validation] Pool Mode is set to SYSTEM but that is not supported for a Windows node pool";
  private final static String MAX_NODE_COUNT = "[AzureNodePool Validation] Max node count must be a positive integer (> 0)";
  private final static String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_PATTERN = "[AzureNodePool Validation] %s must be between %d and %d, the value entered is: %d";
  private final static Integer MIN_NUMBER_OF_NODES = 1;
  private final static Integer MAX_NUMBER_OF_NODES = 100;
  private final static Integer MIN_NUMBER_OF_PODS_PER_NODE = MIN_NUMBER_OF_NODES;
  private final static Integer MAX_NUMBER_OF_PODS_PER_NODE = 255;

  private int diskSizeGb;

  @NotNull
  private Integer initialNodeCount;

  private AzureMachineType machineType;
  private int maxNodeCount;
  private int maxSurge;
  private int minNodeCount;
  private String name;

  private Integer maxPodsPerNode;
  @Builder.Default
  private AzureOsType osType = AzureOsType.LINUX;
  @Builder.Default
  private AzureAgentPoolMode agentPoolMode = AzureAgentPoolMode.SYSTEM;

  private SortedSet<String> nodeTaints;

  public static AzureNodePoolBuilder builder() {
    return new AzureNodePoolBuilder(){
      @Override
      public AzureNodePool build() {
        Validate.notBlank(super.name, "NodePool name cannot be null or empty!");

        Validate.inclusiveBetween(MIN_NUMBER_OF_NODES, MAX_NUMBER_OF_NODES, super.initialNodeCount,
            String.format(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_PATTERN, "InitialNodeCount", MIN_NUMBER_OF_NODES, MAX_NUMBER_OF_NODES, super.initialNodeCount));


        if(super.maxPodsPerNode == null) {
          super.maxPodsPerNode = 30;
        }

        return super.build();
      }
    };
  }
  public static class AzureNodePoolBuilder {


    private SortedSet<String> nodeTaints;

    public AzureNodePoolBuilder withInitialNodeCount(int initialNodeCount) {
      this.initialNodeCount = initialNodeCount;

      return this;
    }

    public AzureNodePoolBuilder withMinNodeCount(int minNodeCount) {
      validateIntegerInRange("MinNodeCount", minNodeCount, MIN_NUMBER_OF_NODES, MAX_NUMBER_OF_NODES);
      this.minNodeCount = minNodeCount;

      return this;
    }

    public AzureNodePoolBuilder withMaxNodeCount(int maxNodeCount) {
      validateIntegerInRange("MaxNodeCount", maxNodeCount, MIN_NUMBER_OF_NODES, MAX_NUMBER_OF_NODES);

      this.maxNodeCount = maxNodeCount;

      return this;
    }

    public AzureNodePoolBuilder withMaxPodsPerNode(int maxPodsPerNode) {
      validateIntegerInRange("MaxPodsPerNode", maxPodsPerNode, MIN_NUMBER_OF_PODS_PER_NODE, MAX_NUMBER_OF_PODS_PER_NODE);

      this.maxPodsPerNode = maxPodsPerNode;

      return this;
    }

    private void validateIntegerInRange(String fieldName, int entered, int min, int max) {
      if (entered < min || entered > max) {
        throw new IllegalArgumentException(String.format("%s must be between %d and %d, the value entered is: %d", fieldName, min, max, entered));
      }
    }

    public AzureNodePoolBuilder withNodeTaint(NodeTaint nodeTaint) {
      if (nodeTaints == null) {
        nodeTaints = new TreeSet<>();
      }
      nodeTaints.add(nodeTaint.toString());
      return this;
    }

    public AzureNodePoolBuilder withNodeTaints(List<NodeTaint> nodeTaintList) {
      if (CollectionUtils.isBlank(nodeTaintList)) {
        return this;
      }

      if (nodeTaints == null) {
        nodeTaints = new TreeSet<>();
      }

      for (var nodeTaint : nodeTaintList) {
        nodeTaints.add(nodeTaint.toString());
      }
      return this;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (isBlank(name)) {
      errors.add(NAME_IS_BLANK);
    }

    if (maxNodeCount <= 0) {
      errors.add(NAME_IS_BLANK);
    }

    if (diskSizeGb < 30) {
      errors.add(DISK_SIZE_UNDER_30GB);
    }

    if (agentPoolMode == AzureAgentPoolMode.SYSTEM && osType == AzureOsType.WINDOWS) {
      errors.add(SYSTEM_POOL_MODE_WINDOWS);
    }

    return errors;
  }
}

