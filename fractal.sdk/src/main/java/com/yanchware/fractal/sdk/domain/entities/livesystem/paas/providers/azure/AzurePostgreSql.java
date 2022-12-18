package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlImpl;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class AzurePostgreSql extends PaaSPostgreSqlImpl {

  private final static String REGION_IS_NULL = "[AzurePostgreSQL Validation] Region has not been defined and it is required";

  private final static String INVALID_STORAGE_MB = "[AzurePostgreSQL Validation] Storage MB is less than minimum requirement of 5 GB";

  private final static String INVALID_BACKUP_RETENTION_DAYS = "[AzurePostgreSQL Validation] Backup Retention Days must be between 7 and 35 days";

  private String rootUser;

  private AzureRegion region;

  private AzureSkuName skuName;

  private AzureStorageAutoGrow storageAutoGrow;

  private Integer storageMB;

  private Integer backupRetentionDays;

  public static AzurePostgreSQLBuilder builder() {
    return new AzurePostgreSQLBuilder();
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static class AzurePostgreSQLBuilder extends Builder<AzurePostgreSql, AzurePostgreSQLBuilder> {

    @Override
    protected AzurePostgreSql createComponent() {
      return new AzurePostgreSql();
    }

    @Override
    protected AzurePostgreSQLBuilder getBuilder() {
      return this;
    }

    public AzurePostgreSQLBuilder withDatabase(AzurePostgreSqlDb db) {
      return withDatabases(List.of(db));
    }

    public AzurePostgreSQLBuilder withDatabases(Collection<? extends AzurePostgreSqlDb> dbs) {
      if (CollectionUtils.isBlank(dbs)) {
        return builder;
      }

      if (component.getDatabases() == null) {
        component.setDatabases(new ArrayList<>());
      }

      dbs.forEach(db -> {
        db.getDependencies().add(component.getId());
      });
      component.getDatabases().addAll(dbs);
      return builder;
    }

    public AzurePostgreSQLBuilder withRootUser(String rootUser) {
      component.setRootUser(rootUser);
      return builder;
    }

    public AzurePostgreSQLBuilder withRegion(AzureRegion region) {
      component.setRegion(region);
      return builder;
    }

    public AzurePostgreSQLBuilder withSkuName(AzureSkuName skuName) {
      component.setSkuName(skuName);
      return builder;
    }

    public AzurePostgreSQLBuilder withStorageAutoGrow(AzureStorageAutoGrow storageAutoGrow) {
      component.setStorageAutoGrow(storageAutoGrow);
      return builder;
    }

    public AzurePostgreSQLBuilder withStorageMB(int storageMB) {
      component.setStorageMB(storageMB);
      return builder;
    }

    public AzurePostgreSQLBuilder withBackupRetentionDays(int backupRetentionDays) {
      component.setBackupRetentionDays(backupRetentionDays);
      return builder;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (region == null) {
      errors.add(REGION_IS_NULL);
    }

    if (storageMB != null && storageMB < 5 * 1024) {
      errors.add(INVALID_STORAGE_MB);
    }

    if (backupRetentionDays != null && (backupRetentionDays < 7 || backupRetentionDays > 35)) {
      errors.add(INVALID_BACKUP_RETENTION_DAYS);
    }

    return errors;
  }
}
