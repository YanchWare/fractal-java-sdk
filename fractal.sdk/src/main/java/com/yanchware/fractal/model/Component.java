package com.yanchware.fractal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public abstract class Component {
    protected final Id id;
    protected final Version version;
    protected final Type type;
    protected final String displayName;
    protected final String description;
    protected final Parameters parameters;
    protected final OutputFields outputFields;
    protected final List<Link> links;
    protected final List<? extends Dependency> dependencies;

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
