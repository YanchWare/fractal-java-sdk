package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.blueprint.paas.PaaSRelationalDbms;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_ACCOUNT;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLowercaseLettersNumbersAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureCosmosPostgreSqlDbms extends PaaSRelationalDbms implements LiveSystemComponent, AzureCosmosAccount {

  public static final String TYPE = PAAS_COSMOS_ACCOUNT.getId();

  private final static String NAME_NOT_VALID = "[AzureCosmosPostgreSqlDbms Validation] The name must only contains " +
    "lowercase letters, numbers, and hyphens. The name must not start or end in a hyphen and must be between 3 and 40" +
    " characters long";


  private String name;
  private Integer maxTotalThroughput;
  private String publicNetworkAccess;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;
  private Collection<AzureCosmosPostgreSqlDatabase> cosmosEntities;
  private AzureCosmosBackupPolicy backupPolicy;
  protected AzureCosmosPostgreSqlDbms() {
    cosmosEntities = new ArrayList<>();
  }

  public static AzureCosmosPostgreSqlDbmsBuilder builder() {
    return new AzureCosmosPostgreSqlDbmsBuilder();
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    errors.addAll(AzureCosmosAccount.validateCosmosAccount(this, "PostgreSql DBMS"));

    if (StringUtils.isNotBlank(name)) {
      var hasValidCharacters = isValidLowercaseLettersNumbersAndHyphens(name);
      var hasValidLengths = isValidStringLength(name, 3, 40);
      if (!hasValidCharacters || !hasValidLengths) {
        errors.add(NAME_NOT_VALID);
      }
    }

    return errors;
  }

  public static class AzureCosmosPostgreSqlDbmsBuilder extends AzureCosmosAccountBuilder<AzureCosmosPostgreSqlDbms,
    AzureCosmosPostgreSqlDbmsBuilder> {

    @Override
    protected AzureCosmosPostgreSqlDbms createComponent() {
      return new AzureCosmosPostgreSqlDbms();
    }

    @Override
    protected AzureCosmosPostgreSqlDbmsBuilder getBuilder() {
      return this;
    }
  }

}
