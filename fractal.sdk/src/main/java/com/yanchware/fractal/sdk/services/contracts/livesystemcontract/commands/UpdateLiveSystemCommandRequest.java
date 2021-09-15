package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands;

import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.EnvironmentDto;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentDto;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class UpdateLiveSystemCommandRequest {
    private String liveSystemId;
    private String fractalId;
    private String description;
    private String provider; //not set, here just to match the request in liveSystem. We will have provider at LiveSystemComponent level
    private Map<String, LiveSystemComponentDto> blueprintMap;
    private EnvironmentDto environment;

    public static UpdateLiveSystemCommandRequest fromInstantiateCommand(InstantiateLiveSystemCommandRequest command) {
        return UpdateLiveSystemCommandRequest.builder()
            .liveSystemId(command.getLiveSystemId())
            .fractalId(command.getFractalId())
            .description(command.getDescription())
            .environment(command.getEnvironment())
            .blueprintMap(command.getBlueprintMap())
            .build();
    }
}
