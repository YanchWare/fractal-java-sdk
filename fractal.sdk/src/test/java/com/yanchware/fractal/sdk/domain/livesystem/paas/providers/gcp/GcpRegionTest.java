package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class GcpRegionTest {

  @Test
  public void gcpActionConstants_shouldNotBeBlank() {
    GcpRegion.values().forEach(x ->
            assertThat(x.toString()).isNotBlank());
  }

  @Test
  public void fromString_shouldReturnCorrespondingGcpAction() {
    assertThat(GcpRegion.fromString("africa-south1"))
        .as("fromString should return AFRICA_SOUTH1 for 'africa-south1'")
        .isEqualTo(GcpRegion.AFRICA_SOUTH1);

    assertThat(GcpRegion.fromString("asia-east1"))
            .as("fromString should return ASIA_EAST1 for 'asia-east1'")
            .isEqualTo(GcpRegion.ASIA_EAST1);

    assertThat(GcpRegion.fromString("asia-east2"))
            .as("fromString should return ASIA_EAST2 for 'asia-east2'")
            .isEqualTo(GcpRegion.ASIA_EAST2);

    assertThat(GcpRegion.fromString("asia-northeast1-b"))
            .as("fromString should return ASIA_NORTHEAST1 for 'asia-northeast1-b'")
            .isEqualTo(GcpRegion.ASIA_NORTHEAST1);

    assertThat(GcpRegion.fromString("asia-northeast2"))
            .as("fromString should return ASIA_NORTHEAST2 for 'asia-northeast2'")
            .isEqualTo(GcpRegion.ASIA_NORTHEAST2);

    assertThat(GcpRegion.fromString("asia-northeast3"))
            .as("fromString should return ASIA_NORTHEAST3 for 'asia-northeast3'")
            .isEqualTo(GcpRegion.ASIA_NORTHEAST3);

    assertThat(GcpRegion.fromString("asia-south1"))
            .as("fromString should return ASIA_SOUTH1 for 'asia-south1'")
            .isEqualTo(GcpRegion.ASIA_SOUTH1);

    assertThat(GcpRegion.fromString("asia-south2"))
            .as("fromString should return ASIA_SOUTH2 for 'asia-south2'")
            .isEqualTo(GcpRegion.ASIA_SOUTH2);

    assertThat(GcpRegion.fromString("asia-southeast1"))
            .as("fromString should return ASIA_SOUTHEAST1 for 'asia-southeast1'")
            .isEqualTo(GcpRegion.ASIA_SOUTHEAST1);

    assertThat(GcpRegion.fromString("asia-southeast2"))
            .as("fromString should return ASIA_SOUTHEAST2 for 'asia-southeast2'")
            .isEqualTo(GcpRegion.ASIA_SOUTHEAST2);

    assertThat(GcpRegion.fromString("australia-southeast1"))
            .as("fromString should return AUSTRALIA_SOUTHEAST1 for 'australia-southeast1'")
            .isEqualTo(GcpRegion.AUSTRALIA_SOUTHEAST1);

    assertThat(GcpRegion.fromString("australia-southeast2"))
            .as("fromString should return AUSTRALIA_SOUTHEAST2 for 'australia-southeast2'")
            .isEqualTo(GcpRegion.AUSTRALIA_SOUTHEAST2);

    assertThat(GcpRegion.fromString("europe-central2"))
            .as("fromString should return EUROPE_CENTRAL2 for 'europe-central2'")
            .isEqualTo(GcpRegion.EUROPE_CENTRAL2);

    assertThat(GcpRegion.fromString("europe-north1"))
            .as("fromString should return EUROPE_NORTH1 for 'europe-north1'")
            .isEqualTo(GcpRegion.EUROPE_NORTH1);

    assertThat(GcpRegion.fromString("europe-southwest1"))
            .as("fromString should return EUROPE_SOUTHWEST1 for 'europe-southwest1'")
            .isEqualTo(GcpRegion.EUROPE_SOUTHWEST1);

    assertThat(GcpRegion.fromString("europe-west1"))
            .as("fromString should return EUROPE_WEST1 for 'europe-west1'")
            .isEqualTo(GcpRegion.EUROPE_WEST1);

    assertThat(GcpRegion.fromString("europe-west10"))
            .as("fromString should return EUROPE_WEST10 for 'europe-west10'")
            .isEqualTo(GcpRegion.EUROPE_WEST10);

    assertThat(GcpRegion.fromString("europe-west12"))
            .as("fromString should return EUROPE_WEST12 for 'europe-west12'")
            .isEqualTo(GcpRegion.EUROPE_WEST12);

    assertThat(GcpRegion.fromString("europe-west2"))
            .as("fromString should return EUROPE_WEST2 for 'europe-west2'")
            .isEqualTo(GcpRegion.EUROPE_WEST2);

    assertThat(GcpRegion.fromString("europe-west3"))
            .as("fromString should return EUROPE_WEST3 for 'europe-west3'")
            .isEqualTo(GcpRegion.EUROPE_WEST3);

    assertThat(GcpRegion.fromString("europe-west4"))
            .as("fromString should return EUROPE_WEST4 for 'europe-west4'")
            .isEqualTo(GcpRegion.EUROPE_WEST4);

    assertThat(GcpRegion.fromString("europe-west6"))
            .as("fromString should return EUROPE_WEST6 for 'europe-west6'")
            .isEqualTo(GcpRegion.EUROPE_WEST6);

    assertThat(GcpRegion.fromString("europe-west8"))
            .as("fromString should return EUROPE_WEST8 for 'europe-west8'")
            .isEqualTo(GcpRegion.EUROPE_WEST8);

    assertThat(GcpRegion.fromString("europe-west9"))
            .as("fromString should return EUROPE_WEST9 for 'europe-west9'")
            .isEqualTo(GcpRegion.EUROPE_WEST9);

    assertThat(GcpRegion.fromString("me-central1"))
            .as("fromString should return ME_CENTRAL1 for 'me-central1'")
            .isEqualTo(GcpRegion.ME_CENTRAL1);

    assertThat(GcpRegion.fromString("me-central2"))
            .as("fromString should return ME_CENTRAL2 for 'me-central2'")
            .isEqualTo(GcpRegion.ME_CENTRAL2);

    assertThat(GcpRegion.fromString("me-west1"))
            .as("fromString should return ME_WEST1 for 'me-west1'")
            .isEqualTo(GcpRegion.ME_WEST1);

    assertThat(GcpRegion.fromString("northamerica-northeast1"))
            .as("fromString should return NORTHAMERICA_NORTHEAST1 for 'northamerica-northeast1'")
            .isEqualTo(GcpRegion.NORTHAMERICA_NORTHEAST1);

    assertThat(GcpRegion.fromString("northamerica-northeast2"))
            .as("fromString should return NORTHAMERICA_NORTHEAST2 for 'northamerica-northeast2'")
            .isEqualTo(GcpRegion.NORTHAMERICA_NORTHEAST2);

    assertThat(GcpRegion.fromString("northamerica-south1"))
            .as("fromString should return NORTHAMERICA_SOUTH1 for 'northamerica-south1'")
            .isEqualTo(GcpRegion.NORTHAMERICA_SOUTH1);

    assertThat(GcpRegion.fromString("southamerica-east1"))
            .as("fromString should return SOUTHAMERICA_EAST1 for 'southamerica-east1'")
            .isEqualTo(GcpRegion.SOUTHAMERICA_EAST1);

    assertThat(GcpRegion.fromString("southamerica-west1"))
            .as("fromString should return SOUTHAMERICA_WEST1 for 'southamerica-west1'")
            .isEqualTo(GcpRegion.SOUTHAMERICA_WEST1);

    assertThat(GcpRegion.fromString("us-central1"))
            .as("fromString should return US_CENTRAL1 for 'us-central1'")
            .isEqualTo(GcpRegion.US_CENTRAL1);

    assertThat(GcpRegion.fromString("us-east1"))
            .as("fromString should return US_EAST1 for 'us-east1'")
            .isEqualTo(GcpRegion.US_EAST1);

    assertThat(GcpRegion.fromString("us-east4"))
            .as("fromString should return US_EAST4 for 'us-east4'")
            .isEqualTo(GcpRegion.US_EAST4);

    assertThat(GcpRegion.fromString("us-east5"))
            .as("fromString should return US_EAST5 for 'us-east5'")
            .isEqualTo(GcpRegion.US_EAST5);

    assertThat(GcpRegion.fromString("us-south1"))
            .as("fromString should return US_SOUTH5 for 'us-south1'")
            .isEqualTo(GcpRegion.US_SOUTH1);

    assertThat(GcpRegion.fromString("us-west1"))
            .as("fromString should return US_WEST1 for 'us-west1'")
            .isEqualTo(GcpRegion.US_WEST1);

    assertThat(GcpRegion.fromString("us-west2"))
            .as("fromString should return US_WEST2 for 'us-west2'")
            .isEqualTo(GcpRegion.US_WEST2);

    assertThat(GcpRegion.fromString("us-west3"))
            .as("fromString should return US_WEST3 for 'us-west3'")
            .isEqualTo(GcpRegion.US_WEST3);

    assertThat(GcpRegion.fromString("us-west4"))
            .as("fromString should return US_WEST4 for 'us-west4'")
            .isEqualTo(GcpRegion.US_WEST4);
  }

  @Test
  public void valuesMethod_shouldContainAllGcpActionsWithCorrectSize() {
    Collection<GcpRegion> values = GcpRegion.values();
    
    assertThat(values)
        .as("Values should contain all specified GcpRegions")
        .contains(
            GcpRegion.AFRICA_SOUTH1,
            GcpRegion.AFRICA_SOUTH1,
            GcpRegion.ASIA_EAST1,
            GcpRegion.ASIA_EAST2,
            GcpRegion.ASIA_NORTHEAST1,
            GcpRegion.ASIA_NORTHEAST2,
            GcpRegion.ASIA_NORTHEAST3,
            GcpRegion.ASIA_SOUTH1,
            GcpRegion.ASIA_SOUTH2,
            GcpRegion.ASIA_SOUTHEAST1,
            GcpRegion.ASIA_SOUTHEAST2,
            GcpRegion.AUSTRALIA_SOUTHEAST1,
            GcpRegion.AUSTRALIA_SOUTHEAST2,
            GcpRegion.EUROPE_CENTRAL2,
            GcpRegion.EUROPE_NORTH1,
            GcpRegion.EUROPE_SOUTHWEST1,
            GcpRegion.EUROPE_WEST1,
            GcpRegion.EUROPE_WEST10,
            GcpRegion.EUROPE_WEST12,
            GcpRegion.EUROPE_WEST2,
            GcpRegion.EUROPE_WEST3,
            GcpRegion.EUROPE_WEST4,
            GcpRegion.EUROPE_WEST6,
            GcpRegion.EUROPE_WEST8,
            GcpRegion.EUROPE_WEST9,
            GcpRegion.ME_CENTRAL1,
            GcpRegion.ME_CENTRAL2,
            GcpRegion.ME_WEST1,
            GcpRegion.NORTHAMERICA_NORTHEAST1,
            GcpRegion.NORTHAMERICA_NORTHEAST2,
            GcpRegion.NORTHAMERICA_SOUTH1,
            GcpRegion.SOUTHAMERICA_EAST1,
            GcpRegion.SOUTHAMERICA_WEST1,
            GcpRegion.US_CENTRAL1,
            GcpRegion.US_EAST1,
            GcpRegion.US_EAST4,
            GcpRegion.US_EAST5,
            GcpRegion.US_SOUTH1,
            GcpRegion.US_WEST1,
            GcpRegion.US_WEST2,
            GcpRegion.US_WEST3,
            GcpRegion.US_WEST4);
  }
}