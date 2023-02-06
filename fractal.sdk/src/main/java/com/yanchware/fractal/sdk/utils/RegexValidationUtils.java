package com.yanchware.fractal.sdk.utils;

public class RegexValidationUtils {
  private static final String IP_MASK_REGEX = "^(?:\\d{1,2}|1\\d{2}|2[0-4]\\d|25[0-5])(?:\\.(?:\\d{1,2}|1\\d{2}|2[0-4]\\d|25[0-5])){3}(?<Mask>/(?:\\d|1\\d|2\\d|3[0-2]))$";
  private static final String ALPHANUMERICS_UNDERSCORES_HYPHENS_REGEX_PATTERN = "(^[a-zA-Z0-9])([a-zA-Z0-9\\-\\_]+)([a-zA-Z0-9])$";
  private static final String ALPHANUMERICS_HYPHENS_REGEX_PATTERN = "(^[a-zA-Z0-9])([a-zA-Z0-9\\-]+)([a-zA-Z0-9])$";
  private static final String LOWERCASE_LETTERS_NUMBERS_AND_HYPHENS_REGEX_PATTERN = "(^[a-z0-9])([a-z0-9\\-]+)([a-z0-9])$";
  private static final String LOWERCASE_LETTERS_AND_NUMBERS_REGEX_PATTERN = "^[a-z0-9]+$";

  public static boolean isValidIpMask(String input) {
    return input.matches(IP_MASK_REGEX);
  }
  
  public static boolean isValidAlphanumericsUnderscoresHyphens(String input) {
    return input.matches(ALPHANUMERICS_UNDERSCORES_HYPHENS_REGEX_PATTERN);
  }

  public static boolean isValidAlphanumericsHyphens(String input) {
    return input.matches(ALPHANUMERICS_HYPHENS_REGEX_PATTERN);
  }

  public static boolean isValidLowercaseLettersAndNumbers(String input) {
    return input.matches(LOWERCASE_LETTERS_AND_NUMBERS_REGEX_PATTERN);
  }

  public static boolean isValidLowercaseLettersNumbersAndHyphens(String input) {
    return input.matches(LOWERCASE_LETTERS_NUMBERS_AND_HYPHENS_REGEX_PATTERN);
  }
}
