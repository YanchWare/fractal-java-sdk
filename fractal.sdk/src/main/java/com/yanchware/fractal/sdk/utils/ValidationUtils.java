package com.yanchware.fractal.sdk.utils;

import java.util.Collection;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidIpMask;

public class ValidationUtils {

  private final static String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_PATTERN = "%s must be between %d and %d, the value entered is: %d";
  private final static String ENTERED_NULL_MESSAGE_PATTERN = "%s cannot be set to null";

  public static void validateIntegerInRange(String fieldName, Integer entered, Integer min, Integer max, Collection<String> errors) {
    if(entered == null) {
      errors.add(String.format(ENTERED_NULL_MESSAGE_PATTERN, fieldName));
    } else if(entered < min || entered > max) {
      errors.add(String.format(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE_PATTERN, fieldName, min, max, entered));
    }
  }

  public static void isPresentAndValidIpRange(String input, Collection<String> errors, String errorMessage) {
    if (input != null && !isValidIpMask(input)) {
      errors.add(errorMessage);
    }
  }
}
