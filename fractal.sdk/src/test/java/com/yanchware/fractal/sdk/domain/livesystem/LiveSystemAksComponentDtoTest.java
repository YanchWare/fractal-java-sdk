package com.yanchware.fractal.sdk.domain.livesystem;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.LiveSystemComponentDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.LiveSystemKubernetesComponentDtoTest;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.domain.values.ComponentType;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import com.yanchware.fractal.sdk.utils.TestUtils;
import io.github.resilience4j.retry.RetryRegistry;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.TestUtils.assertGenericComponent;
import static org.assertj.core.api.Assertions.assertThat;

class LiveSystemAksComponentDtoTest extends LiveSystemKubernetesComponentDtoTest {

  @Test
  public void liveSystemComponentDto_matches_liveSystemComponents_withCorrectTypeForLiveSystem_forAks() {
    var factory = new LiveSystemsFactory(
        HttpClient.newBuilder().build(),
        new LocalSdkConfiguration(""),
        RetryRegistry.ofDefaults());
    var aks = TestUtils.getAksExample();
    var liveSystem = factory.builder()
        .withId(new LiveSystemIdValue("test", "test"))
        .withStandardProvider(ProviderType.AZURE)
        .withComponent(aks)
        .build();
    var lsDtoMap = liveSystem.blueprintMapFromLiveSystemComponents();
    assertAks(aks, lsDtoMap);
    assertAksNodePool(aks, lsDtoMap);
    assertCaaSComponents(aks, lsDtoMap);
  }

  private void assertAksNodePool(AzureKubernetesService aks, Map<String, LiveSystemComponentDto> lsDtoMap) {
    var dto = lsDtoMap.get(aks.getId().getValue());
    assertThat(dto.getParameters().get("nodePools"))
        .asInstanceOf(InstanceOfAssertFactories.LIST)
        .first()
        .hasFieldOrProperty("labels");
  }

  private void assertAks(AzureKubernetesService aks, Map<String, LiveSystemComponentDto> lsDtoMap) {
    var dto = lsDtoMap.get(aks.getId().getValue());
    assertGenericComponent(dto, aks, ComponentType.PAAS_KUBERNETES.getId());
    assertThat(dto.getProvider()).isEqualTo(aks.getProvider());
    assertThat(dto.getParameters())
        .extracting(
            "addonProfiles",
            "azureActiveDirectoryProfile",
            "azureRegion",
            "externalWorkspaceResourceId",
            "kubernetesVersion",
            "nodePools",
            "outboundIps",
            "podIpRange",
            "podManagedIdentity",
            "priorityClasses",
            "roles",
            "serviceIpRange",
            "tags",
            "vnetAddressSpaceIpRange",
            "vnetSubnetAddressIpRange")
        .containsExactly(
            aks.getAddonProfiles(),
            aks.getAzureActiveDirectoryProfile(),
            aks.getAzureRegion(),
            aks.getExternalWorkspaceResourceId(),
            aks.getKubernetesVersion(),
            aks.getNodePools(),
            aks.getOutboundIps(),
            aks.getPodIpRange(),
            aks.getPodManagedIdentity(),
            aks.getPriorityClasses(),
            aks.getRoles(),
            aks.getServiceIpRange(),
            aks.getTags(),
            aks.getVnetAddressSpaceIpRange(),
            aks.getVnetSubnetAddressIpRange());
  }
}