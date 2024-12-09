package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * Choose the AWS region that's right for you and your customers
 */
public class AwsRegion extends ExtendableEnum<AwsRegion> {
    public static final AwsRegion US_EAST_1 = fromString("us-east-1");
    public static final AwsRegion US_EAST_2 = fromString("us-east-2");
    public static final AwsRegion US_WEST_1 = fromString("us-west-1");
    public static final AwsRegion US_WEST_2 = fromString("us-west-2");
    public static final AwsRegion AF_SOUTH_1 = fromString("af-south-1");
    public static final AwsRegion AP_EAST_1 = fromString("ap-east-1");
    public static final AwsRegion AP_SOUTH_2 = fromString("ap-south-2");
    public static final AwsRegion AP_SOUTHEAST_3 = fromString("ap-southeast-3");
    public static final AwsRegion AP_SOUTHEAST_5 = fromString("ap-southeast-5");
    public static final AwsRegion AP_SOUTHEAST_4 = fromString("ap-southeast-4");
    public static final AwsRegion AP_SOUTH_1 = fromString("ap-south-1");
    public static final AwsRegion AP_NORTHEAST_3 = fromString("ap-northeast-3");
    public static final AwsRegion AP_NORTHEAST_2 = fromString("ap-northeast-2");
    public static final AwsRegion AP_SOUTHEAST_1 = fromString("ap-southeast-1");
    public static final AwsRegion AP_SOUTHEAST_2 = fromString("ap-southeast-2");
    public static final AwsRegion AP_NORTHEAST_1 = fromString("ap-northeast-1");
    public static final AwsRegion CA_CENTRAL_1 = fromString("ca-central-1");
    public static final AwsRegion CA_WEST_1 = fromString("ca-west-1");
    public static final AwsRegion CN_NORTH_1 = fromString("cn-north-1");
    public static final AwsRegion CN_NORTHWEST_1 = fromString("cn-northwest-1");
    public static final AwsRegion EU_CENTRAL_1 = fromString("eu-central-1");
    public static final AwsRegion EU_WEST_1 = fromString("eu-west-1");
    public static final AwsRegion EU_WEST_2 = fromString("eu-west-2");
    public static final AwsRegion EU_SOUTH_1 = fromString("eu-south-1");
    public static final AwsRegion EU_WEST_3 = fromString("eu-west-3");
    public static final AwsRegion EU_SOUTH_2 = fromString("eu-south-2");
    public static final AwsRegion EU_NORTH_1 = fromString("eu-north-1");
    public static final AwsRegion EU_CENTRAL_2 = fromString("eu-central-2");
    public static final AwsRegion IL_CENTRAL_1 = fromString("il-central-1");
    public static final AwsRegion ME_SOUTH_1 = fromString("me-south-1");
    public static final AwsRegion ME_CENTRAL_1 = fromString("me-central-1");
    public static final AwsRegion SA_EAST_1 = fromString("sa-east-1");


    /**
     * Creates or finds a AwsRegion from its string representation.
     *
     * @param name a name to look for.
     * @return the corresponding AwsRegion.
     */
    @JsonCreator
    public static AwsRegion fromString(String name) {
        return fromString(name, AwsRegion.class);
    }

    /**
     * Gets known AwsRegion values.
     *
     * @return known AwsRegion values.
     */
    public static Collection<AwsRegion> values() {
        return values(AwsRegion.class);
    }
}
