package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import lombok.Getter;

import java.util.Map;


@Getter
public class AzureResource extends AzureProxyResource {
  private final AzureResourceGroup azureResourceGroup;

  public AzureResource(String name, 
                     AzureRegion region, 
                     Map<String, String> tags, 
                     AzureResourceGroup azureResourceGroup) {
    super(name, region, tags);

    this.azureResourceGroup = azureResourceGroup;
  }

  public static Builder<? extends Builder<?>> builder() {
    return new Builder<>();
  }
  

  public static class Builder<S extends Builder<S>> extends AzureProxyResource.Builder<S> {
    protected AzureResourceGroup azureResourceGroup;

    public Builder() {}

    /**
     * A resource group is a collection of resources that share the same lifecycle, permissions, and policies.
     * The resource group can include all the resources for the solution, or only those resources that you want to manage as a group.
     */
    public Builder<S> withAzureResourceGroup(AzureResourceGroup resourceGroup) {
      this.azureResourceGroup = resourceGroup;
      return this;
    }

    @Override
    public AzureResource build() {
      return new AzureResource(name, region, tags, azureResourceGroup);
    }
  }
}
