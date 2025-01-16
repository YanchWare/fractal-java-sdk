package com.yanchware.fractal.sdk.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class StringHandler {
  public static String getStringFromInputStream(InputStream inputStream, String delimiter) {
    return new BufferedReader(new InputStreamReader(inputStream))
      .lines().parallel().collect(Collectors.joining(delimiter));
  }

  public static String getStringFromInputStream(InputStream inputStream) {
    return getStringFromInputStream(inputStream, "\n");
  }
}
