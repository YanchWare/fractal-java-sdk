package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws;

import com.yanchware.fractal.sdk.domain.livesystem.paas.KubernetesCluster;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.SubnetUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidAlphanumericsUnderscoresHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;

@Getter
@Setter
public class AwsElasticKubernetesService extends KubernetesCluster implements AwsResourceEntity {
    private final static String REGION_IS_NULL = "[AwsElasticKuernetesService Validation] Region is not specified and it is required";
    private final static String NAME_NOT_VALID = "[KubernetesCluster Validation] The name can contain only letters, numbers, underscores, and hyphens. The name must start and end with a letter or number and must be between 1 and 63 characters long";

    private AwsRegion awsRegion;
    private Map<String, String> tags;
    private String name;
    private String vpcCidrBlock;
    private List<String> privateSubnetCidrs;
    private Integer desiredAvailabilityZoneCount;

    @Override
    public ProviderType getProvider() {
        return ProviderType.AWS;
    }

    public static AwsElasticKubernetesServiceBuilder builder() {
        return new AwsElasticKubernetesServiceBuilder();
    }

    public static class AwsElasticKubernetesServiceBuilder extends Builder<AwsElasticKubernetesService, AwsElasticKubernetesServiceBuilder> {
        @Override
        protected AwsElasticKubernetesService createComponent() {
            return new AwsElasticKubernetesService();
        }

        @Override
        protected AwsElasticKubernetesServiceBuilder getBuilder() {
            return this;
        }

        public AwsElasticKubernetesServiceBuilder withRegion(AwsRegion region) {
            component.setAwsRegion(region);
            return builder;
        }

        public AwsElasticKubernetesServiceBuilder withName(String name) {
            component.setName(name);
            return builder;
        }

        public AwsElasticKubernetesServiceBuilder withTags(Map<String, String> tags) {
            component.setTags(tags);
            return builder;
        }

        public AwsElasticKubernetesServiceBuilder withTag(String key, String value) {
            if (component.getTags() == null) {
                withTags(new HashMap<>());
            }

            component.getTags().put(key, value);
            return builder;
        }

        /**
         * Sets the VPC CIDR block for the EKS cluster.
         * <p>
         * Must be a valid CIDR block between /16 and /24 (inclusive).
         * If not set, the default {@code 172.33.0.0/16} will be used.
         *
         * @param cidrBlock the VPC CIDR block (e.g., {@code "10.0.0.0/16"})
         * @return the builder instance
         */
        public AwsElasticKubernetesServiceBuilder withVpcCidrBlock(String cidrBlock) {
            component.setVpcCidrBlock(cidrBlock);
            return builder;
        }

        /**
         * Sets the private subnet CIDRs for the EKS cluster.
         * <p>
         * Each CIDR must:
         * <ul>
         *   <li>Be within the configured VPC CIDR block</li>
         *   <li>Have a longer prefix than the VPC CIDR (e.g., /20 if VPC is /16)</li>
         *   <li>Not overlap with other subnets</li>
         * </ul>
         * <br>
         * <strong>Default (if not explicitly set):</strong>
         * Derived from the VPC CIDR prefix:
         * <ul>
         *   <li>{@code <vpcPrefix>.128.0/20}</li>
         *   <li>{@code <vpcPrefix>.144.0/20}</li>
         *   <li>{@code <vpcPrefix>.160.0/20}</li>
         * </ul>
         *
         * @param cidrs list of private subnet CIDRs
         * @return the builder instance
         */
        public AwsElasticKubernetesServiceBuilder withPrivateSubnetCidrs(List<String> cidrs) {
            component.setPrivateSubnetCidrs(cidrs);
            return builder;
        }

        /**
         * Sets how many availability zones the EKS cluster should span.
         * <p>
         * Must be a value between 1 and 3 (inclusive), in accordance with AWS best practices.
         * If set, the number of private subnets provided must match this count.
         * <br>
         * <strong>Default:</strong> 3 availability zones
         *
         * @param count number of desired availability zones
         * @return the builder instance
         */
        public AwsElasticKubernetesServiceBuilder withDesiredAvailabilityZoneCount(int count) {
            component.setDesiredAvailabilityZoneCount(count);
            return builder;
        }
    }

