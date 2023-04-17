package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.aks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AzureKubernetesAddonTest {
  @Test
  public void shouldReturnProperValue() {
    assertAll(
        () -> assertEquals(AzureKubernetesAddon.fromString("httpApplicationRouting"), AzureKubernetesAddon.HTTP_APPLICATION_ROUTING),
        () -> assertEquals(AzureKubernetesAddon.fromString("omsagent"), AzureKubernetesAddon.MONITORING),
        () -> assertEquals(AzureKubernetesAddon.fromString("aciConnectorLinux"), AzureKubernetesAddon.VIRTUAL_NODE),
        () -> assertEquals(AzureKubernetesAddon.fromString("azurepolicy"), AzureKubernetesAddon.AZURE_POLICY),
        () -> assertEquals(AzureKubernetesAddon.fromString("ingressApplicationGateway"), AzureKubernetesAddon.INGRESS_APPGW),
        () -> assertEquals(AzureKubernetesAddon.fromString("ACCSGXDevicePlugin"), AzureKubernetesAddon.CONFCOM),
        () -> assertEquals(AzureKubernetesAddon.fromString("openServiceMesh"), AzureKubernetesAddon.OPEN_SERVICE_MESH),
        () -> assertEquals(AzureKubernetesAddon.fromString("azureKeyvaultSecretsProvider"), AzureKubernetesAddon.AZURE_KEYVAULT_SECRETS_PROVIDER),
        () -> assertEquals(AzureKubernetesAddon.fromString("webAppRouting"), AzureKubernetesAddon.WEB_APPLICATION_ROUTING));
  }

}