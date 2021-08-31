package com.yanchware.fractal.sdk.services.livesystemcontract.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class InstantiateCommandRequest {
    private String liveSystemId;
    private String fractalId;
    private String type;
    private String description;
    private String provider;
    //private Map<String, LiveSystemComponentDto> blueprintMap;
    //private EnvironmentDto environmentDto;
}
