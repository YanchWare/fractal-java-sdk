package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureIdentityType;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AzureIdentityTypeTest {
  @Test
  public void azureIdentityTypeConstants_shouldNotBeNull() {
    assertThat(AzureIdentityType.NONE)
        .as("NONE constant should not be null")
        .isNotNull();

    assertThat(AzureIdentityType.SYSTEM_ASSIGNED)
        .as("SYSTEM_ASSIGNED constant should not be null")
        .isNotNull();

    assertThat(AzureIdentityType.USER_ASSIGNED)
        .as("USER_ASSIGNED constant should not be null")
        .isNotNull();

    assertThat(AzureIdentityType.SYSTEM_ASSIGNED_USER_ASSIGNED)
        .as("SYSTEM_ASSIGNED_USER_ASSIGNED constant should not be null")
        .isNotNull();
  }

  @Test
  public void fromString_shouldReturnCorrespondingAzureIdentityType() {
    assertThat(AzureIdentityType.fromString("None"))
        .as("fromString should return NONE for 'None'")
        .isEqualTo(AzureIdentityType.NONE);

    assertThat(AzureIdentityType.fromString("SystemAssigned"))
        .as("fromString should return SYSTEM_ASSIGNED for 'SystemAssigned'")
        .isEqualTo(AzureIdentityType.SYSTEM_ASSIGNED);

    assertThat(AzureIdentityType.fromString("UserAssigned"))
        .as("fromString should return USER_ASSIGNED for 'UserAssigned'")
        .isEqualTo(AzureIdentityType.USER_ASSIGNED);

    assertThat(AzureIdentityType.fromString("SystemAssigned,UserAssigned"))
        .as("fromString should return SYSTEM_ASSIGNED_USER_ASSIGNED for 'SystemAssigned,UserAssigned'")
        .isEqualTo(AzureIdentityType.SYSTEM_ASSIGNED_USER_ASSIGNED);
  }

  @Test
  public void valuesMethod_shouldContainAllAzureIdentityTypesWithCorrectSize() {
    Collection<AzureIdentityType> values = AzureIdentityType.values();

    assertThat(values)
        .as("Values should contain NONE, SYSTEM_ASSIGNED, USER_ASSIGNED, and SYSTEM_ASSIGNED_USER_ASSIGNED and have exactly 4 values")
        .containsExactlyInAnyOrder(AzureIdentityType.NONE,
            AzureIdentityType.SYSTEM_ASSIGNED,
            AzureIdentityType.USER_ASSIGNED,
            AzureIdentityType.SYSTEM_ASSIGNED_USER_ASSIGNED);
  }
}