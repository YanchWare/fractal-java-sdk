package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.AzureStorageAccount;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.AzureStorageAccount.builder;
import static org.assertj.core.api.Assertions.*;

public class AzureStorageAccountTest {

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullId() {
    assertThatThrownBy(() -> builder().withId("").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Component Id is illegal");
  }

  @Test
  public void exceptionThrown_when_storageAccountBuiltWithNullRegion() {
    assertThatThrownBy(() -> generateBuilder().withRegion(null).build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Region cannot be empty");
  }
  
  private AzureStorageAccount.AzureStorageAccountBuilder generateBuilder() {
    return builder().withId("storage-account");
  }
  
}
