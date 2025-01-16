package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * <pre>
 * Represents the set of valid operators used in Kubernetes tolerations.
 * A toleration operator determines how the toleration matches taints on nodes.
 *
 * - EQUAL: Specifies that the toleration matches taints with the same key and effect, and exactly the same value.
 * - EXISTS: Specifies that the toleration matches taints with the same key and effect, regardless of the taint's value.</pre>
 */
public final class TolerationOperator extends ExtendableEnum<TolerationOperator> {
  public static final TolerationOperator EQUAL = fromString("Equal");
  public static final TolerationOperator EXISTS = fromString("Exists");


  /**
   * Creates or finds a TolerationOperator from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding TolerationOperator.
   */
  @JsonCreator
  public static TolerationOperator fromString(String name) {
    return fromString(name, TolerationOperator.class);
  }

  /**
   * Gets known TolerationOperator values.
   *
   * @return known TolerationOperator values.
   */
  public static Collection<TolerationOperator> values() {
    return values(TolerationOperator.class);
  }

}
