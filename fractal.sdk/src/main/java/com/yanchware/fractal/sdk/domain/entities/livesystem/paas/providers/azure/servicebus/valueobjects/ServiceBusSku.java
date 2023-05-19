package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus.valueobjects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
public class ServiceBusSku {
  /**
   * Service Bus Sku capacity
   */
  private Integer capacity;

  /**
   * Service Bus Sku Tier
   */
  private ServiceBusSkuTier tier;


  public static ServiceBusSkuBuilder builder() {
    return new ServiceBusSkuBuilder();
  }

  public static class ServiceBusSkuBuilder {
    private final ServiceBusSku serviceBusSku;
    private final ServiceBusSkuBuilder builder;

    public ServiceBusSkuBuilder() {
      this.serviceBusSku = new ServiceBusSku();
      this.builder = this;
    }

    public ServiceBusSkuBuilder withCapacity(Integer capacity) {
      serviceBusSku.setCapacity(capacity);
      return builder;
    }

    public ServiceBusSkuBuilder withTier(ServiceBusSkuTier tier) {
      serviceBusSku.setTier(tier);
      return builder;
    }

    public ServiceBusSku build() {
      return serviceBusSku;
    }
  }
}

