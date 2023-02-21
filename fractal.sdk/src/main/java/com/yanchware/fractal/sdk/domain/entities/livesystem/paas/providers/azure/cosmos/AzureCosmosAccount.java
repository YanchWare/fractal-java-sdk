package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceEntity;

import java.util.Collection;

public interface AzureCosmosAccount extends AzureResourceEntity {

  <C extends Component & AzureCosmosEntity> Collection<C> getCosmosEntities();
  int getMaxTotalThroughput();
  void setMaxTotalThroughput(int maxTotalThroughput);
  AzureCosmosBackupPolicy getBackupPolicy();
  void setBackupPolicy(AzureCosmosBackupPolicy backupPolicy);
  
  void setPublicNetworkAccess(String publicNetworkAccessMode);

  static Collection<String> validateCosmosAccount(AzureCosmosAccount cosmosAccount, String accountType) {
    var errors = AzureResourceEntity.validateAzureResourceEntity(cosmosAccount, accountType);

    cosmosAccount.getCosmosEntities().stream()
      .map(AzureCosmosEntity::validateCosmosEntity)
      .forEach(errors::addAll);

    return errors;
  }
}
