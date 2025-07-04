package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_KUBERNETES;
import static com.yanchware.fractal.sdk.utils.TestUtils.getDefaultEks;
import static org.assertj.core.api.Assertions.*;

public class AwsElasticKubernetesServiceTest {

    @Test
    public void noValidationErrors_when_eksHasRequiredFields() {
        var eks = getDefaultEks().build();
        assertThat(eks.validate()).isEmpty();
    }

    @Test
    public void exceptionThrown_when_eksCreatedWithNullId() {
        assertThatThrownBy(() -> getDefaultEks().withId("").build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("A valid component id cannot be null, empty or contain spaces");
    }

    @Test
    public void exceptionThrown_when_eksCreatedWithNullRegion() {
        assertThatThrownBy(() -> getDefaultEks().withRegion(null).build()).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Region is not specified and it is required");
    }

    @Test
    public void typeIsKubernetes_when_eksIsBuiltWithoutSpecifyType() {
        var eksBuilder = getDefaultEks();
        assertThat(eksBuilder.build().getType()).isEqualTo(PAAS_KUBERNETES);
        assertThatCode(eksBuilder::build).doesNotThrowAnyException();
    }

    @Test
    public void withName_setsNameCorrectly() {
        var eks = getDefaultEks()
                .withName("valid-name_123")
                .build();

        assertThat(eks.getName()).isEqualTo("valid-name_123");
    }

    @Test
    public void withName_allowsNullOrEmptyName() {
        var eks = getDefaultEks()
                .withName(null)
                .build();

        assertThat(eks.getName()).isNull();

        eks = getDefaultEks()
                .withName("")
                .build();

        assertThat(eks.getName()).isEmpty();
    }


    @Test
    public void validate_addsError_whenNameIsInvalid() {
        var eks = getDefaultEks()
                .withName("Invalid*Name!");


        assertThatThrownBy(eks::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The name can contain only letters, numbers, underscores, and hyphens");
    }

    @Test
    public void validate_addsError_whenNameIsTooLong() {
        var longName = "a".repeat(64); // 64 chars > 63 max

        var eks = getDefaultEks()
                .withName(longName);

        assertThatThrownBy(eks::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must be between 1 and 63 characters long");
    }

    @Test
    public void withTags_setsTagsMap() {
        var tags = new HashMap<String, String>();
        tags.put("env", "prod");
        tags.put("team", "devops");

        var eks = getDefaultEks()
                .withTags(tags)
                .build();

        assertThat(eks.getTags())
                .isNotNull()
                .containsEntry("env", "prod")
                .containsEntry("team", "devops");
    }

    @Test
    public void withTag_addsSingleTagToExistingTags() {
        var eksBuilder = getDefaultEks()
                .withTags(new HashMap<>(Map.of("env", "prod")));

        eksBuilder.withTag("team", "devops");
        var eks = eksBuilder.build();

        assertThat(eks.getTags())
                .isNotNull()
                .containsEntry("env", "prod")
                .containsEntry("team", "devops");
    }

    @Test
    public void withTag_initializesTagsMapIfNullAndAddsTag() {
        var eksBuilder = getDefaultEks()
                .withTags(null); // simulate starting from no tags

        eksBuilder.withTag("env", "prod");
        var eks = eksBuilder.build();

        assertThat(eks.getTags())
                .isNotNull()
                .hasSize(1)
                .containsEntry("env", "prod");
    }

    @Test
    public void exceptionThrown_when_desiredAvailabilityZoneCountInvalid() {
        assertThatThrownBy(() -> getDefaultEks().withDesiredAvailabilityZoneCount(0).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("desiredAvailabilityZoneCount must be greater than 0");

        assertThatThrownBy(() -> getDefaultEks().withDesiredAvailabilityZoneCount(5).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("desiredAvailabilityZoneCount cannot be greater than 3 (AWS best practice)");
    }

    @Test
    public void validationFails_when_zoneCountDoesNotMatchPrivateSubnets() {
        var eks = getDefaultEks()
                .withDesiredAvailabilityZoneCount(2)
                .withPrivateSubnetCidrs(List.of("172.33.128.0/20"));

        assertThatThrownBy(eks::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(("privateSubnetCidrs size (1) must match desiredAvailabilityZoneCount (2)")
                );
    }

    @Test
    public void noValidationErrors_when_subnetCountMatchesZoneCount() {
        var eks = getDefaultEks()
                .withDesiredAvailabilityZoneCount(3)
                .withPrivateSubnetCidrs(List.of(
                        "172.33.128.0/20",
                        "172.33.144.0/20",
                        "172.33.160.0/20"
                ))
                .withVpcCidrBlock("172.33.0.0/16")
                .build();

        assertThat(eks.validate()).isEmpty();
    }

    @Test
    public void validationFails_when_vpcCidrBlockIsInvalidFormat() {
        var eks = getDefaultEks()
                .withVpcCidrBlock("INVALID_CIDR");

        assertThatThrownBy(eks::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(("vpcCidrBlock is not a valid CIDR")
                );
    }

    @Test
    public void validationFails_when_subnetNotWithinVpcCidr() {
        var eks = getDefaultEks()
                .withVpcCidrBlock("172.33.0.0/16")
                .withDesiredAvailabilityZoneCount(1)
                .withPrivateSubnetCidrs(List.of("10.0.0.0/24"));

        assertThatThrownBy(eks::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(("is not within the VPC CIDR range")
                );
    }

    @Test
    public void validate_shouldAddError_whenVpcCidrBlockMaskOutOfRange() {
        var eks = getDefaultEks()
                .withVpcCidrBlock("10.0.0.0/15");

        assertThatThrownBy(eks::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(("vpcCidrBlock mask must be between /16 and /24"));

        eks = getDefaultEks()
                .withVpcCidrBlock("10.0.0.0/25");

        assertThatThrownBy(eks::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(("vpcCidrBlock mask must be between /16 and /24"));
    }

    @Test
    public void validate_shouldNotAddError_whenVpcCidrBlockMaskIsValid() {
        var eks = getDefaultEks()
                .withVpcCidrBlock("10.0.0.0/20")
                .build();

        assertThat(eks.validate()).isEmpty();
    }

    @Test
    public void validate_shouldAddError_whenSubnetMaskNotGreaterThanVpcMask() {
        var eks = getDefaultEks()
                .withVpcCidrBlock("10.0.0.0/20")
                .withPrivateSubnetCidrs(List.of("10.0.0.0/20"));

        assertThatThrownBy(eks::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Subnet 10.0.0.0/20 mask must be greater than VPC's (10.0.0.0/20)");
    }

    @Test
    public void validate_shouldAddError_whenSubnetHasInvalidCidrFormat() {
        var eks = getDefaultEks()
                .withVpcCidrBlock("10.0.0.0/20")
                .withPrivateSubnetCidrs(List.of("invalid-cidr"));

        assertThatThrownBy(eks::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid CIDR format: invalid-cidr");
    }

    @Test
    public void validate_shouldAddError_whenPrivateSubnetsOverlap() {
        var eks = getDefaultEks()
                .withVpcCidrBlock("10.0.0.0/20")
                .withPrivateSubnetCidrs(List.of("10.0.1.0/24", "10.0.1.128/25"));

        assertThatThrownBy(eks::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Subnet CIDRs 10.0.1.0/24 and 10.0.1.128/25 overlap");
    }


    @Test
    public void noErrors_whenPrivateSubnetsDoNotOverlap() {
        var eks = getDefaultEks()
                .withVpcCidrBlock("10.0.0.0/16")
                .withPrivateSubnetCidrs(List.of(
                        "10.0.1.0/24",
                        "10.0.2.0/24",
                        "10.0.3.0/24"
                ))
                .build();

        var errors = eks.validate();

        // Should not have overlap error
        assertThat(errors)
                .noneMatch(msg -> msg.contains("overlap"));
    }

    @Test
    public void errors_whenPrivateSubnetsOverlap() {
        var eks = getDefaultEks()
                .withVpcCidrBlock("10.0.0.0/16")
                .withPrivateSubnetCidrs(List.of(
                        "10.0.1.0/24",
                        "10.0.1.128/25"
                ));

        assertThatThrownBy(eks::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("overlap");
    }

    @Test
    public void errors_whenPrivateSubnetHasInvalidCidrFormat() {
        var eks = AwsElasticKubernetesService.builder()
                .withVpcCidrBlock("10.0.0.0/16")
                .withPrivateSubnetCidrs(List.of(
                        "10.0.1.0/24",
                        "invalid_cidr"
                ));

        assertThatThrownBy(eks::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid CIDR format");
    }

    @Test
    public void errors_whenSubnetMaskIsNotGreaterThanVpcMask() {
        var eks = AwsElasticKubernetesService.builder()
                .withVpcCidrBlock("10.0.0.0/24")
                .withPrivateSubnetCidrs(List.of(
                        "10.0.0.0/24"  // equal mask, invalid
                ));


        assertThatThrownBy(eks::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("mask must be greater than VPC's");
    }

    @Test
    public void compareIpsReturnsZero_whenIpAddressesAreIdentical() {
        var eks = AwsElasticKubernetesService.builder()
                .withVpcCidrBlock("10.0.0.0/16")
                .withPrivateSubnetCidrs(List.of(
                        "10.0.1.0/25",
                        "10.0.1.0/25"
                ));

        assertThatThrownBy(eks::build)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("overlap");
    }
}