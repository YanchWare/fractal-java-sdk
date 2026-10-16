package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.ComponentLink;
import com.yanchware.fractal.sdk.domain.values.ComponentId;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CaaSKubernetesWorkloadSearchLinkTest {

  @Test
  public void linkCarriesAccessOnly_when_linkedWithoutPrefix() {
    var workload = workloadBuilder()
        .linkToSearch(openSearch(), SearchAccess.READ)
        .build();

    assertThat(workload.getLinks())
        .singleElement()
        .satisfies(link -> {
          assertThat(link.getComponentId()).isEqualTo("opensearch");
          assertThat(link.getSettings()).isEqualTo(Map.of("access", "read"));
        });
    assertThat(workload.getDependencies()).doesNotContain(ComponentId.from("opensearch"));
  }

  @Test
  public void linkCarriesAccessAndPrefix_when_linkedWithPrefix() {
    var workload = workloadBuilder()
        .linkToSearch(openSearch(), SearchAccess.READ_WRITE, "orders-")
        .build();

    assertThat(workload.getLinks())
        .extracting(ComponentLink::getSettings)
        .containsExactly(Map.of("access", "read-write", "indexPrefix", "orders-"));
  }

  @Test
  public void linkTargetsReference_when_linkedById() {
    var workload = workloadBuilder()
        .linkToSearch(ComponentId.from("shared-search"), SearchAccess.WRITE, null)
        .build();

    assertThat(workload.getLinks())
        .singleElement()
        .satisfies(link -> {
          assertThat(link.getComponentId()).isEqualTo("shared-search");
          assertThat(link.getSettings()).isEqualTo(Map.of("access", "write"));
        });
  }

  @Test
  public void exceptionThrown_when_accessIsMissing() {
    assertThatThrownBy(() -> workloadBuilder().linkToSearch(openSearch(), null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Access has not been defined and it is required");
  }

  @Test
  public void exceptionThrown_when_indexPrefixIsIllegal() {
    assertThatThrownBy(() -> workloadBuilder().linkToSearch(openSearch(), SearchAccess.READ, "orders/*"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Index prefix 'orders/*' is illegal");
  }

  @Test
  public void exceptionThrown_when_searchIsMissing() {
    assertThatThrownBy(() -> workloadBuilder().linkToSearch((CaaSOpenSearch) null, SearchAccess.READ))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Search component has not been defined and it is required");
  }

  private static CaaSOpenSearch openSearch() {
    return CaaSOpenSearch.builder().withId("opensearch").build();
  }

  private static CaaSKubernetesWorkload.KubernetesWorkloadBuilder workloadBuilder() {
    return CaaSKubernetesWorkload.builder()
        .withId("orders")
        .withNamespace("orders")
        .withPrivateSSHKeyPassphraseEnvironmentSecretShortName("passphrase")
        .withPrivateSSHKeyEnvironmentSecretShortName("ssh-key")
        .withSSHRepositoryURI("ssh-uri")
        .withRepoId("orders-repo")
        .withBranchName("main");
  }
}
