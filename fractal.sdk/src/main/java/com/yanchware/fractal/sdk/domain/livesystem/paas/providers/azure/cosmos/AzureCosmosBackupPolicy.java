package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.Validatable;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.BackupStorageRedundancy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.yanchware.fractal.sdk.utils.ValidationUtils.validateIntegerInRange;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureCosmosBackupPolicy implements Validatable {
  private final static String BACKUP_STORAGE_REDUNDANCY_IS_BLANK = "[AzureCosmosBackupPolicy Validation] BackupStorageRedundancy has not been defined and it is required";
  private final static String BACKUP_POLICY_TYPE_IS_BLANK = "[AzureCosmosBackupPolicy Validation] BackupPolicyType has not been defined and it is required";
  private Integer backupIntervalInMinutes;
  private Integer backupRetentionIntervalInHours;
  private BackupStorageRedundancy backupStorageRedundancy;
  private AzureCosmosBackupPolicyType backupPolicyType;

  public static AzureCosmosBackupPolicyBuilder builder() {
    return new AzureCosmosBackupPolicyBuilder();
  }

  public AzureCosmosBackupPolicy() {
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if(this.backupPolicyType == null) {
      errors.add(BACKUP_STORAGE_REDUNDANCY_IS_BLANK);
    }
    
    if(this.backupPolicyType == AzureCosmosBackupPolicyType.PERIODIC) {
      if(this.backupIntervalInMinutes != null) {
        validateIntegerInRange("[AzureCosmosPeriodicModeBackupPolicy Validation] BackupIntervalInMinutes", this.backupIntervalInMinutes, 60, 1440, errors);

        if(this.backupStorageRedundancy == null) {
          errors.add(BACKUP_STORAGE_REDUNDANCY_IS_BLANK);
        }
      }

      if(this.backupRetentionIntervalInHours != null) {
        validateIntegerInRange("[AzureCosmosPeriodicModeBackupPolicy Validation] BackupRetentionIntervalInHours", this.backupRetentionIntervalInHours, 8, 720, errors);
      }
    }
    
    return errors;
  }

  public static class AzureCosmosBackupPolicyBuilder{
    private final AzureCosmosBackupPolicy backupPolicy;
    private final AzureCosmosBackupPolicyBuilder builder;

    public AzureCosmosBackupPolicyBuilder () {
      backupPolicy = createComponent();
      builder = getBuilder();
    }

    protected AzureCosmosBackupPolicy createComponent() {
      return new AzureCosmosBackupPolicy();
    }

    protected AzureCosmosBackupPolicyBuilder getBuilder() {
      return this;
    }

    public AzureCosmosBackupPolicyBuilder withBackupIntervalInMinutes(Integer backupIntervalInMinutes) {
      backupPolicy.setBackupIntervalInMinutes(backupIntervalInMinutes);
      return builder;
    }

    public AzureCosmosBackupPolicyBuilder withBackupRetentionIntervalInHours(Integer backupRetentionIntervalInHours) {
      backupPolicy.setBackupRetentionIntervalInHours(backupRetentionIntervalInHours);
      return builder;
    }

    public AzureCosmosBackupPolicyBuilder withBackupStorageRedundancy(BackupStorageRedundancy backupStorageRedundancy) {
      backupPolicy.setBackupStorageRedundancy(backupStorageRedundancy);
      return builder;
    }

    public AzureCosmosBackupPolicyBuilder withBackupPolicyType(AzureCosmosBackupPolicyType backupPolicyType) {
      backupPolicy.setBackupPolicyType(backupPolicyType);
      return builder;
    }

    public AzureCosmosBackupPolicy build(){
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
