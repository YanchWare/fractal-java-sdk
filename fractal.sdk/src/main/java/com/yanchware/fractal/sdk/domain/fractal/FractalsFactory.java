package com.yanchware.fractal.sdk.domain.fractal;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.fractal.service.dtos.BlueprintComponentDto;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemAggregate;
import io.github.resilience4j.retry.RetryRegistry;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.Collection;

public class FractalsFactory {
  private final HttpClient client;
  private final SdkConfiguration sdkConfiguration;
  private final RetryRegistry retryRegistry;

  public FractalsFactory(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {

    this.client = client;
    this.sdkConfiguration = sdkConfiguration;
    this.retryRegistry = retryRegistry;
  }

  public FractalBuilder builder() {
    return new FractalBuilder(client, sdkConfiguration, retryRegistry);
  }

  public static class FractalBuilder {
    private final FractalAggregate fractal;
    private final FractalBuilder builder;

    public FractalBuilder(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {
      fractal = createFractal(client, sdkConfiguration, retryRegistry);
      builder = getBuilder();
    }

    public FractalBuilder fromLiveSystem(LiveSystemAggregate liveSystem) {
      fractal.setId(liveSystem.getFractalId());
      fractal.setDescription(String.format("Blueprint created via SDK from LiveSystem with Fractal ID: %s", liveSystem.getFractalId()));
      fractal.setPrivate(true);
      fractal.setComponents(BlueprintComponentDto.fromLiveSystemComponents(liveSystem.getComponents()));
      return builder;
    }

    public FractalAggregate build() {
      Collection<String> errors = fractal.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
          "Fractal validation failed. Errors: %s",
          Arrays.toString(errors.toArray())));
      }

      return fractal;
    }

    protected FractalBuilder getBuilder() {
      return this;
    }

    protected FractalAggregate createFractal(
      HttpClient client,
      SdkConfiguration sdkConfiguration,
      RetryRegistry retryRegistry)
    {
      return new FractalAggregate(client, sdkConfiguration, retryRegistry);
    }
  }

}
