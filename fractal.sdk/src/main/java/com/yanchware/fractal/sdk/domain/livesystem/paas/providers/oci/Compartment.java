package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yanchware.fractal.sdk.domain.Validatable;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * A container that holds related resources for an OCI solution.
 * The resource group can include all the resources for the solution, or only those resources that you want to manage as a group.
 * You decide how you want to allocate resources to resource groups based on what makes the most sense for you  .
 */
@Getter
public class Compartment implements Validatable {
    @JsonIgnore
    public static final String NAME_IS_NULL_OR_EMPTY = "[OCI Compartment Validation] Name cannot be null or empty";
    @JsonIgnore
    public static final String NAME_IS_NOT_VALID = "[OCI Compartment Validation] Name can only include alphanumeric, underscore, parentheses, hyphen, period (except at end), and Unicode characters that match the allowed characters";
    private static final String NAME_EXCEEDS_LENGTH_LIMIT = "[OCI Compartment Validation] Name exceeds 90 characters";
    private static final Pattern COMPARTMENT_NAME_PATTERN = Pattern.compile("^[\\w()-.]+[\\w()-]$");

    private String name;
    private String parentId;
    private Map<String, String> tags;

    public Compartment() {
    }

    public Compartment(String name, String parentId, Map<String, String> tags) {
        this.name = name;
        this.parentId = parentId;
        this.tags = tags;
    }

    public static CompartmentBuilder builder() {
        return new CompartmentBuilder();
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = new ArrayList<>();
        var name = this.getName();

        if (StringUtils.isBlank(name)) {
            errors.add(NAME_IS_NULL_OR_EMPTY);
        }

        if (StringUtils.isNotBlank(name) && !COMPARTMENT_NAME_PATTERN.matcher(name).matches()) {
            errors.add(NAME_IS_NOT_VALID);
        }

        if (StringUtils.isNotBlank(name) && name.length() > 90) {
            errors.add(NAME_EXCEEDS_LENGTH_LIMIT);
        }

        return errors;
    }

    public static class CompartmentBuilder {

        protected String name;
        protected String parentId;
        protected Map<String, String> tags;

        public CompartmentBuilder() {
        }

        public CompartmentBuilder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Tags are name/value pairs that enable you to categorize resources and view consolidated billing by
         * applying the same tag to multiple resources and resource groups.
         */
        public CompartmentBuilder withTags(Map<String, String> tags) {
            if (this.tags == null) {
                this.tags = new HashMap<>();
            }
            this.tags.putAll(tags);
            return this;
        }

        /**
         * Tag is name/value pairs that enable you to categorize resources and view consolidated billing by
         * applying the same tag to multiple resources and resource groups.
         */
        public CompartmentBuilder withTag(String key, String value) {
            if (this.tags == null) {
                withTags(new HashMap<>());
            }

            this.tags.put(key, value);
            return this;
        }

        public Compartment build() {
            var compartment = new Compartment(name, parentId, tags);

            var errors = compartment.validate();

            if (!errors.isEmpty()) {
                throw new IllegalArgumentException(String.format(
                        "Oci compartmenr validation failed. Errors: %s",
                        Arrays.toString(errors.toArray())));
            }

            return compartment;
        }
    }
}
