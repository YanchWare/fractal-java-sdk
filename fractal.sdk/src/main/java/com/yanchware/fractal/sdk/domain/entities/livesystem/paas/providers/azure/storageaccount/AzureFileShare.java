package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSFileShare;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_FILE_SHARE;

@Getter
@Setter(AccessLevel.PROTECTED)
public class AzureFileShare extends PaaSFileShare implements LiveSystemComponent, Validatable {
  private static final Pattern NAME_PATTERN = Pattern.compile("^(?![0-9-])[a-z0-9]+(-[a-z0-9]+)*$");
  private static final int MAX_QUOTA_LARGE_FILE_SHARE = 102400;


  private String name;
  private AzureFileShareAccessTier accessTier;
  private AzureFileShareEnabledProtocols enabledProtocols;
  private Map<String, String> metadata;
  private AzureFileShareRootSquashType rootSquash;
  private Integer shareQuota;
  private List<AzureFileShareSignedIdentifier> signedIdentifiers;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  public static AzureFileShareBuilder builder() {
    return new AzureFileShareBuilder();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (StringUtils.isBlank(name)) {
      errors.add("Name cannot be null or empty");
    } else if (name.length() < 3 || name.length() > 63) {
      errors.add("Name must be between 3 and 63 characters in length");
    } else if (!NAME_PATTERN.matcher(name).matches()) {
      errors.add("Invalid name: '" + name + "'; Name must use numbers, lower-case letters, and dash (-) only. Every dash (-) must be immediately preceded and followed by a letter or number");
    }

    if (enabledProtocols == AzureFileShareEnabledProtocols.NFS) {
      if (rootSquash == null) {
        errors.add("Root squash type must be specified for NFS shares.");
      }
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

    if (shareQuota != null && shareQuota > MAX_QUOTA_LARGE_FILE_SHARE) {
      errors.add("Share quota must be less than or equal to " + MAX_QUOTA_LARGE_FILE_SHARE + " for shares.");
    }

    if (signedIdentifiers != null) {
      signedIdentifiers.forEach(signedIdentifier -> errors.addAll(signedIdentifier.validate()));
    }
    
    if(shareQuota != null && shareQuota <=0) {
      errors.add("Share quota must be greater than 0 for shares.");
    }

    return errors;
  }

  public static class AzureFileShareBuilder extends Builder<AzureFileShare, AzureFileShareBuilder> {
    @Override
    protected AzureFileShare createComponent() {
      return new AzureFileShare();
    }

    @Override
    protected AzureFileShareBuilder getBuilder() {
      return this;
    }

    @Override
    public AzureFileShare build() {
      component.setType(PAAS_FILE_SHARE);
      return super.build();
    }


    public AzureFileShareBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    public AzureFileShareBuilder withAccessTier(AzureFileShareAccessTier accessTier) {
      component.setAccessTier(accessTier);
      return builder;
    }

    public AzureFileShareBuilder withEnabledProtocols(AzureFileShareEnabledProtocols enabledProtocols) {
      component.setEnabledProtocols(enabledProtocols);
      return builder;
    }

    public AzureFileShareBuilder withMetadata(Map<String, String> metadata) {
      component.setMetadata(metadata);
      return builder;
    }

    public AzureFileShareBuilder withRootSquash(AzureFileShareRootSquashType rootSquash) {
      component.setRootSquash(rootSquash);
      return builder;
    }

    public AzureFileShareBuilder withShareQuota(Integer shareQuota) {
      component.setShareQuota(shareQuota);
      return builder;
    }

    public AzureFileShareBuilder withSignedIdentifiers(List<AzureFileShareSignedIdentifier> signedIdentifiers) {
      if (CollectionUtils.isBlank(signedIdentifiers)) {
        return builder;
      }

      if (component.getSignedIdentifiers() == null) {
        component.setSignedIdentifiers(new ArrayList<>());
      }

      component.signedIdentifiers.addAll(signedIdentifiers);
      return builder;
    }

    public AzureFileShareBuilder withSignedIdentifier(AzureFileShareSignedIdentifier signedIdentifier) {
      if (signedIdentifier == null) {
        return builder;
      }

      if (component.getSignedIdentifiers() == null) {
        component.setSignedIdentifiers(new ArrayList<>());
      }

      component.signedIdentifiers.add(signedIdentifier);
      return builder;
    }
  }
}
