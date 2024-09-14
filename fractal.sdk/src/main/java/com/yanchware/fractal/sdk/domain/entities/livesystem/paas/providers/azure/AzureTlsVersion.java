package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * TLS version to be permitted on requests
 */
public final class AzureTlsVersion extends ExtendableEnum<AzureTlsVersion> {
  public static final AzureTlsVersion TLS1_0 = fromString("TLS1_0");
  
  public static final AzureTlsVersion TLS1_1 = fromString("TLS1_1");
  
  public static final AzureTlsVersion TLS1_2 = fromString("TLS1_2");
  

  /**
   * Creates or finds a AzureTlsVersion from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding AzureTlsVersion.
   */
  @JsonCreator
  public static AzureTlsVersion fromString(String name) {
    return fromString(name, AzureTlsVersion.class);
  }

  /**
   * Gets known AzureTlsVersion values.
   *
   * @return known AzureTlsVersion values.
   */
  public static Collection<AzureTlsVersion> values() {
    return values(AzureTlsVersion.class);
  }
}
