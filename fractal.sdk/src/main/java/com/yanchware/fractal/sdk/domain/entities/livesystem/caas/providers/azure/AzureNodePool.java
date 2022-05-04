package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
@Builder(setterPrefix = "with")
public class AzureNodePool implements Validatable {
    private final static String NAME_IS_BLANK = "[AzureNodePool Validation] Name has not been defined and it is required";
    private final static String DISK_SIZE_UNDER_30GB = "[AzureNodePool Validation] Disk size must be at least 30GB";

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

        if (isBlank(name)) {
            errors.add(NAME_IS_BLANK);
        }

        if (diskSizeGb < 30) {
            errors.add(DISK_SIZE_UNDER_30GB);
        }

        return errors;
    }
}