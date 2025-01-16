package com.yanchware.fractal.sdk.domain.exceptions;

import java.io.Serial;

public class InstantiatorException extends Exception {
  @Serial
  private static final long serialVersionUID = 1L;

  public InstantiatorException(String message) {
    super(message);
  }

  public InstantiatorException(String message, Throwable cause) {
    super(message, cause);
  }

}
