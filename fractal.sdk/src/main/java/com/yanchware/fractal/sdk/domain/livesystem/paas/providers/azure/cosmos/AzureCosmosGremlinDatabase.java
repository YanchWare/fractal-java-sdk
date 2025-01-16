package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.blueprint.paas.PaaSGraphDatabase;
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

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_GREMLIN_DATABASE;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureCosmosGremlinDatabase extends PaaSGraphDatabase implements LiveSystemComponent, AzureCosmosEntity {

  public static final String TYPE = PAAS_COSMOS_GREMLIN_DATABASE.getId();
  private String name;
  private int throughput;
  private int maxThroughput;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;
  @Setter(AccessLevel.PRIVATE)
  private String entityName = "Gremlin Database";

  public static AzureCosmosGremlinDatabaseBuilder builder() {
    return new AzureCosmosGremlinDatabaseBuilder();
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  @Override
  public Collection<String> validate() {
    return super.validate();
  }

  public static class AzureCosmosGremlinDatabaseBuilder extends AzureCosmosEntityBuilder<AzureCosmosGremlinDatabase,
    AzureCosmosGremlinDatabaseBuilder> {
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
}
