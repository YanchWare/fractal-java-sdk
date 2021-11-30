package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.gcp;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.PostgreSQL;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class GcpProgreSQL extends PostgreSQL {

    private final static String REGION_IS_NULL = "[GcpProgreSQL Validation] Region has not been defined and it is required";

    private final static String NETWORK_IS_NULL_OR_EMPTY = "[GcpProgreSQL Validation] Network has not been defined and it is required";

    private final static String PEERING_NETWORK_ADDRESS_IS_EMPTY = "[GcpProgreSQL Validation] Peering network address is empty";

    private final static String PEERING_NETWORK_ADDRESS_DESCRIPTION_IS_EMPTY = "[GcpProgreSQL Validation] Peering network address description is empty";

    private final static String PEERING_NETWORK_NAME_IS_EMPTY = "[GcpProgreSQL Validation] Peering network name is empty";

    private final static String PEERING_NETWORK_PREFIX_IS_EMPTY = "[GcpProgreSQL Validation] Peering network prefix is empty";

    private GcpRegion region;
    private String network;
    private String peeringNetworkAddress;
    private String peeringNetworkAddressDescription;
    private String peeringNetworkName;
    private String peeringNetworkPrefix;

    public static GcpPostgreSQLBuilder builder() {
        return new GcpPostgreSQLBuilder();
    }

    @Override
    public ProviderType getProvider() {
        return ProviderType.GCP;
    }

    public static class GcpPostgreSQLBuilder extends Builder<GcpProgreSQL, GcpPostgreSQLBuilder> {

        @Override
        protected GcpProgreSQL createComponent() {
            return new GcpProgreSQL();
        }

        @Override
        protected GcpPostgreSQLBuilder getBuilder() {
            return this;
        }

        public GcpPostgreSQLBuilder withRegion(GcpRegion region) {
            component.setRegion(region);
            return builder;
        }

        public GcpPostgreSQLBuilder withNetwork(String network) {
            component.setNetwork(network);
            return builder;
        }

        public GcpPostgreSQLBuilder withPeeringNetworkAddress(String peeringNetworkAddress) {
            component.setPeeringNetworkAddress(peeringNetworkAddress);
            return builder;
        }

        public GcpPostgreSQLBuilder withPeeringNetworkAddressDescription(String peeringNetworkAddressDescription) {
            component.setPeeringNetworkAddressDescription(peeringNetworkAddressDescription);
            return builder;
        }

        public GcpPostgreSQLBuilder withPeeringNetworkName(String peeringNetworkName) {
            component.setPeeringNetworkName(peeringNetworkName);
            return builder;
        }

        public GcpPostgreSQLBuilder withPeeringNetworkPrefix(String peeringNetworkPrefix) {
            component.setPeeringNetworkPrefix(peeringNetworkPrefix);
            return builder;
        }
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (region == null) {
            errors.add(REGION_IS_NULL);
        }

        if (isBlank(network)) {
            errors.add(NETWORK_IS_NULL_OR_EMPTY);
        }

        if (peeringNetworkAddress != null && isBlank(peeringNetworkAddress)) {
            errors.add(PEERING_NETWORK_ADDRESS_IS_EMPTY);
        }

        if (peeringNetworkAddressDescription != null && isBlank(peeringNetworkAddressDescription)) {
            errors.add(PEERING_NETWORK_ADDRESS_DESCRIPTION_IS_EMPTY);
        }

        if (peeringNetworkName != null && isBlank(peeringNetworkName)) {
            errors.add(PEERING_NETWORK_NAME_IS_EMPTY);
        }

        if (peeringNetworkPrefix != null && isBlank(peeringNetworkPrefix)) {
            errors.add(PEERING_NETWORK_PREFIX_IS_EMPTY);
        }

        return errors;
    }
}
