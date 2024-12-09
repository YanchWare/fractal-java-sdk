package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsRegion;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AwsRegionTest {

  @Test
  public void awsActionConstants_shouldNotBeBlank() {
    AwsRegion.values().forEach(x ->
            assertThat(x.toString()).isNotBlank());
  }

  @Test
  public void fromString_shouldReturnCorrespondingAwsAction() {
    assertThat(AwsRegion.fromString("us-east-1"))
        .as("fromString should return US_EAST_1 for 'us-east-1'")
        .isEqualTo(AwsRegion.US_EAST_1);
    assertThat(AwsRegion.fromString("us-east-1"))
            .as("fromString should return US_EAST_1 for 'us-east-1'")
            .isEqualTo(AwsRegion.US_EAST_1);

    assertThat(AwsRegion.fromString("us-east-2"))
            .as("fromString should return US_EAST_2 for 'us-east-2'")
            .isEqualTo(AwsRegion.US_EAST_2);

    assertThat(AwsRegion.fromString("us-west-1"))
            .as("fromString should return US_WEST_1 for 'us-west-1'")
            .isEqualTo(AwsRegion.US_WEST_1);

    assertThat(AwsRegion.fromString("us-west-2"))
            .as("fromString should return US_WEST_2 for 'us-west-2'")
            .isEqualTo(AwsRegion.US_WEST_2);

    assertThat(AwsRegion.fromString("af-south-1"))
            .as("fromString should return AF_SOUTH_1 for 'af-south-1'")
            .isEqualTo(AwsRegion.AF_SOUTH_1);

    assertThat(AwsRegion.fromString("ap-east-1"))
            .as("fromString should return AP_EAST_1 for 'ap-east-1'")
            .isEqualTo(AwsRegion.AP_EAST_1);

    assertThat(AwsRegion.fromString("ap-south-2"))
            .as("fromString should return AP_SOUTH_2 for 'ap-south-2'")
            .isEqualTo(AwsRegion.AP_SOUTH_2);

    assertThat(AwsRegion.fromString("ap-southeast-3"))
            .as("fromString should return AP_SOUTHEAST_3 for 'ap-southeast-3'")
            .isEqualTo(AwsRegion.AP_SOUTHEAST_3);

    assertThat(AwsRegion.fromString("ap-southeast-5"))
            .as("fromString should return AP_SOUTHEAST_5 for 'ap-southeast-5'")
            .isEqualTo(AwsRegion.AP_SOUTHEAST_5);

    assertThat(AwsRegion.fromString("ap-southeast-4"))
            .as("fromString should return AP_SOUTHEAST_4 for 'ap-southeast-4'")
            .isEqualTo(AwsRegion.AP_SOUTHEAST_4);

    assertThat(AwsRegion.fromString("ap-south-1"))
            .as("fromString should return AP_SOUTH_1 for 'ap-south-1'")
            .isEqualTo(AwsRegion.AP_SOUTH_1);

    assertThat(AwsRegion.fromString("ap-northeast-3"))
            .as("fromString should return AP_NORTHEAST_3 for 'ap-northeast-3'")
            .isEqualTo(AwsRegion.AP_NORTHEAST_3);

    assertThat(AwsRegion.fromString("ap-northeast-2"))
            .as("fromString should return AP_NORTHEAST_2 for 'ap-northeast-2'")
            .isEqualTo(AwsRegion.AP_NORTHEAST_2);

    assertThat(AwsRegion.fromString("ap-southeast-1"))
            .as("fromString should return AP_SOUTHEAST_1 for 'ap-southeast-1'")
            .isEqualTo(AwsRegion.AP_SOUTHEAST_1);

    assertThat(AwsRegion.fromString("ap-southeast-2"))
            .as("fromString should return AP_SOUTHEAST_2 for 'ap-southeast-2'")
            .isEqualTo(AwsRegion.AP_SOUTHEAST_2);

    assertThat(AwsRegion.fromString("ap-northeast-1"))
            .as("fromString should return AP_NORTHEAST_1 for 'ap-northeast-1'")
            .isEqualTo(AwsRegion.AP_NORTHEAST_1);

    assertThat(AwsRegion.fromString("ca-central-1"))
            .as("fromString should return CA_CENTRAL_1 for 'ca-central-1'")
            .isEqualTo(AwsRegion.CA_CENTRAL_1);

    assertThat(AwsRegion.fromString("ca-west-1"))
            .as("fromString should return CA_WEST_1 for 'ca-west-1'")
            .isEqualTo(AwsRegion.CA_WEST_1);

    assertThat(AwsRegion.fromString("cn-north-1"))
            .as("fromString should return CN_NORTH_1 for 'cn-north-1'")
            .isEqualTo(AwsRegion.CN_NORTH_1);

    assertThat(AwsRegion.fromString("cn-northwest-1"))
            .as("fromString should return CN_NORTHWEST_1 for 'cn-northwest-1'")
            .isEqualTo(AwsRegion.CN_NORTHWEST_1);

    assertThat(AwsRegion.fromString("eu-central-1"))
            .as("fromString should return EU_CENTRAL_1 for 'eu-central-1'")
            .isEqualTo(AwsRegion.EU_CENTRAL_1);

    assertThat(AwsRegion.fromString("eu-west-1"))
            .as("fromString should return EU_WEST_1 for 'eu-west-1'")
            .isEqualTo(AwsRegion.EU_WEST_1);

    assertThat(AwsRegion.fromString("eu-west-2"))
            .as("fromString should return EU_WEST_2 for 'eu-west-2'")
            .isEqualTo(AwsRegion.EU_WEST_2);

    assertThat(AwsRegion.fromString("eu-south-1"))
            .as("fromString should return EU_SOUTH_1 for 'eu-south-1'")
            .isEqualTo(AwsRegion.EU_SOUTH_1);

    assertThat(AwsRegion.fromString("eu-west-3"))
            .as("fromString should return EU_WEST_3 for 'eu-west-3'")
            .isEqualTo(AwsRegion.EU_WEST_3);

    assertThat(AwsRegion.fromString("eu-south-2"))
            .as("fromString should return EU_SOUTH_2 for 'eu-south-2'")
            .isEqualTo(AwsRegion.EU_SOUTH_2);

    assertThat(AwsRegion.fromString("eu-north-1"))
            .as("fromString should return EU_NORTH_1 for 'eu-north-1'")
            .isEqualTo(AwsRegion.EU_NORTH_1);

    assertThat(AwsRegion.fromString("eu-central-2"))
            .as("fromString should return EU_CENTRAL_2 for 'eu-central-2'")
            .isEqualTo(AwsRegion.EU_CENTRAL_2);

    assertThat(AwsRegion.fromString("il-central-1"))
            .as("fromString should return IL_CENTRAL_1 for 'il-central-1'")
            .isEqualTo(AwsRegion.IL_CENTRAL_1);

    assertThat(AwsRegion.fromString("me-south-1"))
            .as("fromString should return ME_SOUTH_1 for 'me-south-1'")
            .isEqualTo(AwsRegion.ME_SOUTH_1);

    assertThat(AwsRegion.fromString("me-central-1"))
            .as("fromString should return ME_CENTRAL_1 for 'me-central-1'")
            .isEqualTo(AwsRegion.ME_CENTRAL_1);

    assertThat(AwsRegion.fromString("sa-east-1"))
            .as("fromString should return SA_EAST_1 for 'sa-east-1'")
            .isEqualTo(AwsRegion.SA_EAST_1);
  }

  @Test
  public void valuesMethod_shouldContainAllAwsActionsWithCorrectSize() {
    Collection<AwsRegion> values = AwsRegion.values();
    
    assertThat(values)
        .as("Values should contain all specified AwsRegions")
        .contains(
            AwsRegion.US_EAST_1,
            AwsRegion.US_EAST_2,
            AwsRegion.US_WEST_1,
            AwsRegion.US_WEST_2,
            AwsRegion.AF_SOUTH_1,
            AwsRegion.AP_EAST_1,
            AwsRegion.AP_SOUTH_2,
            AwsRegion.AP_SOUTHEAST_3,
            AwsRegion.AP_SOUTHEAST_5,
            AwsRegion.AP_SOUTHEAST_4,
            AwsRegion.AP_SOUTH_1,
            AwsRegion.AP_NORTHEAST_3,
            AwsRegion.AP_NORTHEAST_2,
            AwsRegion.AP_SOUTHEAST_1,
            AwsRegion.AP_SOUTHEAST_2,
            AwsRegion.AP_NORTHEAST_1,
            AwsRegion.CA_CENTRAL_1,
            AwsRegion.CA_WEST_1,
            AwsRegion.CN_NORTH_1,
            AwsRegion.CN_NORTHWEST_1,
            AwsRegion.EU_CENTRAL_1,
            AwsRegion.EU_WEST_1,
            AwsRegion.EU_WEST_2,
            AwsRegion.EU_SOUTH_1,
            AwsRegion.EU_WEST_3,
            AwsRegion.EU_SOUTH_2,
            AwsRegion.EU_NORTH_1,
            AwsRegion.EU_CENTRAL_2,
            AwsRegion.IL_CENTRAL_1,
            AwsRegion.ME_SOUTH_1,
            AwsRegion.ME_CENTRAL_1,
            AwsRegion.SA_EAST_1);
  }
}