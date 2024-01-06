package com.yanchware.fractal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Blueprint {
    private Fractal.Id fractalId;
    private List<BlueprintComponent> components;
    private String displayName;
    private String description;
}
