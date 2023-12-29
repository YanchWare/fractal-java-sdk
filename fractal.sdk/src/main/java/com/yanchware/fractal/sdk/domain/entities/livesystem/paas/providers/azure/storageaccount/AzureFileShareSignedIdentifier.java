package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureFileShareSignedIdentifier implements Validatable {
  /**
   * A unique identifier of the stored access policy.
   */
  private String id;
  
  /**
   * Access policy
   */
  private AzureFileShareAccessPolicy accessPolicy;

  public static AzureFileShareSignedIdentifierBuilder builder() {
    return new AzureFileShareSignedIdentifierBuilder();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();
    
    if (StringUtils.isBlank(id)) {
      errors.add("ID cannot be null or empty.");
    }
    
    if (accessPolicy == null) {
      errors.add("Access policy cannot be null.");
    } else {
      errors.addAll(accessPolicy.validate());
    }

    return errors;
  }

  public static class AzureFileShareSignedIdentifierBuilder {
    private final AzureFileShareSignedIdentifier signedIdentifier;
    private final AzureFileShareSignedIdentifierBuilder builder;

    public AzureFileShareSignedIdentifierBuilder() {
      this.signedIdentifier = new AzureFileShareSignedIdentifier();
      this.builder = this;
    }

    public AzureFileShareSignedIdentifierBuilder withId(String id) {
      signedIdentifier.setId(id);
      return builder;
    }

    public AzureFileShareSignedIdentifierBuilder withAccessPolicy(AzureFileShareAccessPolicy accessPolicy) {
      signedIdentifier.setAccessPolicy(accessPolicy);
      return builder;
    }

    public AzureFileShareSignedIdentifier build() {
      var errors = signedIdentifier.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format("AzureFileShareSignedIdentifier validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return signedIdentifier;
    }
  }
}
