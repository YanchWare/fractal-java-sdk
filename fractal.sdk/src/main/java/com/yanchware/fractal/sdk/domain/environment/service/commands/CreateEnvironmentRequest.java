package com.yanchware.fractal.sdk.domain.environment.service.commands;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public record CreateEnvironmentRequest(
        String name,
        Collection<UUID> resourceGroups,
        Map<String, Object> parameters) {
}
