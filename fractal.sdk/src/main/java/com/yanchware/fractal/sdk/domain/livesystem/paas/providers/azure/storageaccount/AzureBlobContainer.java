package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.Validatable;
import com.yanchware.fractal.sdk.domain.blueprint.paas.PaaSStorageContainer;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_STORAGE_CONTAINER;

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


    /**
     * <pre>
     * Sets the name for the Azure Blob Container.
     * 
     * The name must be a valid Azure Blob Container name, adhering to Azure naming requirements. 
     * It may only contain lowercase letters, numbers, and hyphens, and must begin and end with a letter or a number. 
     * Each hyphen must be preceded and followed by a non-hyphen character. 
     * The name must also be between 3 and 63 characters long.</pre>
     *
     * @param name A <code>String</code> representing the name for the Azure Blob Container. Must follow Azure naming conventions.
     * @return The builder instance for chaining.
     */
    public AzureBlobContainerBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    /**
     * <pre>
     * Sets the default encryption scope for the Azure Blob Container.
     * 
     * This defines the encryption mechanism used for the Blob Container. 
     * Setting a default encryption scope enables specifying the encryption key 
     * to be used for storing the blobs in this container.</pre>
     *
     * @param defaultEncryptionScope A <code>String</code> representing the default encryption scope.
     * @return The builder instance for chaining.
     */
    public AzureBlobContainerBuilder withDefaultEncryptionScope(String defaultEncryptionScope) {
      component.setDefaultEncryptionScope(defaultEncryptionScope);
      return builder;
    }

    /**
     * <pre>
     * Specifies whether the encryption scope override should be denied for the Azure Blob Container.
     * 
     * When set to <code>true</code>, it prevents the clients from specifying an encryption scope other than the default 
     * for this container. This ensures that all blobs in the container are encrypted under the same scope.</pre>
     *
     * @param denyEncryptionScopeOverride A <code>Boolean</code> indicating whether to deny encryption scope override.
     * @return The builder instance for chaining.
     */
    public AzureBlobContainerBuilder withDenyEncryptionScopeOverride(Boolean denyEncryptionScopeOverride) {
      component.setDenyEncryptionScopeOverride(denyEncryptionScopeOverride);
      return builder;
    }

    /**
     * <pre>
     * Enables or disables the NFSv3 'all squash' feature for the Azure Blob Container.
     * 
     * When enabled, all user access is mapped to anonymous access. 
     * This can be useful in scenarios where you do not want to maintain user identities for access to the container.</pre>
     *
     * @param enableNfsV3AllSquash A <code>Boolean</code> indicating whether to enable NFSv3 all squash.
     * @return The builder instance for chaining.
     */
    public AzureBlobContainerBuilder withEnableNfsV3AllSquash(Boolean enableNfsV3AllSquash) {
      component.setEnableNfsV3AllSquash(enableNfsV3AllSquash);
      return builder;
    }

    /**
     * <pre>
     * Enables or disables the NFSv3 'root squash' feature for the Azure Blob Container.
     * 
     * When enabled, it maps the root user access to an anonymous user access. 
     * This is a security feature to prevent a root user on the client machine from having root privileges 
     * on the container.</pre>
     *
     * @param enableNfsV3RootSquash A <code>Boolean</code> indicating whether to enable NFSv3 root squash.
     * @return The builder instance for chaining.
     */
    public AzureBlobContainerBuilder withEnableNfsV3RootSquash(Boolean enableNfsV3RootSquash) {
      component.setEnableNfsV3RootSquash(enableNfsV3RootSquash);
      return builder;
    }

    /**
     * <pre>
     * Sets the immutable storage with versioning configuration for the Azure Blob Container.
     * 
     * Immutable storage with versioning helps protect your data from accidental or malicious modification and deletion. 
     * It's useful for scenarios where data preservation is critical.</pre>
     *
     * @param immutableStorageWithVersioning An instance of {@link AzureBlobContainerImmutableStorageWithVersioning} representing the configuration settings.
     * @return The builder instance for chaining.
     */
    public AzureBlobContainerBuilder withImmutableStorageWithVersioning(AzureBlobContainerImmutableStorageWithVersioning immutableStorageWithVersioning) {
      component.setImmutableStorageWithVersioning(immutableStorageWithVersioning);
      return builder;
    }

    /**
     * <pre>
     * Sets the metadata for the Azure Blob Container.
     * 
     * Metadata is represented as a collection of key-value pairs that can be used to store additional information 
     * about the container. Each key and value must be a non-null and non-empty string.</pre>
     *
     * @param metadata A <code>Map&lt;String, String&gt;</code> containing the metadata key-value pairs.
     * @return The builder instance for chaining.
     */
    public AzureBlobContainerBuilder withMetadata(Map<String, String> metadata) {
      component.setMetadata(metadata);
      return builder;
    }

    /**
     * <pre>
     * Sets the level of public access allowed for the Azure Blob Container.
     * 
     * Determines the accessibility of the data within the container from the internet based on the specified level 
     * of public access:
     *   - {@link AzureBlobContainerPublicAccess#CONTAINER}: Full public read access for both container and blob data.
     *   - {@link AzureBlobContainerPublicAccess#BLOB}: Public read access for blob data only; container data is not 
     *     accessible publicly.
     *   - {@link AzureBlobContainerPublicAccess#NONE}: No public read access; data within the container can only be 
     *     accessed with appropriate authorization.
     *
     * This setting is crucial for controlling the exposure of data stored in Azure Blob Containers to the public 
     * internet.</pre>
     *
     * @param publicAccess An instance of {@link AzureBlobContainerPublicAccess} representing the desired level of public access.
     * @return The builder instance for chaining, allowing for fluent configuration of the {@link AzureBlobContainer}.
     */
    public AzureBlobContainerBuilder withPublicAccess(AzureBlobContainerPublicAccess publicAccess) {
      component.setPublicAccess(publicAccess);
      return builder;
    }
  }
}
