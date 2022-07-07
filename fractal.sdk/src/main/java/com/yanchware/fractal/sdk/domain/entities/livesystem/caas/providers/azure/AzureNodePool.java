package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.NodeTaint;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static com.yanchware.fractal.sdk.utils.ValidationUtils.validateIntegerInRange;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(setterPrefix = "with")
public class AzureNodePool implements Validatable {
  private final static String NAME_IS_BLANK = "[AzureNodePool Validation] Name has not been defined and it is required";
  private final static String DISK_SIZE_UNDER_30GB = "[AzureNodePool Validation] Disk size must be at least 30GB";
  private final static String SYSTEM_POOL_MODE_WINDOWS = "[AzureNodePool Validation] Pool Mode is set to SYSTEM but that is not supported for a Windows node pool";
  private final static String MAX_NODE_COUNT = "[AzureNodePool Validation] Max node count must be a positive integer (> 0)";
  private final static String MIN_NODE_COUNT_IS_NULL = "[AzureNodePool Validation] MinNodeCount has not been defined and it is required when autoscaling is enabled";
  private final static String MAX_NODE_COUNT_IS_NULL = "[AzureNodePool Validation] MaxNodeCount has not been defined and it is required when autoscaling is enabled";
  private final static String INITIAL_NODE_COUNT_IS_NULL = "[AzureNodePool Validation] InitialNodeCount has not been defined and it is required";
  private final static Integer MIN_NUMBER_OF_NODES = 1;
  private final static Integer MAX_NUMBER_OF_NODES = 100;
  private final static Integer MIN_NUMBER_OF_PODS_PER_NODE = MIN_NUMBER_OF_NODES;
  private final static Integer MAX_NUMBER_OF_PODS_PER_NODE = 255;

  private int diskSizeGb;

  private Integer initialNodeCount;

  private AzureMachineType machineType;
  private Integer maxNodeCount;
  private Integer maxSurge;
  private Integer minNodeCount;
  private String name;

  private Integer maxPodsPerNode;

  private AzureOsType osType;

  private AzureAgentPoolMode agentPoolMode;

  private SortedSet<String> nodeTaints;

  private boolean autoscalingEnabled;

  public static AzureNodePoolBuilder builder() {
    return new AzureNodePoolBuilder(){
      @Override
      public AzureNodePool build() {

        if(super.osType == null) {
          super.osType = AzureOsType.LINUX;
        }

        if(super.agentPoolMode == null) {
          super.agentPoolMode = AzureAgentPoolMode.SYSTEM;
        }

        Collection<String> errors = getValidationErrors();

        if(!errors.isEmpty()) {
          throw new IllegalArgumentException(String.join("; ", errors));
        }

        return super.build();
      }

      public Collection<String> getValidationErrors() {
        Collection<String> errors = new ArrayList<>();

        if (isBlank(super.name)) {
          errors.add(NAME_IS_BLANK);
        }

        if (super.diskSizeGb < 30) {
          errors.add(DISK_SIZE_UNDER_30GB);
        }

        if (super.agentPoolMode == AzureAgentPoolMode.SYSTEM && super.osType == AzureOsType.WINDOWS) {
          errors.add(SYSTEM_POOL_MODE_WINDOWS);
        }

        if(super.initialNodeCount == null) {
          errors.add(INITIAL_NODE_COUNT_IS_NULL);
        }

        if(super.initialNodeCount != null) {
          validateIntegerInRange("InitialNodeCount", super.initialNodeCount,
              MIN_NUMBER_OF_NODES,MAX_NUMBER_OF_NODES, errors);
        }

        if(super.maxNodeCount != null) {
          validateIntegerInRange("MaxNodeCount", super.maxNodeCount,
              MIN_NUMBER_OF_NODES,MAX_NUMBER_OF_NODES, errors);
        }

        if(super.minNodeCount != null) {
          validateIntegerInRange("MinNodeCount", super.minNodeCount,
              MIN_NUMBER_OF_NODES,MAX_NUMBER_OF_NODES, errors);
        }

        if(super.maxPodsPerNode != null) {
          validateIntegerInRange("MaxPodsPerNode", super.maxPodsPerNode,
              MIN_NUMBER_OF_PODS_PER_NODE, MAX_NUMBER_OF_PODS_PER_NODE, errors);
        }

        if(super.autoscalingEnabled) {
          if(super.maxNodeCount == null) {
            errors.add(MAX_NODE_COUNT_IS_NULL);
          }

          if(super.minNodeCount == null) {
            errors.add(MIN_NODE_COUNT_IS_NULL);
          }
        }

        return errors;
      }
    };
  }
  public static class AzureNodePoolBuilder {

    private SortedSet<String> nodeTaints;

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

    if (isBlank(this.name)) {
      errors.add(NAME_IS_BLANK);
    }

    if (this.diskSizeGb < 30) {
      errors.add(DISK_SIZE_UNDER_30GB);
    }

    if (this.agentPoolMode == AzureAgentPoolMode.SYSTEM && this.osType == AzureOsType.WINDOWS) {
      errors.add(SYSTEM_POOL_MODE_WINDOWS);
    }

    validateIntegerInRange("InitialNodeCount", this.initialNodeCount,
        MIN_NUMBER_OF_NODES,MAX_NUMBER_OF_NODES, errors);

    if(this.maxNodeCount != null) {
      validateIntegerInRange("MaxNodeCount", this.maxNodeCount,
          MIN_NUMBER_OF_NODES,MAX_NUMBER_OF_NODES, errors);
    }

    if(this.minNodeCount != null) {
      validateIntegerInRange("MinNodeCount", this.minNodeCount,
          MIN_NUMBER_OF_NODES,MAX_NUMBER_OF_NODES, errors);
    }

    if(this.maxPodsPerNode != null) {
      validateIntegerInRange("MaxPodsPerNode", this.maxPodsPerNode,
          MIN_NUMBER_OF_PODS_PER_NODE, MAX_NUMBER_OF_PODS_PER_NODE, errors);
    }

    if(this.autoscalingEnabled) {
      if(this.maxNodeCount == null) {
        errors.add(MAX_NODE_COUNT_IS_NULL);
      }

      if(this.minNodeCount == null) {
        errors.add(MIN_NODE_COUNT_IS_NULL);
      }
    }

    return errors;
  }
}

