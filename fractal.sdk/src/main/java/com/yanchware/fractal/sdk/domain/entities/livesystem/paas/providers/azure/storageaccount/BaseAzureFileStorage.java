package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSDataStorage;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_AZURE_STORAGE;

@Getter
@Setter
public abstract class BaseAzureFileStorage extends PaaSDataStorage implements AzureResourceEntity, LiveSystemComponent {
  private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9]{3,24}$");
  public static final String NAME_IS_NOT_VALID = "Name must be between 3 and 24 characters in length and use numbers and lower-case letters only";
  public static final String AZURE_RESOURCE_GROUP_IS_BLANK = "Azure Resource group has not been defined and it is required";
  public static final String AZURE_REGION_IS_BLANK = "Region has not been defined and it is required";
  public static final String TAG_KEY_IS_BLANK = "Tag key cannot be null or empty";
  private static final String TAG_VALUE_INVALID_FORMAT = "Tag value for key '%s' cannot be null or empty";



  private String name;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;
  private AzureStorageAccountConnectivity connectivity;
  private AzureStorageAccountSettings settings;
  private AzureStorageAccountInfrastructure infrastructure;
  private AzureStorageAccountBackup backup;
  private AzureStorageAccountFileService fileService;

  public abstract String getKind();

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static abstract class Builder<T extends Component, B extends Builder<T, B>> extends Component.Builder<T, B> {

    @Override
    public T build() {
      component.setType(PAAS_AZURE_STORAGE);
      return super.build();
    }

    /**
     * The name of the storage account within the specified resource group. 
     * Storage account names must be between 3 and 24 characters in length and use numbers and lower-case letters only.
     * <p>
     * Regex pattern: ^[a-z0-9]+$
     */
    public B withName(String name) {
      if (component instanceof BaseAzureFileStorage) {
        ((BaseAzureFileStorage) component).setName(name);
      }

      return builder;
    }

    /**
     * Choose the Azure region that's right for you and your customers
     */
    public B withRegion(AzureRegion azureRegion) {
      if (component instanceof BaseAzureFileStorage) {
        ((BaseAzureFileStorage) component).setAzureRegion(azureRegion);
      }

      return builder;
    }

    /**
     * A resource group is a collection of resources that share the same lifecycle, permissions, and policies.
     * The resource group can include all the resources for the solution, or only those resources that you want to manage as a group.
     */
    public B withResourceGroup(AzureResourceGroup resourceGroup) {
      if (component instanceof BaseAzureFileStorage) {
        ((BaseAzureFileStorage) component).setAzureResourceGroup(resourceGroup);
      }

      return builder;
    }

    /**
     * Tags are name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public B withTags(Map<String, String> tags) {
      if (component instanceof BaseAzureFileStorage) {
        ((BaseAzureFileStorage) component).setTags(tags);
      }

      return builder;
    }

    /**
     * Tag is name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public B withTag(String key, String value) {
      if (component instanceof BaseAzureFileStorage) {
        if (((BaseAzureFileStorage) component).getTags() == null) {
          withTags(new HashMap<>());
        }

        ((BaseAzureFileStorage) component).getTags().put(key, value);
      }

      return builder;
    }

    public B withBackup(AzureStorageAccountBackup backup) {
      ((BaseAzureFileStorage) component).setBackup(backup);
      return builder;
    }

    public B withConnectivity(AzureStorageAccountConnectivity connectivity) {
      ((BaseAzureFileStorage) component).setConnectivity(connectivity);
      return builder;
    }

    public B withInfrastructure(AzureStorageAccountInfrastructure infrastructure) {
      ((BaseAzureFileStorage) component).setInfrastructure(infrastructure);
      return builder;
    }

    public B withSettings(AzureStorageAccountSettings settings) {
      ((BaseAzureFileStorage) component).setSettings(settings);
      return builder;
    }

    public B withFileService(AzureStorageAccountFileService fileService) {
      ((BaseAzureFileStorage) component).setFileService(fileService);
      return builder;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    // Validate name
    if (StringUtils.isBlank(name) || !NAME_PATTERN.matcher(name).matches()) {
      errors.add(NAME_IS_NOT_VALID);
    }

    // Validate Azure Region
    if (azureRegion == null) {
      errors.add(AZURE_REGION_IS_BLANK);
    }

    // Validate Azure Resource Group
    if (azureResourceGroup == null) {
      errors.add(AZURE_RESOURCE_GROUP_IS_BLANK);
    }

    // Validate tags
    if (tags != null) {
      for (Map.Entry<String, String> tag : tags.entrySet()) {
        if (tag.getKey() == null || tag.getKey().isEmpty()) {
          errors.add(TAG_KEY_IS_BLANK);
        }
        if (tag.getValue() == null || tag.getValue().isEmpty()) {
          errors.add(String.format(TAG_VALUE_INVALID_FORMAT, tag.getKey()));
        }
      }
    }
    
    return errors;
  }
}