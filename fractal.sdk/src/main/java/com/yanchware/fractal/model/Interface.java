package com.yanchware.fractal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class Interface {
    private final Fractal.Id fractalId;
    private final List<Operation> operations;

    public record Operation(
            String name,
            String description,
            List<BlueprintComponent> components,
            Parameters parameters)
    {
        public record Parameters(Map<String, Object> value) { }
    }
}
