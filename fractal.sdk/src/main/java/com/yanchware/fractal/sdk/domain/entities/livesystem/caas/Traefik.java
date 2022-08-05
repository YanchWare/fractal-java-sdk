package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.TRAEFIK;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Traefik extends CaaSAPIGatewayImpl {
    private final static String OIDC_IS_PARTIAL = "[Traefik Validation] OIDC has been partially configured. You must provide all required values or none of them";
    private final static String NO_ENTRY_POINTS = "[Traefik Validation] Traefik should have at least one entrypoint";
    private final static String NO_HOSTNAME = "[Traefik Validation] Traefik should have a hostname";

    private String oidcClientId;
    private String oidcClientSecretId;
    private String forwardAuthSecretId;
    private String oidcIssuer;
    private String loadbalancerIp;
    private String jaegerHost;
    private String hostname;
    private List<TraefikEntryPoint> entryPoints;
    private List<TraefikTlsCertificate> tlsCertificates;


    protected Traefik() {
    }

    public static TraefikBuilder builder() {
        return new TraefikBuilder();
    }

    public static class TraefikBuilder extends Builder<Traefik, TraefikBuilder> {
        @Override
        protected Traefik createComponent() {
            return new Traefik();
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
            component.setOidcIssuer(forwardAuthSettings.getOidcIssuer());
            component.setOidcClientSecretId(forwardAuthSettings.getOidcClientSecretId());
            component.setForwardAuthSecretId(forwardAuthSettings.getForwardAuthSecretId());
            return builder;
        }

        @Override
        public Traefik build() {
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
        return isBlank(oidcIssuer)
          && isBlank(oidcClientId)
          && isBlank(oidcClientSecretId)
          && isBlank(forwardAuthSecretId);
    }

    private boolean allDefined(){
        return !isBlank(oidcIssuer)
          && !isBlank(oidcClientId)
          && !isBlank(oidcClientSecretId)
          && !isBlank(forwardAuthSecretId);
    }

}
