package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSCassandra;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class AzureCosmosCassandraCluster extends PaaSCassandra {
    private final static String NAME_IS_BLANK = "Cosmos Cassandra Cluster name has not been defined and it is required";
    private final static String ILLEGAL_AMOUNT_OF_HOURS_BETWEEN_BACKUP = "Cosmos Cassandra Cluster periodic backup feature needs a value of hours between backups that is larger or equal to 1";

    private String name;
    private String cassandraVersion;
    private boolean useCassandraAuthentication = true;
    private boolean isDeallocated;
    private String delegatedManagementSubnetId;
    private boolean isCassandraAuditLoggingEnabled;
    private int hoursBetweenBackups;

    protected AzureCosmosCassandraCluster() {
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

        public AzureCosmosCassandraClusterBuilder withName(String name) {
            component.setName(name);
            return builder;
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
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (isBlank(name)) {
            errors.add(NAME_IS_BLANK);
        }

        if(hoursBetweenBackups <= 0) {
            errors.add(ILLEGAL_AMOUNT_OF_HOURS_BETWEEN_BACKUP);
        }

        return errors;
    }
}
