package com.yanchware.fractal.sdk.domain.livesystem;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.blueprint.FractalIdValue;
import com.yanchware.fractal.sdk.domain.environment.Environment;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import io.github.resilience4j.retry.RetryRegistry;

import java.net.http.HttpClient;
import java.util.*;

public class LiveSystemsFactory {

    private final HttpClient client;
    private final SdkConfiguration sdkConfiguration;
    private final RetryRegistry retryRegistry;

    public LiveSystemsFactory(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry)
    {
        this.client = client;
        this.sdkConfiguration = sdkConfiguration;
        this.retryRegistry = retryRegistry;
    }

    public LiveSystemBuilder builder() {
        return new LiveSystemBuilder(client, sdkConfiguration, retryRegistry);
    }

    public static class LiveSystemBuilder {
        private final LiveSystemAggregate liveSystem;
        private final LiveSystemBuilder builder;

        public LiveSystemBuilder(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {
            liveSystem = createLiveSystem(client, sdkConfiguration, retryRegistry);
            builder = getBuilder();
            liveSystem.setCreated(new Date());
        }

        protected LiveSystemAggregate createLiveSystem(
                HttpClient client,
                SdkConfiguration sdkConfiguration,
                RetryRegistry retryRegistry)
        {
            return new LiveSystemAggregate(client, sdkConfiguration, retryRegistry);
        }

        protected LiveSystemBuilder getBuilder() {
            return this;
        }

        public LiveSystemBuilder withId(LiveSystemIdValue liveSystemId) {
            liveSystem.setId(liveSystemId);
            return builder;
        }

        public LiveSystemBuilder withFractalId(FractalIdValue fractalId) {
            if(fractalId == null) {
                throw new IllegalArgumentException("Fractal id cannot be null or empty");
            }
            liveSystem.setFractalId(fractalId);
            return builder;
        }

        public LiveSystemBuilder withDescription(String description) {
            liveSystem.setDescription(description);
            return builder;
        }

        public LiveSystemBuilder withEnvironment(Environment environment) {
            liveSystem.setEnvironment(environment);
            return builder;
        }

        public LiveSystemBuilder withComponents(Collection<? extends LiveSystemComponent> components) {
            if (CollectionUtils.isBlank(components)) {
                return builder;
            }

            if (liveSystem.getComponents() == null) {
                liveSystem.setComponents(new ArrayList<>());
            }

            liveSystem.getComponents().addAll(components);
            return builder;
        }

        public LiveSystemBuilder withComponent(LiveSystemComponent component) {
            return withComponents(List.of(component));
        }

        public LiveSystemBuilder withStandardProvider(ProviderType provider) {
            liveSystem.setProvider(provider);
            return this;
        }

        public LiveSystemAggregate build() {
            Collection<String> errors = liveSystem.validate();

            if (!errors.isEmpty()) {
                throw new IllegalArgumentException(String.format(
                        "LiveSystem validation failed. Errors: %s",
                        Arrays.toString(errors.toArray())));
            }

            return liveSystem;
        }
    }
}
