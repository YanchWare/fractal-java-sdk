package com.yanchware.fractal.sdk.domain.account;

import com.yanchware.fractal.sdk.domain.accounts.AccountsFactory;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AccountFactoryTest {
    private static final String SHORT_NAME= "rg-1";
    private  static final String DISPLAY_NAME = "RG One";

    @Test
    void builder_buildsAggregate_and_stagesPersonalResourceGroup() throws Exception {
        var httpClient = HttpClient.newHttpClient();
        var sdkConfig = new LocalSdkConfiguration("http://localhost:8080"); // test config
        var retry = RetryRegistry.ofDefaults();

        var factory = new AccountsFactory(httpClient, sdkConfig, retry);
        var aggregate = factory.builder()
                .withResourceGroup(SHORT_NAME, DISPLAY_NAME)
                .build();

        assertThat(aggregate).isNotNull();

    }


}
