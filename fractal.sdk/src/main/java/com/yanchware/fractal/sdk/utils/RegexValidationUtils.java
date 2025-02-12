package com.yanchware.fractal.sdk.utils;

public class RegexValidationUtils {
  private static final String IP_MASK_REGEX = "^(?:\\d{1,2}|1\\d{2}|2[0-4]\\d|25[0-5])(?:\\.(?:\\d{1,2}|1\\d{2}|2[0-4]\\d|25[0-5])){3}(?<Mask>/(?:\\d|1\\d|2\\d|3[0-2]))$";
  private static final String ALPHANUMERICS_UNDERSCORES_HYPHENS_REGEX_PATTERN = "(^[a-zA-Z0-9])([a-zA-Z0-9\\-_]+)([a-zA-Z0-9])$";
  private static final String ALPHANUMERICS_HYPHENS_REGEX_PATTERN = "^[a-zA-Z0-9]+(?:-[a-zA-Z0-9]+)*$";
  private static final String LOWERCASE_LETTERS_NUMBERS_AND_HYPHENS_REGEX_PATTERN = "(^[a-z0-9])([a-z0-9\\-]+)([a-z0-9])$";
  private static final String LETTERS_NUMBERS_PERIODS_AND_HYPHENS_REGEX_PATTERN = "^(?=.*[.])([a-zA-Z0-9_.\\-]+)([a-z0-9])$";
  private static final String LETTERS_NUMBERS_UNDERSCORES_DASHES_PERIODS_REGEX_PATTERN = "^(?=.*[.])([a-zA-Z0-9_.\\-]+)$";
  private static final String LETTERS_NUMBERS_UNDERSCORES_DASHES_PERIODS_PERIOD_NOT_REQUIRED_REGEX_PATTERN = "^([a-zA-Z0-9_.\\-*]+)$";
  private static final String LOWERCASE_LETTERS_AND_NUMBERS_REGEX_PATTERN = "^[a-z0-9]+$";
  private static final String LETTERS_AND_NUMBERS_REGEX_PATTERN = "^[a-zA-Z0-9]+$";
  private static final String IP_V6_REGEX_PATTERN = "(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]+|::(ffff(:0{1,4})?:)?((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1?[0-9])?[0-9])\\.){3}(25[0-5]|(2[0-4]|1?[0-9])?[0-9]))";
  private static final String IP_V4_REGEX_PATTERN = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";

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

  public static boolean isValidLettersNumbersPeriodsAndHyphens(String input) {
    return input.matches(LETTERS_NUMBERS_PERIODS_AND_HYPHENS_REGEX_PATTERN);
  }

  public static boolean isValidLettersNumbersUnderscoresDashesAndPeriods(String input) {
    return input.matches(LETTERS_NUMBERS_UNDERSCORES_DASHES_PERIODS_REGEX_PATTERN);
  }

  public static boolean isValidLettersNumbersUnderscoresDashesAndPeriodsAndPeriodIsNotRequired(String input) {
    return input.matches(LETTERS_NUMBERS_UNDERSCORES_DASHES_PERIODS_PERIOD_NOT_REQUIRED_REGEX_PATTERN);
  }

  public static boolean isValidLettersAndNumbers(String input) {
    return input.matches(LETTERS_AND_NUMBERS_REGEX_PATTERN);
  }

  public static boolean isValidIpV6Address(String input) {
    return input.matches(IP_V6_REGEX_PATTERN);
  }

  public static boolean isValidIpV4Address(String input) {
    return input.matches(IP_V4_REGEX_PATTERN);
  }
}
