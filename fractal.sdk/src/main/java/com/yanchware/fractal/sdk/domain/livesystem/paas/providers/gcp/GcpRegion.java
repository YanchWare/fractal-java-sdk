package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Collection;

public enum GcpRegion {
  EAST_US1("us-east1"),
  EAST_US4("us-east4"),
  CENTRAL_US1("us-central1"),
  US_WEST1("us-west1"),
  EU_WEST4("europe-west4"),
  EU_WEST1("europe-west1"),
  EU_WEST3("europe-west3"),
  EU_WEST2("europe-west2"),
  EAST_ASIA1("asia-east1"),
  SOUTHEAST_ASIA1("asia-southeast1"),
  ASIA_NORTHEAST1("asia-northeast1-b"),
  ASIA_SOUTH1("asia-south1"),
  AUSTRALIA_SOUTHEAST1("australia-southeast1"),
  SOUTH_AMERICAEAST1("southamerica-east1"),
  EAST_ASIA2("asia-east2"),
  ASIA_NORTHEAST2("asia-northeast2"),
  ASIA_NORTHEAST3("asia-northeast3"),
  ASIA_SOUTH2("asia-south2"),
  SOUTHEAST_ASIA2("asia-southeast2"),
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

  public Collection<GcpZone> getZones() {
    // TODO: Implement
    throw new NotImplementedException("GcpZone.getZones() - Not implemented");
  }

  @JsonValue
  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return id;
  }
}
