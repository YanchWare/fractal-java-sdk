package com.yanchware.fractal.sdk.domain.livesystem.caas;

import lombok.Getter;

/**
 * Scope of the access a consumer is granted on a search component (e.g. {@link CaaSOpenSearch})
 * through a link. It is carried as the <code>access</code> link setting.
 */
@Getter
public enum SearchAccess {
  READ("read"),
  WRITE("write"),
  READ_WRITE("read-write");

  private final String value;

  SearchAccess(String value) {
    this.value = value;
  }
}
