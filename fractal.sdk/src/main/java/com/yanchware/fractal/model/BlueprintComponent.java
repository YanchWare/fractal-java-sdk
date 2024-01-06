package com.yanchware.fractal.model;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class BlueprintComponent extends Component {
    protected final Id id;
    protected final List<Service> services;
    protected final Component.Parameters parameters;
    protected final List<Link> links;

    public BlueprintComponent(
            Id id,
            Version version,
            Type type,
            String displayName,
            String description,
            List<Service> services,
            Component.Parameters parameters,
            List<Dependency> dependencies,
            List<Link> links)
    {
        super(version, type, displayName, description, dependencies);
        this.id = id;
        this.services = services;
        this.parameters = parameters;
        this.links = links;
    }

    @Getter
    public static class Type extends Component.Type {
        private PascalCaseString name;
        public Type(InfrastructureDomain domain, PascalCaseString name){
            super(domain);
        }

        @Override
        public String toString()
        {
            return String.format("%s.%s",
                    super.toString(),
                    name);
        }
    }

    public record Id(KebabCaseString value) {
    }

    public record Link(Id id, Parameters parameters) {
        public record Parameters(Map<String, Object> value) {
        }
    }

    public record Dependency(Component.Type type, Id id) implements Component.Dependency { }
}
