package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsAaaaRecord;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsPtrRecord;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsZone;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci.OciRegion;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;
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
  public void noValidationErrors_when_environmentCreatedWithValidShortName() {
    var env = generateBuilderWithInfo("production-001");
    assertThat(env.validate()).isEmpty();
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithDnsZone() {
    var managementEnvironment = ManagementEnvironment.builder()
        .withId(new EnvironmentIdValue(
            EnvironmentType.PERSONAL,
            UUID.randomUUID(),
            "production-001"))
        .withResourceGroup(UUID.randomUUID())
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
    var managementEnvironment = ManagementEnvironment.builder()
        .withId(new EnvironmentIdValue(
            EnvironmentType.PERSONAL,
            UUID.randomUUID(),
            "production-001"))
        .withAzureCloudAgent(
            AzureRegion.AUSTRALIA_CENTRAL,
            UUID.randomUUID(),
            UUID.randomUUID())
        .withResourceGroup(UUID.randomUUID())
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
    var managementEnvironment = ManagementEnvironment.builder()
        .withId(new EnvironmentIdValue(
            EnvironmentType.PERSONAL,
            UUID.randomUUID(),
            "production-001"))
        .withAzureCloudAgent(
            AzureRegion.AUSTRALIA_CENTRAL,
            UUID.randomUUID(),
            UUID.randomUUID())
        .withResourceGroup(UUID.randomUUID())
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
        .withResourceGroup(UUID.randomUUID())
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
        .withResourceGroup(UUID.randomUUID())
        .withAwsCloudAgent(AwsRegion.EU_NORTH_1, UUID.randomUUID().toString(), UUID.randomUUID().toString())
        .withAzureCloudAgent(AzureRegion.WEST_EUROPE, UUID.randomUUID(), UUID.randomUUID())
        .withGcpCloudAgent(GcpRegion.EU_WEST1, UUID.randomUUID().toString(), UUID.randomUUID().toString())
        .withOciCloudAgent(OciRegion.EU_ZURICH_1, UUID.randomUUID().toString(), UUID.randomUUID().toString())
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

  private ManagementEnvironment generateBuilderWithInfo(String shortName) {
    return ManagementEnvironment.builder()
        .withId(new EnvironmentIdValue(
            EnvironmentType.PERSONAL,
            UUID.randomUUID(),
            shortName))
        .withResourceGroup(UUID.randomUUID())
        .build();
  }
}