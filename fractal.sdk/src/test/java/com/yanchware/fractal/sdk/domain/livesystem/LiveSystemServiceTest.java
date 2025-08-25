package com.yanchware.fractal.sdk.domain.livesystem;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.blueprint.FractalIdValue;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentType;
import com.yanchware.fractal.sdk.domain.environment.ManagementEnvironment;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.AzureCosmosGremlinDatabase;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos.AzureCosmosGremlinDbms;
import com.yanchware.fractal.sdk.domain.livesystem.service.LiveSystemService;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupType;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import com.yanchware.fractal.sdk.utils.StringHandler;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WireMockTest
public class LiveSystemServiceTest {


  @Test
  public void urlPathMatching_when_postRequestToLiveSystem(WireMockRuntimeInfo wmRuntimeInfo) throws InstantiatorException {
    var httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();
    var sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
    var liveSystemsFactory = new LiveSystemsFactory(httpClient, sdkConfiguration, RetryRegistry.ofDefaults());
    var liveSystemsService = new LiveSystemService(httpClient, sdkConfiguration, RetryRegistry.ofDefaults());
    var ownerId = UUID.fromString("b2bd7eab-ee3d-4603-86ac-3112ff6b2175");
    var resourceGroupId = new ResourceGroupId(ResourceGroupType.PERSONAL, ownerId, "rg");

    var inputStream = getClass().getClassLoader()
        .getResourceAsStream("test-resources/postRequestToLiveSystemBody.json");

    assertThat(inputStream).isNotNull();

    var postRequestToLiveSystemBody = StringHandler.getStringFromInputStream(inputStream);

    var managementEnvironment = ManagementEnvironment.builder()
            .withId(new EnvironmentIdValue(
                    EnvironmentType.PERSONAL,
                    ownerId,
                    "5d5bc38d-1d23-4d10-8"))
            .withResourceGroup(ResourceGroupId.fromString("Personal/b2bd7eab-ee3d-4603-86ac-3112ff6b2175/rg"))
            .build();
    
    var liveSystem = liveSystemsFactory.builder()
            .withId(new LiveSystemIdValue(resourceGroupId, "livesystem-name"))
            .withFractalId(new FractalIdValue(resourceGroupId, "fractalName", "fractalVersion" ))
            .withDescription("prod")
            .withEnvironmentId(managementEnvironment.getId())
            .withStandardProvider(ProviderType.AZURE)
            .withComponent(AzureCosmosGremlinDbms.builder()
              .withId("cosmos-graph-1")
              .withMaxTotalThroughput(500)
              .withAzureResourceGroup(
                      AzureResourceGroup.builder()
                              .withName("MyRg")
                              .withRegion(AzureRegion.EAST_ASIA)
                              .build())
              .withCosmosEntity(AzureCosmosGremlinDatabase.builder()
                      .withId("graph-db-1")
                      .build())
              .build())
            .build();

    stubFor(post(urlPathMatching("/livesystems/"))
        .withRequestBody(equalToJson(postRequestToLiveSystemBody, true, false))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")));

    liveSystemsService.instantiateLiveSystem(
            liveSystem.getId().toString(),
            liveSystem.getFractalId(),
            liveSystem.getDescription(),
            null,
            liveSystem.blueprintMapFromLiveSystemComponents(),
            liveSystem.getEnvironment());

    verify(postRequestedFor(urlPathEqualTo("/livesystems/")));
  }

  @Test
  public void urlPathMatching_when_getRequestToLiveSystemMutation(WireMockRuntimeInfo wmRuntimeInfo) throws InstantiatorException {
    HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();
    SdkConfiguration sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
    var liveSystemService = new LiveSystemService(httpClient, sdkConfiguration, RetryRegistry.ofDefaults());
    var resourceGroupId = new ResourceGroupId(ResourceGroupType.PERSONAL, UUID.randomUUID(), "rg");
    var url = String.format("/livesystems/%s/livesystem-name/mutations/mutation-id", resourceGroupId);
    stubFor(get(urlPathMatching(url))
        .willReturn(aResponse()
            .withStatus(200)
            .withBody("{\"liveSystemId\":\"fancy-group/CompanyCloud\",\"id\":\"a09f1ca7-5ee8-4d53-a187-644146d19479\",\"status\":\"Completed\",\"created\":\"2023-04-13T23:24:29.503384+00:00\",\"lastUpdated\":\"2023-04-13T23:24:29.503384+00:00\",\"componentsReadyIds\":[\"company-cloud\",\"relational-dbms-platform\"],\"componentsCompletedIds\":[],\"componentsFailedIds\":[],\"componentsById\":{\"company-cloud\":{\"status\":\"Instantiating\",\"outputFields\":{\"cluster\":{\"abc\":123}},\"lastUpdated\":\"2023-04-13T23:24:30.948156+00:00\",\"lastOperationRetried\":0,\"provider\":\"Azure\",\"lastOperationStatusMessage\":\"\",\"displayName\":\"AKS instantiation\",\"description\":\"Cloud AKS cluster\",\"type\":\"NetworkAndCompute.PaaS.ContainerPlatform\",\"id\":\"company-cloud\",\"version\":\"1.0\",\"parameters\":{\"region\":\"westeurope\",\"network\":\"network-host\",\"nodePools\":[{\"name\":\"linuxdynamic\",\"osType\":\"LINUX\",\"maxSurge\":1,\"diskSizeGb\":128,\"machineType\":\"STANDARD_B4MS\",\"maxNodeCount\":9,\"minNodeCount\":1,\"agentPoolMode\":\"SYSTEM\",\"maxPodsPerNode\":30,\"initialNodeCount\":1},{\"name\":\"windyn\",\"osType\":\"WINDOWS\",\"maxSurge\":1,\"diskSizeGb\":128,\"machineType\":\"STANDARD_B4MS\",\"maxNodeCount\":9,\"minNodeCount\":1,\"agentPoolMode\":\"USER\",\"maxPodsPerNode\":30,\"initialNodeCount\":1}],\"podsRange\":\"tier-1-pods\",\"subNetwork\":\"compute-tier-1\",\"serviceRange\":\"tier-1-services\"},\"dependencies\":[],\"links\":[]},\"company-controller\":{\"status\":\"Instantiating\",\"outputFields\":{},\"lastUpdated\":\"2023-04-13T23:24:30.948169+00:00\",\"lastOperationRetried\":0,\"provider\":\"Azure\",\"lastOperationStatusMessage\":\"\",\"displayName\":\"Company controller\",\"description\":\"Company controller\",\"type\":\"CustomWorkloads.Caas.K8sWorkload\",\"id\":\"company-controller\",\"version\":\"1.0\",\"parameters\":{\"roles\":[],\"repoId\":\"Company.Cloud/Company.Cloud.Controller\",\"namespace\":\"company-cloud\",\"sshRepositoryURI\":\"git@ssh.dev.azure.com:v3/CompanyCloud/Company.Cloud/Company.Cloud.Controller\",\"containerPlatform\":\"company-cloud\",\"privateSSHKeySecretId\":\"fractalDeployerSshKey\",\"privateSSHKeyPassphraseSecretId\":\"fractalDeployerSshKeyPassphrase\"},\"dependencies\":[\"company-cloud\"],\"links\":[]},\"relational-dbms-platform\":{\"status\":\"Instantiating\",\"outputFields\":{},\"lastUpdated\":\"2023-04-13T23:24:30.948178+00:00\",\"lastOperationRetried\":0,\"provider\":\"Azure\",\"lastOperationStatusMessage\":\"\",\"displayName\":\"Relational DBMS Platform\",\"description\":\"Platform providing RDBMS as a service\",\"type\":\"DataStorage.PaaS.PostgreSQL\",\"id\":\"relational-dbms-platform\",\"version\":\"1.0\",\"parameters\":{\"tier\":\"GP_Gen5_4\",\"region\":\"westeurope\",\"version\":\"11\",\"storageAutoResize\":true},\"dependencies\":[],\"links\":[]}},\"componentsBlocked\":{\"company-cloud\":[\"company-controller\"]}}")
            .withHeader("Content-Type", "application/json")));

    liveSystemService.checkLiveSystemMutationStatus(
        new LiveSystemIdValue(resourceGroupId, "livesystem-name"),
        "mutation-id");

    verify(getRequestedFor(urlPathEqualTo(url)));
  }

  @Test
  public void testRetrieveLiveSystem_Success(WireMockRuntimeInfo wmRuntimeInfo) throws InstantiatorException {
    HttpClient httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .build();
    
    SdkConfiguration sdkConfiguration = new LocalSdkConfiguration(wmRuntimeInfo.getHttpBaseUrl());
    
    var liveSystemService = new LiveSystemService(httpClient, sdkConfiguration, RetryRegistry.ofDefaults());
    var resourceGroupId = new ResourceGroupId(ResourceGroupType.PERSONAL, UUID.randomUUID(), "rg");

    LiveSystemIdValue liveSystemId = new LiveSystemIdValue(resourceGroupId, "Development");

    var inputStream = getClass().getClassLoader()
        .getResourceAsStream("test-resources/getRequestToLiveSystemResponse.json");

    assertThat(inputStream).isNotNull();

    var liveSystemResponse= StringHandler.getStringFromInputStream(inputStream);
    
    stubFor(get(urlPathMatching(String.format("/livesystems/%s/Development", resourceGroupId)))
        .willReturn(aResponse()
            .withStatus(200)
            .withBody(liveSystemResponse)
            .withHeader("Content-Type", "application/json")));

    var result = liveSystemService.retrieveLiveSystem(liveSystemId);

    // Assertions
    assertNotNull(result);
  }
}