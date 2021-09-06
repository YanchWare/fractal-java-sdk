package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter(AccessLevel.PRIVATE)
public class Environment implements Validatable {
    private final static String ID_IS_NULL = "Environment id has not been defined and it is required";

    private String id;
    private String displayName;
    private String parentId;
    private String parentType;

    public static EnvironmentBuilder builder() {
        return new EnvironmentBuilder();
    }

    public static class EnvironmentBuilder {
        private Environment component;
        private EnvironmentBuilder builder;

        public EnvironmentBuilder() {
            component = createComponent();
            builder = getBuilder();
        }

        protected Environment createComponent() {
            return new Environment();
        }

        protected EnvironmentBuilder getBuilder() {
            return this;
        }

        public EnvironmentBuilder id(String id) {
            component.setId(id);
            return builder;
        }

        public EnvironmentBuilder displayName(String displayName) {
            component.setDisplayName(displayName);
            return builder;
        }

        public EnvironmentBuilder parentId(String parentId) {
            component.setParentId(parentId);
            return builder;
        }

        public EnvironmentBuilder parentType(String parentType) {
            component.setParentType(parentType);
            return builder;
        }

        public Environment build() {
            Collection<String> errors = component.validate();

            if (!errors.isEmpty()) {
                throw new IllegalArgumentException(String.format(
                        "Environment validation failed. Errors: %s",
                        Arrays.toString(errors.toArray())));
            }

            return component;
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
