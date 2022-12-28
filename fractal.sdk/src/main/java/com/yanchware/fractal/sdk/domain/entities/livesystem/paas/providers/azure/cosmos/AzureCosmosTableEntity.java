package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSRelationalDatabase;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_TABLE;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureCosmosTableEntity extends PaaSRelationalDatabase implements LiveSystemComponent, AzureCosmosEntity {

    public static final String TYPE = PAAS_COSMOS_TABLE.getId();

    public static AzureCosmosTableEntityBuilder builder() {
        return new AzureCosmosTableEntityBuilder();
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


    public static class AzureCosmosTableEntityBuilder extends AzureCosmosEntityBuilder<AzureCosmosTableEntity, AzureCosmosTableEntityBuilder> {
        @Override
        protected AzureCosmosTableEntity createComponent() {
            return new AzureCosmosTableEntity();
        }

        @Override
        protected AzureCosmosTableEntityBuilder getBuilder() {
            return this;
        }

        @Override
        public AzureCosmosTableEntity build() {
            component.setType(PAAS_COSMOS_TABLE);
            return super.build();
        }

    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();
        errors.addAll(AzureCosmosEntity.validateCosmosEntity(this, "Table Entity"));
        return errors;
    }
}
