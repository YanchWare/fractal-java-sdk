package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(setterPrefix = "with")
public class PriorityClass implements Validatable {
  private final static String VALUE_IS_NOT_ALLOWED = "[PriorityClass Validation] Value must be between 1 and 1_000_000_000";

  private String name;
  private String description;
  private PreemptionPolicy preemptionPolicy;
  private Integer value;

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (value == null || value < 1 || value > 1_000_000_000) {
      errors.add(VALUE_IS_NOT_ALLOWED);
    }

    return errors;
  }
}
