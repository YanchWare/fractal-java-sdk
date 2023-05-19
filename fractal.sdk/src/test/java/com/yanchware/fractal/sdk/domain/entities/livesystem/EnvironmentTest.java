package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.domain.entities.environment.AaaaRecord;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsZone;
import com.yanchware.fractal.sdk.domain.entities.environment.PtrRecord;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EnvironmentTest {

  @Test
  public void exceptionThrown_when_environmentCreatedWithNullId() {
    assertThatThrownBy(() -> generateBuilderWithInfo(null)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Environment id has not been defined");
  }

  @Test
  public void exceptionThrown_when_environmentCreatedWithEmptyId() {
    assertThatThrownBy(() -> generateBuilderWithInfo("")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Environment id has not been defined");
  }

  @Test
  public void exceptionThrown_when_environmentCreatedWithBlankId() {
    assertThatThrownBy(() -> generateBuilderWithInfo("   ")).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Environment id has not been defined");
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithValidId() {
    var env = generateBuilderWithInfo("production-001");
    assertThat(env.validate()).isEmpty();
  }

  @Test
  public void noValidationErrors_when_environmentCreatedWithDnsZone() {
    var env = Environment.builder()
        .withId("production-001")
        .withDisplayName("PROD")
        .withParentId("123456789")
        .withParentType("folder")
        .withDnsZone(
            DnsZone.builder()
                .withName("dns.name")
                .withRecords(List.of(
                    new AaaaRecord("name", "1.2.3.4", Duration.ofMinutes(1)),
                    new PtrRecord("name", List.of(""), Duration.ofMinutes(1))
                ))
                .withParameter("key", "value")
                .isPrivate(false)
                .build())
        .build();

    assertThat(env.validate()).isEmpty();

    var jsonEnvironment = TestUtils.getJsonRepresentation(env);
    assertThat(jsonEnvironment).isNotBlank();
  }

  private Environment generateBuilderWithInfo(String id) {
    return Environment.builder()
        .withId(id)
        .withDisplayName("PROD")
        .withParentId("123456789")
        .withParentType("folder")
        .build();
  }

}