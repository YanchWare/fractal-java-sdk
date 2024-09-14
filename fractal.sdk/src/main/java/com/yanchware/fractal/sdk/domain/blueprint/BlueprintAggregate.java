package com.yanchware.fractal.sdk.domain.blueprint;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.blueprint.service.BlueprintService;
import com.yanchware.fractal.sdk.domain.blueprint.service.dtos.BlueprintComponentDto;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.Getter;

import java.net.http.HttpClient;
import java.util.Collection;

@Getter
public class BlueprintAggregate {
    private final BlueprintService service;
    private final String fractalId;
    private final String description;
    private final boolean isPrivate;
    private final Collection<BlueprintComponentDto> components;

    public BlueprintAggregate(
            HttpClient client,
            SdkConfiguration sdkConfiguration,
            RetryRegistry retryRegistry,
            String fractalId,
            String description,
            boolean isPrivate,
            // TODO: We should find a way to fix this instead to have DTOs in the Aggregate 🤮
            Collection<BlueprintComponentDto> components)
    {
        this.fractalId = fractalId;
        this.description = description;
        this.isPrivate = isPrivate;
        this.components = components;
        this.service = new BlueprintService(client, sdkConfiguration, retryRegistry);
    }

    public void createOrUpdate() throws InstantiatorException {
        service.createOrUpdate(fractalId, description, isPrivate, components);
    }
}
