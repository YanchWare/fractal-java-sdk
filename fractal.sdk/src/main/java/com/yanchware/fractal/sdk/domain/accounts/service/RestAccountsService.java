package com.yanchware.fractal.sdk.domain.accounts.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.Service;
import com.yanchware.fractal.sdk.domain.accounts.service.commands.UpsertPersonalResourceGroupRequest;
import com.yanchware.fractal.sdk.domain.accounts.service.dtos.OrganizationalResourceGroupResponse;
import com.yanchware.fractal.sdk.domain.accounts.service.dtos.PersonalResourceGroupResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.utils.HttpUtils;
import io.github.resilience4j.retry.RetryRegistry;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.UUID;

import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;


public class RestAccountsService extends Service implements AccountsService {
    public RestAccountsService(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {
        super(client, sdkConfiguration, retryRegistry);
    }

    @Override
    public OrganizationalResourceGroupResponse upsertOrganizationalResourceGroup(UUID organizationId, String shortName) throws InstantiatorException {
        return null;
    }

    @Override
    public PersonalResourceGroupResponse upsertPersonalResourceGroup(String shortName, String displayName) throws InstantiatorException {
        return executeRequestWithRetries(
                "upsertPersonalResourceGroup",
                "upsertPersonalResourceGroup",
                client,
                retryRegistry,
                HttpUtils.buildPostRequest(
                        getResourceGroupsUri(shortName),
                        sdkConfiguration,
                        serializeSafely(new UpsertPersonalResourceGroupRequest(displayName, null, null))),
                new int[]{200},
                PersonalResourceGroupResponse.class);
    }

    @Override
    public PersonalResourceGroupResponse getByShortName(String shortName) throws InstantiatorException {
        return executeRequestWithRetries(
                "upsertPersonalResourceGroup",
                "upsertPersonalResourceGroup",
                client,
                retryRegistry,
                HttpUtils.buildGetRequest(
                        getResourceGroupsUri(shortName),
                        sdkConfiguration),
                new int[]{200},
                PersonalResourceGroupResponse.class);
    }

    private String serializeSafely(Object command) throws InstantiatorException {
        try {
            return serialize(command);
        } catch (JsonProcessingException e) {
            throw new InstantiatorException("Error serializing command because of JsonProcessing error", e);
        }
    }

    private URI getResourceGroupsUri(String shortName) {
        var path = String.format("%s/%s/",
                sdkConfiguration.getAccountsEndpoint(),
                "resourceGroups");
        return URI.create(path + shortName);
    }
}
