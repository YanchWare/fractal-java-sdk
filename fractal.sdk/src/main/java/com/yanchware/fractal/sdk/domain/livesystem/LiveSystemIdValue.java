package com.yanchware.fractal.sdk.domain.livesystem;

public record LiveSystemIdValue(String resourceGroupId, String name) {

  @Override
  public String toString(){
    return String.format("%s/%s", resourceGroupId, name);
  }

}
