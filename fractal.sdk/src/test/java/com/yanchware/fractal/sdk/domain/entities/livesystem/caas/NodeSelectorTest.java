package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;

class NodeSelectorTest {
  private NodeSelector.NodeSelectorBuilder builder;

  @BeforeEach
  void setUp() {
    builder = NodeSelector.builder();
  }

  @Test
  void singleSelectorIsCorrectlyAdded_when_withSelectorIsCalled() {
    String label = "disktype";
    String value = "ssd";

    NodeSelector nodeSelector = builder.withSelector(label, value).build();

    assertThat(nodeSelector.getSelector())
        .as("Node selector should contain the added label and value")
        .containsExactly(entry(label, value));
  }

  @Test
  void multipleSelectorsAreCorrectlySet_when_withSelectorsIsCalled() {
    Map<String, String> selectors = Map.of("disktype", "ssd", "region", "us-west");

    NodeSelector nodeSelector = builder.withSelectors(selectors).build();

    assertThat(nodeSelector.getSelector())
        .as("Node selector should contain all the provided selectors")
        .containsAllEntriesOf(selectors);
  }

  @Test
  void builderMethodsCanBeChained_toSetUpMultipleSelectors() {
    NodeSelector nodeSelector = builder
        .withSelector("disktype", "ssd")
        .withSelectors(Map.of("region", "us-west"))
        .build();

    assertThat(nodeSelector.getSelector())
        .as("Node selector should contain all selectors from both individual and bulk addition")
        .containsExactlyInAnyOrderEntriesOf(Map.of("disktype", "ssd", "region", "us-west"));
  }
}