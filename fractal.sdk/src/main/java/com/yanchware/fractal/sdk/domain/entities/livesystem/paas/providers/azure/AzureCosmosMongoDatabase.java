package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSDocumentDatabase;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureCosmosUtilities.validateCosmosEntity;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_MONGO_DATABASE;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class AzureCosmosMongoDatabase extends PaaSDocumentDatabase implements LiveSystemComponent, AzureCosmosEntity {

    public static final String TYPE = PAAS_COSMOS_MONGO_DATABASE.getId();

    public static AzureCosmosMongoDatabaseBuilder builder() {
        return new AzureCosmosMongoDatabaseBuilder();
    }

    private String name;
    private String cosmosAccount;
    private int throughput;
    private int maxThroughput;

    @Override
    public ProviderType getProvider() {
        return ProviderType.AZURE;
    }


    public static class AzureCosmosMongoDatabaseBuilder extends AzureCosmosEntityBuilder<AzureCosmosMongoDatabase, AzureCosmosMongoDatabaseBuilder> {
        @Override
        protected AzureCosmosMongoDatabase createComponent() {
            return new AzureCosmosMongoDatabase();
        }

        @Override
        protected AzureCosmosMongoDatabaseBuilder getBuilder() {
            return this;
        }

        @Override
        public AzureCosmosMongoDatabase build() {
            component.setType(PAAS_COSMOS_MONGO_DATABASE);
            return super.build();
        }

    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();
        errors.addAll(validateCosmosEntity(this, "Cosmos Gremlin Database"));
        return errors;
    }
}
