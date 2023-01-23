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
public class AzureCosmosPostgreSqlDbms extends PaaSRelationalDbms implements LiveSystemComponent, AzureCosmosAccount {

  public static final String TYPE = PAAS_COSMOS_ACCOUNT.getId();

  private int maxTotalThroughput;
  private String publicNetworkAccess;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  private Collection<AzureCosmosPostgreSqlDatabase> cosmosEntities;
  private AzureCosmosBackupPolicy backupPolicy;

  protected AzureCosmosPostgreSqlDbms() {
    cosmosEntities = new ArrayList<>();
  }

  public static AzureCosmosPostgreSqlDbmsBuilder builder() {
    return new AzureCosmosPostgreSqlDbmsBuilder();
  }

  public static class AzureCosmosPostgreSqlDbmsBuilder extends AzureCosmosAccountBuilder<AzureCosmosPostgreSqlDbms, AzureCosmosPostgreSqlDbmsBuilder> {

    @Override
    protected AzureCosmosPostgreSqlDbms createComponent() {
      return new AzureCosmosPostgreSqlDbms();
    }

    @Override
    protected AzureCosmosPostgreSqlDbmsBuilder getBuilder() {
      return this;
    }
  }

  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    errors.addAll(AzureCosmosAccount.validateCosmosAccount(this, "PostgreSql DBMS"));
    return errors;
  }

}
