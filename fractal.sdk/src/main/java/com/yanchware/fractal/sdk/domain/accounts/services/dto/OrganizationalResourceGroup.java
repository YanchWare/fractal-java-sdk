package com.yanchware.fractal.sdk.domain.accounts.services.dto;

import com.yanchware.fractal.sdk.domain.accounts.EntityStatus;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;
import java.util.Collection;

public record OrganizationalResourceGroup(
    ResourceGroupId Id,
    String DisplayName,
    String Description,
    EntityStatus Status,
    String Icon,
    Collection<String> Members,
    Collection<String> TeamsIds,
    Collection<String> ManagersIds,
    Collection<String> Livesystems,
    Collection<String> Fractals
) {}