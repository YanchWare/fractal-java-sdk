package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsAaaaRecord;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsPtrRecord;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsZone;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OperationalEnvironmentTest {
    private static final String VALID_SHORT_NAME = "operational-001";
    private static final String VALID_DISPLAY_NAME = "My Operational Environment";
    private static final UUID VALID_RESOURCE_GROUP_ID = UUID.randomUUID();
    
    @Test
    void exceptionThrown_when_environmentCreatedWithNullShortName() {
        assertThatThrownBy(() -> generateBuilderWithInfo(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Environment ShortName has not been defined and it is required");
    }

    @Test
    void exceptionThrown_when_environmentCreatedWithEmptyShortName() {
        assertThatThrownBy(() -> generateBuilderWithInfo(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Environment ShortName has not been defined and it is required");
    }

    @Test
    void exceptionThrown_when_environmentCreatedWithBlankShortName() {
        assertThatThrownBy(() -> generateBuilderWithInfo("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Environment ShortName has not been defined and it is required");
    }

    @Test
    void exceptionThrown_when_environmentCreatedWithUppercaseShortName() {
        assertThatThrownBy(() -> generateBuilderWithInfo("Production-001"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Environment ShortName must only contain lowercase letters, numbers, and dashes.");
    }

    @Test
    void exceptionThrown_when_environmentCreatedWithDuplicateSecretNames() {
        assertThatThrownBy(() -> generateBuilder("operational-001")
                .withSecret(new Secret("my-secret", "My Secret Display Name","My Secret Description", "value1"))
                .withSecret(new Secret("my-secret", "My Secret Display Name","My Secret Description", "value2"))
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Secret short names must be unique");
    }

    @Test
    void exceptionThrown_when_environmentCreatedWithNullSecret() {
        assertThatThrownBy(() -> generateBuilder("operational-001")
                .withSecret(null)
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The secret cannot be null");
    }

    @Test
    void exceptionThrown_when_environmentCreatedWithCiCdProfilesButWithoutDefaultCiCdProfile() {
        assertThatThrownBy(() -> generateBuilder(VALID_SHORT_NAME)
                .withCiCdProfile(new CiCdProfile("default", "Default", "data", "pass"))
                .withCiCdProfile(new CiCdProfile("custom", "Custom","data", "pass"))
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("A default CI/CD profile must be set if additional CI/CD profiles are defined");
    }

    @Test
    void exceptionThrown_when_environmentCreatedWithEmptyCiCdProfileShortName() {
        assertThatThrownBy(() -> generateBuilder(VALID_SHORT_NAME)
                .withDefaultCiCdProfile(new CiCdProfile("", "Default", "data", "pass")) // Empty short name
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The Short Name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen"); // Assuming SHORT_NAME_NOT_VALID is your validation message
    }

    @Test
    void exceptionThrown_when_environmentCreatedWithNullCiCdProfile() {
        assertThatThrownBy(() -> generateBuilder(VALID_SHORT_NAME)
                .withDefaultCiCdProfile(null) // Null profile
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The default CI/CD profile cannot be null"); // Or a more specific message
    }

    @Test
    void exceptionThrown_when_environmentCreatedWithDuplicateCiCdProfileShortNames() {
        assertThatThrownBy(() -> generateBuilder(VALID_SHORT_NAME)
                .withDefaultCiCdProfile(new CiCdProfile("default", "Default", "data", "pass"))
                .withCiCdProfile(new CiCdProfile("default", "Custom", "data", "pass"))
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CI/CD profile short names must be unique, including the default profile");
    }

    @Test
    void exceptionThrown_when_nonDefaultCiCdProfileShortNamesAreDuplicated() {
        assertThatThrownBy(() -> generateBuilder(VALID_SHORT_NAME)
                .withDefaultCiCdProfile(new CiCdProfile("default", "Default", "data", "pass"))
                .withCiCdProfile(new CiCdProfile("custom", "Custom", "data", "pass"))
                .withCiCdProfile(new CiCdProfile("custom", "Custom", "data", "pass"))
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CI/CD profile short names must be unique, including the default profile");
    }

    @Test
    void exceptionThrown_when_environmentCreatedWithInvalidDnsZone() {
        assertThatThrownBy(() -> generateBuilder("operational-001")
                .withDnsZone(DnsZone.builder().withName("").build())
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("DnsZone validation failed");
    }

    @Test
    void exceptionThrown_when_environmentCreatedWithEmptyResourceGroups() {
        assertThatThrownBy(() -> OperationalEnvironment.builder()
                .withShortName("production-001")
                .withAzureSubscription(
                        AzureRegion.AUSTRALIA_CENTRAL,
                        UUID.randomUUID()).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Environment ResourceGroups has not been defined and it is required");
    }

    @Test
    void exceptionThrown_when_environmentResourceGroupsNotDefined() {
        assertThatThrownBy(() -> OperationalEnvironment.builder()
                .withShortName("production-001")
                .withAzureSubscription(
                        AzureRegion.AUSTRALIA_CENTRAL,
                        UUID.randomUUID()).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Environment ResourceGroups has not been defined and it is required");
    }

    @Test
    void noValidationErrors_when_environmentCreateWithRequiredData() {
        var environment = OperationalEnvironment.builder()
                .withShortName("production-001")
                .withResourceGroup(UUID.randomUUID())
                .withAzureSubscription(AzureRegion.WEST_EUROPE, UUID.randomUUID())
                .build();

        assertThat(environment.validate()).isEmpty();

        var jsonEnvironment = TestUtils.getJsonRepresentation(environment);
        assertThat(jsonEnvironment).isNotBlank();
    }

    @Test
    void noValidationErrors_when_environmentCreatedWithSecrets() {
        var environment = generateBuilder(VALID_SHORT_NAME)
                .withAzureSubscription(
                        AzureRegion.JAPAN,
                        UUID.randomUUID())
                .withSecret(new Secret("secret-1", "My Secret Display Name","My Secret Description", "value-1"))
                .withSecret(new Secret("secret-2", "My Secret Display Name","My Secret Description", "value-2"))
                .withSecrets(List.of(
                        new Secret("secret-3", "My Secret Display Name","My Secret Description", "value-3"),
                        new Secret("secret-4", "My Secret Display Name","My Secret Description", "value-4")
                ))
                .build();

        assertValidEnvironment(environment);

        var secrets = environment.getSecrets();
        assertThat(secrets).hasSize(4);
    }

    @Test
    void noValidationErrors_when_environmentCreatedWithCiCdProfiles() {
        var environment = OperationalEnvironment.builder()
                .withShortName(VALID_SHORT_NAME)
                .withAzureSubscription(
                        AzureRegion.AUSTRALIA_CENTRAL,
                        UUID.randomUUID())
                .withResourceGroup(UUID.randomUUID())
                .withDefaultCiCdProfile(new CiCdProfile("default", "Default", "data", "pass"))
                .withCiCdProfile(new CiCdProfile("custom", "Custom", "data", "pass"))
                .withCiCdProfile(new CiCdProfile("additional", "Additional","data", "pass"))
                .build();

        assertThat(environment.validate()).isEmpty();
        assertThat(environment.getDefaultCiCdProfile()).isNotNull();

        var jsonEnvironment = TestUtils.getJsonRepresentation(environment);
        assertThat(jsonEnvironment).isNotBlank();

        var ciCdProfiles = environment.getCiCdProfiles();

        assertThat(ciCdProfiles).hasSize(2);
    }

    @Test
    void noValidationErrors_when_environmentCreatedWithValidShortName() {
        var shortName = "operational-001";
        var environment = generateBuilder(shortName).build();
        
        assertThat(environment.validate()).isEmpty();
        assertThat(environment.getShortName()).isEqualTo(shortName);
    }

    @Test
    void noValidationErrors_when_environmentCreatedWithDnsZone() {
        var environment = generateBuilder(VALID_SHORT_NAME)
                .withDnsZone(
                        DnsZone.builder()
                                .withName("dns.name")
                                .withRecords(Map.of("componentId", List.of(
                                        DnsAaaaRecord.builder()
                                                .withName("name")
                                                .withIpV6Address("2001:db8:3333:4444:CCCC:DDDD:EEEE:FFFF")
                                                .withTtl(Duration.ofMinutes(1))
                                                .build(),
                                        DnsPtrRecord.builder()
                                                .withName("name")
                                                .withDomainName("")
                                                .withTtl(Duration.ofMinutes(1))
                                                .build()
                                )))
                                .withParameter("key", "value")
                                .isPrivate(false)
                                .build())
                .build();

        assertValidEnvironment(environment);
    }

    @Test
    void noValidationErrors_when_environmentCreatedWithRegionTenantIdAndSubscriptionId() {
        var environment = generateBuilder(VALID_SHORT_NAME)
                .withAzureSubscription(
                        AzureRegion.BRAZIL_SOUTH,
                        UUID.randomUUID())
                .build();

        assertValidEnvironment(environment);
    }

    @Test
    void noValidationErrors_when_environmentCreatedWithTags() {
        var environment = generateBuilder(VALID_SHORT_NAME)
                .withAzureSubscription(
                        AzureRegion.EAST_US_EUAP,
                        UUID.randomUUID())
                .withTags(Map.of("key1", "value1", "key2", "value2"))
                .withTag("key1", "value2")
                .withTag("key3", "value3")
                .build();

        assertValidEnvironment(environment);

        Map<String, String> tags = environment.getTags();
        assertThat(tags).hasSize(3);
    }

    @Test
    void noValidationErrors_when_environmentCreatedWithEmptyName() {
        var environment = generateBuilder(VALID_SHORT_NAME)
                .withAzureSubscription(
                        AzureRegion.NEWZEALAND,
                        UUID.randomUUID())
                .withName("")
                .build();

        assertThat(environment.validate()).isEmpty();
        assertThat(environment.getShortName()).isEqualTo(VALID_SHORT_NAME);
        assertThat(environment.getName()).isEqualTo(environment.getShortName());
        assertThat(environment.getResourceGroups()).containsExactly(VALID_RESOURCE_GROUP_ID);
    }

    @Test
    void noValidationErrors_when_environmentCreatedWithNullName() {
        var environment = generateBuilder(VALID_SHORT_NAME)
                .withName(null)
                .build();

        assertThat(environment.validate()).isEmpty();
        assertThat(environment.getShortName()).isEqualTo(VALID_SHORT_NAME);
        assertThat(environment.getName()).isEqualTo(environment.getShortName());
        assertThat(environment.getResourceGroups()).containsExactly(VALID_RESOURCE_GROUP_ID);
    }

    @Test
    void noValidationErrors_when_environmentCreatedWithEmptyTags() {
        var environment = generateBuilder(VALID_SHORT_NAME)
                .withTags(Collections.emptyMap())
                .build();

        assertValidEnvironment(environment);
    }

    void generateBuilderWithInfo(String shortName) {
        generateBuilder(shortName).build();
    }

    private OperationalEnvironment.OperationalEnvironmentBuilder generateBuilder(String shortName) {
        return OperationalEnvironment.builder()
                .withShortName(shortName)
                .withName(VALID_DISPLAY_NAME)
                .withResourceGroup(VALID_RESOURCE_GROUP_ID);
    }

    private void assertValidEnvironment(OperationalEnvironment environment) {
        assertThat(environment.validate()).isEmpty();

        // Assert properties
        assertThat(environment.getShortName()).isEqualTo(VALID_SHORT_NAME);
        assertThat(environment.getName()).isEqualTo(VALID_DISPLAY_NAME); 
        assertThat(environment.getResourceGroups()).containsExactly(VALID_RESOURCE_GROUP_ID);
    }
}