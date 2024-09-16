package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.Validatable;
import com.yanchware.fractal.sdk.domain.livesystem.SourceControlType;
import com.yanchware.fractal.sdk.domain.livesystem.paas.SupportedTlsVersions;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice.valueobjects.AzureFtpsState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class AzureWebAppConfiguration implements Validatable {
  private Boolean acrUseManagedIdentityCredentials;
  private String acrUserManagedIdentityId;
  private Boolean alwaysOn;
  private String apiDefinitionUrl;
  private String apiManagementConfigId;
  private String appCommandLine;
  private Map<String, String> appSettings;
  private String autoSwapSlotName;
  private Collection<AzureConnectionStringInfo> connectionStrings;
  private AzureWebAppCorsSettings corsSettings;
  private Collection<String> defaultDocuments;
  private Boolean detailedErrorLoggingEnabled;
  private String documentRoot;
  private AzureFtpsState ftpsState;
  private Integer functionAppScaleLimit;
  private Boolean functionsRuntimeScaleMonitoringEnabled;
  private Collection<AzureWebAppHandlerMapping> handlerMappings;
  private String healthCheckPath;
  private Boolean http20Enabled;
  private Boolean httpLoggingEnabled;
  private Collection<AzureWebAppIpSecurityRestriction> ipSecurityRestrictions;
  private String javaContainer;
  private String javaContainerVersion;
  private String javaVersion;
  private String keyVaultReferenceIdentity;
  private AzureSiteLimits limits;
  private String linuxFxVersion;
  private AzureSiteLoadBalancing loadBalancing;
  private Boolean localMySqlEnabled;
  private Integer logsDirectorySizeLimit;
  private AzureManagedPipelineMode managedPipelineMode;
  private Integer managedServiceIdentityId;
  private SupportedTlsVersions minTlsVersion;
  private Integer minimumElasticInstanceCount;
  private String dotnetVersion;
  private String nodeVersion;
  private Integer numberOfWorkers;
  private String phpVersion;
  private String powerShellVersion;
  private Integer preWarmedInstanceCount;
  private String publicNetworkAccess;
  private String publishingUsername;
  private AzureWebAppPushSettings pushSettings;
  private String pythonVersion;
  private Boolean remoteDebuggingEnabled;
  private String remoteDebuggingVersion;
  private Boolean requestTracingEnabled;
  private String requestTracingExpirationTime;
  private Collection<AzureWebAppIpSecurityRestriction> scmIpSecurityRestrictions;
  private Boolean scmIpSecurityRestrictionsUseMain;
  private SupportedTlsVersions scmMinTlsVersion;
  private SourceControlType scmType;
  private String tracingOptions;
  private Boolean use32BitWorkerProcess;
  private Collection<AzureVirtualApplication> virtualApplications;
  private String vnetName;
  private Integer vnetPrivatePortsCount;
  private Boolean vnetRouteAllEnabled;
  private Boolean webSocketsEnabled;
  private String websiteTimeZone;
  private String windowsFxVersion;
  private Integer xManagedServiceIdentityId;

  public static AzureWebAppConfigurationBuilder builder() {
    return new AzureWebAppConfigurationBuilder();
  }

  public static class AzureWebAppConfigurationBuilder {
    private final AzureWebAppConfiguration configuration;
    private final AzureWebAppConfigurationBuilder builder;

    public AzureWebAppConfigurationBuilder() {
      configuration = new AzureWebAppConfiguration();
      builder = this;
    }

    public AzureWebAppConfigurationBuilder withAcrUseManagedIdentityCredentials(Boolean acrUseManagedIdentityCredentials) {
      configuration.setAcrUseManagedIdentityCredentials(acrUseManagedIdentityCredentials);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withAcrUserManagedIdentityId(String acrUserManagedIdentityId) {
      configuration.setAcrUserManagedIdentityId(acrUserManagedIdentityId);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withAlwaysOn(Boolean alwaysOn) {
      configuration.setAlwaysOn(alwaysOn);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withApiDefinitionUrl(String apiDefinitionUrl) {
      configuration.setApiDefinitionUrl(apiDefinitionUrl);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withApiManagementConfigId(String apiManagementConfigId) {
      configuration.setApiManagementConfigId(apiManagementConfigId);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withAppCommandLine(String appCommandLine) {
      configuration.setAppCommandLine(appCommandLine);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withAppSettings(Map<String, String> appSettings) {
      configuration.setAppSettings(appSettings);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withAutoSwapSlotName(String autoSwapSlotName) {
      configuration.setAutoSwapSlotName(autoSwapSlotName);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withConnectionStrings(Collection<AzureConnectionStringInfo> connectionStrings) {
      configuration.setConnectionStrings(connectionStrings);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withCorsSettings(AzureWebAppCorsSettings corsSettings) {
      configuration.setCorsSettings(corsSettings);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withDefaultDocuments(Collection<String> defaultDocuments) {
      configuration.setDefaultDocuments(defaultDocuments);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withDetailedErrorLoggingEnabled(Boolean detailedErrorLoggingEnabled) {
      configuration.setDetailedErrorLoggingEnabled(detailedErrorLoggingEnabled);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withDocumentRoot(String documentRoot) {
      configuration.setDocumentRoot(documentRoot);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withFtpsState(AzureFtpsState ftpsState) {
      configuration.setFtpsState(ftpsState);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withFunctionAppScaleLimit(Integer functionAppScaleLimit) {
      configuration.setFunctionAppScaleLimit(functionAppScaleLimit);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withFunctionsRuntimeScaleMonitoringEnabled(Boolean functionsRuntimeScaleMonitoringEnabled) {
      configuration.setFunctionsRuntimeScaleMonitoringEnabled(functionsRuntimeScaleMonitoringEnabled);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withHandlerMappings(Collection<AzureWebAppHandlerMapping> handlerMappings) {
      configuration.setHandlerMappings(handlerMappings);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withHealthCheckPath(String healthCheckPath) {
      configuration.setHealthCheckPath(healthCheckPath);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withHttp20Enabled(Boolean http20Enabled) {
      configuration.setHttp20Enabled(http20Enabled);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withHttpLoggingEnabled(Boolean httpLoggingEnabled) {
      configuration.setHttpLoggingEnabled(httpLoggingEnabled);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withIpSecurityRestrictions(Collection<AzureWebAppIpSecurityRestriction> ipSecurityRestrictions) {
      configuration.setIpSecurityRestrictions(ipSecurityRestrictions);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withJavaContainer(String javaContainer) {
      configuration.setJavaContainer(javaContainer);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withJavaContainerVersion(String javaContainerVersion) {
      configuration.setJavaContainerVersion(javaContainerVersion);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withJavaVersion(String javaVersion) {
      configuration.setJavaVersion(javaVersion);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withKeyVaultReferenceIdentity(String keyVaultReferenceIdentity) {
      configuration.setKeyVaultReferenceIdentity(keyVaultReferenceIdentity);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withLimits(AzureSiteLimits limits) {
      configuration.setLimits(limits);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withLinuxFxVersion(String linuxFxVersion) {
      configuration.setLinuxFxVersion(linuxFxVersion);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withLoadBalancing(AzureSiteLoadBalancing loadBalancing) {
      configuration.setLoadBalancing(loadBalancing);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withLocalMySqlEnabled(Boolean localMySqlEnabled) {
      configuration.setLocalMySqlEnabled(localMySqlEnabled);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withLogsDirectorySizeLimit(Integer logsDirectorySizeLimit) {
      configuration.setLogsDirectorySizeLimit(logsDirectorySizeLimit);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withManagedPipelineMode(AzureManagedPipelineMode managedPipelineMode) {
      configuration.setManagedPipelineMode(managedPipelineMode);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withManagedServiceIdentityId(Integer managedServiceIdentityId) {
      configuration.setManagedServiceIdentityId(managedServiceIdentityId);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withMinTlsVersion(SupportedTlsVersions minTlsVersion) {
      configuration.setMinTlsVersion(minTlsVersion);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withMinimumElasticInstanceCount(Integer minimumElasticInstanceCount) {
      configuration.setMinimumElasticInstanceCount(minimumElasticInstanceCount);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withDotnetVersion(String dotnetVersion) {
      configuration.setDotnetVersion(dotnetVersion);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withNodeVersion(String nodeVersion) {
      configuration.setNodeVersion(nodeVersion);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withNumberOfWorkers(Integer numberOfWorkers) {
      configuration.setNumberOfWorkers(numberOfWorkers);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withPhpVersion(String phpVersion) {
      configuration.setPhpVersion(phpVersion);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withPowerShellVersion(String powerShellVersion) {
      configuration.setPowerShellVersion(powerShellVersion);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withPreWarmedInstanceCount(Integer preWarmedInstanceCount) {
      configuration.setPreWarmedInstanceCount(preWarmedInstanceCount);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withPublicNetworkAccess(String publicNetworkAccess) {
      configuration.setPublicNetworkAccess(publicNetworkAccess);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withPublishingUsername(String publishingUsername) {
      configuration.setPublishingUsername(publishingUsername);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withPushSettings(AzureWebAppPushSettings pushSettings) {
      configuration.setPushSettings(pushSettings);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withPythonVersion(String pythonVersion) {
      configuration.setPythonVersion(pythonVersion);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withRemoteDebuggingEnabled(Boolean remoteDebuggingEnabled) {
      configuration.setRemoteDebuggingEnabled(remoteDebuggingEnabled);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withRemoteDebuggingVersion(String remoteDebuggingVersion) {
      configuration.setRemoteDebuggingVersion(remoteDebuggingVersion);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withRequestTracingEnabled(Boolean requestTracingEnabled) {
      configuration.setRequestTracingEnabled(requestTracingEnabled);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withRequestTracingExpirationTime(String requestTracingExpirationTime) {
      configuration.setRequestTracingExpirationTime(requestTracingExpirationTime);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withScmIpSecurityRestrictions(Collection<AzureWebAppIpSecurityRestriction> scmIpSecurityRestrictions) {
      configuration.setScmIpSecurityRestrictions(scmIpSecurityRestrictions);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withScmIpSecurityRestrictionsUseMain(Boolean scmIpSecurityRestrictionsUseMain) {
      configuration.setScmIpSecurityRestrictionsUseMain(scmIpSecurityRestrictionsUseMain);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withScmMinTlsVersion(SupportedTlsVersions scmMinTlsVersion) {
      configuration.setScmMinTlsVersion(scmMinTlsVersion);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withScmType(SourceControlType scmType) {
      configuration.setScmType(scmType);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withTracingOptions(String tracingOptions) {
      configuration.setTracingOptions(tracingOptions);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withUse32BitWorkerProcess(Boolean use32BitWorkerProcess) {
      configuration.setUse32BitWorkerProcess(use32BitWorkerProcess);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withVirtualApplications(Collection<AzureVirtualApplication> virtualApplications) {
      configuration.setVirtualApplications(virtualApplications);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withVnetName(String vnetName) {
      configuration.setVnetName(vnetName);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withVnetPrivatePortsCount(Integer vnetPrivatePortsCount) {
      configuration.setVnetPrivatePortsCount(vnetPrivatePortsCount);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withVnetRouteAllEnabled(Boolean vnetRouteAllEnabled) {
      configuration.setVnetRouteAllEnabled(vnetRouteAllEnabled);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withWebSocketsEnabled(Boolean webSocketsEnabled) {
      configuration.setWebSocketsEnabled(webSocketsEnabled);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withWebsiteTimeZone(String websiteTimeZone) {
      configuration.setWebsiteTimeZone(websiteTimeZone);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withWindowsFxVersion(String windowsFxVersion) {
      configuration.setWindowsFxVersion(windowsFxVersion);
      return builder;
    }

    public AzureWebAppConfigurationBuilder withXManagedServiceIdentityId(Integer xManagedServiceIdentityId) {
      configuration.setXManagedServiceIdentityId(xManagedServiceIdentityId);
      return builder;
    }

    public AzureWebAppConfiguration build() {
      Collection<String> errors = configuration.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
            "AzureWebAppConfiguration validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return configuration;
    }
  }


  @Override
  public Collection<String> validate() {
    final var DUPLICATED_HOSTING_TYPES = "[AzureWebAppConfiguration Validation] Only one hosting configuration can be set. [%s] has already been set";
    final var INCOMPLETE_JAVA_CONTAINER = "[AzureWebAppConfiguration Validation] Incomplete hosting types definition. Both [javaContainer] and [javaContainerVersion] must be set";

    var errors = new ArrayList<String>();

    var versionType = "";

    if (!StringUtils.isBlank(getDotnetVersion())) {
      versionType = "DOTNET";
    }

    if (getJavaVersion() != null && !StringUtils.isBlank(getJavaVersion())) {
      if (isNotBlank(versionType)) {
        errors.add(String.format(DUPLICATED_HOSTING_TYPES, versionType));
        return errors;
      }
      versionType = "JAVA";
    }

    if (getPythonVersion() != null && !StringUtils.isBlank(getPythonVersion())) {
      if (isNotBlank(versionType)) {
        errors.add(String.format(DUPLICATED_HOSTING_TYPES, versionType));
        return errors;
      }
      versionType = "PYTHON";
    }

    if (getPhpVersion() != null && !StringUtils.isBlank(getPhpVersion())) {
      if (isNotBlank(versionType)) {
        errors.add(String.format(DUPLICATED_HOSTING_TYPES, versionType));
        return errors;
      }
      versionType = "PHP";
    }

    if (getNodeVersion() != null && !StringUtils.isBlank(getNodeVersion())) {
      if (isNotBlank(versionType)) {
        errors.add(String.format(DUPLICATED_HOSTING_TYPES, versionType));
        return errors;
      }
      versionType = "NODE";
    }

    if (getJavaContainer() != null && !StringUtils.isBlank(getJavaContainer()) &&
        getJavaContainerVersion() != null && !StringUtils.isBlank(getJavaContainerVersion())) {
      if (isNotBlank(versionType)) {
        errors.add(String.format(DUPLICATED_HOSTING_TYPES, versionType));
        return errors;
      }
    } else if ((getJavaContainer() == null || StringUtils.isBlank(getJavaContainer())) &&
        getJavaContainerVersion() != null && !StringUtils.isBlank(getJavaContainerVersion())) {
      errors.add(INCOMPLETE_JAVA_CONTAINER);
      return errors;
    } else if (getJavaContainer() != null && !StringUtils.isBlank(getJavaContainer()) &&
        (getJavaContainerVersion() == null || StringUtils.isBlank(getJavaContainerVersion()))) {
      errors.add(INCOMPLETE_JAVA_CONTAINER);
      return errors;
    }

    return errors;
  }
}
