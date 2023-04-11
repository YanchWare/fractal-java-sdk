package com.yanchware.fractal.sdk.services;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.configuration.instantiation.InstantiationWaitConfiguration;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentDto;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemMutationDto;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest
public class ProviderServiceTest {

    @Test
    public void urlPathMatching_when_postRequestToBlueprint(WireMockRuntimeInfo wmRuntimeInfo) throws InstantiatorException {
        var httpClient = HttpClient.newBuilder()
          .version(HttpClient.Version.HTTP_2)
          .build();
        var sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
        var providerService = new ProviderService(httpClient, sdkConfiguration);

        stubFor(get(urlPathMatching("/providers/resourceGroupId/provider-name/livesystems/livesystem-name/mutations/mutation-id"))
          .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")));

        providerService.checkLiveSystemMutationStatus(
          LiveSystem.builder()
            .withComponent(() -> null)
            .withResourceGroupId("resourceGroupId")
            .withName("livesystem-name")
            .build(),
          new LiveSystemMutationDto(){{
              setId("mutation-id");
          }}, new InstantiationWaitConfiguration());

        verify(getRequestedFor(urlPathEqualTo("/providers/resourceGroupId/provider-name/livesystems/livesystem-name/mutations/mutation-id")));
    }
}