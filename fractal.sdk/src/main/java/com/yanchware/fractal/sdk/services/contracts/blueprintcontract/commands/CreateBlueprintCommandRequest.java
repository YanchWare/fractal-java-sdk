package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands;

import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos.BlueprintComponentDto;
import lombok.Data;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

@Data
@ToString
public class CreateBlueprintCommandRequest {
    private String description;
    private boolean isPrivate;
    List<BlueprintComponentDto> components;

    public static CreateBlueprintCommandRequest fromLiveSystem(Collection<LiveSystemComponent> components, String description) {
        CreateBlueprintCommandRequest command = new CreateBlueprintCommandRequest();
        command.setPrivate(true);
        command.setDescription(description);
        command.setComponents(BlueprintComponentDto.fromLiveSystemComponents(components));
        return command;
    }
}
