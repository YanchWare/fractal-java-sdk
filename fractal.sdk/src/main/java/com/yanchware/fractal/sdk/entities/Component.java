package com.yanchware.fractal.sdk.entities;

import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class Component {
  private String displayName;
  private String type;
  private ComponentId id;
  private String version;
  private Set<ComponentId> dependencies;
  private Set<ComponentId> links;
  private String description;

  protected Component() {
    links = new HashSet<>();
    dependencies = new HashSet<>();
  }

  public static ComponentBuilder builder() {
    return new ComponentBuilder();
  }

  public static class ComponentBuilder {
    private final Component component;
    private String temporaryComponentId;
    private final Set<String> temporaryDependencies;
    private final Set<String> temporaryLinks;

    public ComponentBuilder() {
      this.component = new Component();
      temporaryLinks = new HashSet<>();
      temporaryDependencies = new HashSet<>();
    }

    public T displayName(String displayName) {
      component.setDisplayName(displayName);
      return this;
    }

    public T type<T extends ComponentB>(String type) {
      component.setType(type);
      return this;
    }

    public T id(String id) {
      temporaryComponentId = id;
      return this;
    }

    public ComponentBuilder id(ComponentId id) {
      component.setId(id);
      return this;
    }

    public ComponentBuilder version(String version) {
      component.setVersion(version);
      return this;
    }

    public ComponentBuilder dependencies(Set<String> dependencies) {
      temporaryDependencies.addAll(dependencies);
      return this;
    }

    public ComponentBuilder dependency(String dependency) {
      temporaryDependencies.add(dependency);
      return this;
    }

    public ComponentBuilder dependency(ComponentId dependency) {
      if(component.getDependencies() == null) {
        component.setDependencies(new HashSet<>());
      }
      component.getDependencies().add(dependency);
      return this;
    }

    public ComponentBuilder links(Set<String> links) {
      temporaryLinks.addAll(links);
      return this;
    }

    public ComponentBuilder link(String link) {
      temporaryLinks.add(link);
      return this;
    }

    public ComponentBuilder link(ComponentId link) {
      if(component.getLinks() == null) {
        component.setLinks(new HashSet<>());
      }
      component.getLinks().add(link);
      return this;
    }

    public ComponentBuilder description(String description) {
      component.setDescription(description);
      return this;
    }

    public Collection<String> validate() {
      Collection<String> errors = new ArrayList<>();
      if (component.getId() == null) {
        errors.addAll(ComponentId.Validate(temporaryComponentId));
      }

      errors.addAll(
        Stream.concat(temporaryDependencies.stream(), temporaryLinks.stream())
        .map(ComponentId::Validate)
        .flatMap(Collection::stream)
        .collect(Collectors.toList()));

      return errors;
    }

    public Component build() {
      Collection<String> errors = validate();

      if (errors.isEmpty()) {
        if (component.getId() == null) {
          component.setId(ComponentId.With(temporaryComponentId));
        }

        component.getDependencies().addAll(
          temporaryDependencies.stream()
            .map(ComponentId::With)
            .collect(Collectors.toSet()));
        component.setDependencies(Collections.unmodifiableSet(component.getDependencies()));

        component.getLinks().addAll(
          temporaryLinks.stream()
            .map(ComponentId::With)
            .collect(Collectors.toSet()));
        component.setLinks(Collections.unmodifiableSet(component.getLinks()));
      } else {
        throw new IllegalArgumentException(String.format(
          "Component validation failed. Errors: %s",
          Arrays.toString(errors.toArray())));
      }

      return component;
    }
  }
}
