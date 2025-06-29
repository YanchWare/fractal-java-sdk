package com.yanchware.fractal.sdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.EnvironmentResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemIdValue;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.http.HttpClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

@WireMockTest
public class AutomatonTest extends TestWithFixture {

  @Test
  public void success_when_deleteLiveSystem(WireMockRuntimeInfo wmRuntimeInfo) throws URISyntaxException, InstantiatorException, JsonProcessingException {
    var httpClient = HttpClient.newBuilder()
      .version(HttpClient.Version.HTTP_2)
      .build();
    var sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
    Automaton.initializeAutomaton(httpClient, sdkConfiguration);
    var sut = Automaton.getInstance();

    var liveSystemIds = aListOf(LiveSystemIdValue.class);
    var environmentResponse = a(EnvironmentResponse.class);
    var environmentId = environmentResponse.id();

    stubFor(get(urlPathMatching(String.format("/environments/%s/%s/%s", environmentId.type(), environmentId.ownerId(), environmentId.shortName())))
      .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-Type", "application/json")
        .withBody(new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
          .writeValueAsString(environmentResponse))));

    stubFor(delete(urlPathMatching("/livesystems/.*"))
      .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-Type", "application/json")));

    sut.delete(EnvironmentIdValue.fromDto(environmentResponse.id()), liveSystemIds);

    for(var liveSystemId : liveSystemIds) {
      verify(deleteRequestedFor(urlPathEqualTo(String.format("/livesystems/%s",  liveSystemId))));
    }
  }

}
