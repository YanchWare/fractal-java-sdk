package com.yanchware.fractal.sdk;

import com.yanchware.fractal.sdk.entities.livesystem.caas.GoogleKubernetesEngine;

public class LiveSystemFirstTest {

  public void PositiveTest() {
    var gke = GoogleKubernetesEngine.builder()
      .service()
      .id("")
      .build();
  }

}