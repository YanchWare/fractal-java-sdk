package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.AMBASSADOR;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Ambassador extends CaaSAPIGatewayImpl {

    protected Ambassador() {
    }

    public static AmbassadorBuilder builder() {
        return new AmbassadorBuilder();
    }

    public static class AmbassadorBuilder extends Builder<Ambassador, AmbassadorBuilder> {
        @Override
        protected Ambassador createComponent() {
            return new Ambassador();
        }

        @Override
        protected AmbassadorBuilder getBuilder() {
            return this;
        }

        public AmbassadorBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public AmbassadorBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        @Override
        public Ambassador build() {
            component.setType(AMBASSADOR);
            return super.build();
        }
    }
}
