package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AzureWebAppDeploymentSlotTest {
  @Test
  void builder_withCloneSettingsFromWebApp_createsExpectedDeploymentSlot() {
    // Setup
    boolean expectedCloneSettings = true; // Assuming the default or specific scenario you want to test

    // Act
    var deploymentSlot = AzureWebAppDeploymentSlot.builder()
        .withName("slot")
        .withCloneSettingsFromWebApp(expectedCloneSettings)
        .build();

    // Assert
    assertEquals(expectedCloneSettings, deploymentSlot.isCloneSettingsFromWebApp(),
        "The cloneSettingsFromWebApp should match the expected value.");
  }

  @Test
  void builder_withCloneSettingsFromWebApp_createsExpectedDeploymentSlotName() {
    // Setup
    var expectedCloneSettings = true; // Assuming the default or specific scenario you want to test
    var slotName = "slotName"; // Assuming the default or specific scenario you want to test

    // Act
    var deploymentSlot = AzureWebAppDeploymentSlot.builder()
        .withName(slotName)
        .build();

    // Assert
    assertEquals(expectedCloneSettings, deploymentSlot.isCloneSettingsFromWebApp(),
        "The cloneSettingsFromWebApp should match the expected value.");

    assertEquals(slotName, deploymentSlot.getName(),
        "The name should match the expected value.");
  }
}