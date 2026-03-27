package com.yanchware.fractal.sdk.domain.account;

import com.yanchware.fractal.sdk.domain.accounts.OrganizationFactory;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.accounts.OrganizationAggregate;
import com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService;
import com.yanchware.fractal.sdk.domain.accounts.service.dtos.OrganizationalResourceGroupResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class OrganizationFactoryTest {
    private static final String BASE_URL = "http://localhost:8080";
    private static final String SHORT_NAME = "rg-1";
    private static final String DISPLAY_NAME = "RG One";

    @Test
    void builder_buildsAggregate_and_stagesOrganizationalResourceGroup() {
        var httpClient = HttpClient.newHttpClient();
        var sdkConfig = new LocalSdkConfiguration(BASE_URL);
        var retry = RetryRegistry.ofDefaults();

        var orgId = UUID.randomUUID();

        var factory = new OrganizationFactory(httpClient, sdkConfig, retry);
        var aggregate = factory.builder()
                .withResourceGroup(orgId, SHORT_NAME, DISPLAY_NAME)
                .build();

        assertThat(aggregate).isNotNull();
    }

    @Test
    void builder_isFluent_and_returnsSameInstance() {
        var httpClient = HttpClient.newHttpClient();
        var sdkConfig = new LocalSdkConfiguration(BASE_URL);
        var retry = RetryRegistry.ofDefaults();

        var orgId = UUID.randomUUID();

        var builder = new OrganizationFactory(httpClient, sdkConfig, retry).builder();
        var returned = builder.withResourceGroup(orgId, SHORT_NAME, DISPLAY_NAME);

        assertThat(returned).isSameAs(builder);
    }

    @Test
    void build_returnsSameAggregate_onRepeatedBuildCalls() {
        var httpClient = HttpClient.newHttpClient();
        var sdkConfig = new LocalSdkConfiguration(BASE_URL);
        var retry = RetryRegistry.ofDefaults();

        var orgId = UUID.randomUUID();
        var builder = new OrganizationFactory(httpClient, sdkConfig, retry).builder().withResourceGroup(orgId, SHORT_NAME, DISPLAY_NAME);

        var aggregate1 = builder.build();
        var aggregate2 = builder.build();

        assertThat(aggregate1).isSameAs(aggregate2);
    }

    @Test
    void withResourceGroup_stages_and_createOrUpdate_callsService_when_NotExists() throws InstantiatorException {
        var httpClient = HttpClient.newHttpClient();
        SdkConfiguration sdkConfig = new LocalSdkConfiguration(BASE_URL);
        var retry = RetryRegistry.ofDefaults();

        var mockService = mock(RestAccountsService.class);

        var orgId = UUID.randomUUID();
        var shortName = SHORT_NAME;
        var displayName = DISPLAY_NAME;

        when(mockService.getOrganizationalResourceGroupByShortName(eq(orgId), eq(shortName))).thenReturn(null);
        when(mockService.upsertOrganizationalResourceGroup(eq(orgId), eq(shortName), eq(displayName)))
                .thenReturn(mock(OrganizationalResourceGroupResponse.class));

        var builder = new OrganizationFactory.OrganizationBuilder(httpClient, sdkConfig, retry) {
            @Override
            protected OrganizationAggregate create(HttpClient client, SdkConfiguration cfg, RetryRegistry reg) {
                return new OrganizationAggregate(mockService);
            }
        };

        var aggregate = builder
                .withResourceGroup(orgId, shortName, displayName)
                .build();

        aggregate.createOrUpdate();

        verify(mockService, times(1)).getOrganizationalResourceGroupByShortName(eq(orgId), eq(shortName));
        verify(mockService, times(1)).upsertOrganizationalResourceGroup(eq(orgId), eq(shortName), eq(displayName));
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void withResourceGroup_chainMultiple_stagesAll_and_callsService() throws InstantiatorException {
        var httpClient = HttpClient.newHttpClient();
        SdkConfiguration sdkConfig = new LocalSdkConfiguration(BASE_URL);
        var retry = RetryRegistry.ofDefaults();

        var mockService = mock(RestAccountsService.class);

        var orgId = UUID.randomUUID();
        var shortNameA = SHORT_NAME + "-a";
        var displayA = DISPLAY_NAME + " A";
        var shortNameB = SHORT_NAME + "-b";
        var displayB = DISPLAY_NAME + " B";

        when(mockService.getOrganizationalResourceGroupByShortName(eq(orgId), eq(shortNameA))).thenReturn(null);
        when(mockService.getOrganizationalResourceGroupByShortName(eq(orgId), eq(shortNameB))).thenReturn(null);

        when(mockService.upsertOrganizationalResourceGroup(eq(orgId), eq(shortNameA), eq(displayA)))
                .thenReturn(mock(OrganizationalResourceGroupResponse.class));
        when(mockService.upsertOrganizationalResourceGroup(eq(orgId), eq(shortNameB), eq(displayB)))
                .thenReturn(mock(OrganizationalResourceGroupResponse.class));

        var builder = new OrganizationFactory.OrganizationBuilder(httpClient, sdkConfig, retry) {
            @Override
            protected OrganizationAggregate create(HttpClient client, SdkConfiguration cfg, RetryRegistry reg) {
                return new OrganizationAggregate(mockService);
            }
        };

        var aggregate = builder
                .withResourceGroup(orgId, shortNameA, displayA)
                .withResourceGroup(orgId, shortNameB, displayB)
                .build();

        aggregate.createOrUpdate();

        verify(mockService, times(1)).getOrganizationalResourceGroupByShortName(eq(orgId), eq(shortNameA));
        verify(mockService, times(1)).getOrganizationalResourceGroupByShortName(eq(orgId), eq(shortNameB));
        verify(mockService, times(1)).upsertOrganizationalResourceGroup(eq(orgId), eq(shortNameA), eq(displayA));
        verify(mockService, times(1)).upsertOrganizationalResourceGroup(eq(orgId), eq(shortNameB), eq(displayB));
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void withResourceGroup_duplicateIdentity_updatesStagedDisplayName() throws InstantiatorException {
        var httpClient = HttpClient.newHttpClient();
        SdkConfiguration sdkConfig = new LocalSdkConfiguration(BASE_URL);
        var retry = RetryRegistry.ofDefaults();

        var mockService = mock(RestAccountsService.class);

        var orgId = UUID.randomUUID();
        var shortName = SHORT_NAME;
        var newDisplayName = DISPLAY_NAME + " v2";

        when(mockService.getOrganizationalResourceGroupByShortName(eq(orgId), eq(shortName))).thenReturn(null);
        when(mockService.upsertOrganizationalResourceGroup(eq(orgId), eq(shortName), eq(newDisplayName)))
                .thenReturn(mock(OrganizationalResourceGroupResponse.class));

        var builder = new OrganizationFactory.OrganizationBuilder(httpClient, sdkConfig, retry) {
            @Override
            protected OrganizationAggregate create(HttpClient client, SdkConfiguration cfg, RetryRegistry reg) {
                return new OrganizationAggregate(mockService);
            }
        };

        var aggregate = builder
                .withResourceGroup(orgId, shortName, DISPLAY_NAME)
                .withResourceGroup(orgId, shortName, newDisplayName) // overwrite staged display
                .build();

        aggregate.createOrUpdate();

        verify(mockService, times(1)).getOrganizationalResourceGroupByShortName(eq(orgId), eq(shortName));
        verify(mockService, times(1)).upsertOrganizationalResourceGroup(eq(orgId), eq(shortName), eq(newDisplayName));
        verifyNoMoreInteractions(mockService);
    }
}
