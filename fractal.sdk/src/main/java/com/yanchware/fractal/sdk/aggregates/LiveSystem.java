package com.yanchware.fractal.sdk.aggregates;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;
import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
public class LiveSystem implements Validatable {
    private final static String ID_IS_NULL = "[LiveSystem Validation] Id has not been defined and it is required";
    private final static String RESOURCE_GROUP_ID_IS_NULL = "[LiveSystem Validation] ResourceGroupId has not been defined and it is required'";
    private final static String EMPTY_COMPONENT_LIST = "[LiveSystem Validation] Components list is null or empty and at least one component is required";

    private String name;
    private String resourceGroupId;
    private String fractalName;
    private String description;
    private Environment environment;
    private Date created;
    private Date lastUpdated;
    private Collection<LiveSystemComponent> components;

    protected LiveSystem() {
        components = new ArrayList<>();
    }

    public static LiveSystemBuilder builder() {
        return new LiveSystemBuilder();
    }

    public static class LiveSystemBuilder {
        private LiveSystem liveSystem;
        private LiveSystemBuilder builder;

        public LiveSystemBuilder() {
            liveSystem = createLiveSystem();
            builder = getBuilder();
            liveSystem.setCreated(new Date());
        }

        protected LiveSystem createLiveSystem() {
            return new LiveSystem();
        }

        protected LiveSystemBuilder getBuilder() {
            return this;
        }

        public LiveSystemBuilder withName(String name) {
            liveSystem.setName(name);
            return builder;
        }

        public LiveSystemBuilder withFractalName(String fractalName) {
            if(isBlank(fractalName)) {
                throw new IllegalArgumentException("Fractal name cannot be null or empty");
            }
            liveSystem.setFractalName(fractalName);
            return builder;
        }

        public LiveSystemBuilder withResourceGroupId(String resourceGroupId) {
            liveSystem.setResourceGroupId(resourceGroupId);
            return builder;
        }

        public LiveSystemBuilder withDescription(String description) {
            liveSystem.setDescription(description);
            return builder;
        }

        public LiveSystemBuilder withEnvironment(Environment environment) {
            liveSystem.setEnvironment(environment);
            return builder;
        }

        public LiveSystemBuilder withComponents(Collection<? extends LiveSystemComponent> components) {
            if (isBlank(components)) {
                return builder;
            }

            if (liveSystem.getComponents() == null) {
                liveSystem.setComponents(new ArrayList<>());
            }

            liveSystem.getComponents().addAll(components);
            return builder;
        }

        public LiveSystemBuilder withComponent(LiveSystemComponent component) {
            return withComponents(List.of(component));
        }

        public LiveSystem build() {
            Collection<String> errors = liveSystem.validate();

            if (!errors.isEmpty()) {
                throw new IllegalArgumentException(String.format(
                        "LiveSystem validation failed. Errors: %s",
                        Arrays.toString(errors.toArray())));
            }

            return liveSystem;
        }
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = new ArrayList<>();

        if (isBlank(name)) {
            errors.add(ID_IS_NULL);
        }

        if (isBlank(resourceGroupId)) {
            errors.add(RESOURCE_GROUP_ID_IS_NULL);
        }

        if (components.isEmpty()) {
            errors.add(EMPTY_COMPONENT_LIST);
        }
        return errors;
    }

    public String getFractalId() {
        return String.format("%s/%s:%s", getResourceGroupId(),
            getFractalName() != null ? getFractalName() : getName(), DEFAULT_VERSION);
    }

    public String getLiveSystemId() {
        return String.format("%s/%s", getResourceGroupId(), getName());
    }
}
