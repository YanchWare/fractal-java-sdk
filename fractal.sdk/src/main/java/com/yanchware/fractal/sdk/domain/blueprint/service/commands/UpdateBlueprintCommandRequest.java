package com.yanchware.fractal.sdk.domain.blueprint.service.commands;

import com.yanchware.fractal.sdk.domain.blueprint.FractalIdValue;
import com.yanchware.fractal.sdk.domain.blueprint.service.dtos.BlueprintComponentDto;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class UpdateBlueprintCommandRequest {
    private String resourceGroupId;
    private String fractalName;
    private String fractalVersion;
    private String description;
    private boolean isPrivate;
    Collection<BlueprintComponentDto> components;

    public static UpdateBlueprintCommandRequest fromCreateCommand(CreateBlueprintCommandRequest command, FractalIdValue fractalId) {
        String[] splitId = fractalId.toString().split("/|\\:");
        return UpdateBlueprintCommandRequest.builder()
                .resourceGroupId(splitId[0])
                .fractalName(splitId[1])
                .fractalVersion(splitId[2])
                .description(command.description())
                .isPrivate(command.isPrivate())
                .components(command.components())
                .build();
    }
}
