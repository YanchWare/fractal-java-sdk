package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSCassandra;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class AzureCosmosCassandraCluster extends PaaSCassandra implements AzureEntity {
    private final static String ILLEGAL_AMOUNT_OF_HOURS_BETWEEN_BACKUP = "Cosmos Cassandra Cluster periodic backup feature needs a value of hours between backups that is larger or equal to 1";

    private String cassandraVersion;
    private boolean useCassandraAuthentication;
    private boolean isDeallocated;
    private String delegatedManagementSubnetId;
    private boolean isCassandraAuditLoggingEnabled;
    private int hoursBetweenBackups;
    @Setter
    private AzureRegion azureRegion;
    @Setter
    private AzureResourceGroup azureResourceGroup;


    protected AzureCosmosCassandraCluster() {
        useCassandraAuthentication = true;
    }

    public static AzureCosmosCassandraClusterBuilder builder() {
        return new AzureCosmosCassandraClusterBuilder();
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.AZURE;
    }

    public static class AzureCosmosCassandraClusterBuilder extends PaaSCassandra.Builder<AzureCosmosCassandraCluster, AzureCosmosCassandraClusterBuilder> {

        @Override
        protected AzureCosmosCassandraCluster createComponent() {
            return new AzureCosmosCassandraCluster();
        }

        @Override
        protected AzureCosmosCassandraClusterBuilder getBuilder() {
            return this;
        }

        public AzureCosmosCassandraClusterBuilder withCassandraVersion(String cassandraVersion) {
            component.setCassandraVersion(cassandraVersion);
            return builder;
        }

        public AzureCosmosCassandraClusterBuilder withCassandraAuthentication(boolean useCassandraAuthentication) {
            component.setUseCassandraAuthentication(useCassandraAuthentication);
            return builder;
        }

        public AzureCosmosCassandraClusterBuilder withDeallocated(boolean deallocated) {
            component.setDeallocated(deallocated);
            return builder;
        }

        public AzureCosmosCassandraClusterBuilder withDelegatedManagementSubnetId(String delegatedManagementSubnetId) {
            component.setDelegatedManagementSubnetId(delegatedManagementSubnetId);
            return builder;
        }

        public AzureCosmosCassandraClusterBuilder withAuditLoggingEnabled(boolean auditLoggingEnabled) {
            component.setCassandraAuditLoggingEnabled(auditLoggingEnabled);
            return builder;
        }

        public AzureCosmosCassandraClusterBuilder withHoursBetweenBackups(int hoursBetweenBackups){
            component.setHoursBetweenBackups(hoursBetweenBackups);
            return builder;
        }

        public AzureCosmosCassandraClusterBuilder withRegion(AzureRegion region) {
            component.setAzureRegion(region);
            return builder;
        }

        public AzureCosmosCassandraClusterBuilder withAzureResourceGroup(AzureResourceGroup azureResourceGroup) {
            component.setAzureResourceGroup(azureResourceGroup);
            return builder;
        }

    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();
        errors.addAll(AzureEntity.validateAzureEntity(this, "Cassandra Cluster"));

        if(hoursBetweenBackups <= 0) {
            errors.add(ILLEGAL_AMOUNT_OF_HOURS_BETWEEN_BACKUP);
        }

        return errors;
    }
}
