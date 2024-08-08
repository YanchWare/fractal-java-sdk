package com.yanchware.fractal.sdk.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;

@Slf4j
public class OutputFieldUtils {
  public static void printOutputFields(Map<String, Object> outputFields) {
    if (!outputFields.isEmpty()) {
      log.info("Output Fields:");
      outputFields.forEach((key, value) -> {
        if (value != null) {
          if (value instanceof String strValue) {
            if (!strValue.isEmpty()) {
              log.info("  {}: {}", key, strValue);
            }
          } else if (value instanceof Collection<?> collection) {
            if (!collection.isEmpty()) {
              log.info("  {}:", key);
              collection.forEach(item -> log.info("    - {}", item));
            }
          } else {
            log.info("  {}: {}", key, value);
          }
        }
      });
    }
  }
}
