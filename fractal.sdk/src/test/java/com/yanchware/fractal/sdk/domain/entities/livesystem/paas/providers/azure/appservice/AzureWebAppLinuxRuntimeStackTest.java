package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AzureWebAppLinuxRuntimeStackTest {
  @Test
  public void shouldReturnProperValue() {
    assertAll(
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("DOTNETCORE|7.0"), AzureWebAppLinuxRuntimeStack.DOTNET_CORE_7_0),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("DOTNETCORE|6.0"), AzureWebAppLinuxRuntimeStack.DOTNET_CORE_6_0),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("NODE|18-lts"), AzureWebAppLinuxRuntimeStack.NODE_18_LTS),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("NODE|16-lts"), AzureWebAppLinuxRuntimeStack.NODE_16_LTS),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("NODE|14-lts"), AzureWebAppLinuxRuntimeStack.NODE_14_LTS),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("PYTHON|3.11"), AzureWebAppLinuxRuntimeStack.PYTHON_3_11),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("PYTHON|3.10"), AzureWebAppLinuxRuntimeStack.PYTHON_3_10),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("PYTHON|3.9"), AzureWebAppLinuxRuntimeStack.PYTHON_3_9),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("PYTHON|3.8"), AzureWebAppLinuxRuntimeStack.PYTHON_3_8),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("PYTHON|3.7"), AzureWebAppLinuxRuntimeStack.PYTHON_3_7),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("PHP|8.2"), AzureWebAppLinuxRuntimeStack.PHP_8_2),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("PHP|8.1"), AzureWebAppLinuxRuntimeStack.PHP_8_1),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("PHP|8.0"), AzureWebAppLinuxRuntimeStack.PHP_8_0),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("RUBY|2.7"), AzureWebAppLinuxRuntimeStack.RUBY_2_7),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("JAVA|17-java17"), AzureWebAppLinuxRuntimeStack.JAVA_17),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("JAVA|11-java11"), AzureWebAppLinuxRuntimeStack.JAVA_11),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("JAVA|8-jre8"), AzureWebAppLinuxRuntimeStack.JAVA_8),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("JBOSSEAP|7-java11"), AzureWebAppLinuxRuntimeStack.JBOSS_EAP_7_JAVA_11),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("JBOSSEAP|7-java8"), AzureWebAppLinuxRuntimeStack.JBOSS_EAP_7_JAVA_8),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("TOMCAT|10.0-java17"), AzureWebAppLinuxRuntimeStack.TOMCAT_10_0_JAVA_17),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("TOMCAT|10.0-java11"), AzureWebAppLinuxRuntimeStack.TOMCAT_10_0_JAVA_11),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("TOMCAT|10.0-jre8"), AzureWebAppLinuxRuntimeStack.TOMCAT_10_0_JRE_8),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("TOMCAT|9.0-java17"), AzureWebAppLinuxRuntimeStack.TOMCAT_9_0_JAVA_17),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("TOMCAT|9.0-java11"), AzureWebAppLinuxRuntimeStack.TOMCAT_9_0_JAVA_11),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("TOMCAT|9.0-jre8"), AzureWebAppLinuxRuntimeStack.TOMCAT_9_0_JRE_8),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("TOMCAT|8.5-java11"), AzureWebAppLinuxRuntimeStack.TOMCAT_8_5_JAVA_11),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("TOMCAT|8.5-jre8"), AzureWebAppLinuxRuntimeStack.TOMCAT_8_5_JRE_8),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("GO|1.19"), AzureWebAppLinuxRuntimeStack.GO_1_19),
        () -> assertEquals(AzureWebAppLinuxRuntimeStack.fromString("GO|1.18"), AzureWebAppLinuxRuntimeStack.GO_1_18)
    );
  }
}