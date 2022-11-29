package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.OCELOT;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.TRAEFIK;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class CaaSTraefik extends CaaSAPIGatewayImpl {
    private final static String OIDC_IS_PARTIAL = "[CaaSTraefik Validation] OIDC has been partially configured. You must provide all required values or none of them";
    private final static String NO_ENTRY_POINTS = "[CaaSTraefik Validation] Traefik should have at least one entrypoint";
    private final static String NO_HOSTNAME = "[CaaSTraefik Validation] Traefik should have a hostname";

    private String oidcClientId;
    private String oidcClientSecretId;
    private String forwardAuthSecretId;
    private String oidcIssuerUrl;
    private String loadbalancerIp;
    private String jaegerHost;
    private String hostname;
    private List<TraefikEntryPoint> entryPoints;
    private List<TraefikTlsCertificate> tlsCertificates;

    protected CaaSTraefik() {
    }

    public static TraefikBuilder builder() {
        return new TraefikBuilder();
    }

    public static class TraefikBuilder extends Builder<CaaSTraefik, TraefikBuilder> {
        public static final String TYPE = TRAEFIK.getId();

        @Override
        protected CaaSTraefik createComponent() {
            return new CaaSTraefik();
        }

        @Override
        protected TraefikBuilder getBuilder() {
            return this;
        }

        public TraefikBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public TraefikBuilder withCertificates(List<TraefikTlsCertificate> tlsCertificates) {
            component.setTlsCertificates(tlsCertificates);
            return builder;
        }

        public TraefikBuilder withHostname(String hostname) {
            component.setHostname(hostname);
            return builder;
        }

        public TraefikBuilder withContainerPlatform(String containerPlatform) {
            component.setProvider(ProviderType.CAAS);
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        public TraefikBuilder withJaegerHost(String host) {
            component.setJaegerHost(host);
            return builder;
        }

        public TraefikBuilder withEntryPoints(List<TraefikEntryPoint> entryPoints) {
            component.setEntryPoints(entryPoints);
            return builder;
        }

        public TraefikBuilder withForwardAuth(ForwardAuthSettings forwardAuthSettings) {
            component.setOidcClientId(forwardAuthSettings.getOidcClientId());
            component.setOidcIssuerUrl(forwardAuthSettings.getOidcIssuer());
            component.setOidcClientSecretId(forwardAuthSettings.getOidcClientSecretId());
            component.setForwardAuthSecretId(forwardAuthSettings.getForwardAuthSecretId());
            return builder;
        }

        @Override
        public CaaSTraefik build() {
            component.setType(TRAEFIK);
            return super.build();
        }
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (!allBlank() && !allDefined()){
            errors.add(OIDC_IS_PARTIAL);
        }

        if (isBlank(hostname)) {
            errors.add(NO_HOSTNAME);
        }

        if (entryPoints == null || entryPoints.isEmpty()) {
            errors.add(NO_ENTRY_POINTS);
        }

        return errors;
    }

    private boolean allBlank(){
        return isBlank(oidcIssuerUrl)
          && isBlank(oidcClientId)
          && isBlank(oidcClientSecretId)
          && isBlank(forwardAuthSecretId);
    }

    private boolean allDefined(){
        return !isBlank(oidcIssuerUrl)
          && !isBlank(oidcClientId)
          && !isBlank(oidcClientSecretId)
          && !isBlank(forwardAuthSecretId);
    }

}
