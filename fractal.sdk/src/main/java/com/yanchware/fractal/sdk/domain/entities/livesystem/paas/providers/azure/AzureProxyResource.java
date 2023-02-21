package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
@Getter
@AllArgsConstructor
public class AzureProxyResource {
  private final String name;
  private final AzureRegion region;
  private final Map<String, String> tags;
  
  public static Builder<? extends Builder<?>> builder() {
    return new Builder<>();
  }
  
  public static class Builder<S extends Builder<S>> {
    protected String name;
    protected AzureRegion region;
    protected Map<String, String> tags;

    public Builder() {}

    public S withName(String name) {
      this.name = name;
      return (S) this;
    }

    /**
     * Choose the Azure region that's right for you and your customers
     */
    public S withRegion(AzureRegion region) {
      this.region = region;
      return (S) this;
    }

    /**
     * Tags are name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public S withTags(Map<String, String> tags) {
      if(this.tags == null) {
        this.tags = new HashMap<>();
      }
      this.tags.putAll(tags);
      return (S) this;
    }

    /**
     * Tag is name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public S withTag(String key, String value) {
      if (this.tags == null) {
        withTags(new HashMap<>());
      }

      this.tags.put(key, value);
      return (S) this;
    }
  
    public AzureProxyResource build() {
      return new AzureProxyResource(name, region, tags);
    }
  }
}
