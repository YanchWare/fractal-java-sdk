package com.yanchware.fractal.sdk.services;

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

  public void checkLiveSystemStatus(String resourceGroup,
                                    String liveSystemId,
                                    LiveSystemService liveSystemService,
                                    InstantiationWaitConfiguration config)
      throws InstantiatorException {
    log.info("Starting operation [checkLiveSystem] for resource group [{}] and LiveSystem id [{}]",
        resourceGroup,
        liveSystemId);

    CurrentLiveSystemsResponse liveSystemsResponse = executeRequestWithRetries(
        "checkLiveSystem",
        client,
        HttpUtils.buildGetRequest(getProvidersUri(resourceGroup), sdkConfiguration),
        new int[]{200, 404},
        CurrentLiveSystemsResponse.class,
        liveSystemId,
        liveSystemService,
        config);
  }

  private URI getProvidersUri(String resourceGroup) {
    return URI.create(String.format("%s/%s/livesystems", sdkConfiguration.getProviderEndpoint(), resourceGroup));
  }

}
