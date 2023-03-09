package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleTypeTest {

  @Test
  public void shouldReturnProperValue() {
    assertAll(
        () -> assertEquals(RoleType.fromString("AppRoleAssignment"), RoleType.APP_ROLE_ASSIGNMENT),
        () -> assertEquals(RoleType.fromString("BuiltInRole"), RoleType.BUILT_IN_ROLE),
        () -> assertEquals(RoleType.fromString("CustomRole"), RoleType.CUSTOM_ROLE),
        () -> assertEquals(RoleType.fromString("OcelotRole"), RoleType.OCELOT_ROLE)
    );
  }

}