package com.yanchware.fractal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public abstract class Component {
    private final Id id;
    private final Version version;
    private final Type type;
    private final String displayName;
    private final String description;
    private final Parameters parameters;
    private final OutputFields outputFields;
    private final List<Link> links;
    private final List<? extends Dependency> dependencies;

    public interface Type {
        InfrastructureDomain domain();
        PascalCaseString name();
    }

    public interface Dependency {
        Type componentType();
    }

    public record Id(KebabCaseString value) { }

    public record Parameters(Map<String, Object> value) { }

    public record OutputFields(Map<String, Object> value) { }

    public record Link(Id id, Settings settings) {
        public record Settings(Map<String, Object> value) {
        }
    }
}
