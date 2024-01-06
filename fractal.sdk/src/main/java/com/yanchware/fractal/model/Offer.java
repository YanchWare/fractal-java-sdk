package com.yanchware.fractal.model;

import lombok.Getter;

import java.util.List;

@Getter
public class Offer extends Component {
    private final Provider provider;

    protected Offer(
            Id id,
            Version version,
            BlueprintComponent.Service.Type type,
            String displayName,
            String description,
            Component.Parameters parameters,
            Component.OutputFields outputFields,
            List<Link> links,
            List<BlueprintComponent.Dependency> dependencies,
            Provider provider)
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
                dependencies);
        this.provider = provider;
    }
}