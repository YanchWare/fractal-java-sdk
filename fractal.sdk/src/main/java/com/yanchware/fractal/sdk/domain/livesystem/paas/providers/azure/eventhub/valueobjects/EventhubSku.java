package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub.valueobjects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class EventhubSku {
  /**
   * Eventhub Sku capacity
   */
  private Integer capacity;

  /**
   * Eventhub Sku Tier
   */
  private EventhubSkuTier tier;


  public static EventhubSkuBuilder builder() {
    return new EventhubSkuBuilder();
  }

  public static class EventhubSkuBuilder {
    private final EventhubSku eventhubSku;
    private final EventhubSkuBuilder builder;

    public EventhubSkuBuilder() {
      this.eventhubSku = new EventhubSku();
      this.builder = this;
    }

    public EventhubSkuBuilder withCapacity(Integer capacity) {
      eventhubSku.setCapacity(capacity);
      return builder;
    }

    public EventhubSkuBuilder withTier(EventhubSkuTier tier) {
      eventhubSku.setTier(tier);
      return builder;
    }

    public EventhubSku build() {
      return eventhubSku;
    }
  }
}

