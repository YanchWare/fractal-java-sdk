package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSWorkload;
import com.yanchware.fractal.sdk.domain.entities.livesystem.CustomWorkload;
import com.yanchware.fractal.sdk.domain.entities.livesystem.CustomWorkloadBuilder;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.CustomWorkloadRole;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
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
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_AZURE_WEBAPP;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureWebApp extends PaaSWorkload implements AzureResourceEntity, LiveSystemComponent, CustomWorkload {

  private final static String NAME_NOT_VALID = "[AzureWebApp Validation] The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be between 2 and 60 characters";
  private final static String CUSTOM_DOMAIN_NOT_VALID = "[AzureWebApp Validation] The CustomDomain must contain at least one period, cannot start or end with a period. CustomDomain are made up of letters, numbers, periods, and dashes.";

  private String name;
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
  private AzureWebAppApplication application;
  private AzureWebAppHosting hosting;
  private AzureWebAppConnectivity connectivity;
  private AzureWebAppInfrastructure infrastructure;
  private Map<String, String> tags;
  private AzureAppServicePlan appServicePlan;
  private Collection<AzureKeyVaultCertificate> certificates;
  private Collection<String> customDomains;


  @Override
  public ProviderType getProvider(){
    return ProviderType.AZURE;
  }

  protected AzureWebApp() {
    roles = new ArrayList<>();
  }

  @Override
  public Collection<String> validate() {
    final var NO_HOSTING_DEFINED = "[AzureWebApp Validation] Hosting has not been defined and it's mandatory";
    
    Collection<String> errors = super.validate();
    errors.addAll(CustomWorkload.validateCustomWorkload(this, "Azure Web App"));

    if(StringUtils.isNotBlank(name)) {
      var hasValidCharacters = isValidAlphanumericsHyphens(name);
      var hasValidLengths = isValidStringLength(name, 2, 60);
      if(!hasValidCharacters || !hasValidLengths) {
        errors.add(NAME_NOT_VALID);
      }
    }
    
    if(ObjectUtils.isNotEmpty(customDomains)) {
      for (var customDomain : customDomains) {
        if(StringUtils.isNotBlank(customDomain)) {
          var hasValidCharacters = isValidLettersNumbersPeriodsAndHyphens(customDomain);
          if(!hasValidCharacters) {
            errors.add(CUSTOM_DOMAIN_NOT_VALID);
          }
        }
      }
    }
    
    if(hosting != null) {
      errors.addAll(hosting.validate());  
    }
    else {
      errors.add(NO_HOSTING_DEFINED);
    }
    
    return errors;
  }

  public static AzureWebAppBuilder builder() {
    return new AzureWebAppBuilder();
  }

  public static class AzureWebAppBuilder extends CustomWorkloadBuilder<AzureWebApp, AzureWebAppBuilder> {

    @Override
    protected AzureWebApp createComponent() {
      return new AzureWebApp();
    }

    @Override
    protected AzureWebAppBuilder getBuilder() {
      return this;
    }

    @Override
    public AzureWebApp build() {
      component.setType(PAAS_AZURE_WEBAPP);
      return super.build();
    }
    
    public AzureWebAppBuilder withRegion(AzureRegion region) {
      component.setAzureRegion(region);
      return builder;
    }

    public AzureWebAppBuilder withResourceGroup(AzureResourceGroup resourceGroup) {
      component.setAzureResourceGroup(resourceGroup);
      return builder;
    }
    
    public AzureWebAppBuilder withApplication(AzureWebAppApplication application) {
      component.setApplication(application);
      return builder;
    }

    public AzureWebAppBuilder withConnectivity(AzureWebAppConnectivity connectivity) {
      component.setConnectivity(connectivity);
      return builder;
    }

    public AzureWebAppBuilder withHosting(AzureWebAppHosting hosting) {
      component.setHosting(hosting);
      return builder;
    }

    public AzureWebAppBuilder withInfrastructure(AzureWebAppInfrastructure infrastructure) {
      component.setInfrastructure(infrastructure);
      return builder;
    }

    public AzureWebAppBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    public AzureWebAppBuilder withTags(Map<String, String> tags) {
      component.setTags(tags);
      return builder;
    }

    public AzureWebAppBuilder withTag(String key, String value) {
      if (component.getTags() == null) {
        withTags(new HashMap<>());
      }

      component.getTags().put(key, value);
      return builder;
    }
    
    public AzureWebAppBuilder withAppServicePlan (AzureAppServicePlan appServicePlan) {
      component.setAppServicePlan(appServicePlan);
      return builder;
    }

    public AzureWebAppBuilder withCertificate(AzureKeyVaultCertificate certificate) {
      return withCertificates(List.of(certificate));
    }

    public AzureWebAppBuilder withCertificates(Collection<? extends AzureKeyVaultCertificate> certificates) {
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
    public AzureWebAppBuilder withCustomDomain(String customDomain) {
      return withCustomDomains(List.of(customDomain));
    }

    /**
     * The CustomDomains list must contain CustomDomain that has at least one period, cannot start or end with a period. 
     * CustomDomain are made up of letters, numbers, periods, and dashes.
     */
    public AzureWebAppBuilder withCustomDomains(Collection<? extends String> customDomains) {
      if (isBlank(customDomains)) {
        return builder;
      }

      if (component.getCustomDomains() == null) {
        component.setCustomDomains(new ArrayList<>());
      }

      component.getCustomDomains().addAll(customDomains);
      return builder;
    }
  }

}
