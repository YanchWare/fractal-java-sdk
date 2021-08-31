package com.yanchware.fractal.sdk.services.livesystemcontract.dtos;

import lombok.Data;

@Data
public class EnvironmentDto {
    private String id;
    private String displayName;
    private String parentId;
    private String parentType;
}
