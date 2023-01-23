package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSDataStorage;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.aks.AzureKubernetesService;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.*;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureUserAssignedIdentity;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_AZURE_STORAGE;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_AZURE_WEBAPP;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureStorageAccount extends PaaSDataStorage implements AzureEntity, LiveSystemComponent {

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
    if(azureRegion == null) {
      errors.add("Region cannot be empty");
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
