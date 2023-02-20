package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlDbms;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.valueobjects.AzureSkuName;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLowercaseLettersNumbersAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class AzurePostgreSqlDbms extends PaaSPostgreSqlDbms implements AzureResourceEntity {

  private final static String REGION_IS_NULL = "[AzurePostgreSQL Validation] Region has not been defined and it is required";

  private final static String INVALID_STORAGE_MB = "[AzurePostgreSQL Validation] Storage MB is less than minimum requirement of 5 GB";

  private final static String INVALID_BACKUP_RETENTION_DAYS = "[AzurePostgreSQL Validation] Backup Retention Days must be between 7 and 35 days";

  private final static String NAME_NOT_VALID = "[AzurePostgreSQL Validation] The name must only contain lowercase letters, numbers, and hyphens. It must not start or end in a hyphen and must be between 3 and 63 characters long";

  private String rootUser;

  @Setter
  private AzureRegion azureRegion;
  @Setter
  private AzureResourceGroup azureResourceGroup;

  private AzureSkuName skuName;

  private AzureStorageAutoGrow storageAutoGrow;

  private Integer storageMB;

  private Integer backupRetentionDays;

  @Setter
  private Map<String, String> tags;

  @Setter
  private String name;

  public static AzurePostgreSqlBuilder builder() {
    return new AzurePostgreSqlBuilder();
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static class AzurePostgreSqlBuilder extends Builder<AzurePostgreSqlDbms, AzurePostgreSqlBuilder> {

    @Override
    protected AzurePostgreSqlDbms createComponent() {
      return new AzurePostgreSqlDbms();
    }

    @Override
    protected AzurePostgreSqlBuilder getBuilder() {
      return this;
    }

    public AzurePostgreSqlBuilder withDatabase(AzurePostgreSqlDatabase db) {
      return withDatabases(List.of(db));
    }

    public AzurePostgreSqlBuilder withDatabases(Collection<? extends AzurePostgreSqlDatabase> dbs) {
      if (CollectionUtils.isBlank(dbs)) {
        return builder;
      }

      if (component.getDatabases() == null) {
        component.setDatabases(new ArrayList<>());
      }

      dbs.forEach(db -> {
        db.getDependencies().add(component.getId());
        db.setAzureRegion(component.getAzureRegion());
        db.setAzureResourceGroup(component.getAzureResourceGroup());
      });
      component.getDatabases().addAll(dbs);
      return builder;
    }

    public AzurePostgreSqlBuilder withRootUser(String rootUser) {
      component.setRootUser(rootUser);
      return builder;
    }

    public AzurePostgreSqlBuilder withRegion(AzureRegion region) {
      component.setAzureRegion(region);
      return builder;
    }

    public AzurePostgreSqlBuilder withSkuName(AzureSkuName skuName) {
      component.setSkuName(skuName);
      return builder;
    }

    public AzurePostgreSqlBuilder withStorageAutoGrow(AzureStorageAutoGrow storageAutoGrow) {
      component.setStorageAutoGrow(storageAutoGrow);
      return builder;
    }

    public AzurePostgreSqlBuilder withStorageMB(int storageMB) {
      component.setStorageMB(storageMB);
      return builder;
    }

    public AzurePostgreSqlBuilder withBackupRetentionDays(int backupRetentionDays) {
      component.setBackupRetentionDays(backupRetentionDays);
      return builder;
    }

    public AzurePostgreSqlBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    public AzurePostgreSqlBuilder withTags(Map<String, String> tags) {
      component.setTags(tags);
      return builder;
    }

    public AzurePostgreSqlBuilder withTag(String key, String value) {
      if (component.getTags() == null) {
        withTags(new HashMap<>());
      }

      component.getTags().put(key, value);
      return builder;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (StringUtils.isNotBlank(name)) {
      var hasValidCharacters = isValidLowercaseLettersNumbersAndHyphens(name);
      var hasValidLengths = isValidStringLength(name, 3, 63);
      if (!hasValidCharacters || !hasValidLengths) {
        errors.add(NAME_NOT_VALID);
      }
    }

    if (azureRegion == null && azureResourceGroup == null) {
      errors.add(REGION_IS_NULL);
    }

    if (storageMB != null && storageMB < 5 * 1024) {
      errors.add(INVALID_STORAGE_MB);
    }

    if (backupRetentionDays != null && (backupRetentionDays < 7 || backupRetentionDays > 35)) {
      errors.add(INVALID_BACKUP_RETENTION_DAYS);
    }

    getDatabases().stream()
        .map(x -> AzureResourceEntity.validateAzureResourceEntity((AzureResourceEntity) x, "PostgreSql Database"))
        .forEach(errors::addAll);


    return errors;
  }
}
