package com.yanchware.fractal.sdk.domain.accounts;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
public class OrganizationFactory {
    private final HttpClient client;
    private final SdkConfiguration sdkConfiguration;
    private final RetryRegistry retryRegistry;

    public OrganizationBuilder builder() {
        return new OrganizationBuilder(client, sdkConfiguration, retryRegistry);
    }

    public static class OrganizationBuilder {
        private final OrganizationAggregate aggregate;
        private final OrganizationBuilder builder;

        public OrganizationBuilder(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {
            aggregate = create(client, sdkConfiguration, retryRegistry);
            builder = getBuilder();
        }

        protected OrganizationAggregate create(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {
            return new OrganizationAggregate(new RestAccountsService(client, sdkConfiguration, retryRegistry));
        }

        protected OrganizationBuilder getBuilder() {
            return this;
        }

        public OrganizationBuilder withResourceGroup(UUID organizationId, String shortName, String displayName) {
            aggregate.addOrganizationalResourceGroup(organizationId, shortName, displayName);
            return builder;
        }

        public OrganizationAggregate build() {
            return aggregate;
        }
    }
}
