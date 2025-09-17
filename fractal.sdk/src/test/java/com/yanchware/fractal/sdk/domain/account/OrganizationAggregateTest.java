package com.yanchware.fractal.sdk.domain.account;

import com.yanchware.fractal.sdk.domain.accounts.OrganizationAggregate;
import com.yanchware.fractal.sdk.domain.accounts.service.dtos.OrganizationalResourceGroupResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class OrganizationAggregateTest {
    private static final String SHORT_NAME = "test-rg";
    private static final String DISPLAY_NAME = "Test Resource Group";

    @Test
    void createsOrganizationalResourceGroup_when_NotExists() throws InstantiatorException {
        var mockService = mock(com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService.class);
        var aggregate = new OrganizationAggregate(mockService);

        var orgId = UUID.randomUUID();
        aggregate.addOrganizationalResourceGroup(orgId, SHORT_NAME, DISPLAY_NAME);

        when(mockService.getOrganizationalResourceGroupByShortName(eq(orgId), eq(SHORT_NAME))).thenReturn(null);
        when(mockService.upsertOrganizationalResourceGroup(eq(orgId), eq(SHORT_NAME), eq(DISPLAY_NAME))).thenReturn(mock(OrganizationalResourceGroupResponse.class));

        aggregate.createOrUpdate();

        verify(mockService, times(1)).getOrganizationalResourceGroupByShortName(eq(orgId), eq(SHORT_NAME));
        verify(mockService, times(1)).upsertOrganizationalResourceGroup(eq(orgId), eq(SHORT_NAME), eq(DISPLAY_NAME));
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void skipsUpsert_when_AlreadyExists() throws InstantiatorException {
        var mockService = mock(com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService.class);
        var aggregate = new OrganizationAggregate(mockService);

        var orgId = UUID.randomUUID();
        aggregate.addOrganizationalResourceGroup(orgId, SHORT_NAME, DISPLAY_NAME);

        when(mockService.getOrganizationalResourceGroupByShortName(eq(orgId), eq(SHORT_NAME))).thenReturn(mock(OrganizationalResourceGroupResponse.class));

        aggregate.createOrUpdate();

        verify(mockService, times(1)).getOrganizationalResourceGroupByShortName(eq(orgId), eq(SHORT_NAME));
        verify(mockService, never()).upsertOrganizationalResourceGroup(eq(orgId), eq(SHORT_NAME), anyString());
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void updatesDisplayName_when_AddedTwiceWithDifferentDisplayName() throws InstantiatorException {
        var mockService = mock(com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService.class);
        var aggregate = new OrganizationAggregate(mockService);

        var orgId = UUID.randomUUID();
        aggregate.addOrganizationalResourceGroup(orgId, SHORT_NAME, DISPLAY_NAME);
        aggregate.addOrganizationalResourceGroup(orgId, SHORT_NAME, DISPLAY_NAME + " v2");

        when(mockService.getOrganizationalResourceGroupByShortName(eq(orgId), eq(SHORT_NAME))).thenReturn(null);
        when(mockService.upsertOrganizationalResourceGroup(eq(orgId), eq(SHORT_NAME), eq(DISPLAY_NAME + " v2"))).thenReturn(mock(OrganizationalResourceGroupResponse.class));

        aggregate.createOrUpdate();

        verify(mockService, times(1)).getOrganizationalResourceGroupByShortName(eq(orgId), eq(SHORT_NAME));
        verify(mockService, times(1)).upsertOrganizationalResourceGroup(eq(orgId), eq(SHORT_NAME), eq(DISPLAY_NAME + " v2"));
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void doesNothing_when_NoResourceGroupsStaged() throws InstantiatorException {
        var mockService = mock(com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService.class);
        var aggregate = new OrganizationAggregate(mockService);

        aggregate.createOrUpdate();

        verifyNoMoreInteractions(mockService);
    }

    @Test
    void createsMultipleResourceGroups_when_MultipleStaged() throws InstantiatorException {
        var mockService = mock(com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService.class);
        var aggregate = new OrganizationAggregate(mockService);

        var orgId = UUID.randomUUID();
        var shortNameA = SHORT_NAME + "-a";
        var shortNameB = SHORT_NAME + "-b";
        var displayA = DISPLAY_NAME + " A";
        var displayB = DISPLAY_NAME + " B";

        aggregate.addOrganizationalResourceGroup(orgId, shortNameA, displayA);
        aggregate.addOrganizationalResourceGroup(orgId, shortNameB, displayB);

        when(mockService.getOrganizationalResourceGroupByShortName(eq(orgId), eq(shortNameA))).thenReturn(null);
        when(mockService.getOrganizationalResourceGroupByShortName(eq(orgId), eq(shortNameB))).thenReturn(null);

        when(mockService.upsertOrganizationalResourceGroup(eq(orgId), eq(shortNameA), eq(displayA))).thenReturn(mock(OrganizationalResourceGroupResponse.class));
        when(mockService.upsertOrganizationalResourceGroup(eq(orgId), eq(shortNameB), eq(displayB))).thenReturn(mock(OrganizationalResourceGroupResponse.class));

        aggregate.createOrUpdate();

        verify(mockService, times(1)).getOrganizationalResourceGroupByShortName(eq(orgId), eq(shortNameA));
        verify(mockService, times(1)).getOrganizationalResourceGroupByShortName(eq(orgId), eq(shortNameB));
        verify(mockService, times(1)).upsertOrganizationalResourceGroup(eq(orgId), eq(shortNameA), eq(displayA));
        verify(mockService, times(1)).upsertOrganizationalResourceGroup(eq(orgId), eq(shortNameB), eq(displayB));
        verifyNoMoreInteractions(mockService);
    }

    @Test
    void propagatesException_when_ServiceGetFails() throws InstantiatorException {
        var mockService = mock(com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService.class);
        var aggregate = new OrganizationAggregate(mockService);

        var orgId = UUID.randomUUID();
        aggregate.addOrganizationalResourceGroup(orgId, SHORT_NAME, DISPLAY_NAME);

        when(mockService.getOrganizationalResourceGroupByShortName(eq(orgId), eq(SHORT_NAME))).thenThrow(new InstantiatorException("error"));

        assertThatThrownBy(aggregate::createOrUpdate).isInstanceOf(InstantiatorException.class).hasMessageContaining("error");
    }
}
