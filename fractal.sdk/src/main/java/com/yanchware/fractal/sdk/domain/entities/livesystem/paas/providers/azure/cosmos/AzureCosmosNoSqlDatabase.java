package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSDocumentDatabase;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_NOSQL_DATABASE;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureCosmosNoSqlDatabase extends PaaSDocumentDatabase implements LiveSystemComponent, AzureCosmosEntity {

    public static final String TYPE = PAAS_COSMOS_NOSQL_DATABASE.getId();

    public static AzureCosmosNoSqlDatabaseBuilder builder() {
        return new AzureCosmosNoSqlDatabaseBuilder();
    }

    private String cosmosAccount;
    private int throughput;
    private int maxThroughput;
    private AzureRegion region;
    private AzureResourceGroup azureResourceGroup;


    @Override
    public ProviderType getProvider() {
        return ProviderType.AZURE;
    }


    public static class AzureCosmosNoSqlDatabaseBuilder extends AzureCosmosEntityBuilder<AzureCosmosNoSqlDatabase, AzureCosmosNoSqlDatabaseBuilder> {
        @Override
        protected AzureCosmosNoSqlDatabase createComponent() {
            return new AzureCosmosNoSqlDatabase();
        }

        @Override
        protected AzureCosmosNoSqlDatabaseBuilder getBuilder() {
            return this;
        }

        @Override
        public AzureCosmosNoSqlDatabase build() {
            component.setType(PAAS_COSMOS_NOSQL_DATABASE);
            return super.build();
        }

    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();
        errors.addAll(AzureCosmosEntity.validateCosmosEntity(this, "NoSql Database"));
        return errors;
    }
}
