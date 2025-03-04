package com.yanchware.fractal.sdk.domain;

import com.yanchware.fractal.sdk.domain.values.ComponentId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
public class ComponentLink implements Validatable {
  private static final String ROLE_NAME_PARAM_KEY = "roleName";

  public String componentId;
  public Map<String, Object> settings;

  public ComponentLink() {
    settings = new HashMap<>();
  }

  public static ComponentLinkBuilder builder() {
    return new ComponentLinkBuilder();
  }

  public static class ComponentLinkBuilder {
    private final ComponentLink componentLink;
    private final ComponentLinkBuilder builder;

    public ComponentLinkBuilder() {
      componentLink = createComponentLink();
      builder = getBuilder();
    }

    protected ComponentLink createComponentLink() {
      return new ComponentLink();
    }

    protected ComponentLinkBuilder getBuilder() {
      return this;
    }

    public ComponentLinkBuilder withComponentId(Component component) {
      componentLink.setComponentId(component.getId().getValue());
      return builder;
    }

    public ComponentLinkBuilder withComponentId(ComponentId componentId) {
      componentLink.setComponentId(componentId.getValue());
      return builder;
    }

    public ComponentLinkBuilder withComponentId(String componentId) {
      componentLink.setComponentId(componentId);
      return builder;
    }

    public ComponentLinkBuilder withSettings(Map<String, Object> settings) {
      componentLink.settings.putAll(settings);
      return builder;
    }

    public ComponentLinkBuilder withRoleName(String roleName) {
      componentLink.settings.put(ROLE_NAME_PARAM_KEY, roleName);

      return builder;
    }

    public ComponentLink build() {
      Collection<String> errors = componentLink.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
            "ComponentLink validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return componentLink;
    }
  }

  @Override
  public Collection<String> validate() {
    return ComponentId.validate(componentId);
  }
}
