package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.livesystem.SourceControlType;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.SupportedTlsVersions;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.valueobjects.AzureFtpsState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureWebAppConfiguration implements Validatable {
  Boolean acrUseManagedIdentityCreds;
  String acrUserManagedIdentityId;
  Boolean alwaysOn;
  String apiDefinitionUrl;
  String apiManagementConfigId;
  String appCommandLine;
  Map<String, String> appSettings;
  String autoSwapSlotName;
  Collection<AzureConnectionStringInfo> connectionStrings;
  AzureWebAppCorsSettings corsSettings;
  Collection<String> defaultDocuments;
  Boolean detailedErrorLoggingEnabled;
  String documentRoot;
  AzureFtpsState ftpsState;
  Integer functionAppScaleLimit;
  Boolean functionsRuntimeScaleMonitoringEnabled;
  AzureWebAppHandlerMappings handlerMappings;
  String healthCheckPath;
  Boolean http20Enabled;
  Boolean httpLoggingEnabled;
  Collection<AzureWebAppIpSecurityRestriction> ipSecurityRestrictions;
  String javaContainer;
  String javaContainerVersion;
  String javaVersion;
  String keyVaultReferenceIdentity;
  AzureSiteLimits limits;
  String linuxFxVersion;
  AzureSiteLoadBalancing loadBalancing;
  Boolean localMySqlEnabled;
  Integer logsDirectorySizeLimit;
  AzureManagedPipelineMode managedPipelineMode;
  Integer managedServiceIdentityId;
  SupportedTlsVersions minTlsVersion;
  Integer minimumElasticInstanceCount;
  String dotnetVersion;
  String nodeVersion;
  Integer numberOfWorkers;
  String phpVersion;
  String powerShellVersion;
  Integer preWarmedInstanceCount;
  String publicNetworkAccess;
  String publishingUsername;
  AzureWebAppPushSettings pushSettings;
  String pythonVersion;
  Boolean remoteDebuggingEnabled;
  String remoteDebuggingVersion;
  Boolean requestTracingEnabled;
  String requestTracingExpirationTime;
  Collection<AzureWebAppIpSecurityRestriction> scmIpSecurityRestrictions;
  Boolean scmIpSecurityRestrictionsUseMain;
  SupportedTlsVersions scmMinTlsVersion;
  SourceControlType scmType;
  String tracingOptions;
  Boolean use32BitWorkerProcess;
  Collection<AzureVirtualApplication> virtualApplications;
  String vnetName;
  Integer vnetPrivatePortsCount;
  Boolean vnetRouteAllEnabled;
  Boolean webSocketsEnabled;
  String websiteTimeZone;
  String windowsFxVersion;
  Integer xManagedServiceIdentityId;


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
      if(isNotBlank(versionType)) {
        errors.add(String.format(DUPLICATED_HOSTING_TYPES, versionType));
        return errors;
      }
      versionType = "JAVA";
    }
    
    if (getPythonVersion() != null && !StringUtils.isBlank(getPythonVersion())) {
      if(isNotBlank(versionType)) {
        errors.add(String.format(DUPLICATED_HOSTING_TYPES, versionType));
        return errors;
      }
      versionType = "PYTHON";
    }

    if (getPhpVersion() != null && !StringUtils.isBlank(getPhpVersion())) {
      if(isNotBlank(versionType)) {
        errors.add(String.format(DUPLICATED_HOSTING_TYPES, versionType));
        return errors;
      } 
        versionType = "PHP";
    }
    
    if (getNodeVersion() != null && !StringUtils.isBlank(getNodeVersion())) {
      if(isNotBlank(versionType)) {
        errors.add(String.format(DUPLICATED_HOSTING_TYPES, versionType));
        return errors;
      }
      versionType = "NODE";
    }

    if (getJavaContainer() != null && !StringUtils.isBlank(getJavaContainer()) &&
        getJavaContainerVersion() != null && !StringUtils.isBlank(getJavaContainerVersion())) {
      if(isNotBlank(versionType)) {
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
