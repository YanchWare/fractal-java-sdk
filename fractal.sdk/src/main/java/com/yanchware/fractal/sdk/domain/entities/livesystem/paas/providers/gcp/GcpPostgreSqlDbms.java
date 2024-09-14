package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.gcp;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlDbms;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class GcpPostgreSqlDbms extends PaaSPostgreSqlDbms {

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

  public static GcpPostgreSqlBuilder builder() {
    return new GcpPostgreSqlBuilder();
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.GCP;
  }

  public static class GcpPostgreSqlBuilder extends Builder<GcpPostgreSqlDbms, GcpPostgreSqlBuilder> {

    @Override
    protected GcpPostgreSqlDbms createComponent() {
      return new GcpPostgreSqlDbms();
    }

    @Override
    protected GcpPostgreSqlBuilder getBuilder() {
      return this;
    }

    public GcpPostgreSqlBuilder withDatabase(GcpPostgreSqlDatabase db) {
      return withDatabases(List.of(db));
    }

    public GcpPostgreSqlBuilder withDatabases(Collection<? extends GcpPostgreSqlDatabase> dbs) {
      if (CollectionUtils.isBlank(dbs)) {
        return builder;
      }

      if (component.getDatabases() == null) {
        component.setDatabases(new ArrayList<>());
      }

      dbs.forEach(db -> {
        db.getDependencies().add(component.getId());
      });
      component.getDatabases().addAll(dbs);
      return builder;
    }

    public GcpPostgreSqlBuilder withRegion(GcpRegion region) {
      component.setRegion(region);
      return builder;
    }

    public GcpPostgreSqlBuilder withNetwork(String network) {
      component.setNetwork(network);
      return builder;
    }

    public GcpPostgreSqlBuilder withPeeringNetworkAddress(String peeringNetworkAddress) {
      component.setPeeringNetworkAddress(peeringNetworkAddress);
      return builder;
    }

    public GcpPostgreSqlBuilder withPeeringNetworkAddressDescription(String peeringNetworkAddressDescription) {
      component.setPeeringNetworkAddressDescription(peeringNetworkAddressDescription);
      return builder;
    }

    public GcpPostgreSqlBuilder withPeeringNetworkName(String peeringNetworkName) {
      component.setPeeringNetworkName(peeringNetworkName);
      return builder;
    }

    public GcpPostgreSqlBuilder withPeeringNetworkPrefix(String peeringNetworkPrefix) {
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
