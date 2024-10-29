package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.BeforeEach;

import java.net.http.HttpClient;

public class EnvironmentAggregateTest {

  EnvironmentsFactory factory;

  @BeforeEach
  public void setup() {
    factory = new EnvironmentsFactory(
        HttpClient.newBuilder().build(),
        new LocalSdkConfiguration(""),
        RetryRegistry.ofDefaults());
  }
}