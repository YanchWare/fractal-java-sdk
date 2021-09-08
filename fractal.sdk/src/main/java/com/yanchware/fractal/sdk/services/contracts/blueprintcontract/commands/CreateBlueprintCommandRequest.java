package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands;

import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos.BlueprintComponentDto;
import lombok.Data;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class CreateBlueprintCommandRequest {
    private String description;gi
    private boolean isPrivate;
    List<BlueprintComponentDto> components;

    public static CreateBlueprintCommandRequest fromLiveSystem(Collection<LiveSystemComponent> components, String description) {
        CreateBlueprintCommandRequest command = new CreateBlueprintCommandRequest();
        command.setPrivate(true);
        command.setDescription(description);
        command.setComponents(components.stream().map(BlueprintComponentDto::fromLiveSystemComponent).collect(toList()));
        return command;
    }
}
