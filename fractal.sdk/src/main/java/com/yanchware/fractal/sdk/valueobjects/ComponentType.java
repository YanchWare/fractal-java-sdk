package com.yanchware.fractal.sdk.valueobjects;

public enum ComponentType {
  UNKNOWN("Unknown"),
  CONTAINER_PLATFORM("NetworkAndCompute.CaaS.ContainerPlatform"),
  KUBERNETES("NetworkAndCompute.CaaS.Kubernetes");

  private String id;

  ComponentType(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

}
