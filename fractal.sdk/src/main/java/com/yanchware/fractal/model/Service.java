package com.yanchware.fractal.model;

import lombok.Getter;

import java.util.List;

public record Service(
        Type type,
        String DisplayName,
        List<Offer> offers)
{
    @Getter
    public static class Type extends BlueprintComponent.Type {
        private final BlueprintComponent.Type blueprintComponentType;
        private final InfrastructureTier tier;

        public Type(
                BlueprintComponent.Type blueprintComponentType,
                InfrastructureTier tier,
                PascalCaseString name)
        {
            super(blueprintComponentType.getDomain(), name);
            this.tier = tier;
            this.blueprintComponentType = blueprintComponentType;
        }

        @Override
        public String toString()
        {
            return String.format("%s.%s.%s",
                    blueprintComponentType.getDomain().toString(),
                    tier.toString(),
                    getName());
        }

    }
}