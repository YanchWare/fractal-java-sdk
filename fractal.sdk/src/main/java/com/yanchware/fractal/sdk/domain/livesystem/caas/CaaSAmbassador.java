package com.yanchware.fractal.sdk.domain.livesystem.caas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_AMBASSADOR;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * <p>
 * Builder class to represent an Ambassador API Gateway, served by Emissary-ingress.
 * </p>
 * <br>
 * <p>
 * For more details about creating an Ambassador instance using Fractal Cloud check out
 * our <a href="https://fractal.cloud/docs/docs-ht-create-ambassador">documentation page</a>
 * </p>
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class CaaSAmbassador extends CaaSAPIGatewayImpl {
  private final static String HOST_OWNER_EMAIL_IS_BLANK = "[CaaSAmbassador Validation] Host Owner Email has not been defined and it is required";
  private final static String ACME_PROVIDER_AUTHORITY_IS_BLANK = "[CaaSAmbassador Validation] Automated Certificate Management Environment (ACME) Provider Authority has not been defined and it is required";
  private final static String TLS_SECRET_IS_BLANK = "[CaaSAmbassador Validation] TLS Secret has not been defined and it is required";

  private String host;
  private String hostOwnerEmail;
  private String acmeProviderAuthority;
  private String tlsSecretName;

  protected CaaSAmbassador() {
  }

  public static AmbassadorBuilder builder() {
    return new AmbassadorBuilder();
  }

  public static class AmbassadorBuilder extends Builder<CaaSAmbassador, AmbassadorBuilder> {
    @Override
    protected CaaSAmbassador createComponent() {
      return new CaaSAmbassador();
    }

    @Override
    protected AmbassadorBuilder getBuilder() {
      return this;
    }

    /**
     * Namespace where ambassador will be instantiated
     *
     * @param namespace
     */
    public AmbassadorBuilder withNamespace(String namespace) {
      component.setNamespace(namespace);
      return builder;
    }

    /**
     * The id of the container platform where ambassador will be instantiated
     *
     * @param containerPlatform
     */
    public AmbassadorBuilder withContainerPlatform(String containerPlatform) {
      component.setContainerPlatform(containerPlatform);
      return builder;
    }

    /**
     * Public hostname of the gateway. Components that publish consoles through this gateway use it
     * as their public host.
     * <p>
     * For more details please check <a href="https://www.getambassador.io/docs/emissary/latest/topics/running/host-crd">Emissary-ingress documentation</a>
     *
     * @param host
     */
    public AmbassadorBuilder withHost(String host) {
      component.setHost(host);
      return builder;
    }

    /**
     * Ambassador host owner email used for ACME TLS
     * <p>
     * For more details please check <a href="https://www.getambassador.io/docs/emissary/latest/topics/running/host-crd">Emissary-ingress documentation</a>
     *
     * @param hostOwnerEmail
     */
    public AmbassadorBuilder withHostOwnerEmail(String hostOwnerEmail) {
      component.setHostOwnerEmail(hostOwnerEmail);
      return builder;
    }

    /**
     * ACME provider authority for Ambassador
     *
     * @param acmeProviderAuthority
     */
    public AmbassadorBuilder withAcmeProviderAuthority(String acmeProviderAuthority) {
      component.setAcmeProviderAuthority(acmeProviderAuthority);
      return builder;
    }

    /**
     * The name of the TLS secret that Ambassador will look for to use.
     * 
     * For more details check <a href="https://www.getambassador.io/docs/emissary/latest/topics/running/tls">Emissary-ingress documentation</a>
     *
     * @param tlsSecretName
     */
    public AmbassadorBuilder withTlsSecretName(String tlsSecretName) {
      component.setTlsSecretName(tlsSecretName);
      return builder;
    }

    @Override
    public CaaSAmbassador build() {
      component.setType(CAAS_AMBASSADOR);
      return super.build();
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (isBlank(hostOwnerEmail)) {
      errors.add(HOST_OWNER_EMAIL_IS_BLANK);
    }

    if (isBlank(acmeProviderAuthority)) {
      errors.add(ACME_PROVIDER_AUTHORITY_IS_BLANK);
    }

    if (isBlank(tlsSecretName)) {
      errors.add(TLS_SECRET_IS_BLANK);
    }

    return errors;
  }
}
