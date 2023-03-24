package com.yanchware.fractal.sdk.domain.entities;

import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import com.yanchware.fractal.sdk.valueobjects.ComponentType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;

@Slf4j
@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class Component implements Validatable {
  private final static String ID_IS_NULL = "Component id has not been defined and it is required";
  private final static String COMPONENT_TYPE_NOT_DEFINED = "Component type has not been defined and it is required";

  private String displayName;
  @Setter(AccessLevel.PUBLIC)
  private ComponentType type;
  private ComponentId id;
  private String version;
  private boolean isLocked;
  private Set<ComponentId> dependencies;
  private Set<ComponentLink> links;
  private String description;

  protected Component() {
    links = new HashSet<>();
    dependencies = new HashSet<>();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (id == null) {
      errors.add(ID_IS_NULL);
    }

    if (type == null || type == ComponentType.UNKNOWN) {
      errors.add(COMPONENT_TYPE_NOT_DEFINED);
    }

    return errors;
  }

  public abstract static class Builder<T extends Component, B extends Builder<T, B>> {
    protected final T component;
    protected final B builder;

    public Builder() {
      component = createComponent();
      builder = getBuilder();
    }

    protected abstract T createComponent();
    protected abstract B getBuilder();

    public B withDisplayName(String displayName) {
      component.setDisplayName(displayName);
      return builder;
    }

    public B withId(ComponentId id) {
      component.setId(id);
      return builder;
    }

    public B withId(String id) {
      component.setId(ComponentId.from(id));
      return builder;
    }

    public B withVersion(String version) {
      component.setVersion(version);
      return builder;
    }

    public B withIsLocked(boolean isLocked) {
      component.setLocked(isLocked);
      return builder;
    }

    public B withDependencies(Collection<? extends ComponentId> dependencies) {
      if(component.getDependencies() == null) {
        component.setDependencies(new HashSet<>());
      }

      component.getDependencies().addAll(dependencies);

      return builder;
    }

    public B withDependency(ComponentId dependency) {
      return withDependencies(Set.of(dependency));
    }

    public B withLinks(Collection<? extends ComponentLink> links) {
      if(component.getLinks() == null) {
        component.setLinks(new HashSet<>());
      }

      component.getLinks().addAll(links);

      return builder;
    }

    public B withLink(ComponentLink link) {
      return withLinks(Set.of(link));
    }

    public B withDescription(String description) {
      component.setDescription(description);
      return builder;
    }

    public T build() {
      Collection<String> errors = component.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
          "Component '%s' validation failed. Errors: %s",
          this.getClass().getSimpleName(),
          Arrays.toString(errors.toArray())));
      }

      if (component.getDescription() == null) {
        component.setDescription(String.format("%s generated via SDK", component.getType().getId()));
      }

      //TODO Version is overwritten to 1.0, trying to fix this
      if (component.getVersion() == null) {
        component.setVersion(DEFAULT_VERSION);
      }

      return component;
    }
  }
}
