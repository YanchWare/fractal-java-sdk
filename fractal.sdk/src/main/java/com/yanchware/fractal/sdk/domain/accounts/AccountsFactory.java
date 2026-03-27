package com.yanchware.fractal.sdk.domain.accounts;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;

@Slf4j
@AllArgsConstructor
public class AccountsFactory {
    private final HttpClient client;
    private final SdkConfiguration sdkConfiguration;
    private final RetryRegistry retryRegistry;

    public AccountBuilder builder() {
        return new AccountBuilder(client, sdkConfiguration, retryRegistry);
    }

    public static class AccountBuilder {
        private final AccountAggregate aggregate;
        private final AccountBuilder builder;

        public AccountBuilder(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {
            aggregate = create(client, sdkConfiguration, retryRegistry);
            builder = getBuilder();
        }

        protected AccountAggregate create(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {
            return new AccountAggregate(new RestAccountsService(client, sdkConfiguration, retryRegistry));
        }

        protected AccountsFactory.AccountBuilder getBuilder() {
            return this;
        }

        public AccountBuilder withResourceGroup(String shortName, String displayName) {
            aggregate.addPersonalResourceGroup(shortName, displayName);
            return builder;
        }

        public AccountAggregate build() {
            return aggregate;
        }
    }
}
