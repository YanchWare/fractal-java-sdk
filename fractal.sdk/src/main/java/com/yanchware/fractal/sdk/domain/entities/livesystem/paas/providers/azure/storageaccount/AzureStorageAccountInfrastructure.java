package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureStorageAccountInfrastructure {
  AzureStorageSkuName sku;
  HashMap<String, AzureUserAssignedIdentity> userAssignedIdentities;
  AzureIdentityType identityType;
  String extendedLocationName;
  String extendedLocationType;
  AzureDnsEndpointType dnsEndpointType;
  AzureStorageAccessTier accessTier;
  String customDomainName;
  Boolean customDomainUseSubName;
}
