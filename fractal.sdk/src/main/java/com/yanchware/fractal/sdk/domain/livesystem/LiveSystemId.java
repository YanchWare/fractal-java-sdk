package com.yanchware.fractal.sdk.domain.livesystem;

import static org.apache.commons.lang3.StringUtils.isBlank;

public record LiveSystemId(String liveSystemId) {
  public String toString() {
    return liveSystemId;
  }

  public String resourceGroupId(){
    return liveSystemId.split("/")[0];
  }

  public String name(){
    return liveSystemId.split("/")[1];
  }

  public LiveSystemId {
    if (isBlank(liveSystemId)) {
      throw new IllegalArgumentException("Live System ID is blank");
    }

    var liveSystemIdArr = liveSystemId.split("/");
    if (liveSystemIdArr.length != 2) {
      throw new IllegalArgumentException(String.format("Live System ID [%s] is illegal", liveSystemId));
    }

    if (isBlank(liveSystemIdArr[0])) {
      throw new IllegalArgumentException("Resource Group Id is blank");
    }

    if (isBlank(liveSystemIdArr[1])) {
      throw new IllegalArgumentException("Live System Name is blank");
    }
  }

  public LiveSystemId(String resourceGroupId, String name){
    this(String.format("%s/%s", resourceGroupId, name));
  }
}
