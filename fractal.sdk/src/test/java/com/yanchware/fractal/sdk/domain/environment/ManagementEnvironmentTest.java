package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsAaaaRecord;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsPtrRecord;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsZone;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.hetzner.HetznerRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci.OciRegion;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ManagementEnvironmentTest {

  @Test
  public void exceptionThrown_when_environmentCreatedWithNullShortName() {
    assertThatThrownBy(() -> generateBuilderWithInfo(null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Environment ShortName has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_environmentCreatedWithEmptyShortName() {
    assertThatThrownBy(() -> generateBuilderWithInfo(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Environment ShortName has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_environmentCreatedWithBlankShortName() {
    assertThatThrownBy(() -> generateBuilderWithInfo("   "))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Environment ShortName has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_environmentCreatedWithUppercaseShortName() {
    assertThatThrownBy(() -> generateBuilderWithInfo("Production-001"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Environment ShortName must only contain lowercase letters, numbers, and dashes.");
  }

  @Test
  public void exceptionThrown_when_environmentCreatedWithCiCdProfilesButWithoutDefaultCiCdProfile() {
    assertThatThrownBy(() -> generateBuilderWithId("production-001")
            .withCiCdProfile(new CiCdProfile("default", "Default", "data", "pass"))
            .withCiCdProfile(new CiCdProfile("custom", "Custom","data", "pass"))
            .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("A default CI/CD profile must be set if additional CI/CD profiles are defined");
  }

  @Test
  void exceptionThrown_when_environmentCreatedWithEmptyCiCdProfileShortName() {
    assertThatThrownBy(() -> generateBuilderWithId("production-001")
            .withDefaultCiCdProfile(new CiCdProfile("", "Default", "data", "pass")) // Empty short name
            .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("The Short Name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen"); // Assuming SHORT_NAME_NOT_VALID is your validation message
  }

  @Test
  void exceptionThrown_when_environmentCreatedWithNullCiCdProfile() {
    assertThatThrownBy(() -> generateBuilderWithId("production-001")
            .withDefaultCiCdProfile(null) // Null profile
            .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("The default CI/CD profile cannot be null"); // Or a more specific message
  }

  @Test
  void exceptionThrown_when_environmentCreatedWithDuplicateCiCdProfileShortNames() {
    assertThatThrownBy(() -> generateBuilderWithId("production-001")
            .withDefaultCiCdProfile(new CiCdProfile("default", "Default", "data", "pass"))
            .withCiCdProfile(new CiCdProfile("default", "Custom", "data", "pass"))
            .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("CI/CD profile short names must be unique, including the default profile");
  }

  @Test
  void exceptionThrown_when_nonDefaultCiCdProfileShortNamesAreDuplicated() {
    assertThatThrownBy(() -> generateBuilderWithId("production-001")
            .withDefaultCiCdProfile(new CiCdProfile("default", "Default", "data", "pass"))
            .withCiCdProfile(new CiCdProfile("custom", "Custom", "data", "pass"))
            .withCiCdProfile(new CiCdProfile("custom", "Custom", "data", "pass"))
            .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("CI/CD profile short names must be unique, including the default profile");
  }

  @Test
  void exceptionThrown_when_environmentCreatedWithDuplicateSecretNames() {
    assertThatThrownBy(() -> generateBuilderWithId("production-001")
            .withSecret(new Secret("my-secret", "My Secret Display Name","My Secret Description", "value1"))
            .withSecret(new Secret("my-secret", "My Secret Display Name 2","My Secret Description 2", "value2"))
            .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Secret short names must be unique");
  }

  @Test
  void exceptionThrown_when_environmentCreatedWithNullSecret() {
    assertThatThrownBy(() -> generateBuilderWithId("production-001")
            .withSecret(null)
            .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("The secret cannot be null");
  }

  @Test
  void exceptionThrown_when_environmentCreatedWithInvalidDnsZone() {
    assertThatThrownBy(() -> generateBuilderWithId("production-001")
            .withDnsZone(DnsZone.builder().withName("").build())
            .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("DnsZone validation failed");
  }

  @Test
  void exceptionThrown_when_environmentCreatedWithInvalidOperationalEnvironment() {
    assertThatThrownBy(() -> generateBuilderWithId("production-001")
            .withOperationalEnvironment(OperationalEnvironment.builder().withShortName("").build())
            .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Environment validation failed");
  }

  @Test
  void exceptionThrown_when_environmentCreatedWithEmptyResourceGroups() {
    assertThatThrownBy(() -> ManagementEnvironment.builder()
            .withId(new EnvironmentIdValue(
                    EnvironmentType.PERSONAL,
                    UUID.randomUUID(),
                    "production-001"))
            .withResourceGroups(Collections.emptyList()) // Empty resource groups
            .build())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Environment ResourceGroups has not been defined and it is required");
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithValidShortName() {
    var env = generateBuilderWithInfo("production-001");
    assertThat(env.validate()).isEmpty();
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithDnsZone() {
    var ownerId = UUID.randomUUID();
    var managementEnvironment = ManagementEnvironment.builder()
        .withId(new EnvironmentIdValue(
            EnvironmentType.PERSONAL,
            ownerId,
            "production-001"))
        .withResourceGroup(ResourceGroupId.fromString(String.format("Personal/%s/rg", ownerId)))
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

    assertThat(managementEnvironment.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(managementEnvironment);
    assertThat(jsonEnvironment).isNotBlank();
  }


  @Test
  public void noValidationErrors_when_environmentCreatedWithRegionTenantIdAndSubscriptionId() {
    var ownerId = UUID.randomUUID();
    var managementEnvironment = ManagementEnvironment.builder()
        .withId(new EnvironmentIdValue(
            EnvironmentType.PERSONAL,
            ownerId,
            "production-001"))
        .withAzureCloudAgent(
            AzureRegion.AUSTRALIA_CENTRAL,
            UUID.randomUUID(),
            UUID.randomUUID())
        .withResourceGroup(ResourceGroupId.fromString(String.format("Personal/%s/rg", ownerId)))
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

    assertThat(managementEnvironment.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(managementEnvironment);
    assertThat(jsonEnvironment).isNotBlank();
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithTags() {
    var ownerId = UUID.randomUUID();
    var managementEnvironment = ManagementEnvironment.builder()
        .withId(new EnvironmentIdValue(
            EnvironmentType.PERSONAL,
            ownerId,
            "production-001"))
        .withAzureCloudAgent(
            AzureRegion.AUSTRALIA_CENTRAL,
            UUID.randomUUID(),
            UUID.randomUUID())
        .withResourceGroup(ResourceGroupId.fromString(String.format("Personal/%s/rg", ownerId)))
        .withTags(Map.of("key1", "value1", "key2", "value2"))
        .withTag("key1", "value2")
        .withTag("key3", "value3")
        .build();

    assertThat(managementEnvironment.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(managementEnvironment);
    assertThat(jsonEnvironment).isNotBlank();

    Map<String, String> tags = managementEnvironment.getTags();

    // Assert that the tags map has exactly 3 entries
    assertThat(tags).hasSize(3);
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithOperationalEnvironment() {
    var ownerId = UUID.randomUUID();
    var environmentType = EnvironmentType.PERSONAL;

    var operationalEnvironment = OperationalEnvironment.builder()
        .withShortName("operational-001")
        .withResourceGroup(ResourceGroupId.fromString(String.format("Personal/%s/rg", ownerId)))
        .withAzureSubscription(AzureRegion.WEST_EUROPE, UUID.randomUUID())
        .withTag("key1", "value1")
        .withTag("key2", "value3")
        .withTag("key3", "value4")
        .build();

    var managementEnvironment = ManagementEnvironment.builder()
        .withId(new EnvironmentIdValue(
            environmentType,
            ownerId,
            "production-001"))
        .withResourceGroup(ResourceGroupId.fromString(String.format("Personal/%s/rg2", ownerId)))
        .withAwsCloudAgent(AwsRegion.EU_NORTH_1, UUID.randomUUID().toString(), UUID.randomUUID().toString())
        .withAzureCloudAgent(AzureRegion.WEST_EUROPE, UUID.randomUUID(), UUID.randomUUID())
        .withGcpCloudAgent(GcpRegion.EUROPE_WEST1, UUID.randomUUID().toString(), UUID.randomUUID().toString())
        .withOciCloudAgent(OciRegion.EU_ZURICH_1, UUID.randomUUID().toString(), UUID.randomUUID().toString())
        .withHetznerCloudAgent(HetznerRegion.DE_FALKENSTEIN_1, UUID.randomUUID().toString())
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
        .withOperationalEnvironment(operationalEnvironment)
        .build();

    assertThat(managementEnvironment.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(managementEnvironment);
    assertThat(jsonEnvironment).isNotBlank();
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithSecrets() {
    var ownerId = UUID.randomUUID();
    var managementEnvironment = ManagementEnvironment.builder()
        .withId(new EnvironmentIdValue(
            EnvironmentType.PERSONAL,
            ownerId,
            "production-001"))
        .withAzureCloudAgent(
            AzureRegion.AUSTRALIA_CENTRAL,
            UUID.randomUUID(),
            UUID.randomUUID())
        .withResourceGroup(ResourceGroupId.fromString(String.format("Personal/%s/rg", ownerId)))
        .withSecret(new Secret("secret-1", "Secret Display Name","Secret Description", "value-1"))
        .withSecret(new Secret("secret-2", "Secret 2 Display Name","Secret 2 Description", "value-2"))
        .withSecrets(List.of(
            new Secret("secret-3", "Secret 3 Display Name","Secret 3 Description", "value-3"),
            new Secret("secret-4", "Secret 4 Display Name","Secret 4 Description", "value-4")
        )).build();

    assertThat(managementEnvironment.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(managementEnvironment);
    assertThat(jsonEnvironment).isNotBlank();

    var secrets = managementEnvironment.getSecrets();

    // Assert that the secrets has exactly 4 entries
    assertThat(secrets).hasSize(4);
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithCiCdProfiles() {
    var ownerId = UUID.randomUUID();
    var managementEnvironment = ManagementEnvironment.builder()
        .withId(new EnvironmentIdValue(
            EnvironmentType.PERSONAL,
            ownerId,
            "production-001"))
        .withAzureCloudAgent(
            AzureRegion.AUSTRALIA_CENTRAL,
            UUID.randomUUID(),
            UUID.randomUUID())
        .withResourceGroup(ResourceGroupId.fromString(String.format("Personal/%s/rg", ownerId)))
        .withDefaultCiCdProfile(new CiCdProfile("default", "Default", "data", "pass"))
        .withCiCdProfile(new CiCdProfile("custom", "Custom", "data", "pass"))
        .withCiCdProfile(new CiCdProfile("additional", "Additional","data", "pass"))
        .build();

    assertThat(managementEnvironment.validate()).isEmpty();
    assertThat(managementEnvironment.getDefaultCiCdProfile()).isNotNull();

    var jsonEnvironment = TestUtils.getJsonRepresentation(managementEnvironment);
    assertThat(jsonEnvironment).isNotBlank();

    var ciCdProfiles = managementEnvironment.getCiCdProfiles();
    
    assertThat(ciCdProfiles).hasSize(2);
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithDnsZones() {
    var managementEnvironment = generateBuilderWithId("production-001")
            .withDnsZones(List.of(
                    DnsZone.builder()
                            .withName("dns.name1")
                            .build(),
                    DnsZone.builder()
                            .withName("dns.name2")
                            .build()
            ))
            .build();

    assertThat(managementEnvironment.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(managementEnvironment);
    assertThat(jsonEnvironment).isNotBlank();
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithResourceGroups() {
    var ownerId = UUID.randomUUID();
    var managementEnvironment = ManagementEnvironment.builder()
            .withId(new EnvironmentIdValue(
                    EnvironmentType.PERSONAL,
                    ownerId,
                    "production-001"))
            .withResourceGroups(
              List.of(
                ResourceGroupId.fromString(String.format("Personal/%s/rg", ownerId)),
                ResourceGroupId.fromString(String.format("Personal/%s/rg2", ownerId))))
            .build();

    assertThat(managementEnvironment.validate()).isEmpty();
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithTag() {
    var managementEnvironment = generateBuilderWithId("production-001")
            .withTag("key1", "value1")
            .build();

    assertThat(managementEnvironment.validate()).isEmpty();
    
    var tags = managementEnvironment.getTags();
    assertThat(tags).hasSize(1);
  }

  private ManagementEnvironment generateBuilderWithInfo(String shortName) {
    return generateBuilderWithId(shortName).build();
  }

  private ManagementEnvironment.ManagementEnvironmentBuilder generateBuilderWithId(String shortName) {
    var ownerId = UUID.randomUUID();
    return ManagementEnvironment.builder()
            .withId(new EnvironmentIdValue(
                    EnvironmentType.PERSONAL,
                    ownerId,
                    shortName))
            .withResourceGroup(ResourceGroupId.fromString(String.format("Personal/%s/rg", ownerId)));
  }
}