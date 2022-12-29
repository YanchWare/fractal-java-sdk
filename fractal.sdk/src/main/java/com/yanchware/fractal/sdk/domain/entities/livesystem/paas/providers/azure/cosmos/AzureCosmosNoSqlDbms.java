package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSDocumentDbms;
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
public class AzureCosmosNoSqlDbms extends PaaSDocumentDbms implements LiveSystemComponent, AzureCosmosAccount {

  public static final String TYPE = PAAS_COSMOS_ACCOUNT.getId();

  private int maxTotalThroughput;
  private String publicNetworkAccess;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  private Collection<AzureCosmosNoSqlDatabase> cosmosEntities;

  protected AzureCosmosNoSqlDbms() {
    cosmosEntities = new ArrayList<>();
  }

  public static AzureCosmosNoSqlDbmsBuilder builder() {
    return new AzureCosmosNoSqlDbmsBuilder();
  }

  public static class AzureCosmosNoSqlDbmsBuilder extends AzureCosmosAccountBuilder<AzureCosmosNoSqlDbms, AzureCosmosNoSqlDbmsBuilder> {

    @Override
    protected AzureCosmosNoSqlDbms createComponent() {
      return new AzureCosmosNoSqlDbms();
    }

    @Override
    protected AzureCosmosNoSqlDbmsBuilder getBuilder() {
      return this;
    }
  }

  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    errors.addAll(AzureCosmosAccount.validateCosmosAccount(this, "NoSql DBMS"));
    return errors;
  }

}
