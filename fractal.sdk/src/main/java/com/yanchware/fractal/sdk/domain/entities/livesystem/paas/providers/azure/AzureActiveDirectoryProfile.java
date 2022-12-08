package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
public class AzureActiveDirectoryProfile implements Validatable {
  private static final String ADMIN_GROUP_OBJECT_IDS_IS_EMPTY = "[AzureActiveDirectoryProfile Validation] adminGroupObjectIDs is empty but it is required";
  @JsonProperty("managed")
  private Boolean managed;
  @JsonProperty("enableAzureRBAC")
  private Boolean enableAzureRbac;
  @JsonProperty("adminGroupObjectIDs")
  private List<String> adminGroupObjectIDs;


  public static AzureActiveDirectoryProfileBuilder builder() {
    return new AzureActiveDirectoryProfileBuilder();
  }
  
  private AzureActiveDirectoryProfile() {
  }

  //Builder Class
  public static class AzureActiveDirectoryProfileBuilder{
    private final AzureActiveDirectoryProfile aadProfile;
    private final AzureActiveDirectoryProfileBuilder builder;

    public AzureActiveDirectoryProfileBuilder () {
      aadProfile = createComponent();
      builder = getBuilder();
    }

    protected AzureActiveDirectoryProfile createComponent() {
      return new AzureActiveDirectoryProfile();
    }

    protected AzureActiveDirectoryProfileBuilder getBuilder() {
      return this;
    }

    public AzureActiveDirectoryProfileBuilder withManaged(Boolean managed) {
      aadProfile.setManaged(managed);
      return builder;
    }

    public AzureActiveDirectoryProfileBuilder withEnableAzureRbac(Boolean enableAzureRbac) {
      aadProfile.setEnableAzureRbac(enableAzureRbac);
      return builder;
    }

    public AzureActiveDirectoryProfileBuilder withAdminGroupObjectIDs(Collection<? extends String> adminGroupObjectIDs) {
      if (aadProfile.getAdminGroupObjectIDs() == null) {
        aadProfile.setAdminGroupObjectIDs(new ArrayList<>());
      }

      aadProfile.getAdminGroupObjectIDs().addAll(adminGroupObjectIDs);
      return builder;
    }

    public AzureActiveDirectoryProfileBuilder withAdminGroupObjectId(String adminGroupObjectId) {
      return withAdminGroupObjectIDs(List.of(adminGroupObjectId));
    }

    public AzureActiveDirectoryProfile build(){
      Collection<String> errors = aadProfile.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
            "AzureActiveDirectoryProfile validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }
      
      return aadProfile;
    }

  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (adminGroupObjectIDs != null && adminGroupObjectIDs.size() == 0) {
      errors.add(ADMIN_GROUP_OBJECT_IDS_IS_EMPTY);
    }
    
    return errors;
  }
}
