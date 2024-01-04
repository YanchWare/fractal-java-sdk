package com.yanchware.fractal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public abstract class Component {
    protected final Version version;
    protected final Type type;
    protected final String displayName;
    protected final String description;
    protected final List<? extends Dependency> dependencies;

    public record Type(InfrastructureDomain domain, InfrastructureTier tier, PascalCaseString name) {
    }

    public record Parameters(Map<String, Object> value) {
    }

    public interface Dependency {
        Type type();
    }
}
