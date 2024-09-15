package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * Choose the AWS region that's right for you and your customers
 */
public class AwsRegion extends ExtendableEnum<AwsRegion> {

    /**
     * US East (N. Virginia)
     */
    public static final AwsRegion US_EAST_1 = fromString("us-east-1");

    /**
     * US East (Ohio)
     */
    public static final AwsRegion US_EAST_2 = fromString("us-east-2");

    /**
     * US West (N. California)
     */
    public static final AwsRegion US_WEST_1 = fromString("us-west-1");

    /**
     * US West (Oregon)
     */
    public static final AwsRegion US_WEST_2 = fromString("us-west-2");

    /**
     * Africa (Cape Town)
     */
    public static final AwsRegion AF_SOUTH_1 = fromString("af-south-1");

    /**
     * Asia Pacific (Hong Kong)
     */
    public static final AwsRegion AP_EAST_1 = fromString("ap-east-1");

    /**
     * Asia Pacific (Hyderabad)
     */
    public static final AwsRegion AP_SOUTH_2 = fromString("ap-south-2");

    /**
     * Asia Pacific (Jakarta)
     */
    public static final AwsRegion AP_SOUTHEAST_3 = fromString("ap-southeast-3");

    /**
     * Asia Pacific (Malaysia)
     */
    public static final AwsRegion AP_SOUTHEAST_5 = fromString("ap-southeast-5");

    /**
     * Asia Pacific (Melbourne)
     */
    public static final AwsRegion AP_SOUTHEAST_4 = fromString("ap-southeast-4");

    /**
     * Asia Pacific (Mumbai)
     */
    public static final AwsRegion AP_SOUTH_1 = fromString("ap-south-1");

    /**
     * Asia Pacific (Osaka)
     */
    public static final AwsRegion AP_NORTHEAST_3 = fromString("ap-northeast-3");

    /**
     * Asia Pacific (Seoul)
     */
    public static final AwsRegion AP_NORTHEAST_2 = fromString("ap-northeast-2");

    /**
     * Asia Pacific (Singapore)
     */
    public static final AwsRegion AP_SOUTHEAST_1 = fromString("ap-southeast-1");

    /**
     * Asia Pacific (Sydney)
     */
    public static final AwsRegion AP_SOUTHEAST_2 = fromString("ap-southeast-2");

    /**
     * Asia Pacific (Tokyo)
     */
    public static final AwsRegion AP_NORTHEAST_1 = fromString("ap-northeast-1");

    /**
     * Canada (Central)
     */
    public static final AwsRegion CA_CENTRAL_1 = fromString("ca-central-1");

    /**
     * Canada West (Calgary)
     */
    public static final AwsRegion CA_WEST_1 = fromString("ca-west-1");

    /**
     * China (Beijing)
     */
    public static final AwsRegion CA_NORTH_1 = fromString("cn-north-1");

    /**
     * China (Ningxia)
     */
    public static final AwsRegion CN_NORTHWEST_1 = fromString("cn-northwest-1");

    /**
     * Europe (Frankfurt)
     */
    public static final AwsRegion EU_CENTRAL_1 = fromString("eu-central-1");

    /**
     * Europe (Ireland)
     */
    public static final AwsRegion EU_WEST_1 = fromString("eu-west-1");

    /**
     * Europe (London)
     */
    public static final AwsRegion EU_WEST_2 = fromString("eu-west-2");

    /**
     * Europe (Milan)
     */
    public static final AwsRegion EU_SOUTH_1 = fromString("eu-south-1");

    /**
     * Europe (Paris)
     */
    public static final AwsRegion EU_WEST_3 = fromString("eu-west-3");

    /**
     * Europe (Spain)
     */
    public static final AwsRegion EU_SOUTH_2 = fromString("eu-south-2");

    /**
     * Europe (Stockholm)
     */
    public static final AwsRegion EU_NORTH_1 = fromString("eu-north-1");

    /**
     * Europe (Zurich)
     */
    public static final AwsRegion EU_CENTRAL_2 = fromString("eu-central-2");

    /**
     * Israel (Tel Aviv)
     */
    public static final AwsRegion IL_CENTRAL_1 = fromString("il-central-1");

    /**
     * Middle East (Bahrain)
     */
    public static final AwsRegion ME_SOUTH_1 = fromString("me-south-1");

    /**
     * Middle East (UAE)
     */
    public static final AwsRegion ME_CENTRAL_1 = fromString("me-central-1");

    /**
     * South America (São Paulo)
     */
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
