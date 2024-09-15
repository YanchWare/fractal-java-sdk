package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public final class AzureIpFilterTag extends ExtendableEnum<AzureIpFilterTag> {
  public static final AzureIpFilterTag DEFAULT = fromString("Default");
  public static final AzureIpFilterTag SERVICE_TAG = fromString("ServiceTag");
  public static final AzureIpFilterTag XFF_PROXY = fromString("XffProxy");

  public AzureIpFilterTag() {
  }

  @JsonCreator
  public static AzureIpFilterTag fromString(String name) {
    return fromString(name, AzureIpFilterTag.class);
  }

  public static Collection<AzureIpFilterTag> values() {

    return values(AzureIpFilterTag.class);
  }
}
