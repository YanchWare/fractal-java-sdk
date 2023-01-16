package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.livesystem.CustomWorkload;
import jakarta.validation.constraints.AssertFalse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureWebAppHosting implements Validatable {
  String linuxFxVersion;
  String windowsFxVersion;
  
  ////////////////////////
  String javaContainer;
  String javaContainerVersion;
  String javaVersion;
  String dotnetVersion;
  String nodeVersion;
  String phpVersion;
  String pythonVersion;

  enum AzureWebAppHostingType {
    DOTNET ("dotnet"),
    JAVA ("java"),
    PYTHON("python"),
    PHP ("php"),
    NODEJS ("nodejs"),
    JAVA_CONTAINER ("java container");

    private final String id;

    AzureWebAppHostingType(final String id) {
      this.id = id;
    }

    @JsonValue
    public String getId() {
      return id;
    }

    @Override
    public String toString() {
      return id;
    }
  }
  
  @Override
  public Collection<String> validate(){
    final var DUPLICATED_HOSTING_TYPES = "[%s Validation] Only one hosting configuration can be set. [%s] has already been set";
    final var INCOMPLETE_JAVA_CONTAINER = "[%s Validation] Incomplete hosting types definition. Both [javaContainer] and [javaContainerVersion] must be set";
    final var NO_HOSTING_TYPES = "[%s Validation] No hosting types (dotnet, java, java container, nodejs, python, php) have been set";

    var errors = new ArrayList<String>();
    AzureWebAppHostingType selectedHosting = null;
    
    if (this.getDotnetVersion() != null && !StringUtils.isBlank(getDotnetVersion())) {
      selectedHosting = AzureWebAppHostingType.DOTNET;
    }
    if (getJavaVersion() != null && !StringUtils.isBlank(getJavaVersion())) {
      if(selectedHosting != null) {
        errors.add(String.format(DUPLICATED_HOSTING_TYPES, "javaVersion", selectedHosting));
        return errors;
      }
      selectedHosting = AzureWebAppHostingType.JAVA;
    }
    if (getPythonVersion() != null && !StringUtils.isBlank(getPythonVersion())) {
      if(selectedHosting != null) {
        errors.add(String.format(DUPLICATED_HOSTING_TYPES, "pythonVersion", selectedHosting));
        return errors;
      }
      selectedHosting = AzureWebAppHostingType.PYTHON;
    }
    
    if (getPhpVersion() != null && !StringUtils.isBlank(getPhpVersion())) {
      if(selectedHosting != null) {
        errors.add(String.format(DUPLICATED_HOSTING_TYPES, "phpVersion", selectedHosting));
        return errors;
      } else {
        selectedHosting = AzureWebAppHostingType.PHP;
      }
    }
    if (getNodeVersion() != null && !StringUtils.isBlank(getNodeVersion())) {
      if(selectedHosting != null) {
        errors.add(String.format(DUPLICATED_HOSTING_TYPES, "nodeVersion", selectedHosting));
        return errors;
      } else {
        selectedHosting = AzureWebAppHostingType.NODEJS;
      }
    }

    if (getJavaContainer() != null && !StringUtils.isBlank(getJavaContainer()) &&
        getJavaContainerVersion() != null && !StringUtils.isBlank(getJavaContainerVersion())) {
      if(selectedHosting != null) {
        errors.add(String.format(DUPLICATED_HOSTING_TYPES, "javaContainerVersion", selectedHosting));
        return errors;
      } else {
        selectedHosting = AzureWebAppHostingType.JAVA_CONTAINER;
      }
    } else if ((getJavaContainer() == null || StringUtils.isBlank(getJavaContainer())) &&
        getJavaContainerVersion() != null && !StringUtils.isBlank(getJavaContainerVersion())) {
      errors.add(String.format(INCOMPLETE_JAVA_CONTAINER, "javaContainer", selectedHosting));
      return errors;
    } else if (getJavaContainer() != null && !StringUtils.isBlank(getJavaContainer()) &&
        (getJavaContainerVersion() == null || StringUtils.isBlank(getJavaContainerVersion()))) {
      errors.add(String.format(INCOMPLETE_JAVA_CONTAINER, "javaContainerVersion", selectedHosting));
      return errors;
    }
    
    if(selectedHosting == null) {
      errors.add(String.format(NO_HOSTING_TYPES, "hosting"));
    }

    return errors;
  }
  
}
