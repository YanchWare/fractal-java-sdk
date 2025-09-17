package com.yanchware.fractal.sdk.domain.account;

import com.yanchware.fractal.sdk.domain.accounts.AccountAggregate;
import com.yanchware.fractal.sdk.domain.accounts.service.dtos.PersonalResourceGroupResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;
import com.yanchware.fractal.sdk.domain.accounts.EntityStatus;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

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

        // Act
        aggregate.createOrUpdate();

        // Assert
        verify(mockService, times(1)).getPersonalResourceGroupByShortName(eq(SHORT_NAME));
        verify(mockService, times(1)).upsertPersonalResourceGroup(eq(SHORT_NAME), eq(DISPLAY_NAME));
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void skipsUpsert_when_AlreadyExists() throws InstantiatorException {
        // Arrange
        var mockService = mock(com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService.class);
        var aggregate = new AccountAggregate(mockService);

        var shortName = "short-name-rg";
        var displayName = "Display Name RG";
        aggregate.addPersonalResourceGroup(shortName, displayName);

        var existingResponse = new PersonalResourceGroupResponse(
                ResourceGroupId.fromString(String.format("Personal/%s/%s", UUID.randomUUID(), shortName)),
                "Existing Bob",
                null,
                EntityStatus.ACTIVE,
                null,
                List.of(),
                List.of()
        );

        when(mockService.getPersonalResourceGroupByShortName(eq(shortName))).thenReturn(existingResponse);

        aggregate.createOrUpdate();

        verify(mockService, times(1)).getPersonalResourceGroupByShortName(eq(shortName));
        verify(mockService, never()).upsertPersonalResourceGroup(anyString(), anyString());
        verifyNoMoreInteractions(mockService);
    }
}