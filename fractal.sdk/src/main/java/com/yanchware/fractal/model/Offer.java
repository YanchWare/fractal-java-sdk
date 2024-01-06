package com.yanchware.fractal.model;

import lombok.Getter;

public record Offer(
        Type type,
        Provider provider,
        String DisplayName) {

    @Getter
    public static class Type extends Service.Type {
        public Type(Service.Type serviceType, PascalCaseString name)
        {
            super(serviceType.getBlueprintComponentType(), serviceType.getTier(), name);
        }
    }
}
