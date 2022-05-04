package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.AMBASSADOR;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Ambassador extends CaaSAPIGatewayImpl {
    private final static String HOST_IS_BLANK = "[Ambassador Validation] Host has not been defined and it is required";
    private final static String HOST_OWNER_EMAIL_IS_BLANK = "[Ambassador Validation] Host Owner Email has not been defined and it is required";
    private final static String AUTHORITY_IS_BLANK = "[Ambassador Validation] Authority has not been defined and it is required";
    private final static String LICENSE_IS_BLANK = "[Ambassador Validation] License Key defined was either empty or blank and it is required";
    private final static String TLS_SECRET_IS_BLANK = "[Ambassador Validation] TLS Secret has not been defined and it is required";

    private String host;
    private String hostOwnerEmail;
    private String authority;
    private String licenseKey;
    private String tlsSecretName;

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

        public AmbassadorBuilder withHost(String host) {
            component.setHost(host);
            return builder;
        }

        public AmbassadorBuilder withHostOwnerEmail(String hostOwnerEmail) {
            component.setHostOwnerEmail(hostOwnerEmail);
            return builder;
        }

        public AmbassadorBuilder withAuthority(String authority) {
            component.setAuthority(authority);
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

        @Override
        public Ambassador build() {
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

        if (isBlank(authority)) {
            errors.add(AUTHORITY_IS_BLANK);
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
