package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSDocumentDbms;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLowercaseLettersNumbersAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_ACCOUNT;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureCosmosNoSqlDbms extends PaaSDocumentDbms implements LiveSystemComponent, AzureCosmosAccount {

  public static final String TYPE = PAAS_COSMOS_ACCOUNT.getId();

  private final static String NAME_NOT_VALID = "[AzureCosmosNoSqlDbms Validation] The name must only contains lowercase letters, numbers, and hyphens. The name must not start or end in a hyphen and must be between 3 and 44 characters long";

  private String name;
  private Integer maxTotalThroughput;
  private String publicNetworkAccess;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  private Collection<AzureCosmosNoSqlDatabase> cosmosEntities;
  private AzureCosmosBackupPolicy backupPolicy;

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

    if(StringUtils.isNotBlank(name)) {
      var hasValidCharacters = isValidLowercaseLettersNumbersAndHyphens(name);
      var hasValidLengths = isValidStringLength(name, 3, 44);
      if(!hasValidCharacters || !hasValidLengths) {
        errors.add(NAME_NOT_VALID);
      }
    }
    
    return errors;
  }

}
