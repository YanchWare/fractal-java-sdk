package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.PostgreSQL;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.POSTGRESQL;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class AzurePostgreSQL extends PostgreSQL {

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

    public static class AzurePostgreSQLBuilder extends Builder<AzurePostgreSQL, AzurePostgreSQLBuilder> {

        @Override
        protected AzurePostgreSQL createComponent() {
            return new AzurePostgreSQL();
        }

        @Override
        protected AzurePostgreSQLBuilder getBuilder() {
            return this;
        }

        public AzurePostgreSQLBuilder rootUser(String rootUser) {
            component.setRootUser(rootUser);
            return builder;
        }

        public AzurePostgreSQLBuilder region(AzureRegion region) {
            component.setRegion(region);
            return builder;
        }

        public AzurePostgreSQLBuilder skuName(AzureSkuName skuName) {
            component.setSkuName(skuName);
            return builder;
        }

        public AzurePostgreSQLBuilder storageAutoGrow(AzureStorageAutoGrow storageAutoGrow) {
            component.setStorageAutoGrow(storageAutoGrow);
            return builder;
        }

        public AzurePostgreSQLBuilder storageMB(int storageMB) {
            component.setStorageMB(storageMB);
            return builder;
        }

        public AzurePostgreSQLBuilder backupRetentionDays(int backupRetentionDays) {
            component.setBackupRetentionDays(backupRetentionDays);
            return builder;
        }

        @Override
        public AzurePostgreSQL build() {
            component.setType(POSTGRESQL);
            component.setVersion(DEFAULT_VERSION);
            return super.build();
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
