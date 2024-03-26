package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSWorkloadDeploymentSlot;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsRecord;
import com.yanchware.fractal.sdk.domain.entities.livesystem.CustomWorkload;
import com.yanchware.fractal.sdk.domain.entities.livesystem.CustomWorkloadBuilder;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.CustomWorkloadRole;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureOsType;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.valueobjects.AzureAppServiceClientCertMode;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.valueobjects.AzureAppServiceRedundancyMode;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.SerializationUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidAlphanumericsHyphens;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLettersNumbersPeriodsAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_AZURE_WEBAPP_DEPLOYMENT_SLOT;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureWebAppDeploymentSlot extends PaaSWorkloadDeploymentSlot implements AzureResourceEntity, LiveSystemComponent, CustomWorkload {
  
  private final static String RUNTIME_STACK_AND_OPERATING_SYSTEM_MISMATCH_PATTERN = "[AzureWebApp Validation] The Runtime Stack and Operating System mismatches. Please choose %s or change Operating System to %s";
  private final static String NAME_NOT_VALID = "[AzureWebApp Validation] The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be between 2 and 60 characters";
  private final static String CUSTOM_DOMAIN_NOT_VALID = "[AzureWebApp Validation] The CustomDomain must contain at least one period, cannot start or end with a period. CustomDomain are made up of letters, numbers, periods, and dashes.";
  private final static String RUNTIME_STACK_IS_EMPTY = "[AzureWebApp Validation] The Runtime Stack is either empty or blank and it is required";
  private final static String OPERATING_SYSTEM_IS_EMPTY = "[AzureWebApp Validation] The Operating System is either empty or blank and it is required";
  @JsonIgnore
  public static final String AZURE_RESOURCE_GROUP_IS_BLANK = "Azure Resource group has not been defined and it is required";

  @JsonIgnore
  public static final String AZURE_REGION_IS_BLANK = "Region has not been defined and it is required";

  @JsonIgnore
  public static final String TAG_KEY_IS_BLANK = "Tag key cannot be null or empty";
  private static final String TAG_VALUE_INVALID_FORMAT = "Tag value for key '%s' cannot be null or empty";

  private String name;
  private boolean cloneSettingsFromWebApp;
  private String privateSSHKeyPassphraseSecretId;
  private String privateSSHKeySecretId;
  private String sshRepositoryURI;
  private String repoId;
  private String branchName;
  private List<CustomWorkloadRole> roles;
  private String workloadSecretIdKey;
  private String workloadSecretPasswordKey;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Boolean clientAffinityEnabled;
  private Boolean clientCertEnabled;
  private String clientCertExclusionPaths;
  private AzureAppServiceClientCertMode clientCertMode;
  private AzureWebAppCloningInfo cloningInfo;
  private Integer containerSize;
  private String customDomainVerificationId;
  private Integer dailyMemoryTimeQuota;

  private Boolean enabled;
  private Boolean hostNamesDisabled;

  private String hostingEnvironmentProfileId;
  private Boolean httpsOnly;
  private Boolean hyperV;
  private AzureAppServiceRedundancyMode redundancyMode;
  private Boolean reserved;
  private Boolean scmSiteAlsoStopped;
  private Boolean storageAccountRequired;
  private String virtualNetworkSubnetId;
  private Boolean vnetContentShareEnabled;
  private Boolean vnetImagePullEnabled;
  private AzureWebAppConfiguration configuration;
  private Map<String, String> tags;
  private AzureAppServicePlan appServicePlan;
  private Collection<AzureKeyVaultCertificate> certificates;
  private Collection<String> customDomains;
  private AzureOsType operatingSystem;
  private AzureWebAppRuntimeStack runtimeStack;
  private Map<String, List<Object>> dnsZoneConfig;


  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  protected AzureWebAppDeploymentSlot() {
    roles = new ArrayList<>();
    cloneSettingsFromWebApp = true;
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if(!this.cloneSettingsFromWebApp) {
      errors.addAll(CustomWorkload.validateCustomWorkload(this, "AzureWebAppDeploymentSlot"));
      // Validate Azure Region
      if (azureRegion == null) {
        errors.add(AZURE_REGION_IS_BLANK);
      }

      // Validate Azure Resource Group
      if (azureResourceGroup == null) {
        errors.add(AZURE_RESOURCE_GROUP_IS_BLANK);
      }

      if (configuration == null && operatingSystem == null) {
        errors.add(OPERATING_SYSTEM_IS_EMPTY);
      }

      if (configuration == null && runtimeStack == null) {
        errors.add(RUNTIME_STACK_IS_EMPTY);
      }
    }

    if (configuration != null
        && StringUtils.isBlank(configuration.getDotnetVersion())
        && StringUtils.isBlank(configuration.getJavaVersion())
        && StringUtils.isBlank(configuration.getJavaContainerVersion())
        && StringUtils.isBlank(configuration.getLinuxFxVersion())
        && StringUtils.isBlank(configuration.getNodeVersion())
        && StringUtils.isBlank(configuration.getPhpVersion())
        && StringUtils.isBlank(configuration.getPythonVersion())
        && StringUtils.isBlank(configuration.getWindowsFxVersion())) {

      if (operatingSystem == null) {
        errors.add(OPERATING_SYSTEM_IS_EMPTY);
      }

      if (runtimeStack == null) {
        errors.add(RUNTIME_STACK_IS_EMPTY);
      }
    }

    if (operatingSystem == AzureOsType.LINUX
        && runtimeStack instanceof AzureWebAppWindowsRuntimeStack) {
      errors.add(String.format(RUNTIME_STACK_AND_OPERATING_SYSTEM_MISMATCH_PATTERN, "AzureWebAppLinuxRuntimeStack", "WINDOWS"));
    }

    if (operatingSystem == AzureOsType.WINDOWS
        && runtimeStack instanceof AzureWebAppLinuxRuntimeStack) {
      errors.add(String.format(RUNTIME_STACK_AND_OPERATING_SYSTEM_MISMATCH_PATTERN, "AzureWebAppWindowsRuntimeStack", "LINUX"));
    }


    if (StringUtils.isNotBlank(name)) {
      var hasValidCharacters = isValidAlphanumericsHyphens(name);
      var hasValidLengths = isValidStringLength(name, 2, 60);
      if (!hasValidCharacters || !hasValidLengths) {
        errors.add(NAME_NOT_VALID);
      }
    }

    if (ObjectUtils.isNotEmpty(customDomains)) {
      for (var customDomain : customDomains) {
        if (StringUtils.isNotBlank(customDomain)) {
          var hasValidCharacters = isValidLettersNumbersPeriodsAndHyphens(customDomain);
          if (!hasValidCharacters) {
            errors.add(CUSTOM_DOMAIN_NOT_VALID);
          }
        }
      }
    }

    if (configuration != null) {
      errors.addAll(configuration.validate());
    }

    return errors;
  }

  public static AzureWebAppDeploymentSlotBuilder builder() {
    return new AzureWebAppDeploymentSlotBuilder();
  }

  public static class AzureWebAppDeploymentSlotBuilder extends CustomWorkloadBuilder<AzureWebAppDeploymentSlot, AzureWebAppDeploymentSlotBuilder> {

    @Override
    protected AzureWebAppDeploymentSlot createComponent() {
      return new AzureWebAppDeploymentSlot();
    }

    @Override
    protected AzureWebAppDeploymentSlotBuilder getBuilder() {
      return this;
    }

    @Override
    public AzureWebAppDeploymentSlot build() {
      component.setType(PAAS_AZURE_WEBAPP_DEPLOYMENT_SLOT);
      return super.build();
    }
    
    public AzureWebAppDeploymentSlotBuilder withCloneSettingsFromWebApp(boolean cloneSettingsFromWebApp) {
      component.setCloneSettingsFromWebApp(cloneSettingsFromWebApp);
      return builder;
    }

    /**
     * The region in which the component will be created
     *
     * @param region Azure region
     */
    public AzureWebAppDeploymentSlotBuilder withRegion(AzureRegion region) {
      component.setAzureRegion(region);
      return builder;
    }

    /**
     * The resource group in which the component will be created
     *
     * @param resourceGroup Azure Resource Group reference
     */
    public AzureWebAppDeploymentSlotBuilder withResourceGroup(AzureResourceGroup resourceGroup) {
      component.setAzureResourceGroup(resourceGroup);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withConfiguration(AzureWebAppConfiguration configuration) {
      component.setConfiguration(configuration);
      return builder;
    }

    /**
     * Chosen name for the web app
     *
     * @param name
     */
    public AzureWebAppDeploymentSlotBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    /**
     * Tags are name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public AzureWebAppDeploymentSlotBuilder withTags(Map<String, String> tags) {
      component.setTags(tags);
      return builder;
    }

    /**
     * Tag is name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public AzureWebAppDeploymentSlotBuilder withTag(String key, String value) {
      if (component.getTags() == null) {
        withTags(new HashMap<>());
      }

      component.getTags().put(key, value);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withAppServicePlan(AzureAppServicePlan appServicePlan) {
      component.setAppServicePlan(appServicePlan);
      return builder;
    }

    /**
     * Azure Key Vault certificate to be used by the web app
     *
     * @param certificate
     */
    public AzureWebAppDeploymentSlotBuilder withCertificate(AzureKeyVaultCertificate certificate) {
      return withCertificates(List.of(certificate));
    }

    /**
     * List of Azure Key Vault certificates
     *
     * @param certificates
     */
    public AzureWebAppDeploymentSlotBuilder withCertificates(Collection<? extends AzureKeyVaultCertificate> certificates) {
      if (isBlank(certificates)) {
        return builder;
      }

      if (component.getCertificates() == null) {
        component.setCertificates(new ArrayList<>());
      }

      component.getCertificates().addAll(certificates);
      return builder;
    }

    /**
     * The CustomDomain must contain at least one period, cannot start or end with a period.
     * CustomDomain are made up of letters, numbers, periods, and dashes.
     */
    public AzureWebAppDeploymentSlotBuilder withCustomDomain(String customDomain) {
      return withCustomDomains(List.of(customDomain));
    }

    /**
     * The CustomDomains list must contain CustomDomain that has at least one period, cannot start or end with a period.
     * CustomDomain are made up of letters, numbers, periods, and dashes.
     */
    public AzureWebAppDeploymentSlotBuilder withCustomDomains(Collection<? extends String> customDomains) {
      if (isBlank(customDomains)) {
        return builder;
      }

      if (component.getCustomDomains() == null) {
        component.setCustomDomains(new ArrayList<>());
      }

      component.getCustomDomains().addAll(customDomains);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withClientAffinityEnabled(Boolean clientAffinityEnabled) {
      component.setClientAffinityEnabled(clientAffinityEnabled);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withClientCertEnabled(Boolean clientCertEnabled) {
      component.setClientCertEnabled(clientCertEnabled);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withClientCertExclusionPaths(String clientCertExclusionPaths) {
      component.setClientCertExclusionPaths(clientCertExclusionPaths);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withClientCertMode(AzureAppServiceClientCertMode clientCertMode) {
      component.setClientCertMode(clientCertMode);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withCloningInfo(AzureWebAppCloningInfo cloningInfo) {
      component.setCloningInfo(cloningInfo);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withContainerSize(Integer containerSize) {
      component.setContainerSize(containerSize);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withCustomDomainVerificationId(String customDomainVerificationId) {
      component.setCustomDomainVerificationId(customDomainVerificationId);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withDailyMemoryTimeQuota(Integer dailyMemoryTimeQuota) {
      component.setDailyMemoryTimeQuota(dailyMemoryTimeQuota);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withEnabled(Boolean enabled) {
      component.setEnabled(enabled);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withHostNamesDisabled(Boolean hostNamesDisabled) {
      component.setHostNamesDisabled(hostNamesDisabled);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withHostingEnvironmentProfileId(String hostingEnvironmentProfileId) {
      component.setHostingEnvironmentProfileId(hostingEnvironmentProfileId);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withHttpsOnly(Boolean httpsOnly) {
      component.setHttpsOnly(httpsOnly);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withHyperV(Boolean hyperV) {
      component.setHyperV(hyperV);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withRedundancyMode(AzureAppServiceRedundancyMode redundancyMode) {
      component.setRedundancyMode(redundancyMode);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withReserved(Boolean reserved) {
      component.setReserved(reserved);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withScmSiteAlsoStopped(Boolean scmSiteAlsoStopped) {
      component.setScmSiteAlsoStopped(scmSiteAlsoStopped);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withStorageAccountRequired(Boolean storageAccountRequired) {
      component.setStorageAccountRequired(storageAccountRequired);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withVirtualNetworkSubnetId(String virtualNetworkSubnetId) {
      component.setVirtualNetworkSubnetId(virtualNetworkSubnetId);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withVnetContentShareEnabled(Boolean vnetContentShareEnabled) {
      component.setVnetContentShareEnabled(vnetContentShareEnabled);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withVnetImagePullEnabled(Boolean vnetImagePullEnabled) {
      component.setVnetImagePullEnabled(vnetImagePullEnabled);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withOperatingSystem(AzureOsType operatingSystem) {
      component.setOperatingSystem(operatingSystem);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withRuntimeStack(AzureWebAppRuntimeStack runtimeStack) {
      component.setRuntimeStack(runtimeStack);
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withDnsZoneConfig(String dnsZoneName, DnsRecord dnsRecord) {
      if (component.getDnsZoneConfig() == null) {
        component.setDnsZoneConfig(new HashMap<>());
      }

      if (!component.getDnsZoneConfig().containsKey(dnsZoneName)) {
        component.getDnsZoneConfig().put(dnsZoneName, new ArrayList<>());
      }

      component.getDnsZoneConfig().get(dnsZoneName).add(SerializationUtils.convertValueToMap(dnsRecord));

      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withDnsZoneConfig(String dnsZoneName, Collection<DnsRecord> dnsRecords) {
      dnsRecords.forEach(dnsRecord -> withDnsZoneConfig(dnsZoneName, dnsRecord));
      return builder;
    }

    public AzureWebAppDeploymentSlotBuilder withDnsZoneConfig(Map<? extends String, ? extends Collection<DnsRecord>> dnsRecordsMap) {
      if (dnsRecordsMap == null || dnsRecordsMap.isEmpty()) {
        return builder;
      }

      dnsRecordsMap.forEach((key, value) -> {
        for (var dnsRecord : value) {
          withDnsZoneConfig(key, dnsRecord);
        }
      });

      return builder;
    }
  }

}