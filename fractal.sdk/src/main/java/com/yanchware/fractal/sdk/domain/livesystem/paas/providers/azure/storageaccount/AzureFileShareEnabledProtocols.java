package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public final class AzureFileShareEnabledProtocols extends ExtendableEnum<AzureFileShareEnabledProtocols> {
  public static final AzureFileShareEnabledProtocols NFS = fromString("NFS");
  public static final AzureFileShareEnabledProtocols SMB = fromString("SMB");
  
  public AzureFileShareEnabledProtocols() {
  }

  @JsonCreator
  public static AzureFileShareEnabledProtocols fromString(String name) {
    return fromString(name, AzureFileShareEnabledProtocols.class);
  }

  public static Collection<AzureFileShareEnabledProtocols> values() {
    return values(AzureFileShareEnabledProtocols.class);
  }
}
