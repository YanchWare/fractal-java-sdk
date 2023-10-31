package com.yanchware.fractal.sdk.services;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.EnvironmentType;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsZone;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemId;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosGremlinDatabase;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos.AzureCosmosGremlinDbms;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands.InstantiateLiveSystemCommandRequest;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.EnvironmentDto;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentDto;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import com.yanchware.fractal.sdk.utils.StringHandler;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@WireMockTest
public class LiveSystemServiceTest {

  @Test
  public void urlPathMatching_when_postRequestToLiveSystem2(WireMockRuntimeInfo wmRuntimeInfo) throws InstantiatorException {
    HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();
    
    SdkConfiguration sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
    var liveSystemService = new LiveSystemService(httpClient, sdkConfiguration, RetryRegistry.ofDefaults());

    var inputStream = getClass().getClassLoader()
        .getResourceAsStream("test-resources/postRequestToLiveSystemBody.json");

    assertThat(inputStream).isNotNull();

    var postRequestToLiveSystemBody = StringHandler.getStringFromInputStream(inputStream);
    

    stubFor(post(urlPathMatching("/livesystems/"))
        .withRequestBody(equalToJson(postRequestToLiveSystemBody, true, false))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")));

    liveSystemService.instantiate(buildLiveSystemCommand());
    verify(postRequestedFor(urlPathEqualTo("/livesystems/")));
  }

  @Test
  public void urlPathMatching_when_postRequestToLiveSystem(WireMockRuntimeInfo wmRuntimeInfo) throws InstantiatorException {
    HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();
    SdkConfiguration sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
    var liveSystemService = new LiveSystemService(httpClient, sdkConfiguration, RetryRegistry.ofDefaults());

    var inputStream = getClass().getClassLoader()
        .getResourceAsStream("test-resources/postRequestToLiveSystemBody.json");

    assertThat(inputStream).isNotNull();

    var postRequestToLiveSystemBody = StringHandler.getStringFromInputStream(inputStream);

    stubFor(post(urlPathMatching("/livesystems/"))
        .withRequestBody(equalToJson(postRequestToLiveSystemBody, true, false))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")));

    liveSystemService.instantiate(buildLiveSystemCommand());
    verify(postRequestedFor(urlPathEqualTo("/livesystems/")));
  }

  @Test
  public void urlPathMatching_when_getRequestToLiveSystemMutation(WireMockRuntimeInfo wmRuntimeInfo) throws InstantiatorException {
    HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();
    SdkConfiguration sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
    var liveSystemService = new LiveSystemService(httpClient, sdkConfiguration, RetryRegistry.ofDefaults());

    stubFor(get(urlPathMatching("/livesystems/resourceGroupId/livesystem-name/mutations/mutation-id"))
        .willReturn(aResponse()
            .withStatus(200)
            .withBody("{\"liveSystemId\":\"fancy-group/CompanyCloud\",\"id\":\"a09f1ca7-5ee8-4d53-a187-644146d19479\",\"status\":\"Completed\",\"created\":\"2023-04-13T23:24:29.503384+00:00\",\"lastUpdated\":\"2023-04-13T23:24:29.503384+00:00\",\"componentsReadyIds\":[\"company-cloud\",\"relational-dbms-platform\"],\"componentsCompletedIds\":[],\"componentsFailedIds\":[],\"componentsById\":{\"company-cloud\":{\"status\":\"Instantiating\",\"outputFields\":{\"cluster\":{\"abc\":123}},\"lastUpdated\":\"2023-04-13T23:24:30.948156+00:00\",\"lastOperationRetried\":0,\"provider\":\"Azure\",\"lastOperationStatusMessage\":\"\",\"displayName\":\"AKS instantiation\",\"description\":\"Cloud AKS cluster\",\"type\":\"NetworkAndCompute.PaaS.ContainerPlatform\",\"id\":\"company-cloud\",\"version\":\"1.0\",\"parameters\":{\"region\":\"westeurope\",\"network\":\"network-host\",\"nodePools\":[{\"name\":\"linuxdynamic\",\"osType\":\"LINUX\",\"maxSurge\":1,\"diskSizeGb\":128,\"machineType\":\"STANDARD_B4MS\",\"maxNodeCount\":9,\"minNodeCount\":1,\"agentPoolMode\":\"SYSTEM\",\"maxPodsPerNode\":30,\"initialNodeCount\":1},{\"name\":\"windyn\",\"osType\":\"WINDOWS\",\"maxSurge\":1,\"diskSizeGb\":128,\"machineType\":\"STANDARD_B4MS\",\"maxNodeCount\":9,\"minNodeCount\":1,\"agentPoolMode\":\"USER\",\"maxPodsPerNode\":30,\"initialNodeCount\":1}],\"podsRange\":\"tier-1-pods\",\"subNetwork\":\"compute-tier-1\",\"serviceRange\":\"tier-1-services\"},\"dependencies\":[],\"links\":[]},\"company-controller\":{\"status\":\"Instantiating\",\"outputFields\":{},\"lastUpdated\":\"2023-04-13T23:24:30.948169+00:00\",\"lastOperationRetried\":0,\"provider\":\"Azure\",\"lastOperationStatusMessage\":\"\",\"displayName\":\"Company controller\",\"description\":\"Company controller\",\"type\":\"CustomWorkloads.Caas.K8sWorkload\",\"id\":\"company-controller\",\"version\":\"1.0\",\"parameters\":{\"roles\":[],\"repoId\":\"Company.Cloud/Company.Cloud.Controller\",\"namespace\":\"company-cloud\",\"sshRepositoryURI\":\"git@ssh.dev.azure.com:v3/CompanyCloud/Company.Cloud/Company.Cloud.Controller\",\"containerPlatform\":\"company-cloud\",\"privateSSHKeySecretId\":\"fractalDeployerSshKey\",\"privateSSHKeyPassphraseSecretId\":\"fractalDeployerSshKeyPassphrase\"},\"dependencies\":[\"company-cloud\"],\"links\":[]},\"relational-dbms-platform\":{\"status\":\"Instantiating\",\"outputFields\":{},\"lastUpdated\":\"2023-04-13T23:24:30.948178+00:00\",\"lastOperationRetried\":0,\"provider\":\"Azure\",\"lastOperationStatusMessage\":\"\",\"displayName\":\"Relational DBMS Platform\",\"description\":\"Platform providing RDBMS as a service\",\"type\":\"DataStorage.PaaS.PostgreSQL\",\"id\":\"relational-dbms-platform\",\"version\":\"1.0\",\"parameters\":{\"tier\":\"GP_Gen5_4\",\"region\":\"westeurope\",\"version\":\"11\",\"storageAutoResize\":true},\"dependencies\":[],\"links\":[]}},\"componentsBlocked\":{\"company-cloud\":[\"company-controller\"]}}")
            .withHeader("Content-Type", "application/json")));

    liveSystemService.checkLiveSystemMutationStatus(
        new LiveSystemId("resourceGroupId/livesystem-name"),
        "mutation-id");

    verify(getRequestedFor(urlPathEqualTo("/livesystems/resourceGroupId/livesystem-name/mutations/mutation-id")));
  }

  private InstantiateLiveSystemCommandRequest buildLiveSystemCommand() {
    return InstantiateLiveSystemCommandRequest.builder()
        .description("prod")
        .liveSystemId("resourceGroupId/livesystem-name")
        .fractalId("resourceGroupId/fractalName:fractalVersion")
        .environment(getEnvironment())
        .blueprintMap(LiveSystemComponentDto.fromLiveSystemComponents(List.of(AzureCosmosGremlinDbms.builder()
            .withId("cosmos-graph-1")
            .withMaxTotalThroughput(500)
            .withAzureResourceGroup(
                AzureResourceGroup.builder()
                    .withName("MyRg")
                    .withRegion(AzureRegion.ASIA_EAST)
                    .build())
            .withCosmosEntity(AzureCosmosGremlinDatabase.builder()
                .withId("graph-db-1")
                .build())
            .build())))
        .build();
  }
  
  private EnvironmentDto getEnvironment() {
//    return new EnvironmentDto(
//        "5d5bc38d-1d23-4d10-85ee-67461de4b104",
//        "b2bd7eab-ee3d-4603-86ac-3112ff6b2175",
//        EnvironmentType.PERSONAL,
//        Map.of("DnsZone") );
    
    var environment = Environment.builder()
        .withEnvironmentType(EnvironmentType.ORGANIZATIONAL)
        .withId("5d5bc38d-1d23-4d10-85ee-67461de4b104")
        .withOwnerId("b2bd7eab-ee3d-4603-86ac-3112ff6b2175")
        .withDnsZone(DnsZone.builder()
            .withName("targit.cloud")
            .withParameter("subscriptionId", "c99d9495-d55b-49fc-b45a-eb57ce5ef303")
            .withParameter("azureResourceGroup",
                AzureResourceGroup.builder()
                    .withName("central")
                    .withRegion(AzureRegion.US_CENTRAL)
                    .build())
            .build())
        .build();
    
    return EnvironmentDto.fromEnvironment(environment);
  }

}