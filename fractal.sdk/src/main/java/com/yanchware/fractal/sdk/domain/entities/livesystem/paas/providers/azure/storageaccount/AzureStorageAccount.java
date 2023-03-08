package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSDataStorage;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLowercaseLettersAndNumbers;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_AZURE_STORAGE;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureStorageAccount extends PaaSDataStorage implements AzureResourceEntity, LiveSystemComponent {

  private final static Pattern valueValidation = Pattern.compile("^[a-z0-9]{3,24}$");
  private final static String ILLEGAL_NAME_TEMPLATE = "Component id '%s' is illegal. Storage account names must be between 3 and 24 characters in length and may contain numbers and lowercase letters only";
  private final static String NAME_NOT_VALID = "[AzureStorageAccount Validation] The name can contain only lowercase letters and numbers. Name must be between 3 and 24 characters.";


  private AzureStorageAccountConnectivity connectivity;
  private AzureStorageAccountSettings settings;
  private AzureStorageAccountInfrastructure infrastructure;
  private AzureStorageAccountBackup backup;
  private AzureStorageAccountFileService fileService;  
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;
  private String name;

  protected AzureStorageAccount() {
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static AzureStorageAccountBuilder builder() {
    return new AzureStorageAccountBuilder();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (!valueValidation.matcher(getId().getValue()).matches()) {
      errors.add(String.format(ILLEGAL_NAME_TEMPLATE, getId().getValue()));
    }

    if (StringUtils.isNotBlank(name)) {
      var hasValidCharacters = isValidLowercaseLettersAndNumbers(name);
      var hasValidLengths = isValidStringLength(name, 3, 60);
      if (!hasValidCharacters || !hasValidLengths) {
        errors.add(NAME_NOT_VALID);
      }
    }

    return errors;
  }

  public static class AzureStorageAccountBuilder extends PaaSDataStorage.Builder<AzureStorageAccount, AzureStorageAccountBuilder> {

    @Override
    protected AzureStorageAccount createComponent() {
      return new AzureStorageAccount();
    }

    @Override
    protected AzureStorageAccountBuilder getBuilder() {
      return this;
    }

    @Override
    public AzureStorageAccount build() {
      component.setType(PAAS_AZURE_STORAGE);
      return super.build();
    }

    /**
     * Choose the Azure region that's right for you and your customers
     */
    public AzureStorageAccountBuilder withRegion(AzureRegion region) {
      component.setAzureRegion(region);
      return builder;
    }

    /**
     * A resource group is a collection of resources that share the same lifecycle, permissions, and policies.
     * The resource group can include all the resources for the solution, or only those resources that you want to manage as a group.
     */
    public AzureStorageAccountBuilder withResourceGroup(AzureResourceGroup resourceGroup) {
      component.setAzureResourceGroup(resourceGroup);
      return builder;
    }

    public AzureStorageAccountBuilder withBackup(AzureStorageAccountBackup backup) {
      component.setBackup(backup);
      return builder;
    }

    public AzureStorageAccountBuilder withConnectivity(AzureStorageAccountConnectivity connectivity) {
      component.setConnectivity(connectivity);
      return builder;
    }

    public AzureStorageAccountBuilder withInfrastructure(AzureStorageAccountInfrastructure infrastructure) {
      component.setInfrastructure(infrastructure);
      return builder;
    }

    public AzureStorageAccountBuilder withSettings(AzureStorageAccountSettings settings) {
      component.setSettings(settings);
      return builder;
    }

    public AzureStorageAccountBuilder withFileService(AzureStorageAccountFileService fileService) {
      component.setFileService(fileService);
      return builder;
    }

    public AzureStorageAccountBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    /**
     * Tags are name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public AzureStorageAccountBuilder withTags(Map<String, String> tags) {
      component.setTags(tags);
      return builder;
    }
    
    /**
     * Tag is name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public AzureStorageAccountBuilder withTag(String key, String value) {
      if (component.getTags() == null) {
        withTags(new HashMap<>());
      }

      component.getTags().put(key, value);
      return builder;
    }

  }
}
