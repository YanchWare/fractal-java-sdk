package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AzureWebAppWindowsRuntimeStackTest {
  @Test
  public void shouldReturnProperValue() {
    assertAll(
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("dotnet|7"), AzureWebAppWindowsRuntimeStack.DOTNET_7),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("dotnet|6"), AzureWebAppWindowsRuntimeStack.DOTNET_6),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("ASPNET|V4.8"), AzureWebAppWindowsRuntimeStack.ASPNET_4_8),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("ASPNET|V3.5"), AzureWebAppWindowsRuntimeStack.ASPNET_3_5),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("NODE|18LTS"), AzureWebAppWindowsRuntimeStack.NODE_18_LTS),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("NODE|16LTS"), AzureWebAppWindowsRuntimeStack.NODE_16_LTS),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("NODE|14LTS"), AzureWebAppWindowsRuntimeStack.NODE_14_LTS),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("java|1.8|Java SE|8"), AzureWebAppWindowsRuntimeStack.JAVA_SE_8),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("java|11|Java SE|11"), AzureWebAppWindowsRuntimeStack.JAVA_SE_11),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("java|17|Java SE|17"), AzureWebAppWindowsRuntimeStack.JAVA_SE_17),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("java|1.8|TOMCAT|10.0"), AzureWebAppWindowsRuntimeStack.JAVA_1_8_TOMCAT_10_0),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("java|11|TOMCAT|10.0"), AzureWebAppWindowsRuntimeStack.JAVA_11_TOMCAT_10_0),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("java|17|TOMCAT|10.0"), AzureWebAppWindowsRuntimeStack.JAVA_17_TOMCAT_10_0),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("java|1.8|TOMCAT|9.0"), AzureWebAppWindowsRuntimeStack.JAVA_1_8_TOMCAT_9_0),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("java|11|TOMCAT|9.0"), AzureWebAppWindowsRuntimeStack.JAVA_11_TOMCAT_9_0),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("java|17|TOMCAT|9.0"), AzureWebAppWindowsRuntimeStack.JAVA_17_TOMCAT_9_0),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("java|1.8|TOMCAT|8.5"), AzureWebAppWindowsRuntimeStack.JAVA_1_8_TOMCAT_8_5),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("java|11|TOMCAT|8.5"), AzureWebAppWindowsRuntimeStack.JAVA_11_TOMCAT_8_5),
        () -> assertEquals(AzureWebAppWindowsRuntimeStack.fromString("java|17|TOMCAT|8.5"), AzureWebAppWindowsRuntimeStack.JAVA_17_TOMCAT_8_5)
    );
  }
}