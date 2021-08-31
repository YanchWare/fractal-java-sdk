package com.yanchware.fractal.sdk.services.livesystemcontract.commands;

import com.yanchware.fractal.sdk.services.livesystemcontract.dtos.EnvironmentDto;
import com.yanchware.fractal.sdk.services.livesystemcontract.dtos.LiveSystemComponentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class InstantiateCommandRequest {
    private String liveSystemId;
    private String fractalId;
    private String type;
    private String description;
    private String provider;
    private Map<String, LiveSystemComponentDto> blueprintMap;
    private EnvironmentDto environmentDto;
}
