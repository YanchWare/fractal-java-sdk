package com.yanchware.fractal.sdk;

import com.yanchware.fractal.sdk.entities.livesystem.caas.GcpNodePool;
import com.yanchware.fractal.sdk.entities.livesystem.caas.GoogleKubernetesEngine;
import com.yanchware.fractal.sdk.entities.livesystem.caas.KubernetesService;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LiveSystemFirstTest {

  @Test
  public void PositiveTest() {
    var gke = GoogleKubernetesEngine.builder()
      .service(KubernetesService.builder().id(ComponentId.from("caas-1"))
        .build())
      .id(ComponentId.from("kube-1"))
      .description("Test GKE cluster")
      .displayName("Kube 1")
      .nodePool(GcpNodePool.builder().name("gcp-node-pool-name").build())
    .build();

    assertThat(gke.validate()).isEmpty();
  }

}