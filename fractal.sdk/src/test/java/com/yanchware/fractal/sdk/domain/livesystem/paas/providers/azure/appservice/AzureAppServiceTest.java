package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.livesystem.paas.SupportedTlsVersions;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureOsType;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzurePricingPlan;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice.valueobjects.AzureAppServiceClientCertMode;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice.valueobjects.AzureAppServiceRedundancyMode;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice.valueobjects.AzureFtpsState;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice.AzureWebApp.builder;
import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_WEBAPP;
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
        .withConfiguration(AzureWebAppConfiguration.builder().withDotnetVersion("DOTNET:***").build());
    assertThat(builder.build().getType()).isEqualTo(PAAS_WEBAPP);
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
  public void exceptionThrown_when_mixingWindowsRuntimeStackWithLinuxOs() {
    var builder = generateSampleBuilder()
        .withOperatingSystem(AzureOsType.LINUX)
        .withRuntimeStack(AzureWebAppWindowsRuntimeStack.DOTNET_7);
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("The Runtime Stack and Operating System mismatches. Please choose AzureWebAppLinuxRuntimeStack or change Operating System to WINDOWS");
  }

  @Test
  public void exceptionThrown_when_mixingLinuxRuntimeStackWithWindowsOs() {
    var builder = generateSampleBuilder()
        .withOperatingSystem(AzureOsType.WINDOWS)
        .withRuntimeStack(AzureWebAppLinuxRuntimeStack.DOTNET_CORE_7_0);
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("The Runtime Stack and Operating System mismatches. Please choose AzureWebAppWindowsRuntimeStack or change Operating System to LINUX");
  }

  @Test
  public void exceptionThrown_when_mixingJavaAndDotnetHosting() {
    var builder = AzureWebAppConfiguration.builder()
            .withDotnetVersion("DOTNETCORE:7.0")
            .withJavaVersion("JAVA:17-java17");
    
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Only one hosting configuration can be set. [DOTNET] has already been set");
  }

  @Test
  public void exceptionThrown_when_mixingJavaAndPhpHosting() {
    var builder = AzureWebAppConfiguration.builder()
            .withJavaVersion("JAVA:17-java17")
            .withPhpVersion("PHP:8.1");
    
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Only one hosting configuration can be set. [JAVA] has already been set]");
  }

  @Test
  public void hostingIsDotnet_when_settingDotnetVersion() {
    var builder = generateSampleBuilder()
        .withConfiguration(AzureWebAppConfiguration.builder()
            .withDotnetVersion("DOTNETCORE:7.0")
            .build()
        );
    assertThat(builder.build().getConfiguration().getDotnetVersion()).isEqualTo("DOTNETCORE:7.0");
    assertThatCode(builder::build).doesNotThrowAnyException();
  }

  @Test
  public void hostingIsPhp_when_settingPhpVersion() {
    var builder = generateSampleBuilder()
        .withOperatingSystem(AzureOsType.LINUX)
        .withRuntimeStack(AzureWebAppLinuxRuntimeStack.DOTNET_CORE_6_0)
        .withConfiguration(AzureWebAppConfiguration.builder()
            .withPhpVersion("PHP:8.1")
            .build()
        );
    assertThat(builder.build().getConfiguration().getPhpVersion()).isEqualTo("PHP:8.1");
    assertThat(builder.build().getConfiguration().getPythonVersion()).isEqualTo(null);
    assertThatCode(builder::build).doesNotThrowAnyException();
  }

  @Test
  public void hostingIsJavaContainer_when_settingJavaContainerHosting() {
    var builder = generateSampleBuilder()
        .withConfiguration(AzureWebAppConfiguration.builder()
            .withJavaContainer("xxx")
            .withJavaContainerVersion("yyy")
            .build()
        );
    assertThat(builder.build().getConfiguration().getJavaContainer()).isEqualTo("xxx");
    assertThat(builder.build().getConfiguration().getJavaContainerVersion()).isEqualTo("yyy");
    assertThat(builder.build().getConfiguration().getDotnetVersion()).isEqualTo(null);
    assertThat(builder.build().getConfiguration().getJavaVersion()).isEqualTo(null);
    assertThat(builder.build().getConfiguration().getPhpVersion()).isEqualTo(null);
    assertThat(builder.build().getConfiguration().getNodeVersion()).isEqualTo(null);
    assertThatCode(builder::build).doesNotThrowAnyException();
  }

  @Test
  public void exceptionThrown_when_missingJavaContainerVersionHosting() {
    var builder = AzureWebAppConfiguration.builder()
            .withJavaContainer("xxx");
    
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Incomplete hosting types definition. Both [javaContainer] and [javaContainerVersion] must be set");
  }

  @Test
  public void exceptionThrown_when_missingJavaContainerHosting() {
    var builder = AzureWebAppConfiguration.builder()
            .withJavaContainerVersion("yyy");
    
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Incomplete hosting types definition. Both [javaContainer] and [javaContainerVersion] must be set");
  }

  @Test
  public void exceptionThrown_when_missingRuntimeStack() {
    var builder = generateSampleBuilder()
        .withConfiguration(AzureWebAppConfiguration.builder().build());
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("[AzureWebApp Validation] The Runtime Stack is either empty or blank and it is required");
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
  public void returns_without_errors_when_settingConfiguration() {
    var webApp = generateSampleBuilder()
        .withOperatingSystem(AzureOsType.LINUX)
        .withRuntimeStack(AzureWebAppLinuxRuntimeStack.JAVA_17)
        .withConfiguration(AzureWebAppConfiguration.builder()
            .withJavaVersion("java version")
            .withApiManagementConfigId("withApiManagementConfigId")
            .withApiDefinitionUrl("apiDefinitionUrl")
            .withCorsSettings(AzureWebAppCorsSettings.builder()
                .withSupportCredentials(true)
                .build())
            .withHttp20Enabled(true)
            .withHttpLoggingEnabled(true)
            .withMinTlsVersion(SupportedTlsVersions.ONE_ONE)
            .withRequestTracingEnabled(true)
            .withTracingOptions("withTracingOptions")
            .withWebSocketsEnabled(true)
            .withJavaVersion("java version")
            .withAppSettings(new HashMap<>())
            .withAlwaysOn(true)
            .withAppCommandLine("appCommandLine")
            .withAutoSwapSlotName("autoSwapSlotName")
            .withConnectionStrings(new ArrayList<>())
            .withDefaultDocuments(new ArrayList<>())
            .withDocumentRoot("documentRoot")
            .withNumberOfWorkers(1)
            .withUse32BitWorkerProcess(true)
            .withRemoteDebuggingEnabled(true)
            .withRemoteDebuggingVersion("remoteDebuggingVersion")
            .withWebsiteTimeZone("websiteTimezone")
            .withJavaVersion("java version")
            .withAcrUseManagedIdentityCredentials(true)
            .withAcrUserManagedIdentityId("acrUserManagedIdentityId")
            .withDetailedErrorLoggingEnabled(true)
            .withFtpsState(AzureFtpsState.FTPS_ONLY)
            .withFunctionAppScaleLimit(1)
            .withFunctionsRuntimeScaleMonitoringEnabled(true)
            .withHealthCheckPath("healthCheckPath")
            .withKeyVaultReferenceIdentity("keyVaultReferenceIdentity")
            .withLimits(AzureSiteLimits.builder()
                .withMaxDiskSizeInMb(100)
                .withMaxMemoryInMb(128)
                .withMaxPercentageCpu(50.0)
                .build())
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
            .withXManagedServiceIdentityId(1)
            .build()
        )
        .withClientAffinityEnabled(true)
        .withClientCertEnabled(true)
        .withClientCertExclusionPaths("withClientCertExclusionPaths")
        .withClientCertMode(AzureAppServiceClientCertMode.REQUIRED)
        .withHttpsOnly(true)
        .withContainerSize(100)
        .withCustomDomainVerificationId("customDomainVerificationId")
        .withDailyMemoryTimeQuota(100)
        .withEnabled(true)
        .withHostNamesDisabled(true)
        .withHyperV(true)
        .withRedundancyMode(AzureAppServiceRedundancyMode.GEO_REDUNDANT)
        .withReserved(true)
        .withStorageAccountRequired(true)
        .withVirtualNetworkSubnetId("virtualNetworkSubnetId")
        .withVnetContentShareEnabled(true)
        .withVnetImagePullEnabled(true)
        .withCloningInfo(AzureWebAppCloningInfo.builder()
            .withOverwrite(true)
            .build())
        .withHostingEnvironmentProfileId("HostingEnvironmentProfileId")
        .withScmSiteAlsoStopped(true)
        .build();

    assertThat(webApp.getConfiguration().getApiManagementConfigId()).isEqualTo("withApiManagementConfigId");
    assertThat(webApp.getConfiguration().getApiDefinitionUrl()).isEqualTo("apiDefinitionUrl");
    assertTrue(webApp.getConfiguration().getCorsSettings().getSupportCredentials());
    assertTrue(webApp.getConfiguration().getHttp20Enabled());
    assertTrue(webApp.getConfiguration().getHttpLoggingEnabled());
    assertThat(webApp.getConfiguration().getMinTlsVersion()).isEqualTo(SupportedTlsVersions.ONE_ONE);
    assertTrue(webApp.getConfiguration().getRequestTracingEnabled());
    assertThat(webApp.getConfiguration().getTracingOptions()).isEqualTo("withTracingOptions");
    assertTrue(webApp.getConfiguration().getWebSocketsEnabled());
    assertThat(webApp.getConfiguration().getAppSettings()).isEqualTo(new HashMap<>());
    assertTrue(webApp.getConfiguration().getAlwaysOn());
    assertThat(webApp.getConfiguration().getAppCommandLine()).isEqualTo("appCommandLine");
    assertThat(webApp.getConfiguration().getAutoSwapSlotName()).isEqualTo("autoSwapSlotName");
    assertThat(webApp.getConfiguration().getConnectionStrings()).isEqualTo(new ArrayList<>());
    assertThat(webApp.getConfiguration().getDefaultDocuments()).isEqualTo(new ArrayList<>());
    assertThat(webApp.getConfiguration().getDocumentRoot()).isEqualTo("documentRoot");
    assertThat(webApp.getConfiguration().getNumberOfWorkers()).isEqualTo(1);
    assertTrue(webApp.getConfiguration().getUse32BitWorkerProcess());
    assertTrue(webApp.getConfiguration().getRemoteDebuggingEnabled());
    assertThat(webApp.getConfiguration().getRemoteDebuggingVersion()).isEqualTo("remoteDebuggingVersion");
    assertThat(webApp.getConfiguration().getWebsiteTimeZone()).isEqualTo("websiteTimezone");
    assertTrue(webApp.getConfiguration().getAcrUseManagedIdentityCredentials());
    assertThat(webApp.getConfiguration().getAcrUserManagedIdentityId()).isEqualTo("acrUserManagedIdentityId");
    assertTrue(webApp.getConfiguration().getDetailedErrorLoggingEnabled());
    assertThat(webApp.getConfiguration().getFtpsState()).isEqualTo(AzureFtpsState.FTPS_ONLY);
    assertThat(webApp.getConfiguration().getFunctionAppScaleLimit()).isEqualTo(1);
    assertTrue(webApp.getConfiguration().getFunctionsRuntimeScaleMonitoringEnabled());
    assertThat(webApp.getConfiguration().getHealthCheckPath()).isEqualTo("healthCheckPath");
    assertThat(webApp.getConfiguration().getKeyVaultReferenceIdentity()).isEqualTo("keyVaultReferenceIdentity");
    assertThat(webApp.getConfiguration().getLimits().getMaxDiskSizeInMb()).isEqualTo(100);
    assertThat(webApp.getConfiguration().getLimits().getMaxMemoryInMb()).isEqualTo(128);
    assertThat(webApp.getConfiguration().getLimits().getMaxPercentageCpu()).isEqualTo(50.0);
    assertTrue(webApp.getConfiguration().getLocalMySqlEnabled());
    assertThat(webApp.getConfiguration().getLogsDirectorySizeLimit()).isEqualTo(100);
    assertThat(webApp.getConfiguration().getManagedServiceIdentityId()).isEqualTo(1);
    assertThat(webApp.getConfiguration().getMinimumElasticInstanceCount()).isEqualTo(1);
    assertThat(webApp.getConfiguration().getPreWarmedInstanceCount()).isEqualTo(1);
    assertThat(webApp.getConfiguration().getPublicNetworkAccess()).isEqualTo("publicNetworkAccess");
    assertThat(webApp.getConfiguration().getPublishingUsername()).isEqualTo("publishingUsername");
    assertThat(webApp.getConfiguration().getVnetName()).isEqualTo("vnetName");
    assertTrue(webApp.getConfiguration().getVnetRouteAllEnabled());
    assertThat(webApp.getConfiguration().getVnetPrivatePortsCount()).isEqualTo(1);
    assertThat(webApp.getConfiguration().getXManagedServiceIdentityId()).isEqualTo(1);
    assertTrue(webApp.getClientAffinityEnabled());
    assertTrue(webApp.getClientCertEnabled());
    assertThat(webApp.getClientCertExclusionPaths()).isEqualTo("withClientCertExclusionPaths");
    assertThat(webApp.getClientCertMode()).isEqualTo(AzureAppServiceClientCertMode.REQUIRED);
    assertTrue(webApp.getHttpsOnly());
    assertThat(webApp.getContainerSize()).isEqualTo(100);
    assertThat(webApp.getCustomDomainVerificationId()).isEqualTo("customDomainVerificationId");
    assertThat(webApp.getDailyMemoryTimeQuota()).isEqualTo(100);
    assertTrue(webApp.getEnabled());
    assertTrue(webApp.getHostNamesDisabled());
    assertTrue(webApp.getHyperV());
    assertThat(webApp.getRedundancyMode()).isEqualTo(AzureAppServiceRedundancyMode.GEO_REDUNDANT);
    assertTrue(webApp.getReserved());
    assertTrue(webApp.getStorageAccountRequired());
    assertThat(webApp.getVirtualNetworkSubnetId()).isEqualTo("virtualNetworkSubnetId");
    assertTrue(webApp.getVnetContentShareEnabled());
    assertTrue(webApp.getVnetImagePullEnabled());
    assertTrue(webApp.getCloningInfo().getOverwrite());
    assertThat(webApp.getHostingEnvironmentProfileId()).isEqualTo("HostingEnvironmentProfileId");
    assertTrue(webApp.getScmSiteAlsoStopped());
  }

  @Test
  public void returns_without_errors_when_appServiceBuiltWithProperAppServicePlan() {
    var appServicePlanName = "name-test";
    var selectedRegion = AzureRegion.EAST_US;
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
        .withOperatingSystem(AzureOsType.WINDOWS)
        .withRuntimeStack(AzureWebAppWindowsRuntimeStack.JAVA_8_TOMCAT_10_0)
        .withAppServicePlan(appServicePlan)
        .withConfiguration(AzureWebAppConfiguration.builder()
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
        .withSecretPasswordKey("***")
        .withRegion(AzureRegion.WEST_EUROPE);
  }

}
