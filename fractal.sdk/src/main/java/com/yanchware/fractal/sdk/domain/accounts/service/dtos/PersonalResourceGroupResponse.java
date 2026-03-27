package com.yanchware.fractal.sdk.domain.accounts.service.dtos;

import com.yanchware.fractal.sdk.domain.accounts.EntityStatus;
import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;

import java.util.Collection;

public record PersonalResourceGroupResponse(
        ResourceGroupId Id,
        String DisplayName,
        String Description,
        EntityStatus Status,
        String Icon,
        Collection<String> Livesystems,
        Collection<String> Fractals
) {}