    @Override
    public Collection<String> validate() {

        Collection<String> errors = super.validate();
        errors.addAll(AwsResourceEntity.validateAwsResourceEntity(this, "Kubernetes Service"));

        if(StringUtils.isNotBlank(name)) {
            var isAlphaNumerics = isValidAlphanumericsUnderscoresHyphens(name);
            var hasValidLengths = isValidStringLength(name, 1, 63);
            if(!isAlphaNumerics || !hasValidLengths) {
                errors.add(NAME_NOT_VALID);
            }
        }

        if (awsRegion == null) {
            errors.add(REGION_IS_NULL);
        }

        if (StringUtils.isNotBlank(vpcCidrBlock)) {
            try {
                var vpcUtils = new SubnetUtils(vpcCidrBlock);
                int vpcPrefix = Integer.parseInt(vpcCidrBlock.split("/")[1]);

                if (vpcPrefix < 16 || vpcPrefix > 24) {
                    errors.add("[AwsElasticKubernetesService Validation] vpcCidrBlock mask must be between /16 and /24");
                }

                if (privateSubnetCidrs != null) {
                    for (String subnet : privateSubnetCidrs) {
                        try {
                            var subnetUtils = new SubnetUtils(subnet);
                            int subnetPrefix = Integer.parseInt(subnet.split("/")[1]);

                            if (subnetPrefix <= vpcPrefix) {
                                errors.add(String.format(
                                        "[AwsElasticKubernetesService Validation] Subnet %s mask must be greater than VPC's (%s)", subnet, vpcCidrBlock));
                            }

                            if (!vpcUtils.getInfo().isInRange(subnetUtils.getInfo().getAddress())) {
                                errors.add(String.format(
                                        "[AwsElasticKubernetesService Validation] Subnet %s is not within the VPC CIDR range %s",
                                        subnet, vpcCidrBlock));
                            }
                        } catch (IllegalArgumentException e) {
                            errors.add(String.format("[AwsElasticKubernetesService Validation] Invalid CIDR format: %s", subnet));
                        }
                    }

                    // Optional: check subnet CIDRs do not overlap
                    for (int i = 0; i < privateSubnetCidrs.size(); i++) {
                        for (int j = i + 1; j < privateSubnetCidrs.size(); j++) {
                            if (cidrOverlaps(privateSubnetCidrs.get(i), privateSubnetCidrs.get(j))) {
                                errors.add(String.format(
                                        "[AwsElasticKubernetesService Validation] Subnet CIDRs %s and %s overlap",
                                        privateSubnetCidrs.get(i), privateSubnetCidrs.get(j)));
                            }
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                errors.add("[AwsElasticKubernetesService Validation] vpcCidrBlock is not a valid CIDR");
            }
        }

        if (desiredAvailabilityZoneCount != null) {
            if (desiredAvailabilityZoneCount <= 0) {
                errors.add("[AwsElasticKubernetesService Validation] desiredAvailabilityZoneCount must be greater than 0");
            }

            if (desiredAvailabilityZoneCount > 3) {
                errors.add("[AwsElasticKubernetesService Validation] desiredAvailabilityZoneCount cannot be greater than 3 (AWS best practice)");
            }

            if (privateSubnetCidrs != null && privateSubnetCidrs.size() != desiredAvailabilityZoneCount) {
                errors.add(String.format(
                        "[AwsElasticKubernetesService Validation] privateSubnetCidrs size (%d) must match desiredAvailabilityZoneCount (%d)",
                        privateSubnetCidrs.size(), desiredAvailabilityZoneCount
                ));
            }
        }

        return errors;
    }

    private boolean cidrOverlaps(String cidr1, String cidr2) {
        SubnetUtils subnet1 = new SubnetUtils(cidr1);
        SubnetUtils subnet2 = new SubnetUtils(cidr2);

        var info1 = subnet1.getInfo();
        var info2 = subnet2.getInfo();

        return !(ipLessThan(info1.getHighAddress(), info2.getLowAddress())
                || ipLessThan(info2.getHighAddress(), info1.getLowAddress()));
    }

    private boolean ipLessThan(String ip1, String ip2) {
        String[] a = ip1.split("\\.");
        String[] b = ip2.split("\\.");

        for (int i = 0; i < 4; i++) {
            int diff = Integer.parseInt(a[i]) - Integer.parseInt(b[i]);
            if (diff < 0) return true;
            if (diff > 0) return false;
        }
        return false; // equal IPs means not less than
    }
}
