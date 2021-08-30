package com.yanchware.fractal.sdk.entities.livesystem.caas;

import java.util.Collection;

public enum GcpRegion {
  UsEast1("us-east1"),
  UsEast4("us-east4"),
  UsCentral1("us-central1"),
  UsWest1("us-west1"),
  EuWest4("europe-west4"),
  EuWest1("europe-west1"),
  EuWest3("europe-west3"),
  EuWest2("europe-west2"),
  AsiaEast1("asia-east1"),
  AsiaSouthEast1("asia-southeast1"),
  AsiaNorthEast1("asia-northeast1-b"),
  AsiaSouth1("asia-south1"),
  AustraliaSouthEast1("australia-southeast1"),
  SouthAmericaEast1("southamerica-east1"),
  AsiaEast2("asia-east2"),
  AsiaNorthEast2("asia-northeast2"),
  AsiaNorthEast3("asia-northeast3"),
  AsiaSouth2("asia-south2"),
  AsiaSouthEast2("asia-southeast2"),
  AustraliaSouthEast2("australia-southeast2"),
  EuCentral2("europe-central2"),
  EuNorth1("europe-north1"),
  EuWest6("europe-west6"),
  NorthAmericaNorthEast1("northamerica-northeast1"),
  NorthAmericaNorthEast2("northamerica-northeast2"),
  UsWest2("us-west2"),
  UsWest3("us-west3"),
  UsWest4("us-west4");

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
