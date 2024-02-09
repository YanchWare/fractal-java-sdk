package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter(AccessLevel.PROTECTED)
public class NodeSelector {
  private Map<String, String> selector = new HashMap<>();

  protected NodeSelector() {
  }


  public static NodeSelectorBuilder builder() {
    return new NodeSelectorBuilder();
  }

  public static class NodeSelectorBuilder {
    private final NodeSelector nodeSelector;
    private final NodeSelectorBuilder builder;

    public NodeSelectorBuilder() {
      this.nodeSelector = new NodeSelector();
      this.builder = this;
    }

    /**
     * <pre>
     * Sets multiple node selectors at once. 
     * Each entry in the map represents a label and its desired value.</pre>
     *
     * @param selectors A map of label keys to label values defining the node selection criteria.
     */
    public NodeSelectorBuilder withSelectors(Map<String, String> selectors) {
      nodeSelector.selector.putAll(selectors);
      return builder;
    }

    /**
     * <pre>
     * Adds a single node selector. 
     * This method allows you to specify one label and its desired value at a time.
     * Can be called multiple times to add multiple selectors.</pre>
     *
     * @param label The label key representing a specific characteristic of nodes.
     * @param value The label value that nodes must have to match the selector.
     */
    public NodeSelectorBuilder withSelector(String label, String value) {
      nodeSelector.selector.put(label, value);
      return builder;
    }

    public NodeSelector build() {
      return nodeSelector;
    }
  }
}
