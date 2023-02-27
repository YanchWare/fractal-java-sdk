package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.utils.CollectionUtils;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CAAS_OCELOT;

@ToString(callSuper = true)
public class CaaSOcelot extends CaaSServiceMeshSecurityImpl implements LiveSystemComponent {

    public static OcelotBuilder builder() {
        return new OcelotBuilder();
    }

    public static class OcelotBuilder extends Builder<CaaSOcelot, OcelotBuilder> {

        @Override
        protected CaaSOcelot createComponent() {
            return new CaaSOcelot();
        }

        @Override
        protected OcelotBuilder getBuilder() {
            return this;
        }

        /**
         * Namespace where Ocelot will be instantiated
         *
         * @param namespace
         */
        public OcelotBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        /**
         * The name of the container platform where Ocelot will be instantiated
         *
         * @param containerPlatform
         */
        public OcelotBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        /**
         * The host URL that will be configured
         * @param host
         */
        public OcelotBuilder withHost(String host) {
            component.setHost(host);
            return builder;
        }

        /**
         * Host's owner email
         * @param hostOwnerEmail
         */
        public OcelotBuilder withHostOwnerEmail(String hostOwnerEmail) {
            component.setHostOwnerEmail(hostOwnerEmail);
            return builder;
        }

        /**
         * URL for CORS
         * @param corsOrigins
         */
        public OcelotBuilder withCorsOrigins(String corsOrigins) {
            String[] corsOriginsSplit = corsOrigins.split(",");
            return withCorsOrigins(List.of(corsOriginsSplit));
        }

        /**
         * URLs for CORS
         * @param corsOrigins
         */
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

        /**
         * Maximum age for cookies in seconds
         * Must be greater than 0
         * @param cookieMaxAgeSec
         */
        public OcelotBuilder withCookieMaxAgeSec(int cookieMaxAgeSec) {
            component.setCookieMaxAgeSec(cookieMaxAgeSec);
            return builder;
        }

        /**
         * Path prefix of the host to be configured
         * Default: '/*'
         * @param pathPrefix
         */
        public OcelotBuilder withPathPrefix(String pathPrefix) {
            component.setPathPrefix(pathPrefix);
            return builder;
        }

        @Override
        public CaaSOcelot build() {
            component.setType(CAAS_OCELOT);
            return super.build();
        }
    }
}
