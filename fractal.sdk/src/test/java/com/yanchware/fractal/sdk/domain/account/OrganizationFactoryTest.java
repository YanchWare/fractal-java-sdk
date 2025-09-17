package com.yanchware.fractal.sdk.domain.account;

import com.yanchware.fractal.sdk.domain.accounts.OrganizationFactory;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// Java
class OrganizationFactoryTest {
  @Test
  void builder_buildsAggregate_and_stagesOrganizationalResourceGroup() throws Exception {
    var httpClient = HttpClient.newHttpClient();
    var sdkConfig = new LocalSdkConfiguration("http://localhost:8080");
    var retry = RetryRegistry.ofDefaults();

    var orgId = UUID.randomUUID();

    var factory = new OrganizationFactory(httpClient, sdkConfig, retry);
    var aggregate = factory.builder()
        .withResourceGroup(orgId, "rg-1", "RG One")
        .build();

    assertThat(aggregate).isNotNull();
  }
}
