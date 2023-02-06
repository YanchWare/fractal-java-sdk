package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSGraphDatabase;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.Map;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_GREMLIN_DATABASE;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureCosmosGremlinDatabase extends PaaSGraphDatabase implements LiveSystemComponent, AzureCosmosEntity {

    public static final String TYPE = PAAS_COSMOS_GREMLIN_DATABASE.getId();

    public static AzureCosmosGremlinDatabaseBuilder builder() {
        return new AzureCosmosGremlinDatabaseBuilder();
    }

    private String name;
    private int throughput;
    private int maxThroughput;
    private AzureRegion azureRegion;
    private AzureResourceGroup azureResourceGroup;
    private Map<String, String> tags;

    @Setter(AccessLevel.PRIVATE)
    private String entityName = "Gremlin Database";


    @Override
    public ProviderType getProvider() {
        return ProviderType.AZURE;
    }


    public static class AzureCosmosGremlinDatabaseBuilder extends AzureCosmosEntityBuilder<AzureCosmosGremlinDatabase, AzureCosmosGremlinDatabaseBuilder> {
        @Override
        protected AzureCosmosGremlinDatabase createComponent() {
            return new AzureCosmosGremlinDatabase();
        }

        @Override
        protected AzureCosmosGremlinDatabaseBuilder getBuilder() {
            return this;
        }

        @Override
        public AzureCosmosGremlinDatabase build() {
            component.setType(PAAS_COSMOS_GREMLIN_DATABASE);
            return super.build();
        }

    }

    @Override
    public Collection<String> validate() {
        return super.validate();
    }
}
