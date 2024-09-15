package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.environment.EnvironmentAggregate;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentType;
import com.yanchware.fractal.sdk.domain.environment.DnsAaaaRecord;
import com.yanchware.fractal.sdk.domain.environment.DnsPtrRecord;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsZone;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentsFactory;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import com.yanchware.fractal.sdk.utils.TestUtils;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.yanchware.fractal.sdk.domain.entities.environment.EnvironmentConstants.TAGS_PARAM_KEY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EnvironmentAggregateTest {

  EnvironmentsFactory factory;

  public void setup() {
    factory = new EnvironmentsFactory(
            HttpClient.newBuilder().build(),
            new LocalSdkConfiguration(""),
            RetryRegistry.custom().build());
  }

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
    var env = factory.builder()
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
    var env = factory.builder()
        .withEnvironmentType(EnvironmentType.PERSONAL)
        .withOwnerId(UUID.randomUUID())
        .withShortName("production-001")
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

    assertThat(env.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(env);
    assertThat(jsonEnvironment).isNotBlank();
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithTags() {
    var env = factory.builder()
        .withEnvironmentType(EnvironmentType.PERSONAL)
        .withOwnerId(UUID.randomUUID())
        .withShortName("production-001")
        .withAzureCloudAgent(
                AzureRegion.AUSTRALIA_CENTRAL,
                UUID.randomUUID(),
                UUID.randomUUID())
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

  private EnvironmentAggregate generateBuilderWithInfo(String shortName) {
    return factory.builder()
        .withEnvironmentType(EnvironmentType.PERSONAL)
        .withOwnerId(UUID.randomUUID())
        .withResourceGroup(UUID.randomUUID())
        .withShortName(shortName)
        .build();
  }

}