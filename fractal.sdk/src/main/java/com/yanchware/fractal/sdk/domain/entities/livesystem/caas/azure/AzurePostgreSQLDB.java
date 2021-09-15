package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.PostgreSQLDB;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.POSTGRESQLDB;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class AzurePostgreSQLDB extends PostgreSQLDB {

    public static AzurePostgreSQLDBBuilder builder() {
        return new AzurePostgreSQLDBBuilder();
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.AZURE;
    }

    public static class AzurePostgreSQLDBBuilder extends Builder<AzurePostgreSQLDB, AzurePostgreSQLDBBuilder> {

        @Override
        protected AzurePostgreSQLDB createComponent() {
            return new AzurePostgreSQLDB();
        }

        @Override
        protected AzurePostgreSQLDBBuilder getBuilder() {
            return this;
        }

        @Override
        public AzurePostgreSQLDB build() {
            component.setType(POSTGRESQLDB);
            component.setVersion(DEFAULT_VERSION);
            return super.build();
        }
    }
}
