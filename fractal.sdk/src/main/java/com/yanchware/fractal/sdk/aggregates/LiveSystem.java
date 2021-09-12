package com.yanchware.fractal.sdk.aggregates;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
public class LiveSystem implements Validatable {
    private final static String ID_IS_NULL = "[LiveSystem Validation] Id has not been defined and it is required";
    private final static String RESOURCE_GROUP_ID_IS_NULL = "[LiveSystem Validation] ResourceGroupId has not been defined and it is required'";
    private final static String EMPTY_COMPONENT_LIST = "[LiveSystem Validation] Components list is null or empty and at least one component is required";

    private String name;
    private String resourceGroupId;
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

        public LiveSystemBuilder name(String name) {
            liveSystem.setName(name);
            return builder;
        }

        public LiveSystemBuilder resourceGroupId(String resourceGroupId) {
            liveSystem.setResourceGroupId(resourceGroupId);
            return builder;
        }

        public LiveSystemBuilder description(String description) {
            liveSystem.setDescription(description);
            return builder;
        }

        public LiveSystemBuilder environment(Environment environment) {
            liveSystem.setEnvironment(environment);
            return builder;
        }

        public LiveSystemBuilder components(Collection<? extends LiveSystemComponent> components) {
            if (liveSystem.getComponents() == null) {
                liveSystem.setComponents(new ArrayList<>());
            }

            if (components == null) {
                components = new ArrayList<>();
            }

            liveSystem.getComponents().addAll(components);
            return builder;
        }

        public LiveSystemBuilder component(LiveSystemComponent component) {
            if (liveSystem.getComponents() == null) {
                liveSystem.setComponents(new ArrayList<>());
            }
            if (component != null) {
                liveSystem.getComponents().add(component);
            }
            return builder;
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

        if (resourceGroupId == null || resourceGroupId.isEmpty() || resourceGroupId.isBlank()) {
            errors.add(RESOURCE_GROUP_ID_IS_NULL);
        }

        if (components.isEmpty()) {
            errors.add(EMPTY_COMPONENT_LIST);
        }
        return errors;
    }
}
