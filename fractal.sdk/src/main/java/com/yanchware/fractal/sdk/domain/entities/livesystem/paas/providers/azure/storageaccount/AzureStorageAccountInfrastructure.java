package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter(AccessLevel.PRIVATE)
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

  public static AzureStorageAccountInfrastructureBuilder builder() {
    return new AzureStorageAccountInfrastructureBuilder();
  }

  public static class AzureStorageAccountInfrastructureBuilder {
    private final AzureStorageAccountInfrastructure infrastructure;
    private final AzureStorageAccountInfrastructureBuilder builder;

    public AzureStorageAccountInfrastructureBuilder() {
      this.infrastructure = new AzureStorageAccountInfrastructure();
      this.builder = this;
    }

    public AzureStorageAccountInfrastructureBuilder withSku(AzureStorageSkuName sku) {
      infrastructure.setSku(sku);
      return builder;
    }

    public AzureStorageAccountInfrastructureBuilder withUserAssignedIdentities(HashMap<String, AzureUserAssignedIdentity> userAssignedIdentities) {
      infrastructure.setUserAssignedIdentities(userAssignedIdentities);
      return builder;
    }

    public AzureStorageAccountInfrastructureBuilder withIdentityType(AzureIdentityType identityType) {
      infrastructure.setIdentityType(identityType);
      return builder;
    }

    public AzureStorageAccountInfrastructureBuilder withExtendedLocationName(String extendedLocationName) {
      infrastructure.setExtendedLocationName(extendedLocationName);
      return builder;
    }

    public AzureStorageAccountInfrastructureBuilder withExtendedLocationType(String extendedLocationType) {
      infrastructure.setExtendedLocationType(extendedLocationType);
      return builder;
    }

    public AzureStorageAccountInfrastructureBuilder withDnsEndpointType(AzureDnsEndpointType dnsEndpointType) {
      infrastructure.setDnsEndpointType(dnsEndpointType);
      return builder;
    }

    public AzureStorageAccountInfrastructureBuilder withAccessTier(AzureStorageAccessTier accessTier) {
      infrastructure.setAccessTier(accessTier);
      return builder;
    }

    public AzureStorageAccountInfrastructureBuilder withCustomDomainName(String customDomainName) {
      infrastructure.setCustomDomainName(customDomainName);
      return builder;
    }

    public AzureStorageAccountInfrastructureBuilder withCustomDomainUseSubName(Boolean customDomainUseSubName) {
      infrastructure.setCustomDomainUseSubName(customDomainUseSubName);
      return builder;
    }

    public AzureStorageAccountInfrastructure build() {
      return infrastructure;
    }
  }
}
