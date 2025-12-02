package com.yanchware.fractal.sdk.domain.exceptions;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;

public class ProviderException extends Exception {
  @Serial
  private static final long serialVersionUID = 1L;

  private Collection<String> failedComponents = new ArrayList<>();

  public ProviderException(String message) {
    super(message);
  }

  public ProviderException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProviderException(Collection<String> failedComponentsIds, String message) {
    super(message);
    this.failedComponents = failedComponentsIds;
  }
}

