package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.commands;

import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import lombok.Data;

import java.util.List;

@Data
public class CreateBlueprintCommandRequest {
    private String description;
    private boolean isPrivate;
    List<ComponentDto> components;
}
