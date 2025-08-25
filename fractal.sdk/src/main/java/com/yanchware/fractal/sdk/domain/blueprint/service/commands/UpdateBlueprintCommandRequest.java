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
        String[] splitId = fractalId.toString().split("[/:]");
        return UpdateBlueprintCommandRequest.builder()
                .resourceGroupId(String.format("%s/%s/%s", splitId[0], splitId[1], splitId[2]))
                .fractalName(splitId[3])
                .fractalVersion(splitId[4])
                .description(command.description())
                .isPrivate(command.isPrivate())
                .components(command.components())
                .build();
    }
}
