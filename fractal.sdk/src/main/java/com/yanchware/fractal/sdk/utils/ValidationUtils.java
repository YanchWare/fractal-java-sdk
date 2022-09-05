package com.yanchware.fractal.sdk.utils;

import java.util.Collection;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidIpMask;

public class ValidationUtils {

  private final static String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_PATTERN = "%s must be between %d and %d, the value entered is: %d";

  public static void validateIntegerInRange(String fieldName, int entered, int min, int max, Collection<String> errors) {
    if(entered < min || entered > max) {
      errors.add(String.format(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_PATTERN, fieldName, min, max, entered));
    }
  }

  public static void isPresentAndValidIpMask(String input, Collection<String> errors, String errorMessage) {
    if (input != null && !isValidIpMask(input)) {
      errors.add(errorMessage);
    }
  }
}
