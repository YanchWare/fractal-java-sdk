package com.yanchware.fractal.sdk.domain;

import com.yanchware.fractal.sdk.domain.blueprint.caas.CaaSAPIGateway;
import com.yanchware.fractal.sdk.domain.livesystem.caas.CaaSKubernetesWorkload;
import com.yanchware.fractal.sdk.domain.values.ComponentId;
import com.yanchware.fractal.sdk.domain.values.ComponentType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;
import static org.apache.commons.lang3.StringUtils.isBlank;

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
  private boolean locked;
  private Set<ComponentId> dependencies;
  private Set<ComponentLink> links;
  private String description;
  @Setter(AccessLevel.PUBLIC)
  private boolean recreateOnFailure;

  protected Component() {
    links = new HashSet<>();
    dependencies = new HashSet<>();
    recreateOnFailure = false;
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

    /**
     * <pre>
     * Sets the display name for the current {@link Component} instance.
     *
     * This method allows setting a human-readable name for a component,
     * which can be useful for identification and management purposes within
     * the Fractal Cloud infrastructure. The display name is used across the
     * Fractal Cloud interface and other areas where a more descriptive,
     * human-friendly name for a component is preferable over its technical identifier.</pre>
     *
     * @param displayName A <code>String</code> representing the new display name for the component.
     *                    This cannot be <code>null</code> or empty.
     * @see Component
     */
    public B withDisplayName(String displayName) {
      component.setDisplayName(displayName);
      return builder;
    }

    /**
     * <pre>
     * Sets the unique identifier for the current {@link Component} instance using a {@link ComponentId}.
     *
     * The method validates the provided <code>ComponentId</code> against a series of rules defined in the
     * {@link ComponentId} class. These rules ensure that the ID is not null, not empty, starts with a
     * lowercase letter, contains only lowercase letters, digits, and hyphens, and adheres to length
     * constraints (between 3 and 63 characters).
     *
     * If the <code>ComponentId</code> fails validation, an {@link IllegalArgumentException} is thrown,
     * detailing the reason for failure based on the specific validation rule(s) the provided ID violated.</pre>
     *
     * @param id The {@link ComponentId} to set as the identifier for the component. Must not be <code>null</code>.
     * @throws IllegalArgumentException if the <code>id</code> is <code>null</code> or doesn't meet the validation
     *                                  criteria set by {@link ComponentId#validate(String)}.
     */
    public B withId(ComponentId id) {
      component.setId(id);
      return builder;
    }

    /**
     * <pre>
     * Sets the unique identifier for the current {@link Component} instance by converting a <code>String</code>
     * to a {@link ComponentId}.
     *
     * This convenience method facilitates the direct assignment of a component's ID using a string value.
     * It internally converts the string to a {@link ComponentId} instance, leveraging the validation rules
     * defined within the {@link ComponentId} class. These rules ensure the ID is properly formatted—starting
     * with a lowercase letter, containing only lowercase letters, digits, and hyphens, and conforming to
     * length constraints (between 3 and 63 characters).
     *
     * If the provided string fails to meet these validation criteria, an {@link IllegalArgumentException}
     * is thrown, indicating the specific validation failure(s). This ensures that only valid identifiers
     * are set for components, maintaining integrity within the Fractal Cloud infrastructure.</pre>
     *
     * @param id A <code>String</code> representing the desired identifier for the component. This string is
     *           converted to a {@link ComponentId}, and must adhere to the {@link ComponentId} validation rules.
     * @throws IllegalArgumentException if the <code>id</code> string is <code>null</code>, empty, or violates the
     *                                  validation rules defined by {@link ComponentId#validate(String)}.
     */
    public B withId(String id) {
      component.setId(ComponentId.from(id));
      return builder;
    }

    /**
     * <pre>
     * Assigns the version to the current {@link Component} instance.
     *
     * This method sets the version string for a component, intended for future use to distinguish
     * between different stages or configurations of a component's lifecycle. Currently, if a <code>null</code>
     * version is provided, the version is set to a default value of "1.0". This defaulting behavior
     * ensures backward compatibility and facilitates the future introduction of version management
     * without affecting existing components.
     *
     * The versioning feature, once in use, will allow for precise management and differentiation
     * between component iterations within the Fractal Cloud infrastructure, ensuring that operations
     * utilize the correct component version.</pre>
     *
     * @param version A <code>String</code> representing the new version for the component. If <code>null</code>,
     *                the version is set to the default value of "1.0". Future implementations will enforce
     *                specific format compliance with the application's versioning scheme.
     */
    public B withVersion(String version) {
      component.setVersion(version);
      return builder;
    }

    /**
     * <pre>
     * Locks the current {@link Component}, setting it to prevent hard deletions and enabling its removal
     * from the Fractal Cloud list of components by the customer without deleting the actual resource from
     * the cloud provider.
     *
     * When a component is locked, it is marked as immutable within Fractal Cloud, meaning that no operations
     * can lead to its hard deletion from the underlying cloud provider. This status allows the component to be
     * manually removed from the Fractal Cloud list of components by the customer as needed, without affecting
     * the component's actual resources on the cloud provider. The locked state is especially critical for
     * ensuring the component's persistence and availability in the cloud, even as it gets unlisted from
     * Fractal Cloud UI.
     *
     * This approach is ideal for components that need to be preserved outside the active management scope of
     * Fractal Cloud, such as for archival purposes or during periods of maintenance or transition. It is
     * important to note that while the component will no longer appear within Fractal Cloud's active component
     * list following its deletion by the customer, its resources remain intact and unmodified on the cloud
     * provider's platform.</pre>
     */
    public B lock() {
      component.setLocked(true);
      return builder;
    }

    /**
     * <pre>
     * Unlocks the current {@link Component}, enabling it for standard management procedures within Fractal Cloud,
     * including the potential for hard deletion. This method reinstates the component as fully managed by Fractal Cloud,
     * preparing it for any operational workflows that include its management and potential deletion.
     *
     * <b>Warning:</b> Unlocking can only be applied to components that have not been permanently deleted.
     * It is crucial to ensure that the component is still present and has not undergone a hard deletion process.
     *
     * Use this method with caution to avoid unintended consequences, particularly with components critical to
     * operational stability. Ensure that the component is in a state that should be managed and potentially
     * deleted by Fractal Cloud before proceeding with unlocking.</pre>
     */
    public B unlock() {
      component.setLocked(false);
      return builder;
    }

    /**
     * <pre>
     * Adds a collection of dependencies to the current {@link Component}.
     *
     * This method allows for the specification of dependencies that must be instantiated before
     * the current component, ensuring all necessary resources are ready and available for the
     * component's instantiation. Dependencies are managed as a set, avoiding duplicate entries
     * and ensuring each dependency is only considered once.</pre>
     *
     * @param dependencies A collection of {@link ComponentId} representing the dependencies to be
     *                     added to the component. This collection must not be null, and each
     *                     <code>ComponentId</code> within it represents a unique component dependency
     *                     that must be available prior to the instantiation of the current component.
     */
    public B withDependencies(Collection<? extends ComponentId> dependencies) {
      if (component.getDependencies() == null) {
        component.setDependencies(new HashSet<>());
      }

      component.getDependencies().addAll(dependencies);

      return builder;
    }

    /**
     * <pre>
     * Adds a single dependency to the current {@link Component}.
     *
     * This method provides a convenient way to add a single dependency to the component, ensuring that
     * this dependency is instantiated before the component itself. It leverages the {@link #withDependencies(Collection)}
     * method, wrapping the single <code>dependency</code> in a set and passing it on for addition. This approach
     * maintains the integrity of dependency management, ensuring that all dependencies, whether added
     * individually or in a collection, are handled uniformly.
     *
     * Utilizing this method simplifies the process of adding dependencies one at a time, while still
     * ensuring that the component's instantiation logic respects the necessary order, with all dependencies
     * being ready before the component itself is instantiated.</pre>
     *
     * @param dependency The {@link ComponentId} representing the dependency to be added to the
     *                   component. This dependency is required to be instantiated before the
     *                   current component. The dependency must not be null.
     */
    public B withDependency(ComponentId dependency) {
      return withDependencies(Set.of(dependency));
    }


    /**
     * <pre>
     * Adds a collection of links to the current {@link Component}.
     *
     * Utilizes {@link ComponentLink} objects to define connections or relationships between this component
     * and others within the system. Each link specifies a target component through its ID and includes
     * an optional map of settings to configure the relationship's properties. The method ensures all links
     * are valid and adhere to the constraints defined in {@link ComponentLink#validate()} before adding them
     * to the component.</pre>
     *
     * @param links A collection of {@link ComponentLink} objects representing the links to be added.
     *              Each link must pass validation checks as defined in {@link ComponentLink#validate()},
     *              ensuring that all specified component IDs are valid and that the settings map adheres
     *              to expected formats and values.
     * @throws IllegalArgumentException If any {@link ComponentLink} in the collection fails validation,
     *                                  indicating problems with the component ID validity or settings map
     *                                  configuration.
     */
    public B withLinks(Collection<? extends ComponentLink> links) {
      if (component.getLinks() == null) {
        component.setLinks(new HashSet<>());
      }

      component.getLinks().addAll(links);

      return builder;
    }

    /**
     * <pre>
     * Adds a single link to the current {@link Component}.
     *
     * This method facilitates the addition of a {@link ComponentLink} to define a connection or relationship
     * between this component and another within the system. The specified link identifies a target component
     * through its ID and may include an optional map of settings to configure the relationship's properties.
     * The link is validated according to the constraints defined in {@link ComponentLink#validate()} to ensure
     * it adheres to the necessary standards before being added to the component.
     * </pre>
     *
     * @param link A {@link ComponentLink} object representing the link to be added. The link must pass
     *             validation checks as outlined in {@link ComponentLink#validate()}, confirming the validity
     *             of the specified component ID and the appropriateness of any settings.
     * @throws IllegalArgumentException If the {@link ComponentLink} fails validation, indicating issues
     *                                  with the component ID's validity or the configuration of the settings map.
     */
    public B withLink(ComponentLink link) {
      return withLinks(Set.of(link));
    }


    /**
     * <pre>
     * Sets the description for the current {@link Component}. If the provided description is empty or null,
     * a default description is generated based on the component's type, ensuring the component always has
     * a meaningful description.
     *
     * This method facilitates the provision of detailed information about the component's purpose, usage, or
     * any other relevant details. In cases where the description is not explicitly provided or is deemed invalid
     * (e.g., empty or null), the component is assigned a default description formatted as
     * "[ComponentType] generated via SDK", where [ComponentType] is the identifier of the component's type.
     * This ensures that every component managed by the SDK has a descriptive text, enhancing clarity and
     * manageability within the system.</pre>
     *
     * @param description A <code>String</code> representing the description to set for the component. If this parameter
     *                    is blank (null, empty, or whitespace only), a default description is automatically generated.
     */
    public B withDescription(String description) {
      component.setDescription(description);
      return builder;
    }

    /**
     * <pre>
     * Configures the current {@link Component} to be either recreated or not in the event of a failure.
     *
     * This method sets a flag determining whether the component should be automatically recreated
     * upon encountering a failure during its lifecycle. This feature is crucial for maintaining
     * high availability and resilience, particularly for critical components where automatic recovery
     * is preferred to manual intervention. By default, components do not automatically recreate on failure
     * (<code>recreateOnFailure = false</code>). However, this behavior is overridden to <code>true</code> in specific
     * subclasses, such as {@link CaaSAPIGateway} and {@link CaaSKubernetesWorkload}, to enhance their
     * reliability and self-healing capabilities.
     *
     * Setting this flag to <code>true</code> enables the component for automatic recreation following a failure,
     * while <code>false</code> disables this behavior, necessitating manual remediation to resolve
     * failures. This setting permits tailored lifecycle and resilience strategies for different
     * component types within the system, accommodating both automated recovery needs and scenarios
     * where manual intervention is preferred.</pre>
     *
     * @param recreateOnFailure A <code>boolean</code> value indicating whether the component should be
     *                          recreated on failure (<code>true</code>) or not (<code>false</code>). The default
     *                          behavior may vary based on the component's type or subclass, with certain
     *                          types defaulting to automatic recreation to ensure continuity and resilience.
     */
    public B withRecreateOnFailure(boolean recreateOnFailure) {
      component.setRecreateOnFailure(recreateOnFailure);
      return builder;
    }

    /**
     * <pre>
     * Finalizes the building process of the {@link Component}, ensuring it meets all necessary validation
     * criteria before instantiation.
     *
     * This method performs a final validation check on the component to ensure that all required fields
     * and configurations meet the system's specifications. If any validation errors are found, an
     * {@link IllegalArgumentException} is thrown, detailing the reasons for failure. This approach guarantees
     * that only fully compliant and correctly configured components are created.
     *
     * If the component's description is not explicitly set, a default description is generated based on
     * the component's type to ensure every component is accompanied by descriptive text. Additionally, if
     * the version is not specified, it defaults to <code>DEFAULT_VERSION</code>, which is currently "1.0". This
     * standardizes the component versioning, simplifying management and integration within the system.</pre>
     *
     * @return The fully constructed and validated {@link Component} instance, ready for use within the system.
     * @throws IllegalArgumentException if the component fails any validation checks, with a detailed
     *                                  message of the validation errors encountered.
     */
    public T build() {
      Collection<String> errors = component.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
          "Component '%s' validation failed. Errors: %s",
          this.getClass().getSimpleName(),
          Arrays.toString(errors.toArray())));
      }

      if (isBlank(component.getDescription())) {
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
