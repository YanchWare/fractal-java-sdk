package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public class ReplicationRole extends ExtendableEnum<ReplicationRole> {
  public static final ReplicationRole NONE = fromString("None");
  public static final ReplicationRole PRIMARY = fromString("Primary");
  public static final ReplicationRole ASYNC_REPLICA = fromString("AsyncReplica");
  public static final ReplicationRole GEO_ASYNC_REPLICA = fromString("GeoAsyncReplica");

  /**
   * Creates or finds a ReplicationRole from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding ReplicationRole.
   */
  @JsonCreator
  public static ReplicationRole fromString(String name) {
    return fromString(name, ReplicationRole.class);
  }

  /**
   * Gets known ReplicationRole values.
   *
   * @return known ReplicationRole values.
   */
  public static Collection<ReplicationRole> values() {
    return values(ReplicationRole.class);
  }
}
