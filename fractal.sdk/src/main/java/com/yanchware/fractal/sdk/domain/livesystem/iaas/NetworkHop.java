package com.yanchware.fractal.sdk.domain.livesystem.iaas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public final class NetworkHop extends ExtendableEnum<NetworkHop> {
  public static final NetworkHop NONE = fromString("None");
  public static final NetworkHop INTERNET = fromString("Internet");
  public static final NetworkHop VIRTUAL_APPLIANCE = fromString("VirtualAppliance");
  public static final NetworkHop VIRTUAL_NETWORK_GATEWAY = fromString("VirtualNetworkGateway");
  public static final NetworkHop VNET_LOCAL = fromString("VnetLocal");
  public static final NetworkHop HYPER_NET_GATEWAY = fromString("HyperNetGateway");

  /**
   * Creates or finds a Network Hop from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding NetworkHop.
   */
  @JsonCreator
  public static NetworkHop fromString(String name) {
    return fromString(name, NetworkHop.class);
  }

  /**
   * Gets known NetworkHop values.
   *
   * @return known NetworkHop values.
   */
  public static Collection<NetworkHop> values() {
    return values(NetworkHop.class);
  }
}
