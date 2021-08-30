package com.yanchware.fractal.sdk.valueobjects;

public enum ComponentType {
  Unknown("Unknown"),
  ContainerPlatform("NetworkAndCompute.CaaS.ContainerPlatform");

  private String id;

  ComponentType(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

}
