package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

public enum TaintEffect {

  /** The Kubernetes scheduler will only allow scheduling pods that have tolerations for the tainted nodes */
  NO_SCHEDULE("NoSchedule"),

  /** The Kubernetes scheduler will try to avoid scheduling pods that don’t have tolerations for the tainted nodes */
  PREFER_NO_SCHEDULE("PreferNoSchedule"),

  /** Kubernetes will evict the running pods from the nodes if the pods don’t have tolerations for the tainted nodes */
  NO_EXECUTE("NoExecute");

  private final String id;

  TaintEffect(final String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return getId();
  }
}
