package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos.BlueprintComponentDto;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
public class CreateBlueprintCommandRequest {
    private String description;
    private boolean isPrivate;
    List<BlueprintComponentDto> components;

    public static CreateBlueprintCommandRequest fromLiveSystem(Collection<Component> components) {
        CreateBlueprintCommandRequest command = new CreateBlueprintCommandRequest();
        command.setPrivate(true);
        //command.setComponents(components.stream().map(BlueprintComponentDto::fromLiveSystemComponent).collect(Collectors.toList()));

        return command;
    }
}
