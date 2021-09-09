package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands;

import com.yanchware.fractal.sdk.aggregates.LiveSystem;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.EnvironmentDto;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.LiveSystemComponentDto;
import lombok.*;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
public class InstantiateLiveSystemCommandRequest {
    private String liveSystemId;
    private String fractalId;
    private String description;
    private String provider; //not set, here just to match the request in liveSystem. We will have provider at LiveSystemComponent level
    private Map<String, LiveSystemComponentDto> blueprintMap;
    private EnvironmentDto environmentDto;

    public static InstantiateLiveSystemCommandRequest fromLiveSystem(LiveSystem ls) {
        return InstantiateLiveSystemCommandRequest.builder()
                .liveSystemId(ls.getId())
                .fractalId(ls.getId())//resourceGroup/liveSystemId:1.0
                .description(ls.getDescription())
                .environmentDto(EnvironmentDto.fromEnvironment(ls.getEnvironment()))
                .blueprintMap(ls.getComponents().stream().map(LiveSystemComponentDto::fromLiveSystemComponent).collect(toMap(LiveSystemComponentDto::getId, x -> x)))
                .build();
    }
}
