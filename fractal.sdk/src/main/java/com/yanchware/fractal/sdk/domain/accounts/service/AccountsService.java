package com.yanchware.fractal.sdk.domain.accounts.service;

import com.yanchware.fractal.sdk.domain.accounts.service.dtos.OrganizationalResourceGroupResponse;
import com.yanchware.fractal.sdk.domain.accounts.service.dtos.PersonalResourceGroupResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;

import java.util.UUID;

public interface AccountsService {
     OrganizationalResourceGroupResponse upsertOrganizationalResourceGroup(
     UUID organizationId,
     String shortName) throws InstantiatorException;

    PersonalResourceGroupResponse upsertPersonalResourceGroup(String shortName, String displayName) throws InstantiatorException;
    PersonalResourceGroupResponse getByShortName(String shortName) throws InstantiatorException;
}