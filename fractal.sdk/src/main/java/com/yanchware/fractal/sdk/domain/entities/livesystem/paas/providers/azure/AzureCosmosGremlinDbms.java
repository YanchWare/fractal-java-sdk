package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSGraphDbms;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureCosmosUtilities.validateCosmosAccount;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_ACCOUNT;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class AzureCosmosGremlinDbms extends PaaSGraphDbms implements LiveSystemComponent, AzureCosmosAccount {

  public static final String TYPE = PAAS_COSMOS_ACCOUNT.getId();

  private int maxTotalThroughput;
  private String publicNetworkAccess;

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  private Collection<AzureCosmosGremlinDatabase> cosmosEntities;

  protected AzureCosmosGremlinDbms() {
    cosmosEntities = new ArrayList<>();
  }

  public static AzureCosmosGremlinDatabaseBuilder builder() {
    return new AzureCosmosGremlinDatabaseBuilder();
  }

  public static class AzureCosmosGremlinDatabaseBuilder extends AzureCosmosAccountBuilder<AzureCosmosGremlinDbms, AzureCosmosGremlinDatabaseBuilder> {

    @Override
    protected AzureCosmosGremlinDbms createComponent() {
      return new AzureCosmosGremlinDbms();
    }

    @Override
    protected AzureCosmosGremlinDatabaseBuilder getBuilder() {
      return this;
    }
  }

  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    errors.addAll(validateCosmosAccount(this, "Cosmos Gremlin DBMS"));
    return errors;
  }

}
