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
