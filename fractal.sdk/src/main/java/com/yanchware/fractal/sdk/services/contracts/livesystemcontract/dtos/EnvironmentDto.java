package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnvironmentDto {
    private String id;
    private String displayName;
    private String parentId;
    private String parentType;
}
