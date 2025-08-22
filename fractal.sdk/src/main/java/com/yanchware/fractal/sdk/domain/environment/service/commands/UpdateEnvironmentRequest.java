package com.yanchware.fractal.sdk.domain.environment.service.commands;

import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public record UpdateEnvironmentRequest(
        EnvironmentIdValue managementEnvironmentId,
        String name,
        Collection<String> resourceGroups,
        Map<String, Object> parameters,
        String defaultCiCdProfileShortName) {
}