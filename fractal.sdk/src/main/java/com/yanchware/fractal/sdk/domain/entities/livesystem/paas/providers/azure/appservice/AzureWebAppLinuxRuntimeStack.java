package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public final class AzureWebAppLinuxRuntimeStack extends ExtendableEnum<AzureWebAppLinuxRuntimeStack> implements AzureWebAppRuntimeStack {
  public static final AzureWebAppLinuxRuntimeStack DOTNET_CORE_8_0 = fromString("DOTNETCORE|8.0");
  public static final AzureWebAppLinuxRuntimeStack DOTNET_CORE_7_0 = fromString("DOTNETCORE|7.0");
  public static final AzureWebAppLinuxRuntimeStack DOTNET_CORE_6_0 = fromString("DOTNETCORE|6.0");
  public static final AzureWebAppLinuxRuntimeStack NODE_20_LTS = fromString("NODE|20-lts");
  public static final AzureWebAppLinuxRuntimeStack NODE_18_LTS = fromString("NODE|18-lts");
  public static final AzureWebAppLinuxRuntimeStack NODE_16_LTS = fromString("NODE|16-lts");
  public static final AzureWebAppLinuxRuntimeStack PYTHON_3_12 = fromString("PYTHON|3.12");
  public static final AzureWebAppLinuxRuntimeStack PYTHON_3_11 = fromString("PYTHON|3.11");
  public static final AzureWebAppLinuxRuntimeStack PYTHON_3_10 = fromString("PYTHON|3.10");
  public static final AzureWebAppLinuxRuntimeStack PYTHON_3_9 = fromString("PYTHON|3.9");
  public static final AzureWebAppLinuxRuntimeStack PYTHON_3_8 = fromString("PYTHON|3.8");
  public static final AzureWebAppLinuxRuntimeStack PHP_8_2 = fromString("PHP|8.2");
  public static final AzureWebAppLinuxRuntimeStack PHP_8_1 = fromString("PHP|8.1");
  public static final AzureWebAppLinuxRuntimeStack PHP_8_0 = fromString("PHP|8.0");
  public static final AzureWebAppLinuxRuntimeStack JAVA_17 = fromString("JAVA|17-java17");
  public static final AzureWebAppLinuxRuntimeStack JAVA_11 = fromString("JAVA|11-java11");
  public static final AzureWebAppLinuxRuntimeStack JAVA_8 = fromString("JAVA|8-jre8");
  public static final AzureWebAppLinuxRuntimeStack JBOSS_EAP_7_JAVA_17 = fromString("JBOSSEAP|7-java17");
  public static final AzureWebAppLinuxRuntimeStack JBOSS_EAP_7_JAVA_11 = fromString("JBOSSEAP|7-java11");
  public static final AzureWebAppLinuxRuntimeStack JBOSS_EAP_7_JAVA_8 = fromString("JBOSSEAP|7-java8");
  public static final AzureWebAppLinuxRuntimeStack TOMCAT_10_1_JAVA_17 = fromString("TOMCAT|10.1-java17");
  public static final AzureWebAppLinuxRuntimeStack TOMCAT_10_1_JAVA_11 = fromString("TOMCAT|10.1-java11");
  public static final AzureWebAppLinuxRuntimeStack TOMCAT_10_0_JAVA_17 = fromString("TOMCAT|10.0-java17");
  public static final AzureWebAppLinuxRuntimeStack TOMCAT_10_0_JAVA_11 = fromString("TOMCAT|10.0-java11");
  public static final AzureWebAppLinuxRuntimeStack TOMCAT_10_0_JRE_8 = fromString("TOMCAT|10.0-jre8");
  public static final AzureWebAppLinuxRuntimeStack TOMCAT_9_0_JAVA_17 = fromString("TOMCAT|9.0-java17");
  public static final AzureWebAppLinuxRuntimeStack TOMCAT_9_0_JAVA_11 = fromString("TOMCAT|9.0-java11");
  public static final AzureWebAppLinuxRuntimeStack TOMCAT_9_0_JRE_8 = fromString("TOMCAT|9.0-jre8");
  public static final AzureWebAppLinuxRuntimeStack TOMCAT_8_5_JAVA_11 = fromString("TOMCAT|8.5-java11");
  public static final AzureWebAppLinuxRuntimeStack TOMCAT_8_5_JRE_8 = fromString("TOMCAT|8.5-jre8");
  
  public AzureWebAppLinuxRuntimeStack() {
  }

  @JsonCreator
  @JsonValue
  public static AzureWebAppLinuxRuntimeStack fromString(String name) {
    return fromString(name, AzureWebAppLinuxRuntimeStack.class);
  }

  public static Collection<AzureWebAppLinuxRuntimeStack> values() {

    return values(AzureWebAppLinuxRuntimeStack.class);
  }
}
