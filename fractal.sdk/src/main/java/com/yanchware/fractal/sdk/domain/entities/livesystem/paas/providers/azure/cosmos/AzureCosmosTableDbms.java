package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSRelationalDbms;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_ACCOUNT;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureCosmosTableDbms extends PaaSRelationalDbms implements LiveSystemComponent, AzureCosmosAccount {

  public static final String TYPE = PAAS_COSMOS_ACCOUNT.getId();

  private int maxTotalThroughput;

  private String publicNetworkAccess;
  private Collection<AzureCosmosTableEntity> cosmosEntities;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private AzureCosmosBackupPolicy backupPolicy;


  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }


  protected AzureCosmosTableDbms() {
    cosmosEntities = new ArrayList<>();
  }

  public static AzureCosmosTableDbmsBuilder builder() {
    return new AzureCosmosTableDbmsBuilder();
  }

  public static class AzureCosmosTableDbmsBuilder extends AzureCosmosAccountBuilder<AzureCosmosTableDbms, AzureCosmosTableDbmsBuilder> {

    @Override
    protected AzureCosmosTableDbms createComponent() {
      return new AzureCosmosTableDbms();
    }

    @Override
    protected AzureCosmosTableDbmsBuilder getBuilder() {
      return this;
    }
  }

  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    errors.addAll(AzureCosmosAccount.validateCosmosAccount(this, "Table DBMS"));
    return errors;
  }

}
