package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

public final class AzurePricingPlan extends ExtendableEnum<AzurePricingPlan> {
  public static final AzurePricingPlan BASIC_B1 = fromString("BASIC_B1");
  public static final AzurePricingPlan BASIC_B2 = fromString("BASIC_B2");
  public static final AzurePricingPlan BASIC_B3 = fromString("BASIC_B3");
  public static final AzurePricingPlan STANDARD_S1 = fromString("STANDARD_S1");
  public static final AzurePricingPlan STANDARD_S2 = fromString("STANDARD_S2");
  public static final AzurePricingPlan STANDARD_S3 = fromString("STANDARD_S3");
  public static final AzurePricingPlan PREMIUM_P1 = fromString("PREMIUM_P1");
  public static final AzurePricingPlan PREMIUM_P2 = fromString("PREMIUM_P2");
  public static final AzurePricingPlan PREMIUM_P3 = fromString("PREMIUM_P3");
  public static final AzurePricingPlan PREMIUM_P1V2 = fromString("PREMIUM_P1V2");
  public static final AzurePricingPlan PREMIUM_P2V2 = fromString("PREMIUM_P2V2");
  public static final AzurePricingPlan PREMIUM_P3V2 = fromString("PREMIUM_P3V2");
  public static final AzurePricingPlan PREMIUM_P1V3 = fromString("PREMIUM_P1V3");
  public static final AzurePricingPlan PREMIUM_P2V3 = fromString("PREMIUM_P2V3");
  public static final AzurePricingPlan PREMIUM_P3V3 = fromString("PREMIUM_P3V3");
  public static final AzurePricingPlan FREE_F1 = fromString("FREE_F1");
  public static final AzurePricingPlan SHARED_D1 = fromString("SHARED_D1");

  public AzurePricingPlan() {
  }

  @JsonCreator
  public static AzurePricingPlan fromString(String name) {
    return fromString(name, AzurePricingPlan.class);
  }

  public static Collection<AzurePricingPlan> values() {
    return values(AzurePricingPlan.class);
  }
}
