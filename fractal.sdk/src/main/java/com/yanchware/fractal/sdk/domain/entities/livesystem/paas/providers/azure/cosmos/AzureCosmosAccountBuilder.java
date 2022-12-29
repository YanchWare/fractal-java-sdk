package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.utils.CollectionUtils;

import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_COSMOS_ACCOUNT;

public abstract class AzureCosmosAccountBuilder<T extends Component & AzureCosmosAccount, B extends AzureCosmosAccountBuilder<T, B>> extends Component.Builder<T, B> {

  private static final String PUBLIC_NETWORK_ACCESS_ENABLED_MODE = "Enabled";
  private static final String PUBLIC_NETWORK_ACCESS_DISABLED_MODE = "Disabled";

  public B withMaxTotalThroughput(int maxTotalThroughput) {
    component.setMaxTotalThroughput(maxTotalThroughput);
    return builder;
  }

  public <C extends Component & AzureCosmosEntity> B withCosmosEntity(C azureCosmosEntity) {
    return withCosmosEntities(List.of(azureCosmosEntity));
  }

  public <C extends Component & AzureCosmosEntity> B withCosmosEntities(Collection<C> azureCosmosEntities) {
    if (CollectionUtils.isBlank(azureCosmosEntities)) {
      return builder;
    }

    azureCosmosEntities.forEach(entity -> {
      entity.getDependencies().add(component.getId());
      entity.setAzureRegion(component.getAzureRegion());
      entity.setAzureResourceGroup(component.getAzureResourceGroup());
    });
    component.getCosmosEntities().addAll(azureCosmosEntities);
    return builder;

  }

  public B enablePublicNetworkAccess() {
    component.setPublicNetworkAccess(PUBLIC_NETWORK_ACCESS_ENABLED_MODE);
    return builder;
  }

  public B disablePublicNetworkAccess() {
    component.setPublicNetworkAccess(PUBLIC_NETWORK_ACCESS_DISABLED_MODE);
    return builder;
  }

  public B withAzureResourceGroup(AzureResourceGroup azureResourceGroup) {
    component.setAzureResourceGroup(azureResourceGroup);
    return builder;
  }

  public B withRegion(AzureRegion region) {
    component.setAzureRegion(region);
    return builder;
  }

  @Override
  public T build() {
    component.setType(PAAS_COSMOS_ACCOUNT);
    return super.build();
  }
}
