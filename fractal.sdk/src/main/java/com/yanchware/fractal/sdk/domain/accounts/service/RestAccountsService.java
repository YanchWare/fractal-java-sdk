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
    public OrganizationalResourceGroupResponse upsertOrganizationalResourceGroup(UUID organizationId, String shortName, String displayName) throws InstantiatorException {
        return executeRequestWithRetries(
                "upsertOrganizationalResourceGroup",
                shortName,
                client,
                retryRegistry,
                HttpUtils.buildPostRequest(
                        getOrganizationalResourceGroupsUri(organizationId,shortName),
                        sdkConfiguration,
                        serializeSafely(new UpsertPersonalResourceGroupRequest(displayName, null, null))),
                new int[]{200},
                OrganizationalResourceGroupResponse.class);
    }

    @Override
    public OrganizationalResourceGroupResponse getOrganizationalResourceGroupByShortName(UUID organizationId, String shortName) throws InstantiatorException {
        return executeRequestWithRetries(
                "getOrganizationalResourceGroupByShortName",
                organizationId + "/" + shortName,
                client,
                retryRegistry,
                HttpUtils.buildGetRequest(
                        getOrganizationalResourceGroupsUri(organizationId, shortName),
                        sdkConfiguration),
                new int[]{200, 404},
                OrganizationalResourceGroupResponse.class);
    }


    @Override
    public PersonalResourceGroupResponse upsertPersonalResourceGroup(String shortName, String displayName) throws InstantiatorException {
        return executeRequestWithRetries(
                "upsertPersonalResourceGroup",
                shortName,
                client,
                retryRegistry,
                HttpUtils.buildPostRequest(
                        getPersonalResourceGroupsUri(shortName),
                        sdkConfiguration,
                        serializeSafely(new UpsertPersonalResourceGroupRequest(displayName, null, null))),
                new int[]{200},
                PersonalResourceGroupResponse.class);
    }

    @Override
    public PersonalResourceGroupResponse getPersonalResourceGroupByShortName(String shortName) throws InstantiatorException {
        return executeRequestWithRetries(
                "getPersonalResourceGroupByShortName",
                shortName,
                client,
                retryRegistry,
                HttpUtils.buildGetRequest(
                        getPersonalResourceGroupsUri(shortName),
                        sdkConfiguration),
                new int[]{200, 404},
                PersonalResourceGroupResponse.class);
    }

    private URI getPersonalResourceGroupsUri(String shortName) {
        var path = String.format("%s/%s/",
                sdkConfiguration.getAccountsEndpoint(),
                "resourcegroups"); // or your existing segment if already correct
        return URI.create(path + shortName);
    }

    private URI getOrganizationalResourceGroupsUri(UUID organizationId, String shortName) {
        var base = String.format("%s/%s/%s/%s/",
                sdkConfiguration.getAccountsEndpoint(),
                "organizations",
                organizationId,
                "resourcegroups");
        return URI.create(base + shortName);
    }


    private String serializeSafely(Object command) throws InstantiatorException {
        try {
            return serialize(command);
        } catch (JsonProcessingException e) {
            throw new InstantiatorException("Error serializing command because of JsonProcessing error", e);
        }
    }
}
