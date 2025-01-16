package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AzureFileShareAccessPolicyTest {

  @Test
  public void validPolicyCreated_when_BuilderUsedWithValidValues() {
    var startTime = OffsetDateTime.now();
    var expiryTime = startTime.plusHours(1);
    String permission = "rwx";

    AzureFileShareAccessPolicy policy = AzureFileShareAccessPolicy.builder()
      .withStartTime(startTime)
      .withExpiryTime(expiryTime)
      .withPermission(permission)
      .build();

    assertEquals(startTime, policy.getStartTime());
    assertEquals(expiryTime, policy.getExpiryTime());
    assertEquals(permission, policy.getPermission());
  }

  @Test
  public void exceptionThrown_when_ExpiryTimeIsNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
      AzureFileShareAccessPolicy.builder()
        .withPermission("rwx")
        .withStartTime(OffsetDateTime.now())
        .build()
    );

    assertTrue(exception.getMessage().contains("Expiry time cannot be null"));
  }

  @Test
  public void exceptionThrown_when_ExpiryTimeBeforeStartTime() {
    OffsetDateTime time = OffsetDateTime.now();

    Exception exception = assertThrows(IllegalArgumentException.class, () ->
      AzureFileShareAccessPolicy.builder()
        .withStartTime(time)
        .withExpiryTime(time.minusHours(1))
        .withPermission("rwx")
        .build()
    );

    assertTrue(exception.getMessage().contains("Expiry time must be after start time"));
  }

  @Test
  public void exceptionThrown_when_PermissionIsNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
      AzureFileShareAccessPolicy.builder()
        .withStartTime(OffsetDateTime.now())
        .withExpiryTime(OffsetDateTime.now().plusHours(1))
        .build()
    );

    assertTrue(exception.getMessage().contains("Permission cannot be null or empty"));
  }

  @Test
  public void exceptionThrown_when_StartTimeIsNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
      AzureFileShareAccessPolicy.builder()
        .withExpiryTime(OffsetDateTime.now().plusHours(1))
        .withPermission("rwx")
        .build()
    );

    assertTrue(exception.getMessage().contains("Start time cannot be null"));
  }
}