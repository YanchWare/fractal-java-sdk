package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureAddonProfile {
  MONITORING("monitoring"),
  VIRTUAL_NODE("virtual-node"),
  AZURE_POLICY("azure-policy"),
  INGRESS_APPGW("ingress-appgw"),
  CONFCOM("confcom"),
  OPEN_SERVICE_MESH("open-service-mesh"),
  GITOPS("gitops"),
  AZURE_KEYVAULT_SECRETS_PROVIDER("azure-keyvault-secrets-provider"),
  WEB_APPLICATION_ROUTING("web_application_routing");

  private final String id;

  AzureAddonProfile(final String id) {
    this.id = id;
  }

  @JsonValue
  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return id;
  }
}
