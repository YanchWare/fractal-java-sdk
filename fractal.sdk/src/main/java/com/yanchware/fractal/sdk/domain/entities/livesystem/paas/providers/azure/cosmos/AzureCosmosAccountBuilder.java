package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.utils.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_ACCOUNT;

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

  /**
   * The resource group in which the component will be created
   *
   * @param azureResourceGroup Azure Resource Group reference
   */
  public B withAzureResourceGroup(AzureResourceGroup azureResourceGroup) {
    component.setAzureResourceGroup(azureResourceGroup);
    return builder;
  }

  /**
   * The region in which the component will be created
   *
   * @param region Azure region
   */
  public B withRegion(AzureRegion region) {
    component.setAzureRegion(region);
    return builder;
  }

  public B withBackupPolicy(AzureCosmosBackupPolicy backupPolicy) {
    component.setBackupPolicy(backupPolicy);
    return builder;
  }

  /**
   * Tags are name/value pairs that enable you to categorize resources and view consolidated billing by
   * applying the same tag to multiple resources and resource groups.
   */
  public B withTags(Map<String, String> tags) {
    component.setTags(tags);
    return builder;
  }

  /**
   * Tag is name/value pairs that enable you to categorize resources and view consolidated billing by
   * applying the same tag to multiple resources and resource groups.
   */
  public B withTag(String key, String value) {
    if (component.getTags() == null) {
      withTags(new HashMap<>());
    }

    component.getTags().put(key, value);
    return builder;
  }

  @Override
  public T build() {
    component.setType(PAAS_COSMOS_ACCOUNT);
    return super.build();
  }
}
