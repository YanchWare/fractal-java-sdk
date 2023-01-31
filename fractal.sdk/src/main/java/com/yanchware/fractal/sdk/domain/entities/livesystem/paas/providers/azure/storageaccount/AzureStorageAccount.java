package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSDataStorage;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.regex.Pattern;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_AZURE_STORAGE;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureStorageAccount extends PaaSDataStorage implements AzureEntity, LiveSystemComponent {

  private final static Pattern valueValidation = Pattern.compile("^[a-z0-9]{3,24}$");
  private final static String ILLEGAL_NAME_TEMPLATE = "Component id '%s' is illegal. Storage account names must be between 3 and 24 characters in length and may contain numbers and lowercase letters only";
  
  private AzureStorageAccountConnectivity connectivity;
  private AzureStorageAccountSettings settings;
  private AzureStorageAccountInfrastructure infrastructure;
  private AzureStorageAccountBackup backup;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  
  protected AzureStorageAccount() {}
  
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

    public AzureStorageAccountBuilder withRegion(AzureRegion region) {
      component.setAzureRegion(region);
      return builder;
    }

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

  }
}
