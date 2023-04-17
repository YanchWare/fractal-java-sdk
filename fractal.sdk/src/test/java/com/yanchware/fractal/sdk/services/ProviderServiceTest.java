package com.yanchware.fractal.sdk.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.configuration.EnvVarSdkConfiguration;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.configuration.instantiation.InstantiationWaitConfiguration;
import com.yanchware.fractal.sdk.services.components.EnvTestConfiguration;
import com.yanchware.fractal.sdk.services.components.TargitCloudLiveSystem;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemMutationDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.yanchware.fractal.sdk.configuration.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

@Disabled
class ProviderServiceTest {

  private ProviderService providerService;
  @BeforeEach
  public void setUp() throws URISyntaxException {
    SdkConfiguration sdkConfiguration = new EnvVarSdkConfiguration();
    HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();
    providerService = new ProviderService(httpClient, sdkConfiguration);
  }
  
  @SneakyThrows
  @Test
  @SetEnvironmentVariable(key = CLIENT_ID_KEY, value = "targit-cicd")
  @SetEnvironmentVariable(key = CLIENT_SECRET_KEY, value = "HoIgRJm6AEahBnO1RDlNrm0Ip7UZvHv")
  @SetEnvironmentVariable(key = PROVIDER_NAME_KEY, value = "targit-cloud-production_Azure_default")
  public void checkLiveSystemMutationStatusTest() {
    LiveSystem liveSystem = TargitCloudLiveSystem.getLiveSystem(EnvTestConfiguration.getInstance());
    Collection<String> errors = liveSystem.validate();
    assertThat(errors.size()).isEqualTo(0);
    
    var inputStream = getClass().getClassLoader()
        .getResourceAsStream("liveSystem2.json");

    assertThat(inputStream).isNotNull();

    var liveSystemMutationJson = getStringFromInputStream(inputStream);

    var liveSystemMutation = new ObjectMapper().readValue(liveSystemMutationJson, LiveSystemMutationDto.class);
    
//    liveSystemMutation.setLiveSystemId("targit-cloud-production/Uat");
//    liveSystemMutation.setLiveSystemId("targit-cloud-production/Uat");
//    liveSystemMutation.setId("11da0cc4-17a1-4597-867a-104fe660e354");

    var config = new InstantiationWaitConfiguration();

    
    providerService.checkLiveSystemMutationStatus(liveSystem, liveSystemMutation, config);
  }

  private String getStringFromInputStream(InputStream inputStream) {
    return new BufferedReader(new InputStreamReader(inputStream))
        .lines().parallel().collect(Collectors.joining("\n"));
  }

}