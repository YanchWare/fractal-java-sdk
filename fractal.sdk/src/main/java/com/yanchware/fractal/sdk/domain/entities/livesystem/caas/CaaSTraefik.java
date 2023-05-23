package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.DnsZoneConfig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CAAS_TRAEFIK;
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
        public static final String TYPE = CAAS_TRAEFIK.getId();

        @Override
        protected CaaSTraefik createComponent() {
            return new CaaSTraefik();
        }

        @Override
        protected TraefikBuilder getBuilder() {
            return this;
        }

        /**
         * Namespace where Traefik will be instantiated
         * @param namespace
         */
        public TraefikBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        /**
         * List of TLS certificates for Traefik
         * @param tlsCertificates
         */
        public TraefikBuilder withCertificates(List<TraefikTlsCertificate> tlsCertificates) {
            component.setTlsCertificates(tlsCertificates);
            return builder;
        }

        /**
         * Hostname for Traefik
         * @param hostname
         */
        public TraefikBuilder withHostname(String hostname) {
            component.setHostname(hostname);
            return builder;
        }

        /**
         * The id of the container platform where Traefik will be instantiated
         * @param containerPlatform
         */
        public TraefikBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        /**
         * Jaeger host for Traefik
         * @param host
         */
        public TraefikBuilder withJaegerHost(String host) {
            component.setJaegerHost(host);
            return builder;
        }

        /**
         * List of Traefik entry points
         * @param entryPoints
         */
        public TraefikBuilder withEntryPoints(List<TraefikEntryPoint> entryPoints) {
            component.setEntryPoints(entryPoints);
            return builder;
        }

        /**
         * Forward Auth settings for Traefik
         * For more details check <a href="https://doc.traefik.io/traefik/middlewares/http/forwardauth/">Traefik documentation</a>
         * @param forwardAuthSettings
         */
        public TraefikBuilder withForwardAuth(ForwardAuthSettings forwardAuthSettings) {
            component.setOidcClientId(forwardAuthSettings.getOidcClientId());
            component.setOidcIssuerUrl(forwardAuthSettings.getOidcIssuer());
            component.setOidcClientSecretId(forwardAuthSettings.getOidcClientSecretId());
            component.setForwardAuthSecretId(forwardAuthSettings.getForwardAuthSecretId());
            return builder;
        }

        public TraefikBuilder withDnsZoneConfig(DnsZoneConfig dnsZoneConfig) {
            component.setDnsZoneConfig(dnsZoneConfig);
            return builder;
        }

        @Override
        public CaaSTraefik build() {
            component.setType(CAAS_TRAEFIK);
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
