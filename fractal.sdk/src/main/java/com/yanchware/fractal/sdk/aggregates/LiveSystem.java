package com.yanchware.fractal.sdk.aggregates;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter(AccessLevel.PRIVATE)
public class LiveSystem implements Validatable {
    private final static String ID_IS_NULL = "LiveSystem id has not been defined and it is required";

    private String id;
    private String resourceGroupId;
    private Environment environment;
    private Date created;
    private Date lastUpdated;
    private Collection<Component> components;

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

        public LiveSystemBuilder id(String id) {
            liveSystem.setId(id);
            return builder;
        }

        public LiveSystemBuilder resourceGroupId(String resourceGroupId) {
            liveSystem.setResourceGroupId(resourceGroupId);
            return builder;
        }

        public LiveSystemBuilder environment(Environment environment) {
            liveSystem.setEnvironment(environment);
            return builder;
        }

        public LiveSystemBuilder components(Collection<? extends Component> components) {
            if (liveSystem.getComponents() == null) {
                liveSystem.setComponents(new ArrayList<>());
            }
            liveSystem.getComponents().addAll(components);
            return builder;
        }

        public LiveSystemBuilder component(Component component) {
            if (liveSystem.getComponents() == null) {
                liveSystem.setComponents(new ArrayList<>());
            }
            liveSystem.getComponents().add(component);
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

        if (id == null || id.isEmpty() || id.isBlank()) {
            errors.add(ID_IS_NULL);
        }
        return errors;
    }
}
