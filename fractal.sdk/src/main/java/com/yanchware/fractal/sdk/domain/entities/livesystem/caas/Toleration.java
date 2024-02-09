package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
public class Toleration implements Validatable {
  private String key;
  private TolerationOperator operator;
  private String value;
  private TaintEffect effect;

  protected Toleration() {
  }


  public static TolerationBuilder builder() {
    return new TolerationBuilder();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (isBlank(key)) {
      errors.add("The key must be provided for a toleration");
    }
    
    if (effect == null) {
      errors.add("The effect must be provided for a toleration");
    }

    if (operator == null) {
      errors.add("The operator must be provided for a toleration");
    }
    
    if(operator != null) {
      if (TolerationOperator.EQUAL.equals(operator) && isBlank(value)) {
        errors.add("A value must be provided when the operator is 'Equal'");
      }
    }
  
    return errors;
  }

  public static class TolerationBuilder {
    private final Toleration toleration;
    private final TolerationBuilder builder;

    public TolerationBuilder() {
      this.toleration = new Toleration();
      this.builder = this;
    }

    /**
     * <pre>
     * Sets the key for the toleration. 
     * The key corresponds to the taint key that this toleration is meant to match.</pre>
     *
     * @param key The label key of the taint to tolerate.
     */
    public TolerationBuilder withKey(String key) {
      toleration.setKey(key);
      return builder;
    }

    /**
     * <pre>
     * Sets the operator for the toleration. 
     * The operator defines how to match the taint's value.
     * Use TolerationOperator.EQUAL for exact matches or TolerationOperator.EXISTS to ignore the taint's value.</pre>
     *
     * @param operator The matching operator for the toleration.
     */
    public TolerationBuilder withOperator(TolerationOperator operator) {
      toleration.setOperator(operator);
      return builder;
    }

    /**
     * <pre>
     * Sets the value for the toleration. 
     * This is the value that the taint must have if the operator is TolerationOperator.EQUAL.</pre>
     *
     * @param value The value that must match the taint's value.
     */
    public TolerationBuilder withValue(String value) {
      toleration.setValue(value);
      return builder;
    }

    /**
     * <pre>
     * Sets the effect for the toleration. 
     * The effect specifies what happens to a pod if it does not tolerate the taint. 
     * Common effects include NoSchedule, PreferNoSchedule, and NoExecute.</pre>
     *
     * @param effect The effect of the taint that the pod should tolerate.
     */
    public TolerationBuilder withEffect(TaintEffect effect) {
      toleration.setEffect(effect);
      return builder;
    }
    

    public Toleration build() {
      var errors = toleration.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format("Toleration validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return toleration;
    }
  }
}
