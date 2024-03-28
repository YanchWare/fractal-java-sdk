package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSStorageContainer;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_STORAGE_CONTAINER;

@Getter
@Setter(AccessLevel.PROTECTED)
public class AzureBlobContainer extends PaaSStorageContainer implements LiveSystemComponent, Validatable {
  private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9](?:[a-z0-9]|(?<=[a-z0-9])-(?=[a-z0-9])){1,61}[a-z0-9]$");
  private static final String INVALID_NAME_MESSAGE = "This name may only contain lowercase letters, numbers, and hyphens, and must begin with a letter or a number. Each hyphen must be preceded and followed by a non-hyphen character. The name must also be between 3 and 63 characters long";


  private String name;
  private String defaultEncryptionScope;
  private Boolean denyEncryptionScopeOverride;
  private Boolean enableNfsV3AllSquash;
  private Boolean enableNfsV3RootSquash;
  private AzureBlobContainerImmutableStorageWithVersioning immutableStorageWithVersioning;
  private Map<String, String> metadata;
  private AzureBlobContainerPublicAccess publicAccess;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;


  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static AzureBlobContainerBuilder builder() {
    return new AzureBlobContainerBuilder();
  }

  @Override
  public Collection<String> validate() {
    var errors = new ArrayList<String>();

    if (StringUtils.isBlank(name)) {
      errors.add("Name cannot be null or empty");
    } else if (!NAME_PATTERN.matcher(name).matches()) {
      errors.add("Invalid name: '" + name + "'; " + INVALID_NAME_MESSAGE);
    }

    if (metadata != null) {
      metadata.forEach((key, value) -> {
        if (StringUtils.isBlank(key)) {
          errors.add("Metadata key cannot be null or empty.");
        }
        if (StringUtils.isBlank(value)) {
          errors.add("Metadata value cannot be null or empty for key: " + key);
        }
      });
    }

    return errors;
  }

  public static class AzureBlobContainerBuilder extends Builder<AzureBlobContainer, AzureBlobContainerBuilder> {
    @Override
    protected AzureBlobContainer createComponent() {
      return new AzureBlobContainer();
    }

    @Override
    protected AzureBlobContainerBuilder getBuilder() {
      return this;
    }

    @Override
    public AzureBlobContainer build() {
      component.setType(PAAS_STORAGE_CONTAINER);
      return super.build();
    }


    public AzureBlobContainerBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    public AzureBlobContainerBuilder withDefaultEncryptionScope(String defaultEncryptionScope) {
      component.setDefaultEncryptionScope(defaultEncryptionScope);
      return builder;
    }
    
    public AzureBlobContainerBuilder withDenyEncryptionScopeOverride(Boolean denyEncryptionScopeOverride) {
      component.setDenyEncryptionScopeOverride(denyEncryptionScopeOverride);
      return builder;
    }

    public AzureBlobContainerBuilder withEnableNfsV3AllSquash(Boolean enableNfsV3AllSquash) {
      component.setEnableNfsV3AllSquash(enableNfsV3AllSquash);
      return builder;
    }

    public AzureBlobContainerBuilder withEnableNfsV3RootSquash(Boolean enableNfsV3RootSquash) {
      component.setEnableNfsV3RootSquash(enableNfsV3RootSquash);
      return builder;
    }

    public AzureBlobContainerBuilder withImmutableStorageWithVersioning(AzureBlobContainerImmutableStorageWithVersioning immutableStorageWithVersioning) {
      component.setImmutableStorageWithVersioning(immutableStorageWithVersioning);
      return builder;
    }
    
    public AzureBlobContainerBuilder withMetadata(Map<String, String> metadata) {
      component.setMetadata(metadata);
      return builder;
    }

    public AzureBlobContainerBuilder withPublicAccess(AzureBlobContainerPublicAccess publicAccess) {
      component.setPublicAccess(publicAccess);
      return builder;
    }
  }
}
