package com.yanchware.fractal.model;

import lombok.Getter;

import java.util.List;

@Getter
public class LiveSystemComponent extends Offer {
    private Status status;

    public LiveSystemComponent(
            Id id,
            Version version,
            BlueprintComponent.Service.Type type,
            String displayName,
            String description,
            Component.Parameters parameters,
            Component.OutputFields outputFields,
            List<Link> links,
            List<BlueprintComponent.Dependency> dependencies,
            Provider provider,
            Status status)
    {
        super(
                id,
                version,
                type,
                displayName,
                description,
                parameters,
                outputFields,
                links,
                dependencies,
                provider);
        this.status = status;
    }

    public enum Status {
        INSTANTIATING,
        ACTIVE,
        MUTATING,
        DELETING,
        FAILED
    }
}
