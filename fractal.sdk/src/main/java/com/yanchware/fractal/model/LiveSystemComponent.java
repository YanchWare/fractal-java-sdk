package com.yanchware.fractal.model;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class LiveSystemComponent extends Component {
    private final BlueprintComponent.Id id;
    private final Status status;
    private final Component.Parameters parameters;
    private final OutputFields outputFields;
    private final Provider provider;
    private final List<BlueprintComponent.Link> links;

    public LiveSystemComponent(
            BlueprintComponent.Id id,
            Version version,
            Type type,
            String displayName,
            String description,
            List<BlueprintComponent.Dependency> dependencies,
            Status status,
            Component.Parameters parameters,
            OutputFields outputFields,
            Provider provider,
            List<BlueprintComponent.Link> links)
    {
        super(version, type, displayName, description, dependencies);
        this.id = id;
        this.status = status;
        this.parameters = parameters;
        this.outputFields = outputFields;
        this.provider = provider;
        this.links = links;
    }

    public enum Status {
        INSTANTIATING,
        ACTIVE,
        MUTATING,
        DELETING,
        FAILED
    }

    public record OutputFields(Map<String, Object> value) { }
}
