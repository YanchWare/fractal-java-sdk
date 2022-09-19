package com.yanchware.fractal.sdk.services;

import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.configuration.instantiation.InstantiationWaitConfiguration;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.providers.responses.CurrentLiveSystemsResponse;
import com.yanchware.fractal.sdk.utils.HttpUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import static com.yanchware.fractal.sdk.utils.ResiliencyUtils.executeRequestWithRetries;

@Slf4j
@AllArgsConstructor
public class ProviderService {

  private final HttpClient client;
  private final SdkConfiguration sdkConfiguration;

  public void checkLiveSystemStatus(LiveSystem liveSystem,
                                    LiveSystemService liveSystemService,
                                    InstantiationWaitConfiguration config)
      throws InstantiatorException {
    log.info("Starting operation [checkLiveSystem] for livesystem [{}]", liveSystem.getLiveSystemId());

    CurrentLiveSystemsResponse liveSystemsResponse = executeRequestWithRetries(
        "checkLiveSystem",
        client,
        HttpUtils.buildGetRequest(
            getProvidersUri(liveSystem.getResourceGroupId(), sdkConfiguration.getProviderName()),
            sdkConfiguration
        ),
        new int[]{200, 404},
        CurrentLiveSystemsResponse.class,
        liveSystem.getLiveSystemId(),
        liveSystemService,
        config);
  }

  private URI getProvidersUri(String resourceGroupId, String providerName) {
    return URI.create(String.format("%s/%s/%s/livesystems",
        sdkConfiguration.getProviderEndpoint(), resourceGroupId, providerName));
  }

}
