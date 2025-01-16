package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AzureFileShareSignedIdentifierTest {

  @Test
  public void validSignedIdentifierCreated_when_BuilderUsedWithValidValues() {
    String testId = "testId";
    var startTime = OffsetDateTime.now();
    var expiryTime = startTime.plusHours(1);
    String permission = "rwx";

    AzureFileShareAccessPolicy testPolicy = AzureFileShareAccessPolicy.builder()
      .withStartTime(startTime)
      .withExpiryTime(expiryTime)
      .withPermission(permission)
      .build();

    AzureFileShareSignedIdentifier signedIdentifier = AzureFileShareSignedIdentifier.builder()
      .withId(testId)
      .withAccessPolicy(testPolicy)
      .build();

    assertEquals(testId, signedIdentifier.getId());
    assertEquals(testPolicy, signedIdentifier.getAccessPolicy());
  }

  @Test
  public void exceptionThrown_when_IdIsNull() {
    var startTime = OffsetDateTime.now();
    var expiryTime = startTime.plusHours(1);
    String permission = "rwx";

    AzureFileShareAccessPolicy testPolicy = AzureFileShareAccessPolicy.builder()
      .withStartTime(startTime)
      .withExpiryTime(expiryTime)
      .withPermission(permission)
      .build();

    Exception exception = assertThrows(IllegalArgumentException.class, () ->
      AzureFileShareSignedIdentifier.builder()
        .withAccessPolicy(testPolicy)
        .build()
    );

    assertTrue(exception.getMessage().contains("ID cannot be null or empty."));
  }

  @Test
  public void exceptionThrown_when_AccessPolicyIsNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
      AzureFileShareSignedIdentifier.builder()
        .withId("validId")
        .build()
    );

    assertTrue(exception.getMessage().contains("Access policy cannot be null."));
  }
}