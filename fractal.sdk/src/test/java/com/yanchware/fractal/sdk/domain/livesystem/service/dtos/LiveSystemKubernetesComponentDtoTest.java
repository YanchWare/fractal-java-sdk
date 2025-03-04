package com.yanchware.fractal.sdk.domain.livesystem.service.dtos;

import com.yanchware.fractal.sdk.domain.livesystem.caas.*;
import com.yanchware.fractal.sdk.domain.livesystem.paas.KubernetesCluster;
import com.yanchware.fractal.sdk.domain.values.ComponentType;

import java.util.Map;

import static com.yanchware.fractal.sdk.utils.TestUtils.assertGenericComponent;
import static org.assertj.core.api.Assertions.assertThat;

public class LiveSystemKubernetesComponentDtoTest {
    protected void assertCaaSComponents(KubernetesCluster k8sCluster, Map<String, LiveSystemComponentDto> lsDtoMap) {
        ProviderType provider = k8sCluster.getProvider();
        k8sCluster.getMonitoringInstances().forEach(component -> asserMonitoringComponent(lsDtoMap, (CaaSPrometheus) component, provider));
        k8sCluster.getApiGatewayInstances().forEach(component -> assertApiGatewayComponent(lsDtoMap, (CaaSAmbassador) component, provider));
        k8sCluster.getK8sWorkloadInstances().forEach(component -> assertK8sWorkloadComponent(lsDtoMap, component, provider));
        k8sCluster.getServiceMeshSecurityInstances().forEach(component -> assertSericeMeshSecurityComponent(lsDtoMap, component, provider));
        k8sCluster.getLoggingInstances().forEach(component -> assertLoggingComponent(lsDtoMap, (CaaSElasticLogging) component, provider));
    }

    private void assertLoggingComponent(Map<String, LiveSystemComponentDto> lsDtoMap, CaaSElasticLogging component, ProviderType provider) {
        var dto = lsDtoMap.get(component.getId().getValue());
        assertGenericComponent(dto, component, ComponentType.CAAS_ELASTIC_LOGGING.getId());
        assertThat(dto.getProvider()).isEqualTo(provider);
        assertThat(dto.getParameters())
                .extracting(
                        "containerPlatform",
                        "cpu",
                        "elasticInstances",
                        "elasticVersion",
                        "isApmRequired",
                        "isKibanaRequired",
                        "memory",
                        "namespace",
                        "storage",
                        "storageClassName")
                .containsExactly(
                        component.getContainerPlatform(),
                        component.getCpu(),
                        component.getElasticInstances(),
                        component.getElasticVersion(),
                        component.isApmRequired(),
                        component.isKibanaRequired(),
                        component.getMemory(),
                        component.getNamespace(),
                        component.getStorage(),
                        component.getStorageClassName());
    }

    private void assertSericeMeshSecurityComponent(Map<String, LiveSystemComponentDto> lsDtoMap, CaaSServiceMeshSecurityImpl component, ProviderType provider) {
        var dto = lsDtoMap.get(component.getId().getValue());
        assertGenericComponent(dto, component, ComponentType.CAAS_OCELOT.getId());
        assertThat(dto.getProvider()).isEqualTo(provider);
        assertThat(dto.getParameters())
                .extracting(
                        "containerPlatform",
                        "cookieMaxAgeSec",
                        "corsOrigins",
                        "host",
                        "hostOwnerEmail",
                        "namespace",
                        "pathPrefix")
                .containsExactly(
                        component.getContainerPlatform(),
                        component.getCookieMaxAgeSec(),
                        component.getCorsOrigins(),
                        component.getHost(),
                        component.getHostOwnerEmail(),
                        component.getNamespace(),
                        component.getPathPrefix());
    }

    private void assertK8sWorkloadComponent(Map<String, LiveSystemComponentDto> lsDtoMap, CaaSKubernetesWorkload component, ProviderType provider) {
        var dto = lsDtoMap.get(component.getId().getValue());
        assertGenericComponent(dto, component, ComponentType.CAAS_K8S_WORKLOAD.getId());
        assertThat(dto.getProvider()).isEqualTo(provider);
        assertThat(dto.getParameters())
                .extracting(
                        "branchName",
                        "containerPlatform",
                        "namespace",
                        "privateSSHKeyPassphraseEnvironmentSecretShortName",
                        "privateSSHKeyEnvironmentSecretShortName",
                        "repoId",
                        "roles")
                .containsExactly(
                        component.getBranchName(),
                        component.getContainerPlatform(),
                        component.getNamespace(),
                        component.getPrivateSSHKeyPassphraseEnvironmentSecretShortName(),
                        component.getPrivateSSHKeyEnvironmentSecretShortName(),
                        component.getRepoId(),
                        component.getRoles());
    }

    private void assertApiGatewayComponent(Map<String, LiveSystemComponentDto> lsDtoMap, CaaSAmbassador component, ProviderType provider) {
        var dto = lsDtoMap.get(component.getId().getValue());
        assertGenericComponent(dto, component, ComponentType.CAAS_AMBASSADOR.getId());
        assertThat(dto.getProvider()).isEqualTo(provider);
        assertThat(dto.getParameters())
                .extracting(
                        "acmeProviderAuthority",
                        "containerPlatform",
                        "host",
                        "hostOwnerEmail",
                        "namespace",
                        "tlsSecretName")
                .containsExactly(
                        component.getAcmeProviderAuthority(),
                        component.getContainerPlatform(),
                        component.getHost(),
                        component.getHostOwnerEmail(),
                        component.getNamespace(),
                        component.getTlsSecretName());
    }

    private void asserMonitoringComponent(Map<String, LiveSystemComponentDto> lsDtoMap, CaaSPrometheus component, ProviderType provider) {
        var dto = lsDtoMap.get(component.getId().getValue());
        assertGenericComponent(dto, component, ComponentType.CAAS_PROMETHEUS.getId());
        assertThat(dto.getProvider()).isEqualTo(provider);
        assertThat(dto.getParameters())
                .extracting(
                        "apiGatewayUrl",
                        "containerPlatform",
                        "namespace")
                .containsExactly(
                        component.getApiGatewayUrl(),
                        component.getContainerPlatform(),
                        component.getNamespace());
    }
}