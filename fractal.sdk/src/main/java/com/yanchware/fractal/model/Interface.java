package com.yanchware.fractal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Interface {
    private final Fractal.Id fractalId;
    private final List<Operation> operations;

    public record Operation(
            Id id,
            String displayName,
            String description,
            List<BlueprintComponent> components,
            Parameters parameters)
    {
        public record Id(UUID value) { }
        public record Parameters(Map<String, Object> value) { }
    }
}
