package com.yanchware.fractal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Blueprint {
    private final Fractal.Id fractalId;
    private final List<BlueprintComponent> components;
}
