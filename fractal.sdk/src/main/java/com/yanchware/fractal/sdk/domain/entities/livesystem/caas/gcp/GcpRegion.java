package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.gcp;

import java.util.Collection;

public enum GcpRegion {
  US_EAST1("us-east1"),
  US_EAST4("us-east4"),
  US_CENTRAL1("us-central1"),
  US_WEST1("us-west1"),
  EU_WEST4("europe-west4"),
  EU_WEST1("europe-west1"),
  EU_WEST3("europe-west3"),
  EU_WEST2("europe-west2"),
  ASIA_EAST1("asia-east1"),
  ASIA_SOUTHEAST1("asia-southeast1"),
  ASIA_NORTHEAST1("asia-northeast1-b"),
  ASIA_SOUTH1("asia-south1"),
  AUSTRALIA_SOUTHEAST1("australia-southeast1"),
  SOUTH_AMERICAEAST1("southamerica-east1"),
  ASIA_EAST2("asia-east2"),
  ASIA_NORTHEAST2("asia-northeast2"),
  ASIA_NORTHEAST3("asia-northeast3"),
  ASIA_SOUTH2("asia-south2"),
  ASIA_SOUTHEAST2("asia-southeast2"),
  AUSTRALIA_SOUTHEAST2("australia-southeast2"),
  EU_CENTRAL2("europe-central2"),
  EU_NORTH1("europe-north1"),
  EU_WEST6("europe-west6"),
  NORTHAMERICA_NORTHEAST1("northamerica-northeast1"),
  NORTHAMERICA_NORTHEAST2("northamerica-northeast2"),
  US_WEST2("us-west2"),
  US_WEST3("us-west3"),
  US_WEST4("us-west4");

  private final String id;

  private GcpRegion(final String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
  public Collection<GcpZone> getZones() {
    // TODO: Implement
    throw new IllegalArgumentException("Not implemented");
  }

}
