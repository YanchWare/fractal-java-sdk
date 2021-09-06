package com.yanchware.fractal.sdk;

import com.yanchware.fractal.sdk.domain.entities.livesystem.Environment;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.gcp.GcpNodePool;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.gcp.GoogleKubernetesEngine;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.KubernetesService;
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

        var env = Environment.builder()
                .id("")
                .displayName("STARK PROD")
                .parentId("123456789")
                .parentType("folder")
                .build();

        //LiveSystem.builder()
        //.id(LiveSystemId.from("ls-id"))
        //.component(gke)
        //.build(); //check at build time if you have correct info, if not, generate it if possible

        //Automaton.instantiate(list<livesystems>)
        //automaton could have a public constructor for users to inject as a singleton

        assertThat(env.validate()).isEmpty();

        assertThat(gke.validate()).isEmpty();
    }

}