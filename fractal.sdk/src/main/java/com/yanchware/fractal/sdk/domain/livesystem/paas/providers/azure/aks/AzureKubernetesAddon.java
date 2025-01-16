package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public final class AzureKubernetesAddon extends ExtendableEnum<AzureKubernetesAddon> {
  /**
   * Configure ingress with automatic public DNS name creation
   */
  public static final AzureKubernetesAddon HTTP_APPLICATION_ROUTING = fromString("httpApplicationRouting");

  /**
   * Turn on Log Analytics monitoring. Uses the Log Analytics Default Workspace if it exists, else creates one
   */
  public static final AzureKubernetesAddon MONITORING = fromString("omsagent");

  /**
   * Enable AKS Virtual Node
   */
  public static final AzureKubernetesAddon VIRTUAL_NODE = fromString("aciConnectorLinux");

  /**
   * Enable Azure policy.
   * The Azure Policy add-on for AKS enables at-scale enforcements and safeguards on your clusters in a centralized,
   * consistent manner
   */
  public static final AzureKubernetesAddon AZURE_POLICY = fromString("azurepolicy");

  /**
   * Enable Application Gateway Ingress Controller addon
   */
  public static final AzureKubernetesAddon INGRESS_APPGW = fromString("ingressApplicationGateway");

  /**
   * Enable confcom addon, this will enable SGX device plugin by default
   */
  public static final AzureKubernetesAddon CONFCOM = fromString("ACCSGXDevicePlugin");

  /**
   * Enable Open Service Mesh addon
   */
  public static final AzureKubernetesAddon OPEN_SERVICE_MESH = fromString("openServiceMesh");

  /**
   * Enable Azure Keyvault Secrets Provider addon
   */
  public static final AzureKubernetesAddon AZURE_KEYVAULT_SECRETS_PROVIDER = fromString("azureKeyvaultSecretsProvider");

  /**
   * Enable web application routing
   */
  public static final AzureKubernetesAddon WEB_APPLICATION_ROUTING = fromString("webAppRouting");

  public AzureKubernetesAddon() {
  }

  @JsonCreator
  public static AzureKubernetesAddon fromString(String name) {
    return fromString(name, AzureKubernetesAddon.class);
  }

  public static Collection<AzureKubernetesAddon> values() {
    return values(AzureKubernetesAddon.class);
  }
}
