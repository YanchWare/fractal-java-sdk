package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Getter
@Setter(AccessLevel.PRIVATE)
public class PriorityClass implements Validatable {
  private final static String VALUE_IS_NOT_ALLOWED = "[PriorityClass Validation] Value must be between 1 and 1_000_000_000";

  private String name;
  private String description;
  private PreemptionPolicy preemptionPolicy;
  private Integer value;

  public static PriorityClassBuilder builder() {
    return new PriorityClassBuilder();
  }

  public static class PriorityClassBuilder {
    private final PriorityClass priorityClass;
    private final PriorityClassBuilder builder;

    public PriorityClassBuilder() {
      this.priorityClass = new PriorityClass();
      this.builder = this;
    }

    public PriorityClassBuilder withName(String name) {
      priorityClass.setName(name);
      return builder;
    }

    public PriorityClassBuilder withDescription(String description) {
      priorityClass.setDescription(description);
      return builder;
    }

    public PriorityClassBuilder withPreemptionPolicy(PreemptionPolicy preemptionPolicy) {
      priorityClass.setPreemptionPolicy(preemptionPolicy);
      return builder;
    }

    public PriorityClassBuilder withValue(Integer value) {
      priorityClass.setValue(value);
      return builder;
    }

    public PriorityClass build() {
      var errors = priorityClass.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format("PriorityClass validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return priorityClass;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (value == null || value < 1 || value > 1_000_000_000) {
      errors.add(VALUE_IS_NOT_ALLOWED);
    }

    return errors;
  }
}
