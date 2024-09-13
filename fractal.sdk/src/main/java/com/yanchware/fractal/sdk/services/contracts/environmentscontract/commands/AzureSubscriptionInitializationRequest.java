package com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands;

import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureCloudAgent;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Data;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;

@Data
@ToString
public class AzureSubscriptionInitializationRequest {
  public UUID tenantId;
  public UUID subscriptionId;
  public String region;
  public Map<String, String> tags;

  public static AzureSubscriptionInitializationRequest fromEnvironment(Environment environment) {
    var command = new AzureSubscriptionInitializationRequest();
    var cloudAgentByProviderType = environment.getCloudAgentByProviderType();
    if (!cloudAgentByProviderType.containsKey(ProviderType.AZURE)) {
      throw new IllegalArgumentException("Invalid or missing Azure Cloud Agent in environment parameters");
    }

    var agent = (AzureCloudAgent) cloudAgentByProviderType.get(ProviderType.AZURE);

    command.tenantId = agent.getTenantId();
    command.subscriptionId = agent.getSubscriptionId();
    command.region = agent.getRegion().toString();

    return command;
  }
}
