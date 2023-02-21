package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.*;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.valueobjects.AzureAppServiceClientCertMode;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.valueobjects.AzureAppServiceRedundancyMode;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.valueobjects.AzureFtpsState;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.AzureWebApp.builder;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_AZURE_WEBAPP;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AzureAppServiceTest {

  @Test
  public void exceptionThrown_when_workloadBuiltWithNullId() {
    assertThatThrownBy(() -> builder().withId("").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Component Id is illegal");
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithEmptyValues() {
    assertThatThrownBy(() -> generateBuilder().build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContainingAll(
            "privateSSHKeyPassphraseSecretId is either empty or blank",
            "privateSSHKeySecretId is either empty or blank",
            "sshRepositoryURI is either empty or blank",
            "repoId is either empty or blank");
  }

  @Test
  public void typeIsWebApp_when_workloadBuiltWithAllRequiredValues() {
    var builder = generateBuilder()
        .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
        .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withBranchName("branch-name")
        .withHosting(
            AzureWebAppHosting.builder().withDotnetVersion("DOTNET:***"
            ).build());
    assertThat(builder.build().getType()).isEqualTo(PAAS_AZURE_WEBAPP);
    assertThatCode(builder::build).doesNotThrowAnyException();
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithWorkloadSecretIdKeyEmpty() {
    var builder = generateBuilder()
        .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
        .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withSecretIdKey("");
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Workload Secret Id Key is either empty or blank");
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithBranchNameKeyEmpty() {
    var builder = generateBuilder()
        .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
        .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withBranchName("");
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("branchName is either empty or blank and it is required");
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithWorkloadSecretPasswordKeyEmpty() {
    var builder = generateBuilder()
        .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
        .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withSecretPasswordKey("");
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Workload Secret Password Key is either empty or blank");
  }

  @Test
  public void exceptionThrown_when_mixingJavaAndDotnetHosting() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withDotnetVersion("DOTNETCORE:7.0")
            .withJavaVersion("JAVA:17-java17")
            .build()
        );
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("[javaVersion Validation] Only one hosting configuration can be set. [dotnet] has already been set");
  }

  @Test
  public void exceptionThrown_when_mixingJavaAndPhpHosting() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withJavaVersion("JAVA:17-java17")
            .withPhpVersion("PHP:8.1")
            .build()
        );
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("[phpVersion Validation] Only one hosting configuration can be set. [java] has already been set");
  }

  @Test
  public void hostingIsDotnet_when_settingDotnetHosting() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withDotnetVersion("DOTNETCORE:7.0")
            .build()
        );
    assertThat(builder.build().getHosting().getDotnetVersion()).isEqualTo("DOTNETCORE:7.0");
    assertThatCode(builder::build).doesNotThrowAnyException();
  }

  @Test
  public void hostingIsPhp_when_settingPhpHosting() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withPhpVersion("PHP:8.1")
            .build()
        );
    assertThat(builder.build().getHosting().getPhpVersion()).isEqualTo("PHP:8.1");
    assertThat(builder.build().getHosting().getPythonVersion()).isEqualTo(null);
    assertThatCode(builder::build).doesNotThrowAnyException();
  }

  @Test
  public void hostingIsJavaContainer_when_settingJavaContainerHosting() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withJavaContainer("xxx")
            .withJavaContainerVersion("yyy")
            .build()
        );
    assertThat(builder.build().getHosting().getJavaContainer()).isEqualTo("xxx");
    assertThat(builder.build().getHosting().getJavaContainerVersion()).isEqualTo("yyy");
    assertThat(builder.build().getHosting().getDotnetVersion()).isEqualTo(null);
    assertThat(builder.build().getHosting().getJavaVersion()).isEqualTo(null);
    assertThat(builder.build().getHosting().getPhpVersion()).isEqualTo(null);
    assertThat(builder.build().getHosting().getNodeVersion()).isEqualTo(null);
    assertThatCode(builder::build).doesNotThrowAnyException();
  }

  @Test
  public void exceptionThrown_when_missingJavaContainerVersionHosting() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withJavaContainer("xxx")
            .build()
        );
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("[javaContainerVersion Validation] Incomplete hosting types definition. Both [javaContainer] and [javaContainerVersion] must be set");
  }

  @Test
  public void exceptionThrown_when_missingJavaContainerHosting() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withJavaContainerVersion("yyy")
            .build()
        );
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("[javaContainer Validation] Incomplete hosting types definition. Both [javaContainer] and [javaContainerVersion] must be set");
  }

  @Test
  public void exceptionThrown_when_missingHostingConfiguration() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder().build()
        );
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("[hosting Validation] No hosting types (dotnet, java, java container, nodejs, python, php) have been set");
  }

  @Test
  public void exceptionThrown_when_wrongCustomDomain() {
    var builder = generateSampleBuilder()
        .withCustomDomain("wrong");
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("[AzureWebApp Validation] The CustomDomain must contain at least one period, cannot start or end with a period. CustomDomain are made up of letters, numbers, periods, and dashes");
  }

  @Test
  public void connectivityIsProperlySet_when_settingConnectivity() {
    var webApp = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withJavaVersion("java version")
            .build())
        .withConnectivity(AzureWebAppConnectivity.builder()
            .withApiManagementConfigId("withApiManagementConfigId")
            .withApiDefinitionUrl("apiDefinitionUrl")
            .withCorsAllowedOrigins(new ArrayList<>())
            .withCorsAllowCredentials(true)
            .withHttp2Enabled(true)
            .withHttpLoggingEnabled(true)
            .withMinimumTlsVersion(AzureTlsVersion.TLS1_1)
            .withRequestTracingEnabled(true)
            .withTracingOptions("withTracingOptions")
            .withWebsocketEnabled(true)
            .withClientAffinityEnabled(true)
            .withClientCertEnabled(true)
            .withClientCertExclusionPaths("withClientCertExclusionPaths")
            .withClientCertMode(AzureAppServiceClientCertMode.REQUIRED)
            .withHttpsOnly(true)
            .build()
        ).build();

    assertThat(webApp.getConnectivity().getApiManagementConfigId()).isEqualTo("withApiManagementConfigId");
    assertThat(webApp.getConnectivity().getApiDefinitionUrl()).isEqualTo("apiDefinitionUrl");
    assertThat(webApp.getConnectivity().getCorsAllowedOrigins()).isEqualTo(new ArrayList<>());
    assertTrue(webApp.getConnectivity().getCorsAllowCredentials());
    assertTrue(webApp.getConnectivity().getHttp2Enabled());
    assertTrue(webApp.getConnectivity().getHttpLoggingEnabled());
    assertThat(webApp.getConnectivity().getMinimumTlsVersion()).isEqualTo(AzureTlsVersion.TLS1_1);
    assertTrue(webApp.getConnectivity().getRequestTracingEnabled());
    assertThat(webApp.getConnectivity().getTracingOptions()).isEqualTo("withTracingOptions");
    assertTrue(webApp.getConnectivity().getWebsocketEnabled());
    assertTrue(webApp.getConnectivity().getClientAffinityEnabled());
    assertTrue(webApp.getConnectivity().getClientCertEnabled());
    assertThat(webApp.getConnectivity().getClientCertExclusionPaths()).isEqualTo("withClientCertExclusionPaths");
    assertThat(webApp.getConnectivity().getClientCertMode()).isEqualTo(AzureAppServiceClientCertMode.REQUIRED);
    assertTrue(webApp.getConnectivity().getHttpsOnly());
  }

  @Test
  public void applicationIsProperlySet_when_settingApplication() {
    var webApp = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withJavaVersion("java version")
            .build())
        .withApplication(AzureWebAppApplication.builder()
            .withAppSettings(new HashMap<>())
            .withAlwaysOn(true)
            .withAppCommandLine("appCommandLine")
            .withAutoSwapSlotName("autoSwapSlotName")
            .withConnectionStrings(new HashMap<>())
            .withDefaultDocuments(new ArrayList<>())
            .withDocumentRoot("documentRoot")
            .withNumberOfWorkers(1)
            .withUse32BitWorkerProcess(true)
            .withRemoteDebuggingEnabled(true)
            .withRemoteDebuggingVersion("remoteDebuggingVersion")
            .withWebsiteTimezone("websiteTimezone")
            .build()
        ).build();

    assertThat(webApp.getApplication().getAppSettings()).isEqualTo(new HashMap<>());
    assertTrue(webApp.getApplication().getAlwaysOn());
    assertThat(webApp.getApplication().getAppCommandLine()).isEqualTo("appCommandLine");
    assertThat(webApp.getApplication().getAutoSwapSlotName()).isEqualTo("autoSwapSlotName");
    assertThat(webApp.getApplication().getConnectionStrings()).isEqualTo(new HashMap<>());
    assertThat(webApp.getApplication().getDefaultDocuments()).isEqualTo(new ArrayList<>());
    assertThat(webApp.getApplication().getDocumentRoot()).isEqualTo("documentRoot");
    assertThat(webApp.getApplication().getNumberOfWorkers()).isEqualTo(1);
    assertTrue(webApp.getApplication().getUse32BitWorkerProcess());
    assertTrue(webApp.getApplication().getRemoteDebuggingEnabled());
    assertThat(webApp.getApplication().getRemoteDebuggingVersion()).isEqualTo("remoteDebuggingVersion");
    assertThat(webApp.getApplication().getWebsiteTimezone()).isEqualTo("websiteTimezone");
  }

  @Test
  public void infrastructureIsProperlySet_when_settingInfrastructure() {
    var webApp = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withJavaVersion("java version")
            .build())
        .withInfrastructure(AzureWebAppInfrastructure.builder()
            .withAcrUseManagedIdentityCreds(true)
            .withAcrUserManagedIdentityId("acrUserManagedIdentityId")
            .withDetailedErrorLoggingEnabled(true)
            .withFtpsState(AzureFtpsState.FTPS_ONLY)
            .withFunctionAppScaleLimit(1)
            .withFunctionsRuntimeScaleMonitoringEnabled(true)
            .withHealthCheckPath("healthCheckPath")
            .withKeyVaultReferenceIdentity("keyVaultReferenceIdentity")
            .withMaxDiskSizeInMb(100L)
            .withMaxMemoryInMb(128L)
            .withMaxPercentageCpu(50.0)
            .withLocalMySqlEnabled(true)
            .withLogsDirectorySizeLimit(100)
            .withManagedServiceIdentityId(1)
            .withMinimumElasticInstanceCount(1)
            .withPreWarmedInstanceCount(1)
            .withPublicNetworkAccess("publicNetworkAccess")
            .withPublishingUsername("publishingUsername")
            .withVnetName("vnetName")
            .withVnetRouteAllEnabled(true)
            .withVnetPrivatePortsCount(1)
            .withXServiceIdentityId(1)
            .withContainerSize(100)
            .withCustomDomainVerificationId("customDomainVerificationId")
            .withDailyMemoryTimeQuota(100)
            .withEnabled(true)
            .withHostnamesDisabled(true)
            .withUseHyperV(true)
            .withIsXenon(true)
            .withRedundancyMode(AzureAppServiceRedundancyMode.GEO_REDUNDANT)
            .withIsReserved(true)
            .withIsStorageAccountRequired(true)
            .withVirtualNetworkSubnetId("virtualNetworkSubnetId")
            .withVnetContentSharedEnabled(true)
            .withVnetImagePullEnabled(true)
            .build()
        ).build();

    assertTrue(webApp.getInfrastructure().getAcrUseManagedIdentityCreds());
    assertThat(webApp.getInfrastructure().getAcrUserManagedIdentityId()).isEqualTo("acrUserManagedIdentityId");
    assertTrue(webApp.getInfrastructure().getDetailedErrorLoggingEnabled());
    assertThat(webApp.getInfrastructure().getFtpsState()).isEqualTo(AzureFtpsState.FTPS_ONLY);
    assertThat(webApp.getInfrastructure().getFunctionAppScaleLimit()).isEqualTo(1);
    assertTrue(webApp.getInfrastructure().getFunctionsRuntimeScaleMonitoringEnabled());
    assertThat(webApp.getInfrastructure().getHealthCheckPath()).isEqualTo("healthCheckPath");
    assertThat(webApp.getInfrastructure().getKeyVaultReferenceIdentity()).isEqualTo("keyVaultReferenceIdentity");
    assertThat(webApp.getInfrastructure().getMaxDiskSizeInMb()).isEqualTo(100L);
    assertThat(webApp.getInfrastructure().getMaxMemoryInMb()).isEqualTo(128L);
    assertThat(webApp.getInfrastructure().getMaxPercentageCpu()).isEqualTo(50.0);
    assertTrue(webApp.getInfrastructure().getLocalMySqlEnabled());
    assertThat(webApp.getInfrastructure().getLogsDirectorySizeLimit()).isEqualTo(100);
    assertThat(webApp.getInfrastructure().getManagedServiceIdentityId()).isEqualTo(1);
    assertThat(webApp.getInfrastructure().getMinimumElasticInstanceCount()).isEqualTo(1);
    assertThat(webApp.getInfrastructure().getPreWarmedInstanceCount()).isEqualTo(1);
    assertThat(webApp.getInfrastructure().getPublicNetworkAccess()).isEqualTo("publicNetworkAccess");
    assertThat(webApp.getInfrastructure().getPublishingUsername()).isEqualTo("publishingUsername");
    assertThat(webApp.getInfrastructure().getVnetName()).isEqualTo("vnetName");
    assertTrue(webApp.getInfrastructure().getVnetRouteAllEnabled());
    assertThat(webApp.getInfrastructure().getVnetPrivatePortsCount()).isEqualTo(1);
    assertThat(webApp.getInfrastructure().getXServiceIdentityId()).isEqualTo(1);
    assertThat(webApp.getInfrastructure().getContainerSize()).isEqualTo(100);
    assertThat(webApp.getInfrastructure().getCustomDomainVerificationId()).isEqualTo("customDomainVerificationId");
    assertThat(webApp.getInfrastructure().getDailyMemoryTimeQuota()).isEqualTo(100);
    assertTrue(webApp.getInfrastructure().getEnabled());
    assertTrue(webApp.getInfrastructure().getHostnamesDisabled());
    assertTrue(webApp.getInfrastructure().getUseHyperV());
    assertTrue(webApp.getInfrastructure().getIsXenon());
    assertThat(webApp.getInfrastructure().getRedundancyMode()).isEqualTo(AzureAppServiceRedundancyMode.GEO_REDUNDANT);
    assertTrue(webApp.getInfrastructure().getIsReserved());
    assertTrue(webApp.getInfrastructure().getIsStorageAccountRequired());
    assertThat(webApp.getInfrastructure().getVirtualNetworkSubnetId()).isEqualTo("virtualNetworkSubnetId");
    assertTrue(webApp.getInfrastructure().getVnetContentSharedEnabled());
    assertTrue(webApp.getInfrastructure().getVnetImagePullEnabled());
  }

  @Test
  public void returns_without_errors_when_appServiceBuiltWithProperAppServicePlan() {
    var appServicePlanName = "name-test";
    var selectedRegion = AzureRegion.US_EAST;
    var selectedOperatingSystem = AzureOsType.LINUX;
    var selectedPricingPlan = AzurePricingPlan.BASIC_B1;
    var tags = Map.of("tagKey", "tagValue");


    var resourceGroup = AzureResourceGroup.builder()
        .withName("new-resource-group")
        .withRegion(selectedRegion)
        .build();

    var appServicePlan = AzureAppServicePlan.builder()
        .withName(appServicePlanName)
        .withAzureResourceGroup(resourceGroup)
        .withRegion(selectedRegion)
        .withOperatingSystem(selectedOperatingSystem)
        .withPricingPlan(selectedPricingPlan)
        .withZoneRedundancyEnabled()
        .withTags(tags)
        .build();
    
    var certificate = AzureKeyVaultCertificate.builder()
        .withKeyVaultId("key-vault-id")
        .withName("certificate-name")
        .build();

    var webApp = generateSampleBuilder()
        .withAppServicePlan(appServicePlan)
        .withHosting(AzureWebAppHosting.builder()
            .withJavaVersion("java version")
            .build())
        .withCertificate(certificate)
        .withCustomDomain("custom.domain.com")
        .withCustomDomain("custom1.domain.com")
        .withResourceGroup(resourceGroup)
        .withTags(tags)
        .build();

    var json = TestUtils.getJsonRepresentation(webApp);
    
    assertThat(json).isNotBlank();
    
    assertThat(appServicePlan.getName()).isEqualTo(appServicePlanName);
    assertThat(appServicePlan.getTags().size()).isEqualTo(1);
    assertThat(appServicePlan.getTags()).isEqualTo(tags);
    
    assertThat(webApp.getAppServicePlan()).isEqualTo(appServicePlan);
    assertThat(webApp.getTags().size()).isEqualTo(1);
    assertThat(webApp.getTags()).isEqualTo(tags);
  }
  
  private AzureWebApp.AzureWebAppBuilder generateBuilder() {
    return builder().withId("webapp");
  }

  private AzureWebApp.AzureWebAppBuilder generateSampleBuilder() {
    return generateBuilder()
        .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
        .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withBranchName("env/test")
        .withSecretPasswordKey("***");
  }

}
