package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.aggregates.EnvironmentType;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsAaaaRecord;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsPtrRecord;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsZone;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.yanchware.fractal.sdk.domain.entities.environment.EnvironmentConstants.TAGS_PARAM_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EnvironmentTest {

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
    var env = Environment.builder()
        .withEnvironmentType(EnvironmentType.PERSONAL)        
        .withOwnerId(UUID.randomUUID())
        .withResourceGroup(UUID.randomUUID())
        .withShortName("production-001")
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

    assertThat(env.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(env);
    assertThat(jsonEnvironment).isNotBlank();
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithRegionTenantIdAndSubscriptionId() {
    var env = Environment.builder()
        .withEnvironmentType(EnvironmentType.PERSONAL)
        .withOwnerId(UUID.randomUUID())
        .withShortName("production-001")
        .withRegion(AzureRegion.AUSTRALIA_CENTRAL.toString())
        .withTenantId(UUID.randomUUID())
        .withSubscriptionId(UUID.randomUUID())
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

    assertThat(env.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(env);
    assertThat(jsonEnvironment).isNotBlank();
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithTags() {
    var env = Environment.builder()
        .withEnvironmentType(EnvironmentType.PERSONAL)
        .withOwnerId(UUID.randomUUID())
        .withShortName("production-001")
        .withRegion(AzureRegion.AUSTRALIA_CENTRAL.toString())
        .withResourceGroup(UUID.randomUUID())
        .withTags(Map.of("key1", "value1", "key2", "value2"))
        .withTag("key1", "value2")
        .withTag("key3", "value3")
        .build();

    assertThat(env.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(env);
    assertThat(jsonEnvironment).isNotBlank();

    Map<String, String> tags = (Map<String, String>) env.getParameters().get(TAGS_PARAM_KEY);

    // Assert that the tags map has exactly 3 entries
    assertThat(tags).hasSize(3);
  }

  private Environment generateBuilderWithInfo(String shortName) {
    return Environment.builder()
        .withEnvironmentType(EnvironmentType.PERSONAL)
        .withOwnerId(UUID.randomUUID())
        .withResourceGroup(UUID.randomUUID())
        .withShortName(shortName)
        .build();
  }

}