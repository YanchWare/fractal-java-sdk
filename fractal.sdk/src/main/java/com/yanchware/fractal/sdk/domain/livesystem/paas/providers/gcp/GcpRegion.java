package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * Choose the GCP region that's right for you and your customers
 */
public class GcpRegion extends ExtendableEnum<GcpRegion> {

  public static final GcpRegion AFRICA_SOUTH1 = fromString("africa-south1");
  public static final GcpRegion ASIA_EAST1 = fromString("asia-east1");
  public static final GcpRegion ASIA_EAST2 = fromString("asia-east2");
  public static final GcpRegion ASIA_NORTHEAST1 = fromString("asia-northeast1-b");
  public static final GcpRegion ASIA_NORTHEAST2 = fromString("asia-northeast2");
  public static final GcpRegion ASIA_NORTHEAST3 = fromString("asia-northeast3");
  public static final GcpRegion ASIA_SOUTH1 = fromString("asia-south1");
  public static final GcpRegion ASIA_SOUTH2 = fromString("asia-south2");
  public static final GcpRegion ASIA_SOUTHEAST1 = fromString("asia-southeast1");
  public static final GcpRegion ASIA_SOUTHEAST2 = fromString("asia-southeast2");
  public static final GcpRegion AUSTRALIA_SOUTHEAST1 = fromString("australia-southeast1");
  public static final GcpRegion AUSTRALIA_SOUTHEAST2 = fromString("australia-southeast2");
  public static final GcpRegion EUROPE_CENTRAL2 = fromString("europe-central2");
  public static final GcpRegion EUROPE_NORTH1 = fromString("europe-north1");
  public static final GcpRegion EUROPE_SOUTHWEST1 = fromString("europe-southwest1");
  public static final GcpRegion EUROPE_WEST1 = fromString("europe-west1");
  public static final GcpRegion EUROPE_WEST10 = fromString("europe-west10");
  public static final GcpRegion EUROPE_WEST12 = fromString("europe-west12");
  public static final GcpRegion EUROPE_WEST2 = fromString("europe-west2");
  public static final GcpRegion EUROPE_WEST3 = fromString("europe-west3");
  public static final GcpRegion EUROPE_WEST4 = fromString("europe-west4");
  public static final GcpRegion EUROPE_WEST6 = fromString("europe-west6");
  public static final GcpRegion EUROPE_WEST8 = fromString("europe-west8");
  public static final GcpRegion EUROPE_WEST9 = fromString("europe-west9");
  public static final GcpRegion ME_CENTRAL1 = fromString("me-central1");
  public static final GcpRegion ME_CENTRAL2 = fromString("me-central2");
  public static final GcpRegion ME_WEST1 = fromString("me-west1");
  public static final GcpRegion NORTHAMERICA_NORTHEAST1 = fromString("northamerica-northeast1");
  public static final GcpRegion NORTHAMERICA_NORTHEAST2 = fromString("northamerica-northeast2");
  public static final GcpRegion NORTHAMERICA_SOUTH1 = fromString("northamerica-south1");
  public static final GcpRegion SOUTHAMERICA_EAST1 = fromString("southamerica-east1");
  public static final GcpRegion SOUTHAMERICA_WEST1 = fromString("southamerica-west1");
  public static final GcpRegion US_CENTRAL1 = fromString("us-central1");
  public static final GcpRegion US_EAST1 = fromString("us-east1");
  public static final GcpRegion US_EAST4 = fromString("us-east4");
  public static final GcpRegion US_EAST5 = fromString("us-east5");
  public static final GcpRegion US_SOUTH1 = fromString("us-south1");
  public static final GcpRegion US_WEST1 = fromString("us-west1");
  public static final GcpRegion US_WEST2 = fromString("us-west2");
  public static final GcpRegion US_WEST3 = fromString("us-west3");
  public static final GcpRegion US_WEST4 = fromString("us-west4");

  /**
   * Creates or finds a GcpRegion from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding AwsRegion.
   */
  @JsonCreator
  public static GcpRegion fromString(String name) {
    return fromString(name, GcpRegion.class);
  }

  /**
   * Gets known GcpRegion values.
   *
   * @return known GcpRegion values.
   */
  public static Collection<GcpRegion> values() {
    return values(GcpRegion.class);
  }
}
