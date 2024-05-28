package com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands;

import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import lombok.Data;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;

import static com.yanchware.fractal.sdk.domain.entities.environment.EnvironmentConstants.*;

@Data
@ToString
public class SubscriptionInitializationRequest {
  public UUID tenantId;
  public UUID subscriptionId;
  public String region;
  public Map<String, String> tags;

  public static SubscriptionInitializationRequest fromEnvironment(Environment environment) {
    var command = new SubscriptionInitializationRequest();

    Object tenantIdObj = environment.getParameters().get(TENANT_ID_PARAM_KEY);
    if (tenantIdObj instanceof UUID) {
      command.tenantId = (UUID) tenantIdObj;
    } else {
      throw new IllegalArgumentException("Invalid or missing tenantId in environment parameters");
    }

    Object subscriptionIdObj = environment.getParameters().get(SUBSCRIPTION_ID_PARAM_KEY);
    if (subscriptionIdObj instanceof UUID) {
      command.subscriptionId = (UUID) subscriptionIdObj;
    } else {
      throw new IllegalArgumentException("Invalid or missing subscriptionId in environment parameters");
    }

    Object regionObj = environment.getParameters().get(REGION_PARAM_KEY);
    if (regionObj instanceof AzureRegion) {
      command.region = regionObj.toString();
    } else {
      throw new IllegalArgumentException("Invalid or missing region in environment parameters");
    }

    return command;
  }
}
