package com.yanchware.fractal.sdk.domain.account;

import com.yanchware.fractal.sdk.domain.accounts.AccountAggregate;
import com.yanchware.fractal.sdk.domain.accounts.service.dtos.PersonalResourceGroupResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;
import com.yanchware.fractal.sdk.domain.accounts.EntityStatus;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class AccountAggregateTest {
    private static final String SHORT_NAME = "test-rg";
    private static final String DISPLAY_NAME = "Test Resource Group";

    @Test
    void createsPersonalResourceGroup_when_NotExists() throws InstantiatorException {
        var mockService = mock(com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService.class);
        var aggregate = new AccountAggregate(mockService);

        aggregate.addPersonalResourceGroup(SHORT_NAME, DISPLAY_NAME);

        when(mockService.getPersonalResourceGroupByShortName(eq(SHORT_NAME))).thenReturn(null);
        var createdResponse = new PersonalResourceGroupResponse(
                ResourceGroupId.fromString(String.format("Personal/%s/%s", UUID.randomUUID(), SHORT_NAME)),
                DISPLAY_NAME,
                null,
                EntityStatus.ACTIVE,
                null,
                List.of(),
                List.of()
        );
        when(mockService.upsertPersonalResourceGroup(eq(SHORT_NAME), eq(DISPLAY_NAME)))
                .thenReturn(createdResponse);


        aggregate.createOrUpdate();


        verify(mockService, times(1)).getPersonalResourceGroupByShortName(eq(SHORT_NAME));
        verify(mockService, times(1)).upsertPersonalResourceGroup(eq(SHORT_NAME), eq(DISPLAY_NAME));
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void skipsUpsert_when_AlreadyExists() throws InstantiatorException {

        var mockService = mock(com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService.class);
        var aggregate = new AccountAggregate(mockService);

        aggregate.addPersonalResourceGroup(SHORT_NAME, DISPLAY_NAME);

        var existingResponse = new PersonalResourceGroupResponse(
                ResourceGroupId.fromString(String.format("Personal/%s/%s", UUID.randomUUID(), SHORT_NAME)),
                "Existing RG",
                null,
                EntityStatus.ACTIVE,
                null,
                List.of(),
                List.of()
        );

        when(mockService.getPersonalResourceGroupByShortName(eq(SHORT_NAME))).thenReturn(existingResponse);

        aggregate.createOrUpdate();

        verify(mockService, times(1)).getPersonalResourceGroupByShortName(eq(SHORT_NAME));
        verify(mockService, never()).upsertPersonalResourceGroup(anyString(), anyString());
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void updatesDisplayName_when_AddedTwiceWithDifferentDisplayName() throws InstantiatorException {
        var mockService = mock(com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService.class);
        var aggregate = new AccountAggregate(mockService);


        aggregate.addPersonalResourceGroup(SHORT_NAME, "Old Name");
        aggregate.addPersonalResourceGroup(SHORT_NAME, "New Name");

        when(mockService.getPersonalResourceGroupByShortName(eq(SHORT_NAME))).thenReturn(null);
        when(mockService.upsertPersonalResourceGroup(eq(SHORT_NAME), eq("New Name")))
                .thenReturn(mock(PersonalResourceGroupResponse.class));

        aggregate.createOrUpdate();

        verify(mockService, times(1)).getPersonalResourceGroupByShortName(eq(SHORT_NAME));
        verify(mockService, times(1)).upsertPersonalResourceGroup(eq(SHORT_NAME), eq("New Name"));
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void doesNothing_when_NoResourceGroupsStaged() throws InstantiatorException {
        var mockService = mock(com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService.class);
        var aggregate = new AccountAggregate(mockService);

        aggregate.createOrUpdate();

        verifyNoMoreInteractions(mockService);
    }

    @Test
    void createsMultipleResourceGroups_when_MultipleStaged() throws InstantiatorException {
        var mockService = mock(com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService.class);
        var aggregate = new AccountAggregate(mockService);

        aggregate.addPersonalResourceGroup(SHORT_NAME + "a", DISPLAY_NAME + "A");
        aggregate.addPersonalResourceGroup(SHORT_NAME + "b", DISPLAY_NAME + "B");

        when(mockService.getPersonalResourceGroupByShortName(SHORT_NAME + "a")).thenReturn(null);
        when(mockService.getPersonalResourceGroupByShortName(SHORT_NAME + "b")).thenReturn(null);

        when(mockService.upsertPersonalResourceGroup(SHORT_NAME + "a", DISPLAY_NAME + "A")).thenReturn(mock(PersonalResourceGroupResponse.class));
        when(mockService.upsertPersonalResourceGroup(SHORT_NAME + "b", DISPLAY_NAME + "B")).thenReturn(mock(PersonalResourceGroupResponse.class));

        aggregate.createOrUpdate();

        verify(mockService, times(1)).getPersonalResourceGroupByShortName(SHORT_NAME + "a");
        verify(mockService, times(1)).getPersonalResourceGroupByShortName(SHORT_NAME + "b");
        verify(mockService, times(1)).upsertPersonalResourceGroup(SHORT_NAME + "a", DISPLAY_NAME + "A");
        verify(mockService, times(1)).upsertPersonalResourceGroup(SHORT_NAME + "b", DISPLAY_NAME + "B");
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void propagatesException_when_ServiceGetFails() throws InstantiatorException {
        var mockService = mock(com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService.class);
        var aggregate = new AccountAggregate(mockService);

        aggregate.addPersonalResourceGroup(SHORT_NAME, DISPLAY_NAME);

        when(mockService.getPersonalResourceGroupByShortName(eq(SHORT_NAME)))
                .thenThrow(new InstantiatorException("error"));

        assertThatThrownBy(aggregate::createOrUpdate)
                .isInstanceOf(InstantiatorException.class)
                .hasMessageContaining("error");
    }
}