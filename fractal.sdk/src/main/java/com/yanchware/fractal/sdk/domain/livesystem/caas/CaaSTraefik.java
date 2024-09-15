package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsRecord;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import com.yanchware.fractal.sdk.utils.SerializationUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_TRAEFIK;
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
  private TraefikTracing tracing;

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
     * Specifies the namespace in which Traefik will be deployed.
     *
     * @param namespace the Kubernetes namespace for the Traefik deployment.
     * @return the current builder instance
     */
    public TraefikBuilder withNamespace(String namespace) {
      component.setNamespace(namespace);
      return builder;
    }

    /**
     * Configures the TLS certificates that Traefik will use.
     *
     * @param tlsCertificates a list of TLS certificates.
     * @return the current builder instance
     */
    public TraefikBuilder withCertificates(List<TraefikTlsCertificate> tlsCertificates) {
      component.setTlsCertificates(tlsCertificates);
      return builder;
    }

    /**
     * Sets the hostname for the Traefik deployment.
     *
     * @param hostname the desired hostname.
     * @return the current builder instance
     */
    public TraefikBuilder withHostname(String hostname) {
      component.setHostname(hostname);
      return builder;
    }

    /**
     * Specifies the container platform where Traefik will be deployed.
     *
     * @param containerPlatform the ID of the target container platform.
     * @return the current builder instance
     */
    public TraefikBuilder withContainerPlatform(String containerPlatform) {
      component.setContainerPlatform(containerPlatform);
      return builder;
    }

    /**
     * Configures the Jaeger host for Traefik's tracing capabilities.
     *
     * @param host the Jaeger host's address.
     * @return the current builder instance
     */
    public TraefikBuilder withJaegerHost(String host) {
      component.setJaegerHost(host);
      return builder;
    }

    /**
     * Adds a list of entry points to the Traefik configuration.
     *
     * @param entryPoints a list of desired entry points.
     * @return the current builder instance
     */
    public TraefikBuilder withEntryPoints(List<TraefikEntryPoint> entryPoints) {
      if (CollectionUtils.isBlank(entryPoints)) {
        return builder;
      }

      if (component.getEntryPoints() == null) {
        component.setEntryPoints(new ArrayList<>());
      }

      component.getEntryPoints().addAll(entryPoints);
      return builder;
    }

    /**
     * Adds a single entry point to the Traefik configuration.
     *
     * @param entryPoint the desired entry point.
     * @return the current builder instance
     */
    public TraefikBuilder withEntryPoint(TraefikEntryPoint entryPoint) {
      return withEntryPoints(List.of(entryPoint));
    }

    /**
     * Configures Traefik's forward authentication settings.
     * For more details check <a href="https://doc.traefik.io/traefik/middlewares/http/forwardauth/">Traefik documentation</a>
     *
     * @param forwardAuthSettings settings for forward authentication.
     * @return the current builder instance
     */
    public TraefikBuilder withForwardAuth(ForwardAuthSettings forwardAuthSettings) {
      component.setOidcClientId(forwardAuthSettings.getOidcClientId());
      component.setOidcIssuerUrl(forwardAuthSettings.getOidcIssuer());
      component.setOidcClientSecretId(forwardAuthSettings.getOidcClientSecretId());
      component.setForwardAuthSecretId(forwardAuthSettings.getForwardAuthSecretId());
      return builder;
    }

    /**
     * Adds a DNS zone configuration to Traefik's deployment settings.
     *
     * @param dnsZoneName the name of the DNS zone.
     * @param dnsRecord   the DNS record associated with the zone.
     * @return the current builder instance
     */
    public TraefikBuilder withDnsZoneConfig(String dnsZoneName, DnsRecord dnsRecord) {
      if (component.getDnsZoneConfig() == null) {
        component.setDnsZoneConfig(new HashMap<>());
      }

      if (!component.getDnsZoneConfig().containsKey(dnsZoneName)) {
        component.getDnsZoneConfig().put(dnsZoneName, new ArrayList<>());
      }

      component.getDnsZoneConfig().get(dnsZoneName).add(SerializationUtils.convertValueToMap(dnsRecord));

      return builder;
    }

    /**
     * Adds multiple DNS records to a specific DNS zone in Traefik's deployment settings.
     *
     * @param dnsZoneName the name of the DNS zone.
     * @param dnsRecords  a collection of DNS records for the zone.
     * @return the current builder instance for chaining.
     */
    public TraefikBuilder withDnsZoneConfig(String dnsZoneName, Collection<DnsRecord> dnsRecords) {
      dnsRecords.forEach(dnsRecord -> withDnsZoneConfig(dnsZoneName, dnsRecord));
      return builder;
    }

    /**
     * Adds multiple DNS records across different zones to Traefik's deployment settings.
     *
     * @param dnsRecordsMap a map with DNS zones as keys and their associated records as values.
     * @return the current builder instance for chaining.
     */
    public TraefikBuilder withDnsZoneConfig(Map<? extends String, ? extends Collection<DnsRecord>> dnsRecordsMap) {
      if (dnsRecordsMap == null || dnsRecordsMap.isEmpty()) {
        return builder;
      }

      dnsRecordsMap.forEach((key, value) -> {
        for (var dnsRecord : value) {
          withDnsZoneConfig(key, dnsRecord);
        }
      });

      return builder;
    }

    /**
     * Configures Traefik's tracing settings.
     *
     * @param tracing the tracing configuration.
     * @return the current builder instance for chaining.
     */
    public TraefikBuilder withTracing(TraefikTracing tracing) {
      if (tracing == null) {
        return builder;
      }

      component.setTracing(tracing);

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

    if (!allBlank() && !allDefined()) {
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

  private boolean allBlank() {
    return isBlank(oidcIssuerUrl)
        && isBlank(oidcClientId)
        && isBlank(oidcClientSecretId)
        && isBlank(forwardAuthSecretId);
  }

  private boolean allDefined() {
    return !isBlank(oidcIssuerUrl)
        && !isBlank(oidcClientId)
        && !isBlank(oidcClientSecretId)
        && !isBlank(forwardAuthSecretId);
  }

}
