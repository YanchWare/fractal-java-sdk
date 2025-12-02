package com.yanchware.fractal.sdk.utils;

import java.util.Collection;

public class CollectionUtils {

  public static boolean isBlank(Collection<?> collection) {
    return collection == null || collection.isEmpty();
  }
}
