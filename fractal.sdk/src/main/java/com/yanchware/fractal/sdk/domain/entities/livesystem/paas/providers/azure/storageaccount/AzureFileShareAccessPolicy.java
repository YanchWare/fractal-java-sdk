package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureFileShareAccessPolicy implements Validatable {
  
  private OffsetDateTime expiryTime;
  private String permission;
  private OffsetDateTime startTime;

  public static AzureFileShareAccessPolicyBuilder builder() {
    return new AzureFileShareAccessPolicyBuilder();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();
    
    if (expiryTime == null) {
      errors.add("Expiry time cannot be null");
    } else if (startTime != null && expiryTime.isBefore(startTime)) {
      errors.add("Expiry time must be after start time");
    }
    
    if (permission == null || permission.trim().isEmpty()) {
      errors.add("Permission cannot be null or empty");
    }
    
    if (startTime == null) {
      errors.add("Start time cannot be null");
    }

    return errors;
  }

  public static class AzureFileShareAccessPolicyBuilder {
    private final AzureFileShareAccessPolicy accessPolicy;
    private final AzureFileShareAccessPolicyBuilder builder;

    public AzureFileShareAccessPolicyBuilder() {
      this.accessPolicy = new AzureFileShareAccessPolicy();
      this.builder = this;
    }

    /**
     * <pre>
     * Expiry time of the access policy.
     * </pre>
     */
    public AzureFileShareAccessPolicyBuilder withExpiryTime(OffsetDateTime expiryTime) {
      accessPolicy.setExpiryTime(expiryTime);
      return builder;
    }

    /**
     * <pre>
     * List of abbreviated permissions.
     * </pre>
     */
    public AzureFileShareAccessPolicyBuilder withPermission(String permission) {
      accessPolicy.setPermission(permission);
      return builder;
    }

    /**
     * <pre>
     * Start time of the access policy.
     * </pre>
     */
    public AzureFileShareAccessPolicyBuilder withStartTime(OffsetDateTime startTime) {
      accessPolicy.setStartTime(startTime);
      return builder;
    }

    public AzureFileShareAccessPolicy build() {
      var errors = accessPolicy.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format("AzureFileShareAccessPolicy validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return accessPolicy;
    }
  }
}
