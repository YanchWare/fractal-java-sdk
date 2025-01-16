package com.yanchware.fractal.sdk.domain;

import java.util.Collection;

public interface Validatable {

  /**
   * Perform a validation on the current instance of the class.
   * Returns a list of validation errors or an empty list if validation is successful.
   *
   * @return Error list or empty list in case of successful validation.
   */
  Collection<String> validate();
}
