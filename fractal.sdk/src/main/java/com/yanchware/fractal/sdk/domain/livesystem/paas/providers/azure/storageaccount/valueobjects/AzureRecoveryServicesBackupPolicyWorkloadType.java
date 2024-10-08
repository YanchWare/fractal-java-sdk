package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;


  public enum AzureRecoveryServicesBackupPolicyWorkloadType {
    AzureWorkload ("AzureWorkload"),
    AzureStorage ("AzureStorage"),
    AzureIaasVM ("AzureIaasVM"),
    AzureSql ("AzureSql"),
    GenericProtectionPolicy ("GenericProtectionPolicy"),
    MAB ("MAB");
  
    private final String id;
  
    AzureRecoveryServicesBackupPolicyWorkloadType(final String id) {
      this.id = id;
    }
  
    @JsonValue
    public String getId() {
      return id;
    }
  
    @Override
    public String toString() {
      return id;
    }
  }

  