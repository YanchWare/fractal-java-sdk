package com.yanchware.fractal.sdk.domain.blueprint;

import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;
import org.jetbrains.annotations.NotNull;

public record FractalIdValue(ResourceGroupId resourceGroupId, String name, String version) {
    @NotNull
    @Override
    public String toString(){
        return String.format("%s/%s:%s", resourceGroupId.toString(), name, version);
    }
}
