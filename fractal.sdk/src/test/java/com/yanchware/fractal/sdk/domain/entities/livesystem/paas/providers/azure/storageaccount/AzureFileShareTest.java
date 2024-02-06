package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AzureFileShareTest {
  private final AzureFileShareAccessTier validAccessTier = AzureFileShareAccessTier.HOT;
  private final AzureFileShareEnabledProtocols validEnabledProtocols = AzureFileShareEnabledProtocols.SMB;
  private final AzureFileShareRootSquashType validRootSquash = AzureFileShareRootSquashType.NO_ROOT_SQUASH;
  private final AzureFileShareSignedIdentifier validSignedIdentifier = AzureFileShareSignedIdentifier.builder()
      .withId("validId")
      .withAccessPolicy(AzureFileShareAccessPolicy.builder()
          .withStartTime(OffsetDateTime.now())
          .withExpiryTime(OffsetDateTime.now().plusHours(1))
          .withPermission("rwx")
          .build())
      .build();


  @Test
  public void exceptionThrown_when_NameIsInvalid() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        AzureFileShare.builder()
            .withName("invalid_name!")
            .build()
    );

    assertTrue(exception.getMessage().contains("Name must use numbers, lower-case letters, and dash (-) only"));
  }

  @Test
  public void exceptionThrown_when_MetadataIsInvalid() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        AzureFileShare.builder()
            .withName("validname")
            .withAccessTier(AzureFileShareAccessTier.HOT)
            .withEnabledProtocols(AzureFileShareEnabledProtocols.SMB)
            .withMetadata(Map.of("", "value"))
            .build()
    );

    assertTrue(exception.getMessage().contains("Metadata key cannot be null or empty"));
  }

  @Test
  public void exceptionThrown_when_ShareQuotaIsInvalid() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        AzureFileShare.builder()
            .withName("validname")
            .withAccessTier(AzureFileShareAccessTier.HOT)
            .withEnabledProtocols(AzureFileShareEnabledProtocols.SMB)
            .withShareQuota(-1)
            .build()
    );

    assertTrue(exception.getMessage().contains("Share quota must be greater than 0"));
  }

  @Test
  public void exceptionThrown_when_RootSquashIsNullForNFS() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        AzureFileShare.builder()
            .withName("validname")
            .withAccessTier(validAccessTier)
            .withEnabledProtocols(AzureFileShareEnabledProtocols.NFS)
            .withRootSquash(null)
            .withShareQuota(1000)
            .withSignedIdentifiers(Collections.singletonList(validSignedIdentifier))
            .build()
    );

    assertTrue(exception.getMessage().contains("Root squash type must be specified for NFS shares"));
  }

  @Test
  public void exceptionThrown_when_SignedIdentifiersAreInvalid() {
    Exception exception = assertThrows(IllegalArgumentException.class, () ->
        AzureFileShare.builder()
            .withName("validname")
            .withAccessTier(validAccessTier)
            .withEnabledProtocols(validEnabledProtocols)
            .withRootSquash(validRootSquash)
            .withShareQuota(1000)
            .withSignedIdentifiers(Arrays.asList(validSignedIdentifier, 
                AzureFileShareSignedIdentifier.builder()
                .withId(null)
                .build()))
            .build()
    );

    assertTrue(exception.getMessage().contains("AzureFileShareSignedIdentifier validation failed"));
  }

  @Test
  public void validAzureFileShareCreated_when_ValidDataProvided() {
    // Test creating a valid AzureFileShare
    var fileShare = AzureFileShare.builder()
        .withName("validname")
        .withAccessTier(validAccessTier)
        .withEnabledProtocols(validEnabledProtocols)
        .withRootSquash(validRootSquash)
        .withShareQuota(1000)
        .withSignedIdentifier(validSignedIdentifier)
        .build();
    
    assertEquals(fileShare.getName(), "validname");
    assertEquals(fileShare.getAccessTier(), validAccessTier);
    assertEquals(fileShare.getEnabledProtocols(), validEnabledProtocols);
    assertEquals(fileShare.getRootSquash(), validRootSquash);
    assertEquals(fileShare.getShareQuota(), 1000);
    assertEquals(fileShare.getSignedIdentifiers(), List.of(validSignedIdentifier));
  }

}