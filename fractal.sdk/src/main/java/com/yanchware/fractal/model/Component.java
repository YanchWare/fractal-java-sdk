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

    @AllArgsConstructor
    @Getter
    public static class Type {
        private InfrastructureDomain domain;

       @Override
        public String toString()
       {
           return domain.toString();
       }
    }

    public record Parameters(Map<String, Object> value) { }

    public interface Dependency {
        Type type();
    }
}
