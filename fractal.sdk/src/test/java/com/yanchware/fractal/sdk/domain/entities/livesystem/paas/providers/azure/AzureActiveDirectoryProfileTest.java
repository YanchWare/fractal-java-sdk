package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureActiveDirectoryProfileTest {
  @Test
  public void returns_nulls_when_azureActiveDirectoryProfileWithDefaultBuilder() {
    var aadProfile = AzureActiveDirectoryProfile.builder()
        .build();

    assertThat(aadProfile.getManaged()).isEqualTo(null);
    assertThat(aadProfile.getEnableAzureRbac()).isEqualTo(null);
    assertThat(aadProfile.getAdminGroupObjectIDs()).isEqualTo(null);
  }

  @Test
  public void returns_managed_false_when_azureActiveDirectoryProfileWithManagedFalseBuilder() {
    var aadProfile = AzureActiveDirectoryProfile.builder()
        .withManaged(false)
        .build();

    assertThat(aadProfile.getManaged()).isEqualTo(false);
    assertThat(aadProfile.getEnableAzureRbac()).isEqualTo(null);
    assertThat(aadProfile.getAdminGroupObjectIDs()).isEqualTo(null);
  }

  @Test
  public void returns_managed_true_when_azureActiveDirectoryProfileWithManagedTrueBuilder() {
    var aadProfile = AzureActiveDirectoryProfile.builder()
        .withManaged(true)
        .build();

    assertThat(aadProfile.getManaged()).isEqualTo(true);
    assertThat(aadProfile.getEnableAzureRbac()).isEqualTo(null);
    assertThat(aadProfile.getAdminGroupObjectIDs()).isEqualTo(null);
  }

  @Test
  public void returns_enableAzureRbac_false_when_azureActiveDirectoryProfileWithEnableAzureRbacFalseBuilder() {
    var aadProfile = AzureActiveDirectoryProfile.builder()
        .withManaged(true)
        .withEnableAzureRbac(false)
        .build();

    assertThat(aadProfile.getManaged()).isEqualTo(true);
    assertThat(aadProfile.getEnableAzureRbac()).isEqualTo(false);
    assertThat(aadProfile.getAdminGroupObjectIDs()).isEqualTo(null);
  }

  @Test
  public void returns_enableAzureRbac_true_when_azureActiveDirectoryProfileWithEnableAzureRbacTrueBuilder() {
    var aadProfile = AzureActiveDirectoryProfile.builder()
        .withManaged(true)
        .withEnableAzureRbac(true)
        .build();
    
    assertThat(aadProfile.getManaged()).isEqualTo(true);
    assertThat(aadProfile.getEnableAzureRbac()).isEqualTo(true);
    assertThat(aadProfile.getAdminGroupObjectIDs()).isEqualTo(null);
  }

  @Test
  public void returns_one_AdminGroupObjectIDs_when_azureActiveDirectoryProfileWithOneGroupId() {
    var aadProfile = AzureActiveDirectoryProfile.builder()
        .withAdminGroupObjectId(UUID.randomUUID().toString())
        .build();
    
    assertThat(aadProfile.getAdminGroupObjectIDs().size()).isEqualTo(1);
  }

  @Test
  public void returns_two_AdminGroupObjectIDs_when_azureActiveDirectoryProfileWithListGroupIds() {
    var aadProfile = AzureActiveDirectoryProfile.builder()
        .withAdminGroupObjectIDs(List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString()))
        .build();

    assertThat(aadProfile.getAdminGroupObjectIDs().size()).isEqualTo(2);
  }

  @Test
  public void returns_without_errors_when_azureActiveDirectoryProfileBuilderWithAllParameters() {
    var aadProfile = AzureActiveDirectoryProfile.builder()
        .withManaged(true)
        .withEnableAzureRbac(true)
        .withAdminGroupObjectIDs(List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString()))
        .build();

    assertThat(aadProfile.getManaged()).isEqualTo(true);
    assertThat(aadProfile.getEnableAzureRbac()).isEqualTo(true);
    assertThat(aadProfile.getAdminGroupObjectIDs().size()).isEqualTo(2);
  }

  @Test
  public void validationError_when_azureActiveDirectoryProfileWithAdminGroupObjectIDsEmptyBuilder() {
    assertThatThrownBy(() -> AzureActiveDirectoryProfile.builder()
                            .withManaged(true)
                            .withEnableAzureRbac(true)
                            .withAdminGroupObjectIDs(List.of())
                            .build())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("adminGroupObjectIDs is empty but it is required");

  }
}
