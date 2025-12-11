package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub.valueobjects;

public record RetentionDescription(Long timeInHours, Long tombstoneTimeInHours, CleanupPolicies cleanupPolicy) {
}
