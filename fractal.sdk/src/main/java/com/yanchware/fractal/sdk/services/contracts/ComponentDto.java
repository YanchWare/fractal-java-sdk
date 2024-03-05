package com.yanchware.fractal.sdk.services.contracts;

import com.yanchware.fractal.sdk.configuration.Constants;
import com.yanchware.fractal.sdk.domain.entities.ComponentLink;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

import static com.yanchware.fractal.sdk.configuration.Constants.*;
import static java.util.stream.Collectors.toSet;

@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class ComponentDto {
  private String id;
  private String displayName;
  private String description;
  private String type;
  private String version;
  private boolean locked;
  private boolean recreateOnFailure;
  private Map<String, Object> parameters;
  private Set<String> dependencies;
  private Set<ComponentLink> links;

  public abstract static class Builder<T extends ComponentDto, B extends ComponentDto.Builder<T, B>> {
    protected final T componentDto;
    protected final B builder;

    public Builder() {
      componentDto = createComponent();
      builder = getBuilder();
    }

    protected abstract T createComponent();

    protected abstract B getBuilder();

    public B withId(String id) {
      componentDto.setId(id);
      return builder;
    }

    public B withDisplayName(String displayName) {
      componentDto.setDisplayName(displayName);
      return builder;
    }

    public B withDescription(String description) {
      componentDto.setDescription(description);
      return builder;
    }

    public B withType(String type) {
      componentDto.setType(type);
      return builder;
    }

    public B withVersion(String version) {
      componentDto.setVersion(version);
      return builder;
    }

    public B lock() {
      componentDto.setLocked(true);
      return builder;
    }

    public B unlock() {
      componentDto.setLocked(false);
      return builder;
    }

    public B withRecreateOnFailure(boolean recreateOnFailure) {
      componentDto.setRecreateOnFailure(recreateOnFailure);
      return builder;
    }

    public B withParameters(Map<String, Object> parameters) {
      if (componentDto.getParameters() == null) {
        componentDto.setParameters(new HashMap<>());
      }
      componentDto.getParameters().putAll(parameters);
      return builder;
    }

    public B withDependencies(Collection<? extends String> dependencies) {
      if (componentDto.getDependencies() == null) {
        componentDto.setDependencies(new HashSet<>());
      }
      componentDto.getDependencies().addAll(dependencies);
      return builder;
    }

    public B withLinks(Collection<? extends ComponentLink> links) {
      if (componentDto.getLinks() == null) {
        componentDto.setLinks(new HashSet<>());
      }
      componentDto.getLinks().addAll(links);
      return builder;
    }

    public B withFields(Map<String, Object> allFields) {
      String id = ((ComponentId) allFields.get(ID_KEY)).getValue();
      Set<ComponentId> componentIds = (Set<ComponentId>) allFields.get(DEPENDENCIES_KEY);
      withId(id);
      withDisplayName(String.valueOf(allFields.get(DISPLAY_NAME_KEY)));
      withDescription(String.valueOf(allFields.get(DESCRIPTION_KEY)));
      withVersion(String.valueOf(allFields.get(VERSION_KEY)));
      if ((Boolean)allFields.get(LOCKED_KEY)) {
        lock();
      }
      withRecreateOnFailure((Boolean)allFields.get(RECREATE_ON_FAILURE_KEY));
      withParameters((Map<String, Object>) allFields.get(PARAMETERS_KEY));
      withDependencies(componentIds.stream().map(ComponentId::getValue).collect(toSet()));
      withLinks((Set<ComponentLink>) allFields.get(Constants.LINKS_KEY));
      return builder;
    }

    public T build() {
      return componentDto;
    }
  }
}
