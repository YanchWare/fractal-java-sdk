package com.yanchware.fractal.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class BlueprintComponent extends Component {
    private final List<Service> services;

    public BlueprintComponent(
            Id id,
            Version version,
            Type type,
            String displayName,
            String description,
            Component.Parameters parameters,
            Component.OutputFields outputFields,
            List<Link> links,
            List<Dependency> dependencies)
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

        this.services = new ArrayList<>();
    }

    public record Dependency(Component.Type componentType, Id id) implements Component.Dependency { }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public class Service {
        private Type type;
        private List<Offer> offers;

        public record Type(
                InfrastructureDomain domain,
                InfrastructureTier tier,
                PascalCaseString name) implements Component.Type
        {
            @Override
            public String toString()
            {
                return String.format("%s.%s.%s",
                        domain.toString(),
                        tier.toString(),
                        name);
            }
        }

        public Service withOffer(
                PascalCaseString name,
                String displayName,
                String description,
                Version version,
                Provider provider) {
            var offer = new Offer(
                    getId(),
                    version,
                    new Type(type.domain, type.tier, name),
                    displayName,
                    description,
                    getParameters(),
                    getOutputFields(),
                    getLinks(),
                    (List<Dependency>) getDependencies(),
                    provider);
            offers.add(offer);
            return this;
        }
    }

    public Service withService(InfrastructureTier tier, PascalCaseString name) {
        var service = new Service(new Service.Type(getType().domain(), tier, name), new ArrayList<>());
        services.add(service);
        return service;
    }
}
