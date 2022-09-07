package com.yanchware.fractal.sdk.utils;

public class RegexValidationUtils {
  private static final String IP_MASK_REGEX = "^(?:\\d{1,2}|1\\d{2}|2[0-4]\\d|25[0-5])(?:\\.(?:\\d{1,2}|1\\d{2}|2[0-4]\\d|25[0-5])){3}(?<Mask>/(?:\\d|1\\d|2\\d|3[0-2]))$";

  public static boolean isValidIpMask(String input) {
    return input.matches(IP_MASK_REGEX);
  }
}
