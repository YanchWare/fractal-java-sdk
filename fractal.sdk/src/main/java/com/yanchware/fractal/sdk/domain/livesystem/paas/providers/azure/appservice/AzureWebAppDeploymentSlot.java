package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yanchware.fractal.sdk.domain.Validatable;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureOsType;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice.valueobjects.AzureAppServiceClientCertMode;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice.valueobjects.AzureAppServiceRedundancyMode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidAlphanumericsHyphens;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLettersNumbersPeriodsAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;

@Getter
@Setter
public class AzureWebAppDeploymentSlot implements Validatable {

  private final static String RUNTIME_STACK_AND_OPERATING_SYSTEM_MISMATCH_PATTERN = "[AzureWebAppDeploymentSlot Validation] The Runtime Stack and Operating System mismatches. Please choose %s or change Operating System to %s";
  private final static String NAME_NOT_VALID = "[AzureWebAppDeploymentSlot Validation] The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be between 2 and 59 characters";
  private final static String CUSTOM_DOMAIN_NOT_VALID = "[AzureWebAppDeploymentSlot Validation] The CustomDomain must contain at least one period, cannot start or end with a period. CustomDomain are made up of letters, numbers, periods, and dashes.";
  private final static String RUNTIME_STACK_IS_EMPTY = "[AzureWebAppDeploymentSlot Validation] The Runtime Stack is either empty or blank and it is required";
  private final static String OPERATING_SYSTEM_IS_EMPTY = "[AzureWebAppDeploymentSlot Validation] The Operating System is either empty or blank and it is required";

  

  @JsonIgnore
  public static final String TAG_KEY_IS_BLANK = "Tag key cannot be null or empty";
  private static final String TAG_VALUE_INVALID_FORMAT = "Tag value for key '%s' cannot be null or empty";

  private String name;
  private boolean cloneSettingsFromWebApp;
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
  private Collection<AzureKeyVaultCertificate> certificates;
  private Collection<String> customDomains;
  private AzureOsType operatingSystem;
  private AzureWebAppRuntimeStack runtimeStack;
  

  AzureWebAppDeploymentSlot() {
    cloneSettingsFromWebApp = true;
  }

