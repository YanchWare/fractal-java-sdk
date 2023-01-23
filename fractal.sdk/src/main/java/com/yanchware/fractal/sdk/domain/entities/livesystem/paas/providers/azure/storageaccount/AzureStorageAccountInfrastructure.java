package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureStorageAccountInfrastructure {
  AzureStorageSkuName sku;
  HashMap<String, Object> userAssignedIdentities; // TODO: type to used instead of Object
  AzureIdentityType identityType;
  String extendedLocationName;
  String extendedLocationType; // TODO: this is not probably required since it only has a single value
  AzureDnsEndpointType dnsEndpointType;
  AzureStorageAccessTier accessTier;
  String customDomainName;
  boolean customDomainUseSubName;
}
