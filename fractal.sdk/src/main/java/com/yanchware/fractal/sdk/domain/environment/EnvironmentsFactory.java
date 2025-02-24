package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.environment.service.RestEnvironmentService;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;

@Slf4j
@AllArgsConstructor
public class EnvironmentsFactory {

  private final HttpClient client;
  private final SdkConfiguration sdkConfiguration;
  private final RetryRegistry retryRegistry;

  public EnvironmentBuilder builder() {
    return new EnvironmentBuilder(client, sdkConfiguration, retryRegistry);
  }

  public static class EnvironmentBuilder {
    private final EnvironmentAggregate aggregate;
    private final EnvironmentBuilder builder;

    public EnvironmentBuilder(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {
      aggregate = create(client, sdkConfiguration, retryRegistry);
      builder = getBuilder();
    }

    protected EnvironmentAggregate create(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {
      return new EnvironmentAggregate(new RestEnvironmentService(client, sdkConfiguration, retryRegistry));
    }

    protected EnvironmentBuilder getBuilder() {
      return this;
    }

    public EnvironmentBuilder withManagementEnvironment(ManagementEnvironment managementEnvironment) {
      aggregate.setManagementEnvironment(managementEnvironment);
      return builder;
    }


    public EnvironmentAggregate build() {
      return aggregate;
    }
  }
}
