package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.livesystem.paas.PaaSPostgreSqlDatabase;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class AzurePostgreSqlDatabase extends PaaSPostgreSqlDatabase implements AzureResourceEntity {
  private final static String NAME_IS_BLANK = "[AzurePostgreSqlDatabase Validation] name has not been defined and it " +
    "is required";
  private String name;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;

  public static AzurePostgreSqlDbBuilder builder() {
    return new AzurePostgreSqlDbBuilder();
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (StringUtils.isBlank(name)) {
      errors.add(NAME_IS_BLANK);
    }

    return errors;
  }

  public static class AzurePostgreSqlDbBuilder extends PaaSPostgreSqlDatabase.Builder<AzurePostgreSqlDatabase,
    AzurePostgreSqlDatabase.AzurePostgreSqlDbBuilder> {

    @Override
    protected AzurePostgreSqlDbBuilder getBuilder() {
      return this;
    }

    @Override
    protected AzurePostgreSqlDatabase createComponent() {
      return new AzurePostgreSqlDatabase();
    }
  }

}