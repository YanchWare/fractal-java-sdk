package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.AMBASSADOR;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.POSTGRESQL;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class CaaSAmbassador extends CaaSAPIGatewayImpl {
    public static final String TYPE = AMBASSADOR.getId();

    private final static String HOST_IS_BLANK = "[CaaSAmbassador Validation] Host has not been defined and it is required";
    private final static String HOST_OWNER_EMAIL_IS_BLANK = "[CaaSAmbassador Validation] Host Owner Email has not been defined and it is required";
    private final static String ACME_PROVIDER_AUTHORITY_IS_BLANK = "[CaaSAmbassador Validation] Automated Certificate Management Environment (ACME) Provider Authority has not been defined and it is required";
    private final static String LICENSE_IS_BLANK = "[CaaSAmbassador Validation] License Key defined was either empty or blank and it is required";
    private final static String TLS_SECRET_IS_BLANK = "[CaaSAmbassador Validation] TLS Secret has not been defined and it is required";

    private String host;
    private String hostOwnerEmail;
    private String acmeProviderAuthority;
    private String licenseKey;
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

        public AmbassadorBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public AmbassadorBuilder withContainerPlatform(String containerPlatform) {
            component.setProvider(ProviderType.CAAS);
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        public AmbassadorBuilder withHost(String host) {
            component.setHost(host);
            return builder;
        }

        public AmbassadorBuilder withHostOwnerEmail(String hostOwnerEmail) {
            component.setHostOwnerEmail(hostOwnerEmail);
            return builder;
        }

        public AmbassadorBuilder withAcmeProviderAuthority(String acmeProviderAuthority) {
            component.setAcmeProviderAuthority(acmeProviderAuthority);
            return builder;
        }

        public AmbassadorBuilder withLicenseKey(String licenseKey) {
            component.setLicenseKey(licenseKey);
            return builder;
        }

        public AmbassadorBuilder withTlsSecretName(String tlsSecretName) {
            component.setTlsSecretName(tlsSecretName);
            return builder;
        }

        public AmbassadorBuilder withProvider(ProviderType providerType) {
            component.setProvider(providerType);
            return builder;
        }

        @Override
        public CaaSAmbassador build() {
            component.setType(AMBASSADOR);
            return super.build();
        }
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (isBlank(host)) {
            errors.add(HOST_IS_BLANK);
        }

        if (isBlank(hostOwnerEmail)) {
            errors.add(HOST_OWNER_EMAIL_IS_BLANK);
        }

        if (isBlank(acmeProviderAuthority)) {
            errors.add(ACME_PROVIDER_AUTHORITY_IS_BLANK);
        }

        if (licenseKey != null && isBlank(licenseKey)) {
            errors.add(LICENSE_IS_BLANK);
        }

        if (isBlank(tlsSecretName)) {
            errors.add(TLS_SECRET_IS_BLANK);
        }

        return errors;
    }
}
