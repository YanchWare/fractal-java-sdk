package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlDbImpl;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_POSTGRESQLDB;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class AzureCosmosPostgreSQLDB extends PaaSPostgreSqlDbImpl {
    public static final String TYPE = PAAS_COSMOS_POSTGRESQLDB.getId();
    private final static String NAME_IS_BLANK = "Cosmos PostgreSQLDB name has not been defined and it is required";
    private final static String COSMOS_ACCOUNT_IS_BLANK = "Cosmos PostgreSQLDB defined no connection to a Cosmos Account, and it is required";
    private final static String THROUGHPUT_IS_BLANK = "Cosmos PostgreSQLDB defined no throughput, and it is required";
    private final static String MAX_THROUGHPUT_IS_BLANK = "Cosmos PostgreSQLDB defined no max throughput defined, and it is required";
    private final static String MAX_THROUGHPUT_IS_SMALLER = "Cosmos PostgreSQLDB defined has max throughput defined, but it is less than base throughput";

    private String name;
    private String cosmosAccount;
    private int throughput;
    private int maxThroughput;

    @Getter
    @Setter
    private ProviderType provider;

    protected AzureCosmosPostgreSQLDB() {
    }


    public static PostgreSQLDBBuilder builder() {
        return new PostgreSQLDBBuilder();
    }

    public static class PostgreSQLDBBuilder extends Builder<AzureCosmosPostgreSQLDB, PostgreSQLDBBuilder> {

        @Override
        protected AzureCosmosPostgreSQLDB createComponent() {
            return new AzureCosmosPostgreSQLDB();
        }

        @Override
        protected PostgreSQLDBBuilder getBuilder() {
            return this;
        }

        public PostgreSQLDBBuilder withName(String name) {
            component.setName(name);
            return builder;
        }

        public PostgreSQLDBBuilder withCosmosAccount(String cosmosAccount) {
            component.setCosmosAccount(cosmosAccount);
            return builder;
        }

        public PostgreSQLDBBuilder withThroughput(int throughput) {
            component.setThroughput(throughput);
            return builder;
        }

        public PostgreSQLDBBuilder withMaxThroughput(int maxThroughput) {
            component.setMaxThroughput(maxThroughput);
            return builder;
        }

        @Override
        public AzureCosmosPostgreSQLDB build() {
            component.setType(PAAS_COSMOS_POSTGRESQLDB);
            return super.build();
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
