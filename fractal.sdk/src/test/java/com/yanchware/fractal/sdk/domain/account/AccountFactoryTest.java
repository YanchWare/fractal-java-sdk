package com.yanchware.fractal.sdk.domain.account;

import com.yanchware.fractal.sdk.domain.accounts.AccountsFactory;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AccountFactoryTest {
    @Test
    void builder_buildsAggregate_and_stagesPersonalResourceGroup() throws Exception {
        var httpClient = HttpClient.newHttpClient();
        var sdkConfig = new LocalSdkConfiguration("http://localhost:8080"); // test config
        var retry = RetryRegistry.ofDefaults();

        var factory = new AccountsFactory(httpClient, sdkConfig, retry);
        var aggregate = factory.builder()
                .withResourceGroup("rg-1", "RG One")
                .build();

        assertThat(aggregate).isNotNull();

    }
}
