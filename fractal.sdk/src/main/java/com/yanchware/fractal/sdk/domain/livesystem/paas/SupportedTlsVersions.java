package com.yanchware.fractal.sdk.domain.livesystem.paas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public class SupportedTlsVersions extends ExtendableEnum<SupportedTlsVersions> {
  public static final SupportedTlsVersions ONE_ZERO = fromString("1.0");
  public static final SupportedTlsVersions ONE_ONE = fromString("1.1");
  public static final SupportedTlsVersions ONE_TWO = fromString("1.2");

  public SupportedTlsVersions() {
  }

  @JsonCreator
  public static SupportedTlsVersions fromString(String name) {
    return fromString(name, SupportedTlsVersions.class);
  }

  public static Collection<SupportedTlsVersions> values() {
    return values(SupportedTlsVersions.class);
  }
}
