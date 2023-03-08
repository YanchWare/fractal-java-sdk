package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus.valueobjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class ServiceBusSku {
  /**
   * Service Bus Sku capacity
   */
  private Integer capacity;
  /**
   * Service Bus Sku Tier
   */
  private ServiceBusSkuTier tier;
}

