package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands;

import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos.BlueprintComponentDto;
import lombok.Builder;

import java.util.List;

@Builder
public class UpdateBlueprintCommandRequest {
    private String resourceGroupId;
    private String fractalName;
    private String fractalVersion;
    private String description;
    private boolean isPrivate;
    List<BlueprintComponentDto> components;

    public static UpdateBlueprintCommandRequest fromCreateCommand(CreateBlueprintCommandRequest command, String fractalId) {
        String[] splitId = fractalId.split("/|\\:");
        return UpdateBlueprintCommandRequest.builder()
                .resourceGroupId(splitId[0])
                .fractalName(splitId[1])
                .fractalVersion(splitId[2])
                .description(command.getDescription())
                .isPrivate(command.isPrivate())
                .components(command.getComponents())
                .build();
    }
}
