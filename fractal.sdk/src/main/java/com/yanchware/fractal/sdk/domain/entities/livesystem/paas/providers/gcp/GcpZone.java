package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.gcp;

import com.fasterxml.jackson.annotation.JsonValue;

public enum GcpZone {
  EAST_US1B("us-east1-b"),
  EAST_US1C("us-east1-c"),
  EAST_US1D("us-east1-d"),
  EAST_US4C("us-east4-c"),
  EAST_US4B("us-east4-b"),
  EAST_US4A("us-east4-a"),
  CENTRAL_US1C("us-central1-c"),
  CENTRAL_US1A("us-central1-a"),
  CENTRAL_US1F("us-central1-f"),
  CENTRAL_US1B("us-central1-b"),
  US_WEST1B("us-west1-b"),
  US_WEST1C("us-west1-c"),
  US_WEST1A("us-west1-a"),
  EU_WEST4A("europe-west4-a"),
  EU_WEST4B("europe-west4-b"),
  EU_WEST4C("europe-west4-c"),
  EU_WEST1B("europe-west1-b"),
  EU_WEST1D("europe-west1-d"),
  EU_WEST1C("europe-west1-c"),
  EU_WEST3C("europe-west3-c"),
  EU_WEST3A("europe-west3-a"),
  EU_WEST3B("europe-west3-b"),
  EU_WEST2C("europe-west2-c"),
  EU_WEST2B("europe-west2-b"),
  EU_WEST2A("europe-west2-a"),
  EAST_ASIA1B("asia-east1-b"),
  EAST_ASIA1A("asia-east1-a"),
  EAST_ASIA1C("asia-east1-c"),
  SOUTHEAST_ASIA1B("asia-southeast1-b"),
  SOUTHEAST_ASIA1A("asia-southeast1-a"),
  SOUTHEAST_ASIA1C("asia-southeast1-c"),
  ASIA_NORTHEAST("asia-northeast1-b"),
  ASIA_NORTHEAST1C("asia-northeast1-c"),
  ASIA_NORTHEAST1A("asia-northeast1-a"),
  ASIA_SOUTH1C("asia-south1-c"),
  ASIA_SOUTH1B("asia-south1-b"),
  ASIA_SOUTH1A("asia-south1-a"),
  AUSTRALIA_SOUTHEAST1B("australia-southeast1-b"),
  AUSTRALIA_SOUTHEAST1C("australia-southeast1-c"),
  AUSTRALIA_SOUTHEAST1A("australia-southeast1-a"),
  SOUTHAMERICA_EAST1B("southamerica-east1-b"),
  SOUTHAMERICA_EAST1C("southamerica-east1-c"),
  SOUTHAMERICA_EAST1A("southamerica-east1-a"),
  EAST_ASIA2A("asia-east2-a"),
  EAST_ASIA2B("asia-east2-b"),
  EAST_ASIA2C("asia-east2-c"),
  ASIA_NORTHEAST2A("asia-northeast2-a"),
  ASIA_NORTHEAST2B("asia-northeast2-b"),
  ASIA_NORTHEAST2C("asia-northeast2-c"),
  ASIA_NORTHEAST3A("asia-northeast3-a"),
  ASIA_NORTHEAST3B("asia-northeast3-b"),
  ASIA_NORTHEAST3C("asia-northeast3-c"),
  ASIA_SOUTH2A("asia-south2-a"),
  ASIA_SOUTH2B("asia-south2-b"),
  ASIA_SOUTH2C("asia-south2-c"),
  SOUTHEAST_ASIA2A("asia-southeast2-a"),
  SOUTHEAST_ASIA2B("asia-southeast2-b"),
  SOUTHEAST_ASIA2C("asia-southeast2-c"),
  AUSTRALIA_SOUTHEAST2A("australia-southeast2-a"),
  AUSTRALIA_SOUTHEAST2B("australia-southeast2-b"),
  AUSTRALIA_SOUTHEAST2C("australia-southeast2-c"),
  EU_CENTRAL2A("europe-central2-a"),
  EU_CENTRAL2B("europe-central2-b"),
  EU_CENTRAL2C("europe-central2-c"),
  EU_NORTH1A("europe-north1-a"),
  EU_NORTH1B("europe-north1-b"),
  EU_NORTH1C("europe-north1-c"),
  EU_WEST6A("europe-west6-a"),
  EU_WEST6B("europe-west6-b"),
  EU_WEST6C("europe-west6-c"),
  NORTHAMERICA_NORTHEAST1A("northamerica-northeast1-a"),
  NORTHAMERICA_NORTHEAST1B("northamerica-northeast1-b"),
  NORTHAMERICA_NORTHEAST1C("northamerica-northeast1-c"),
  NORTHAMERICA_NORTHEAST2A("northamerica-northeast2-a"),
  NORTHAMERICA_NORTHEAST2B("northamerica-northeast2-b"),
  NORTHAMERICA_NORTHEAST2C("northamerica-northeast2-c"),
  US_WEST2A("us-west2-a"),
  US_WEST2B("us-west2-b"),
  US_WEST2C("us-west2-c"),
  US_WEST3A("us-west3-a"),
  US_WEST3B("us-west3-b"),
  US_WEST3C("us-west3-c"),
  US_WEST4A("us-west4-a"),
  US_WEST4B("us-west4-b"),
  US_WEST4C("us-west4-c");

  private final String id;

  private GcpZone(final String id) {
    this.id = id;
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
