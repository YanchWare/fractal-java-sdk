package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlDatabase;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class AzurePostgreSqlDatabase extends PaaSPostgreSqlDatabase implements AzureEntity {
  public static AzurePostgreSqlDbBuilder builder() {
    return new AzurePostgreSqlDbBuilder();
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  private AzureRegion region;
  private AzureResourceGroup azureResourceGroup;

  public static class AzurePostgreSqlDbBuilder extends PaaSPostgreSqlDatabase.Builder<AzurePostgreSqlDatabase, AzurePostgreSqlDatabase.AzurePostgreSqlDbBuilder> {

      @Override
      protected AzurePostgreSqlDbBuilder getBuilder() {
        return this;
      }

      @Override
      protected AzurePostgreSqlDatabase createComponent() {
        return new AzurePostgreSqlDatabase();
      }
    }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    errors.addAll(AzureEntity.validateAzureEntity(this, "PostgreSql DBMS"));
    return errors;
  }

}