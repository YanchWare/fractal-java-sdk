package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.blueprint.paas.PaaSRelationalDatabase;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_TABLE;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureCosmosTableEntity extends PaaSRelationalDatabase implements LiveSystemComponent, AzureCosmosEntity {

  public static final String TYPE = PAAS_COSMOS_TABLE.getId();
  private String name;
  private int throughput;
  private int maxThroughput;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;
  @Setter(AccessLevel.PRIVATE)
  private String entityName = "Table Entity";

  public static AzureCosmosTableEntityBuilder builder() {
    return new AzureCosmosTableEntityBuilder();
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  @Override
  public Collection<String> validate() {
    return super.validate();
  }

  public static class AzureCosmosTableEntityBuilder extends AzureCosmosEntityBuilder<AzureCosmosTableEntity,
    AzureCosmosTableEntityBuilder> {
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
}
