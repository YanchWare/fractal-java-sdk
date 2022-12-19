package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlDatabase;
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
public class AzureCosmosCassandraCluster extends PaaSPostgreSqlDatabase {
    private final static String NAME_IS_BLANK = "Cosmos PostgreSQLDB name has not been defined and it is required";
    private final static String COSMOS_ACCOUNT_IS_BLANK = "Cosmos PostgreSQLDB defined no connection to a Cosmos Account, and it is required";
    private final static String THROUGHPUT_IS_BLANK = "Cosmos PostgreSQLDB defined no throughput, and it is required";
    private final static String MAX_THROUGHPUT_IS_BLANK = "Cosmos PostgreSQLDB defined no max throughput defined, and it is required";
    private final static String MAX_THROUGHPUT_IS_SMALLER = "Cosmos PostgreSQLDB defined has max throughput defined, but it is less than base throughput";

    private String name;
    private String cassandraVersion;
    private boolean useCassandraAuthentication;
    private boolean isDeallocated;
    private String delegatedManagementSubnetId;
    private boolean isCassandraAuditLoggingEnabled;
    private int hoursBetweenBackups;

    protected AzureCosmosCassandraCluster() {
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.AZURE;
    }

    public static class Builder<T extends AzureCosmosCassandraCluster, B extends AzureCosmosCassandraCluster.Builder<T, B>> extends Component.Builder<T, B> {

        public B withName(String name) {
            component.setName(name);
            return builder;
        }

        public B withCosmosAccount(String cosmosAccount) {
            component.setCosmosAccount(cosmosAccount);
            return builder;
        }

        public B withThroughput(int throughput) {
            component.setThroughput(throughput);
            return builder;
        }

        public B withMaxThroughput(int maxThroughput) {
            component.setMaxThroughput(maxThroughput);
            return builder;
        }
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (isBlank(name)) {
            errors.add(NAME_IS_BLANK);
        }

        if(isBlank(cosmosAccount)) {
            errors.add(COSMOS_ACCOUNT_IS_BLANK);
        }

        if(throughput <= 0) {
            errors.add(THROUGHPUT_IS_BLANK);
        }

        if(maxThroughput <= 0) {
            errors.add(MAX_THROUGHPUT_IS_BLANK);
        }

        if(maxThroughput < throughput) {
            errors.add(MAX_THROUGHPUT_IS_SMALLER);
        }

        return errors;
    }
}
