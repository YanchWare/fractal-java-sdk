package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;


/**
 * <pre>
 * Enumerates the possible effects of a Kubernetes taint on pods that do not tolerate the taint.
 * Taints are a mechanism in Kubernetes used to evict pods from nodes. If a pod does not tolerate a taint,
 * the effect of the taint determines what happens to the pod on that node.
 *
 * 
 * The effects include:
 * - NoSchedule: Pods that do not tolerate this effect are not scheduled on the node.
 * - PreferNoSchedule: The scheduler tries to avoid scheduling pods that do not tolerate this effect on the node,
 *                     but it is not a strict requirement.
 * - NoExecute: Pods that are already running on the node and do not tolerate this effect are evicted. 
 *               New pods that do not tolerate this effect are not scheduled on the node.
 * </pre>
 */
public final class TaintEffect extends ExtendableEnum<TaintEffect> {
  public static final TaintEffect NO_SCHEDULE = fromString("NoSchedule");
  public static final TaintEffect PREFER_NO_SCHEDULE = fromString("PreferNoSchedule");
  public static final TaintEffect NO_EXECUTE = fromString("NoExecute");


  /**
   * Creates or finds a TaintEffect from its string representation.
   *
   * @param name a name to look for.
   * @return the corresponding TaintEffect.
   */
  @JsonCreator
  public static TaintEffect fromString(String name) {
    return fromString(name, TaintEffect.class);
  }

  /**
   * Gets known TaintEffect values.
   *
   * @return known TaintEffect values.
   */
  public static Collection<TaintEffect> values() {
    return values(TaintEffect.class);
  }

}
