package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

public class AzureWebAppWindowsRuntimeStack extends ExtendableEnum<AzureWebAppWindowsRuntimeStack> implements AzureWebAppRuntimeStack {

  public static final AzureWebAppWindowsRuntimeStack DOTNET_7 = fromString("dotnet|7");
  public static final AzureWebAppWindowsRuntimeStack DOTNET_6 = fromString("dotnet|6");
  public static final AzureWebAppWindowsRuntimeStack ASPNET_4_8 = fromString("ASPNET|V4.8");
  public static final AzureWebAppWindowsRuntimeStack ASPNET_3_5 = fromString("ASPNET|V3.5");
  public static final AzureWebAppWindowsRuntimeStack NODE_18_LTS = fromString("NODE|18LTS");
  public static final AzureWebAppWindowsRuntimeStack NODE_16_LTS = fromString("NODE|16LTS");
  public static final AzureWebAppWindowsRuntimeStack NODE_14_LTS = fromString("NODE|14LTS");
  public static final AzureWebAppWindowsRuntimeStack JAVA_SE_8 = fromString("java|1.8|Java SE|8");
  public static final AzureWebAppWindowsRuntimeStack JAVA_SE_11 = fromString("java|11|Java SE|11");
  public static final AzureWebAppWindowsRuntimeStack JAVA_SE_17 = fromString("java|17|Java SE|17");
  public static final AzureWebAppWindowsRuntimeStack JAVA_1_8_TOMCAT_10_0 = fromString("java|1.8|TOMCAT|10.0");
  public static final AzureWebAppWindowsRuntimeStack JAVA_11_TOMCAT_10_0 = fromString("java|11|TOMCAT|10.0");
  public static final AzureWebAppWindowsRuntimeStack JAVA_17_TOMCAT_10_0 = fromString("java|17|TOMCAT|10.0");
  public static final AzureWebAppWindowsRuntimeStack JAVA_1_8_TOMCAT_9_0 = fromString("java|1.8|TOMCAT|9.0");
  public static final AzureWebAppWindowsRuntimeStack JAVA_11_TOMCAT_9_0 = fromString("java|11|TOMCAT|9.0");
  public static final AzureWebAppWindowsRuntimeStack JAVA_17_TOMCAT_9_0 = fromString("java|17|TOMCAT|9.0");
  public static final AzureWebAppWindowsRuntimeStack JAVA_1_8_TOMCAT_8_5 = fromString("java|1.8|TOMCAT|8.5");
  public static final AzureWebAppWindowsRuntimeStack JAVA_11_TOMCAT_8_5 = fromString("java|11|TOMCAT|8.5");
  public static final AzureWebAppWindowsRuntimeStack JAVA_17_TOMCAT_8_5 = fromString("java|17|TOMCAT|8.5");
  public AzureWebAppWindowsRuntimeStack() {
  }

  @JsonCreator
  public static AzureWebAppWindowsRuntimeStack fromString(String name) {
    return fromString(name, AzureWebAppWindowsRuntimeStack.class);
  }

  public static Collection<AzureWebAppWindowsRuntimeStack> values() {

    return values(AzureWebAppWindowsRuntimeStack.class);
  }
}
