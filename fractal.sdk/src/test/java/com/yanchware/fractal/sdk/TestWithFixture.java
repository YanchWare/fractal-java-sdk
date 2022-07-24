package com.yanchware.fractal.sdk;

import com.flextrade.jfixture.JFixture;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.Set;

public class TestWithFixture {

  protected JFixture fixture;

  @BeforeEach
  public void init() {
    fixture = new JFixture();
  }

  protected <T> T a(Class<T> classToInstantiate) {
    return fixture.create(classToInstantiate);
  }

  protected <T> Set<T> aSetOf(Class<T> elementsClass) {
    return Set.copyOf(fixture.collections().createCollection(elementsClass));
  }

  protected <T> List<T> aListOf(Class<T> elementsClass) {
    return List.copyOf(fixture.collections().createCollection(elementsClass));
  }

}