  public static DeploymentSlotBuilder builder() {
    return new DeploymentSlotBuilder();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if(!this.cloneSettingsFromWebApp) {


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
      var hasValidLengths = isValidStringLength(name, 2, 59);
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

  public static class DeploymentSlotBuilder {

    private final AzureWebAppDeploymentSlot slot;
    private final DeploymentSlotBuilder builder;

    public DeploymentSlotBuilder() {
      this.slot = new AzureWebAppDeploymentSlot();
      this.builder = this;
    }

    public DeploymentSlotBuilder withCloneSettingsFromWebApp(boolean cloneSettingsFromWebApp) {
      slot.setCloneSettingsFromWebApp(cloneSettingsFromWebApp);
      return builder;
    }

    public DeploymentSlotBuilder withConfiguration(AzureWebAppConfiguration configuration) {
      slot.setConfiguration(configuration);
      return builder;
    }

    /**
     * Chosen name for the web app
     *
     * @param name
     */
    public DeploymentSlotBuilder withName(String name) {
      slot.setName(name);
      return builder;
    }

    /**
     * Azure Key Vault certificate to be used by the web app
     *
     * @param certificate
     */
    public DeploymentSlotBuilder withCertificate(AzureKeyVaultCertificate certificate) {
      return withCertificates(List.of(certificate));
    }

    /**
     * List of Azure Key Vault certificates
     *
     * @param certificates
     */
    public DeploymentSlotBuilder withCertificates(Collection<? extends AzureKeyVaultCertificate> certificates) {
      if (isBlank(certificates)) {
        return builder;
      }

      if (slot.getCertificates() == null) {
        slot.setCertificates(new ArrayList<>());
      }

      slot.getCertificates().addAll(certificates);
      return builder;
    }

    /**
     * The CustomDomain must contain at least one period, cannot start or end with a period.
     * CustomDomain are made up of letters, numbers, periods, and dashes.
     */
    public DeploymentSlotBuilder withCustomDomain(String customDomain) {
      return withCustomDomains(List.of(customDomain));
    }

    /**
     * The CustomDomains list must contain CustomDomain that has at least one period, cannot start or end with a period.
     * CustomDomain are made up of letters, numbers, periods, and dashes.
     */
    public DeploymentSlotBuilder withCustomDomains(Collection<? extends String> customDomains) {
      if (isBlank(customDomains)) {
        return builder;
      }

      if (slot.getCustomDomains() == null) {
        slot.setCustomDomains(new ArrayList<>());
      }

      slot.getCustomDomains().addAll(customDomains);
      return builder;
    }

    public DeploymentSlotBuilder withClientAffinityEnabled(Boolean clientAffinityEnabled) {
      slot.setClientAffinityEnabled(clientAffinityEnabled);
      return builder;
    }

    public DeploymentSlotBuilder withClientCertEnabled(Boolean clientCertEnabled) {
      slot.setClientCertEnabled(clientCertEnabled);
      return builder;
    }

    public DeploymentSlotBuilder withClientCertExclusionPaths(String clientCertExclusionPaths) {
      slot.setClientCertExclusionPaths(clientCertExclusionPaths);
      return builder;
    }

    public DeploymentSlotBuilder withClientCertMode(AzureAppServiceClientCertMode clientCertMode) {
      slot.setClientCertMode(clientCertMode);
      return builder;
    }

    public DeploymentSlotBuilder withCloningInfo(AzureWebAppCloningInfo cloningInfo) {
      slot.setCloningInfo(cloningInfo);
      return builder;
    }

    public DeploymentSlotBuilder withContainerSize(Integer containerSize) {
      slot.setContainerSize(containerSize);
      return builder;
    }

    public DeploymentSlotBuilder withCustomDomainVerificationId(String customDomainVerificationId) {
      slot.setCustomDomainVerificationId(customDomainVerificationId);
      return builder;
    }

    public DeploymentSlotBuilder withDailyMemoryTimeQuota(Integer dailyMemoryTimeQuota) {
      slot.setDailyMemoryTimeQuota(dailyMemoryTimeQuota);
      return builder;
    }

    public DeploymentSlotBuilder withEnabled(Boolean enabled) {
      slot.setEnabled(enabled);
      return builder;
    }

    public DeploymentSlotBuilder withHostNamesDisabled(Boolean hostNamesDisabled) {
      slot.setHostNamesDisabled(hostNamesDisabled);
      return builder;
    }

    public DeploymentSlotBuilder withHostingEnvironmentProfileId(String hostingEnvironmentProfileId) {
      slot.setHostingEnvironmentProfileId(hostingEnvironmentProfileId);
      return builder;
    }

    public DeploymentSlotBuilder withHttpsOnly(Boolean httpsOnly) {
      slot.setHttpsOnly(httpsOnly);
      return builder;
    }

    public DeploymentSlotBuilder withHyperV(Boolean hyperV) {
      slot.setHyperV(hyperV);
      return builder;
    }

    public DeploymentSlotBuilder withRedundancyMode(AzureAppServiceRedundancyMode redundancyMode) {
      slot.setRedundancyMode(redundancyMode);
      return builder;
    }

    public DeploymentSlotBuilder withReserved(Boolean reserved) {
      slot.setReserved(reserved);
      return builder;
    }

    public DeploymentSlotBuilder withScmSiteAlsoStopped(Boolean scmSiteAlsoStopped) {
      slot.setScmSiteAlsoStopped(scmSiteAlsoStopped);
      return builder;
    }

    public DeploymentSlotBuilder withStorageAccountRequired(Boolean storageAccountRequired) {
      slot.setStorageAccountRequired(storageAccountRequired);
      return builder;
    }

    public DeploymentSlotBuilder withVirtualNetworkSubnetId(String virtualNetworkSubnetId) {
      slot.setVirtualNetworkSubnetId(virtualNetworkSubnetId);
      return builder;
    }

    public DeploymentSlotBuilder withVnetContentShareEnabled(Boolean vnetContentShareEnabled) {
      slot.setVnetContentShareEnabled(vnetContentShareEnabled);
      return builder;
    }

    public DeploymentSlotBuilder withVnetImagePullEnabled(Boolean vnetImagePullEnabled) {
      slot.setVnetImagePullEnabled(vnetImagePullEnabled);
      return builder;
    }

    public DeploymentSlotBuilder withOperatingSystem(AzureOsType operatingSystem) {
      slot.setOperatingSystem(operatingSystem);
      return builder;
    }

    public DeploymentSlotBuilder withRuntimeStack(AzureWebAppRuntimeStack runtimeStack) {
      slot.setRuntimeStack(runtimeStack);
      return builder;
    }
    

    public AzureWebAppDeploymentSlot build() {
      return slot;
    }
  }

}