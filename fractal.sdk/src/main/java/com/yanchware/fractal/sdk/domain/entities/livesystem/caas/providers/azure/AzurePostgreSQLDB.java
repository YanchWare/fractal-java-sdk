package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.PostgreSQLDB;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    }
}
