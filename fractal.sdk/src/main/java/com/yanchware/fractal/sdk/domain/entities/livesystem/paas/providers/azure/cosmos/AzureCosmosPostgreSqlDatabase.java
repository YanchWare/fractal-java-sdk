package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSRelationalDatabase;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_POSTGRESQL_DATABASE;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureCosmosPostgreSqlDatabase extends PaaSRelationalDatabase implements LiveSystemComponent, AzureCosmosEntity {

    public static final String TYPE = PAAS_COSMOS_POSTGRESQL_DATABASE.getId();

    public static AzureCosmosPostgreSqlDatabaseBuilder builder() {
        return new AzureCosmosPostgreSqlDatabaseBuilder();
    }


    private String cosmosAccount;
    private int throughput;
    private int maxThroughput;
    private AzureRegion azureRegion;
    private AzureResourceGroup azureResourceGroup;

    @Setter(AccessLevel.PRIVATE)
    private String entityName = "PostgreSql Database";

    @Override
    public ProviderType getProvider() {
        return ProviderType.AZURE;
    }


    public static class AzureCosmosPostgreSqlDatabaseBuilder extends AzureCosmosEntityBuilder<AzureCosmosPostgreSqlDatabase, AzureCosmosPostgreSqlDatabaseBuilder> {
        @Override
        protected AzureCosmosPostgreSqlDatabase createComponent() {
            return new AzureCosmosPostgreSqlDatabase();
        }

        @Override
        protected AzureCosmosPostgreSqlDatabaseBuilder getBuilder() {
            return this;
        }

        @Override
        public AzureCosmosPostgreSqlDatabase build() {
            component.setType(PAAS_COSMOS_POSTGRESQL_DATABASE);
            return super.build();
        }

    }

    @Override
    public Collection<String> validate() {
        return super.validate();
    }
}
