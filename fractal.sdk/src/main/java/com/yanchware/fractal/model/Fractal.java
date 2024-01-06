package com.yanchware.fractal.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Fractal extends Component
{
    private final Id id;
    private final Blueprint blueprint;
    private final Interface fractalInterface;

    public Fractal(
            Id id,
            Type type,
            String displayName,
            String description,
            Blueprint blueprint,
            Interface fractalInterface,
            List<Dependency> dependencies)
    {
        super(id.version(), type, displayName, description, dependencies);
        this.id = id;
        this.blueprint = blueprint;
        this.fractalInterface = fractalInterface;
    }

    public record Id(BoundedContext.Id boundedContextId, KebabCaseString name, Version version) {
        @Override
        public String toString() {
            return String.format("%s/%s:%s",
                    boundedContextId.toString(),
                    name.value(),
                    version.toString());
        }
    }
}