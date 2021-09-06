package com.yanchware.fractal.sdk.domain.entities;

import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import com.yanchware.fractal.sdk.valueobjects.ComponentType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter(AccessLevel.PROTECTED)
public abstract class Component implements Validatable {
  private final static String ID_IS_NULL = "Component id has not been defined and it is required";
  private final static String COMPONENT_TYPE_NOT_DEFINED = "Component type has not been defined and it is required";

  private String displayName;
  private ComponentType type;
  private ComponentId id;
  private String version;
  private Set<ComponentId> dependencies;
  private Set<ComponentId> links;
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

    if (type == ComponentType.Unknown) {
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

    public B displayName(String displayName) {
      component.setDisplayName(displayName);
      return builder;
    }

    public B type(ComponentType type) {
      component.setType(type);
      return builder;
    }

    public B id(ComponentId id) {
      component.setId(id);
      return builder;
    }

    public B version(String version) {
      component.setVersion(version);
      return builder;
    }

    public B dependencies(Collection<? extends ComponentId> dependencies) {
      if(component.getDependencies() == null) {
        component.setDependencies(new HashSet<>());
      }

      component.getDependencies().addAll(dependencies);

      return builder;
    }

    public B dependency(ComponentId dependency) {
      if(component.getDependencies() == null) {
        component.setDependencies(new HashSet<>());
      }
      component.getDependencies().add(dependency);
      return builder;
    }

    public B links(Collection<? extends ComponentId> links) {
      if(component.getLinks() == null) {
        component.setLinks(new HashSet<>());
      }

      component.getLinks().addAll(links);

      return builder;
    }

    public B link(ComponentId link) {
      if(component.getLinks() == null) {
        component.setLinks(new HashSet<>());
      }
      component.getLinks().add(link);
      return builder;
    }

    public B description(String description) {
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

      return component;
    }
  }
}
