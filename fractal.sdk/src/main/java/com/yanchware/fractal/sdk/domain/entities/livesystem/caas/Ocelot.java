package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.utils.CollectionUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.OCELOT;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Ocelot extends CaaSServiceMeshSecurityImpl implements LiveSystemComponent {

    public static OcelotBuilder builder() {
        return new OcelotBuilder();
    }

    public static class OcelotBuilder extends Builder<Ocelot, OcelotBuilder> {

        @Override
        protected Ocelot createComponent() {
            return new Ocelot();
        }

        @Override
        protected OcelotBuilder getBuilder() {
            return this;
        }

        public OcelotBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public OcelotBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        public OcelotBuilder withHost(String host) {
            component.setHost(host);
            return builder;
        }

        public OcelotBuilder withHostOwnerEmail(String hostOwnerEmail) {
            component.setHostOwnerEmail(hostOwnerEmail);
            return builder;
        }

        public OcelotBuilder withCorsOrigins(String corsOrigins) {
            String[] corsOriginsSplit = corsOrigins.split(",");
            return withCorsOrigins(List.of(corsOriginsSplit));
        }

        public OcelotBuilder withCorsOrigins(List<String> corsOrigins) {
            if (CollectionUtils.isBlank(corsOrigins)) {
                return builder;
            }
            if (component.getCorsOrigins() == null) {
                component.setCorsOrigins(new ArrayList<>());
            }
            component.getCorsOrigins().addAll(corsOrigins);
            return builder;
        }

        public OcelotBuilder withCookieMaxAgeSec(int cookieMaxAgeSec) {
            component.setCookieMaxAgeSec(cookieMaxAgeSec);
            return builder;
        }

        public OcelotBuilder withPathPrefix(String pathPrefix) {
            component.setPathPrefix(pathPrefix);
            return builder;
        }

        @Override
        public Ocelot build() {
            component.setType(OCELOT);
            return super.build();
        }
    }
}
