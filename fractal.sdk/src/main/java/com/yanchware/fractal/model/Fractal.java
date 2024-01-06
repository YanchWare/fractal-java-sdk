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
            BlueprintComponent.Service.Type type,
            String displayName,
            String description,
            Component.Parameters parameters,
            Component.OutputFields outputFields,
            List<Link> links,
            List<BlueprintComponent.Dependency> dependencies,
            Blueprint blueprint,
            Interface fractalInterface)
    {
        super(
                id.toComponentId(),
                id.version(),
                type,
                displayName,
                description,
                parameters,
                outputFields,
                links,
                dependencies);
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

        public Component.Id toComponentId()
        {
            return new Component.Id(new KebabCaseString(String.format("%s-%s-%s-%s-%d-%d-%d",
                    boundedContextId.ownerType(),
                    boundedContextId.ownerId(),
                    boundedContextId.name(),
                    name.value(),
                    version.major(),
                    version.minor(),
                    version.patch())));
        }
    }
}