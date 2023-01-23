package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.BackupStorageRedundancy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.yanchware.fractal.sdk.utils.ValidationUtils.validateIntegerInRange;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureCosmosPeriodicModeBackupPolicy extends AzureCosmosBackupPolicy implements Validatable {

  private final static String BACKUP_STORAGE_REDUNDANCY_IS_BLANK = "[AzureCosmosPeriodicModeBackupPolicy Validation] BackupStorageRedundancy has not been defined and it is required";
  private Integer backupIntervalInMinutes;
  private Integer backupRetentionIntervalInHours;
  private BackupStorageRedundancy backupStorageRedundancy;

  public static AzureCosmosPeriodicModeBackupPolicyBuilder builder() {
    return new AzureCosmosPeriodicModeBackupPolicyBuilder();
  }

  public AzureCosmosPeriodicModeBackupPolicy() {
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();
    
    if(this.backupIntervalInMinutes != null) {
      validateIntegerInRange("[AzureCosmosPeriodicModeBackupPolicy Validation] BackupIntervalInMinutes", this.backupIntervalInMinutes, 60, 1440, errors);

      if(this.backupStorageRedundancy == null) {
        errors.add(BACKUP_STORAGE_REDUNDANCY_IS_BLANK);
      }
    }
      
    if(this.backupRetentionIntervalInHours != null) {
      validateIntegerInRange("[AzureCosmosPeriodicModeBackupPolicy Validation] BackupRetentionIntervalInHours", this.backupRetentionIntervalInHours, 8, 720, errors);
    }
    
    return errors;
  }

  public static class AzureCosmosPeriodicModeBackupPolicyBuilder{
    private final AzureCosmosPeriodicModeBackupPolicy backupPolicy;
    private final AzureCosmosPeriodicModeBackupPolicyBuilder builder;

    public AzureCosmosPeriodicModeBackupPolicyBuilder () {
      backupPolicy = createComponent();
      builder = getBuilder();
    }

    protected AzureCosmosPeriodicModeBackupPolicy createComponent() {
      return new AzureCosmosPeriodicModeBackupPolicy();
    }

    protected AzureCosmosPeriodicModeBackupPolicyBuilder getBuilder() {
      return this;
    }

    public AzureCosmosPeriodicModeBackupPolicyBuilder withBackupIntervalInMinutes(Integer backupIntervalInMinutes) {
      backupPolicy.setBackupIntervalInMinutes(backupIntervalInMinutes);
      return builder;
    }

    public AzureCosmosPeriodicModeBackupPolicyBuilder withBackupRetentionIntervalInHours(Integer backupRetentionIntervalInHours) {
      backupPolicy.setBackupRetentionIntervalInHours(backupRetentionIntervalInHours);
      return builder;
    }

    public AzureCosmosPeriodicModeBackupPolicyBuilder withBackupStorageRedundancy(BackupStorageRedundancy backupStorageRedundancy) {
      backupPolicy.setBackupStorageRedundancy(backupStorageRedundancy);
      return builder;
    }
    
    public AzureCosmosPeriodicModeBackupPolicy build(){
      Collection<String> errors = backupPolicy.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
            "AzureCosmosPeriodicModeBackupPolicy validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return backupPolicy;
    }
  }
}
