package com.yanchware.fractal.sdk.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static org.assertj.core.api.Assertions.assertThat;

class CollectionUtilsTest {

  @Test
  public void isBlank_when_collectionIsNull() {
    assertThat(isBlank(null)).isTrue();
  }

  @Test
  public void isBlank_when_collectionIsEmpty() {
    assertThat(isBlank(new ArrayList<>())).isTrue();
  }
}