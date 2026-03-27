package com.yanchware.fractal.sdk.domain.account;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.accounts.AccountAggregate;
import com.yanchware.fractal.sdk.domain.accounts.AccountsFactory;
import com.yanchware.fractal.sdk.domain.accounts.EntityStatus;
import com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService;
import com.yanchware.fractal.sdk.domain.accounts.service.dtos.PersonalResourceGroupResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupType;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AccountFactoryTest {

    private static final String BASE_URL = "http://localhost:8080";
    private static final String SHORT_NAME = "rg-1";
    private static final String DISPLAY_NAME = "RG One";

    @Test
    void builder_buildsAggregate_and_stagesPersonalResourceGroup() {
        var httpClient = HttpClient.newHttpClient();
        var sdkConfig = new LocalSdkConfiguration(BASE_URL);
        var retry = RetryRegistry.ofDefaults();

        var factory = new AccountsFactory(httpClient, sdkConfig, retry);
        var aggregate = factory.builder()
                .withResourceGroup(SHORT_NAME, DISPLAY_NAME)
                .build();

        assertThat(aggregate).isNotNull();
    }

    @Test
    void builder_isFluent_and_returnsSameInstance() {
        var httpClient = HttpClient.newHttpClient();
        var sdkConfig = new LocalSdkConfiguration(BASE_URL);
        var retry = RetryRegistry.ofDefaults();

        var builder = new AccountsFactory(httpClient, sdkConfig, retry).builder();
        var returned = builder.withResourceGroup(SHORT_NAME, DISPLAY_NAME);

        assertThat(returned).isSameAs(builder);
    }

    @Test
    void build_returnsSameAggregate_onRepeatedBuildCalls() {
        var httpClient = HttpClient.newHttpClient();
        var sdkConfig = new LocalSdkConfiguration(BASE_URL);
        var retry = RetryRegistry.ofDefaults();

        var builder = new AccountsFactory(httpClient, sdkConfig, retry).builder().withResourceGroup(SHORT_NAME, DISPLAY_NAME);
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

        when(mockService.getPersonalResourceGroupByShortName(eq(SHORT_NAME))).thenReturn(null);

        var createdResponse = new PersonalResourceGroupResponse(
                ResourceGroupId.fromString(String.format("%s/%s/%s", ResourceGroupType.PERSONAL, UUID.randomUUID(), SHORT_NAME)),
                DISPLAY_NAME,
                null,
                EntityStatus.ACTIVE,
                null,
                List.of(),
                List.of()
        );
        when(mockService.upsertPersonalResourceGroup(eq(SHORT_NAME), eq(DISPLAY_NAME))).thenReturn(createdResponse);

        var builder = new AccountsFactory.AccountBuilder(httpClient, sdkConfig, retry) {
            @Override
            protected AccountAggregate create(HttpClient client, SdkConfiguration cfg, RetryRegistry reg) {
                return new AccountAggregate(mockService);
            }
        };

        var aggregate = builder
                .withResourceGroup(SHORT_NAME, DISPLAY_NAME)
                .build();

        aggregate.createOrUpdate();

        verify(mockService, times(1)).getPersonalResourceGroupByShortName(eq(SHORT_NAME));
        verify(mockService, times(1)).upsertPersonalResourceGroup(eq(SHORT_NAME), eq(DISPLAY_NAME));
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void withResourceGroup_chainMultiple_stagesAll_and_callsService() throws InstantiatorException {
        var httpClient = HttpClient.newHttpClient();
        SdkConfiguration sdkConfig = new LocalSdkConfiguration(BASE_URL);
        var retry = RetryRegistry.ofDefaults();

        var mockService = mock(RestAccountsService.class);

        var shortNameA = SHORT_NAME + "-a";
        var displayA = DISPLAY_NAME + " A";
        var shortNameB = SHORT_NAME + "-b";
        var displayB = DISPLAY_NAME + " B";


        when(mockService.getPersonalResourceGroupByShortName(shortNameA)).thenReturn(null);
        when(mockService.getPersonalResourceGroupByShortName(shortNameB)).thenReturn(null);

        when(mockService.upsertPersonalResourceGroup(shortNameA, displayA)).thenReturn(mock(PersonalResourceGroupResponse.class));
        when(mockService.upsertPersonalResourceGroup(shortNameB, displayB)).thenReturn(mock(PersonalResourceGroupResponse.class));

        var builder = new AccountsFactory.AccountBuilder(httpClient, sdkConfig, retry) {
            @Override
            protected AccountAggregate create(HttpClient client, SdkConfiguration cfg, RetryRegistry reg) {
                return new AccountAggregate(mockService);
            }
        };

        var aggregate = builder
                .withResourceGroup(shortNameA, displayA)
                .withResourceGroup(shortNameB, displayB)
                .build();

        aggregate.createOrUpdate();

        verify(mockService, times(1)).getPersonalResourceGroupByShortName(shortNameA);
        verify(mockService, times(1)).getPersonalResourceGroupByShortName(shortNameB);
        verify(mockService, times(1)).upsertPersonalResourceGroup(shortNameA, displayA);
        verify(mockService, times(1)).upsertPersonalResourceGroup(shortNameB, displayB);
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void withResourceGroup_duplicateShortName_updatesStagedDisplayName() throws InstantiatorException {
        var httpClient = HttpClient.newHttpClient();
        SdkConfiguration sdkConfig = new LocalSdkConfiguration(BASE_URL);
        var retry = RetryRegistry.ofDefaults();

        var mockService = mock(RestAccountsService.class);

        var shortName = "rg-dup";
        var oldName = "Old";
        var newName = "New";

        when(mockService.getPersonalResourceGroupByShortName(shortName)).thenReturn(null);
        when(mockService.upsertPersonalResourceGroup(shortName, newName)).thenReturn(mock(PersonalResourceGroupResponse.class));

        var builder = new AccountsFactory.AccountBuilder(httpClient, sdkConfig, retry) {
            @Override
            protected AccountAggregate create(HttpClient client, SdkConfiguration cfg, RetryRegistry reg) {
                return new AccountAggregate(mockService);
            }
        };

        var aggregate = builder
                .withResourceGroup(shortName, oldName)
                .withResourceGroup(shortName, newName)
                .build();

        aggregate.createOrUpdate();

        verify(mockService, times(1)).getPersonalResourceGroupByShortName(shortName);
        verify(mockService, times(1)).upsertPersonalResourceGroup(shortName, newName);
        verifyNoMoreInteractions(mockService);
    }
}
