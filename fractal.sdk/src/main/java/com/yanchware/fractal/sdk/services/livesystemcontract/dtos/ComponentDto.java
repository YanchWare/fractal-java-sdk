package com.yanchware.fractal.sdk.services.livesystemcontract.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;
import java.util.Set;

@Data
@EqualsAndHashCode
public class ComponentDto {
    private String id;
    public String displayName;
    private String description;
    private String type;
    private String version;
    private Map<String, Object> parameters;
    private Set<String> dependencies;
    private Set<String> links;
}
