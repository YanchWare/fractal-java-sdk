package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSDocumentDbms;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureCosmosUtilities.validateCosmosAccount;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_ACCOUNT;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureCosmosMongoDbms extends PaaSDocumentDbms implements LiveSystemComponent, AzureCosmosAccount {

  public static final String TYPE = PAAS_COSMOS_ACCOUNT.getId();

  private int maxTotalThroughput;
  private String publicNetworkAccess;

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  private Collection<AzureCosmosMongoDatabase> cosmosEntities;

  protected AzureCosmosMongoDbms() {
    cosmosEntities = new ArrayList<>();
  }

  public static AzureCosmosMongoDbBuilderBuilder builder() {
    return new AzureCosmosMongoDbBuilderBuilder();
  }

  public static class AzureCosmosMongoDbBuilderBuilder extends AzureCosmosAccountBuilder<AzureCosmosMongoDbms, AzureCosmosMongoDbBuilderBuilder> {

    @Override
    protected AzureCosmosMongoDbms createComponent() {
      return new AzureCosmosMongoDbms();
    }

    @Override
    protected AzureCosmosMongoDbBuilderBuilder getBuilder() {
      return this;
    }
  }

  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    errors.addAll(validateCosmosAccount(this));
    return errors;
  }

}
