package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSServiceMeshSecurity;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.OCELOT;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Ocelot extends CaaSServiceMeshSecurity implements LiveSystemComponent {
    private final static String NAMESPACE_IS_NULL_OR_EMPTY = "[Ocelot Validation] Namespace has not been defined and it is required";
    private final static String CONTAINER_PLATFORM_IS_EMPTY = "[Ocelot Validation] ContainerPlatform defined was either empty or blank and it is required";
    private final static String HOST_IS_NULL_OR_EMPTY = "[Ocelot Validation] Host has not been defined and it is required";
    private final static String HOST_OWNER_EMAIL_IS_NULL_OR_EMPTY = "[Ocelot Validation] Host Owner Email has not been defined and it is required";
    private final static String COOKIE_MAX_AGE_IS_NULL_OR_EMPTY = "[Ocelot Validation] Cookie max age has not been defined or is less than or equal to 0 and must be a positive number (in seconds)";

    private String namespace;
    private String containerPlatform;
    private String host;
    private String hostOwnerEmail;
    private List<String> corsOrigins;
    private int cookieMaxAgeSec;
    private String pathPrefix;
    private ProviderType provider;

    @Override
    public ProviderType getProvider() {
        return provider;
    }

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
            return withCorsOrigins(List.of(corsOrigins));
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

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (isBlank(namespace)) {
            errors.add(NAMESPACE_IS_NULL_OR_EMPTY);
        }

        if (containerPlatform != null && isBlank(containerPlatform)) {
            errors.add(CONTAINER_PLATFORM_IS_EMPTY);
        }

        if (isBlank(host)) {
            errors.add(HOST_IS_NULL_OR_EMPTY);
        }

        if (isBlank(hostOwnerEmail)) {
            errors.add(HOST_OWNER_EMAIL_IS_NULL_OR_EMPTY);
        }

        if (cookieMaxAgeSec <= 0) {
            errors.add(COOKIE_MAX_AGE_IS_NULL_OR_EMPTY);
        }
        return errors;
    }
}
