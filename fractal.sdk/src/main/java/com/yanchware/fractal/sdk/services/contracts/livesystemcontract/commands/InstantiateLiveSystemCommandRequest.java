package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.commands;

import com.yanchware.fractal.sdk.aggregates.LiveSystem;
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
public class InstantiateLiveSystemCommandRequest {
    private String liveSystemId;
    private String fractalId;
    private String description;
    private String provider; //not set, here just to match the request in liveSystem. We will have provider at LiveSystemComponent level
    private Map<String, LiveSystemComponentDto> blueprintMap;
    private EnvironmentDto environment;

    public static InstantiateLiveSystemCommandRequest fromLiveSystem(LiveSystem ls) {
        return InstantiateLiveSystemCommandRequest.builder()
                .liveSystemId(String.format("%s/%s", ls.getResourceGroupId(), ls.getName()))
                .fractalId(String.format("%s/%s:1.0", ls.getResourceGroupId(), ls.getName()))
                .description(ls.getDescription())
                .environment(EnvironmentDto.fromEnvironment(ls.getEnvironment()))
                .blueprintMap(LiveSystemComponentDto.fromLiveSystemComponents(ls.getComponents()))
                .build();
    }
}
