package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsAaaaRecord;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsPtrRecord;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsZone;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OperationalEnvironmentTest {
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
  public void exceptionThrown_when_environmentCreatedWithUpercaseShortName() {
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
    var operationalEnvironment = OperationalEnvironment.builder()
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

    assertThat(operationalEnvironment.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(operationalEnvironment);
    assertThat(jsonEnvironment).isNotBlank();
  }


  @Test
  public void noValidationErrors_when_environmentCreatedWithRegionTenantIdAndSubscriptionId() {
    var operationalEnvironment = OperationalEnvironment.builder()
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

    assertThat(operationalEnvironment.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(operationalEnvironment);
    assertThat(jsonEnvironment).isNotBlank();
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithTags() {
    var operationalEnvironment = OperationalEnvironment.builder()
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

    assertThat(operationalEnvironment.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(operationalEnvironment);
    assertThat(jsonEnvironment).isNotBlank();

    Map<String, String> tags = operationalEnvironment.getTags();

    // Assert that the tags map has exactly 3 entries
    assertThat(tags).hasSize(3);
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithOperationalEnvironment() {
    var operationalEnvironment = OperationalEnvironment.builder()
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

    assertThat(operationalEnvironment.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(operationalEnvironment);
    assertThat(jsonEnvironment).isNotBlank();

    Map<String, String> tags = operationalEnvironment.getTags();

    // Assert that the tags map has exactly 3 entries
    assertThat(tags).hasSize(3);
  }

  private OperationalEnvironment generateBuilderWithInfo(String shortName) {
    return OperationalEnvironment.builder()
        .withId(new EnvironmentIdValue(
            EnvironmentType.PERSONAL,
            UUID.randomUUID(),
            shortName))
        .withResourceGroup(UUID.randomUUID())
        .build();
  }
}