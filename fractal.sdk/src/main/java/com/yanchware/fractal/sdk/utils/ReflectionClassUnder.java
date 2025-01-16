package com.yanchware.fractal.sdk.utils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReflectionClassUnder {
  private boolean isBlueprintComponent;
  private boolean isLiveSystemComponent;
  private boolean isValidatable;

  public boolean isAnyComponent() {
    return isBlueprintComponent || isLiveSystemComponent;
  }
}
