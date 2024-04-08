package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
public class ResourceManagement {
  private ContainerResources limits;
  private ContainerResources requests;

  protected ResourceManagement() {
  }


  public static ResourceManagementBuilder builder() {
    return new ResourceManagementBuilder();
  }

  public static class ResourceManagementBuilder {
    private final ResourceManagement resourceManagement;
    private final ResourceManagementBuilder builder;

    public ResourceManagementBuilder() {
      this.resourceManagement = new ResourceManagement();
      this.builder = this;
    }

    /**
     * <pre>
     * Specifies the resource requests for a container.</pre>
     *
     * @important.note <strong>If you choose to customize these values, please be aware that Fractal Cloud cannot guarantee the functionality of the deployment. 
     * <br>You bear full responsibility for ensuring the correctness of your configuration. 
     * <br>Misconfiguration can lead to application failures or resource allocation issues. 
     * <br>Be mindful of the suffix case and the units of measurement to avoid common pitfalls.</strong>
     *
     * @param requests The desired resource requests for the container, including CPU and memory specifications.
     */
    public ResourceManagementBuilder withRequests(ContainerResources requests) {
      resourceManagement.setRequests(requests);
      return builder;
    }

    /**
     * <pre>
     * Specifies the resource limits for a container.</pre>
     * 
     * @important.note <strong>If you choose to customize these values, please be aware that Fractal Cloud cannot guarantee the functionality of the deployment. 
     * <br>You bear full responsibility for ensuring the correctness of your configuration. 
     * <br>Misconfiguration can lead to application failures or resource allocation issues. 
     * <br>Be mindful of the suffix case and the units of measurement to avoid common pitfalls.</strong>
     * 
     * @param limits The desired resource limits for the container, including CPU and memory specifications.
     */
    public ResourceManagementBuilder withLimits(ContainerResources limits) {
      resourceManagement.setLimits(limits);
      return builder;
    }

    public ResourceManagement build() {
      return resourceManagement;
    }
  }
}
