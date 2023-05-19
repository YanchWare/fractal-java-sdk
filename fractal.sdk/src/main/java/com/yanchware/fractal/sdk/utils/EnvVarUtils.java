package com.yanchware.fractal.sdk.utils;

import static com.yanchware.fractal.sdk.configuration.Constants.IS_LOCAL_DEBUG_KEY;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class EnvVarUtils {
  public static boolean isLocalDebug() {
    String isLocalDebugEnvVar = System.getenv(IS_LOCAL_DEBUG_KEY);
    return !isBlank(isLocalDebugEnvVar) && Boolean.parseBoolean(isLocalDebugEnvVar);
  }
}
