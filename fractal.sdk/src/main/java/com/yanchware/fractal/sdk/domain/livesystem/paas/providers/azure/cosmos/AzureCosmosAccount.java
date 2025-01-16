package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceEntity;

import java.util.Collection;

public interface AzureCosmosAccount extends AzureResourceEntity {

  static Collection<String> validateCosmosAccount(AzureCosmosAccount cosmosAccount, String accountType) {
    var errors = AzureResourceEntity.validateAzureResourceEntity(cosmosAccount, accountType);

    cosmosAccount.getCosmosEntities().stream()
      .map(AzureCosmosEntity::validateCosmosEntity)
      .forEach(errors::addAll);

    return errors;
  }

  <C extends Component & AzureCosmosEntity> Collection<C> getCosmosEntities();

  Integer getMaxTotalThroughput();

  void setMaxTotalThroughput(Integer maxTotalThroughput);

  AzureCosmosBackupPolicy getBackupPolicy();

  void setBackupPolicy(AzureCosmosBackupPolicy backupPolicy);

  void setPublicNetworkAccess(String publicNetworkAccessMode);
}
