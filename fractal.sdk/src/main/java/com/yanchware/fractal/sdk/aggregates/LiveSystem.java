package com.yanchware.fractal.sdk.aggregates;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter(AccessLevel.PRIVATE)
public class LiveSystem implements Validatable {
    private final static String ID_IS_NULL = "LiveSystem id has not been defined and it is required";

    private String id;
    private String resourceGroupId;
    private Environment environment;
    private Timestamp created; //TODO check what timestamp to use
    private Timestamp lastUpdated;
    private Collection<Component> components;

    public static LiveSystem.LiveSystemBuilder builder() {
        return new LiveSystem.LiveSystemBuilder();
    }

    public static class LiveSystemBuilder {
        private LiveSystem liveSystem;
        private LiveSystem.LiveSystemBuilder builder;

        public LiveSystemBuilder() {
            liveSystem = createLiveSystem();
            builder = getBuilder();
            liveSystem.setCreated(Timestamp.from(Instant.now()));
        }

        protected LiveSystem createLiveSystem() {
            return new LiveSystem();
        }

        protected LiveSystem.LiveSystemBuilder getBuilder() {
            return this;
        }

        public LiveSystem.LiveSystemBuilder id(String id) {
            liveSystem.setId(id);
            return builder;
        }

        public LiveSystem.LiveSystemBuilder resourceGroupId(String resourceGroupId) {
            liveSystem.setResourceGroupId(resourceGroupId);
            return builder;
        }

        public LiveSystem.LiveSystemBuilder environment(Environment environment) {
            liveSystem.setEnvironment(environment);
            return builder;
        }

        public LiveSystem.LiveSystemBuilder components(Collection<? extends Component> components) {
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
