package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSDocumentDatabase;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.Map;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_MONGO_DATABASE;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureCosmosMongoDatabase extends PaaSDocumentDatabase implements LiveSystemComponent, AzureCosmosEntity {

    public static final String TYPE = PAAS_COSMOS_MONGO_DATABASE.getId();

    public static AzureCosmosMongoDatabaseBuilder builder() {
        return new AzureCosmosMongoDatabaseBuilder();
    }

    private String name;
    private int throughput;
    private int maxThroughput;
    private AzureRegion azureRegion;
    private AzureResourceGroup azureResourceGroup;

    @Setter(AccessLevel.PRIVATE)
    private String entityName = "Mongo Database";
    
    private Map<String, String> tags;


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
        return super.validate();
    }
}
