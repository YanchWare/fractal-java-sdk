package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Collection;

@Builder
public class AzureNodePool implements Validatable {
    private final static String NAME_IS_NULL_OR_EMPTY = "AzureNodePool name has not been defined and it is required";
    private final static String DISK_SIZE_UNDER_30GB = "AzureNodePool disk size must be at least 30GB";

    private int diskSizeGb;
    private int initialNodeCount;
    private AzureMachineType machineType;
    private int maxNodeCount;
    private int maxSurge;
    private int minNodeCount;
    private String name;
    private int maxPodsPerNode;
    private AzureOsType osType;

    @Override
    public Collection<String> validate() {
        Collection<String> errors = new ArrayList<>();

        if (name == null || name.isEmpty() || name.isBlank()) {
            errors.add(NAME_IS_NULL_OR_EMPTY);
        }

        if (diskSizeGb < 30) {
            errors.add(DISK_SIZE_UNDER_30GB);
        }

        return errors;
    }
}
