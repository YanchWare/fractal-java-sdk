package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

import com.yanchware.fractal.sdk.aggregates.Environment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnvironmentDto {
    private String id;
    private String displayName;
    private String parentId;
    private String parentType;

    public static EnvironmentDto fromEnvironment(Environment env) {
        return EnvironmentDto.builder()
                .id(env.getId())
                .displayName(env.getDisplayName())
                .parentId(env.getParentId())
                .parentType(env.getParentType())
                .build();
    }
}
