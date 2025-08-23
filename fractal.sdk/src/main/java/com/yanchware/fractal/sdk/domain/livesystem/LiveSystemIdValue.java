package com.yanchware.fractal.sdk.domain.livesystem;

import com.yanchware.fractal.sdk.domain.values.ResourceGroupId;
import org.jetbrains.annotations.NotNull;

public record LiveSystemIdValue(ResourceGroupId resourceGroupId, String name) {

  @NotNull
  @Override
  public String toString(){
    return String.format("%s/%s", resourceGroupId.toString(), name);
  }

}
