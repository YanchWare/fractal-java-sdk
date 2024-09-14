package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.*;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSContainerPlatform;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSRelationalDatabase;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSRelationalDbms;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.*;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.KubernetesCluster;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.PaaSPostgreSqlDatabase;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.blueprint.service.dtos.BlueprintComponentDto;
import com.yanchware.fractal.sdk.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.yanchware.fractal.sdk.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class BlueprintComponentDtoTest {

  @Test
  public void blueprintComponentValid_when_aksWithPrometheus() {
    var aks = getAksBuilder().withMonitoring(getPrometheusExample()).build();

    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    var prometheus = (CaaSPrometheus) aks.getMonitoringInstances().getFirst();
    var prometheusDto = getBlueprintComponentDto(blueprintComponentDtoList, prometheus.getId().getValue());
    var aksId = aks.getId().getValue();

    assertGenericComponent(prometheusDto, prometheus, CaaSMonitoring.TYPE);
    assertSoftly(softly -> softly
        .assertThat(prometheusDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            prometheus.getApiGatewayUrl(),
            prometheus.getNamespace(),
            aksId
        ));
  }

  @Test
  public void blueprintComponentValid_when_aksWithAmbassador() {
    var aks = getAksBuilder().withAPIGateway(getAmbassadorExample()).build();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    var ambassador = (CaaSAmbassador) aks.getApiGatewayInstances().getFirst();
    var ambassadorDto = getBlueprintComponentDto(blueprintComponentDtoList, ambassador.getId().getValue());
    var aksId = aks.getId().getValue();

    assertGenericComponent(ambassadorDto, ambassador, CaaSAPIGateway.TYPE);
    assertSoftly(softly -> softly
        .assertThat(ambassadorDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            ambassador.getHost(),
            ambassador.getHostOwnerEmail(),
            ambassador.getAcmeProviderAuthority(),
            ambassador.getTlsSecretName(),
            ambassador.getNamespace(),
            aksId
        ));
  }

  @Test
  public void blueprintComponentValid_when_aksWithOcelot() {
    var aks = getAksBuilder()
      .withServiceMeshSecurity(getOcelotExample()).build();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    var ocelot = (CaaSOcelot) aks.getServiceMeshSecurityInstances().getFirst();
    var ocelotDto = getBlueprintComponentDto(blueprintComponentDtoList, ocelot.getId().getValue());
    var aksId = aks.getId().getValue();

    assertGenericComponent(ocelotDto, ocelot, CaaSServiceMeshSecurity.TYPE);
    assertSoftly(softly -> softly
        .assertThat(ocelotDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            ocelot.getNamespace(),
            ocelot.getHost(),
            ocelot.getHostOwnerEmail(),
            ocelot.getCorsOrigins(),
            ocelot.getCookieMaxAgeSec(),
            ocelot.getPathPrefix(),
            aksId
        ));
  }

  @Test
  public void blueprintComponentValid_when_aksWithElasticLogging() {
    var aks = getAksBuilder().withLogging(getElasticLoggingExample()).build();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    var elasticLogging = (CaaSElasticLogging) aks.getLoggingInstances().getFirst();
    var elasticLoggingDto = getBlueprintComponentDto(blueprintComponentDtoList, elasticLogging.getId().getValue());
    var aksId = aks.getId().getValue();

    assertGenericComponent(elasticLoggingDto, elasticLogging, CaaSLogging.TYPE);
    assertSoftly(softly -> softly
        .assertThat(elasticLoggingDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            elasticLogging.getNamespace(),
            elasticLogging.isApmRequired(),
            elasticLogging.isKibanaRequired(),
            elasticLogging.getElasticVersion(),
            elasticLogging.getElasticInstances(),
            elasticLogging.getStorage(),
            elasticLogging.getStorageClassName(),
            elasticLogging.getMemory(),
            elasticLogging.getCpu(),
            aksId
        ));
  }

  @Test
  public void blueprintComponentValid_when_aksWithElasticDataStore() {
    var aks = getAksBuilder().withDocumentDB(getElasticDataStoreExample()).build();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    var elasticDataStore = (CaaSElasticDataStore) aks.getDocumentDBInstances().getFirst();
    var elasticDataStoreDto = getBlueprintComponentDto(blueprintComponentDtoList, elasticDataStore.getId().getValue());
    var aksId = aks.getId().getValue();

    assertGenericComponent(elasticDataStoreDto, elasticDataStore, CaaSSearch.TYPE);
    assertSoftly(softly -> softly
        .assertThat(elasticDataStoreDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            elasticDataStore.getNamespace(),
            elasticDataStore.isKibanaRequired(),
            elasticDataStore.getElasticVersion(),
            elasticDataStore.getElasticInstances(),
            elasticDataStore.getStorage(),
            elasticDataStore.getStorageClassName(),
            elasticDataStore.getMemory(),
            elasticDataStore.getCpu(),
            aksId
        ));
  }

  @Test
  public void blueprintComponentValid_when_aksWithK8sWorkload() {
    var aks = getAksBuilder().withK8sWorkload(getK8sWorkloadExample()).build();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    var k8sWorkload = aks.getK8sWorkloadInstances().getFirst();
    var k8sWorkloadDto = getBlueprintComponentDto(blueprintComponentDtoList, k8sWorkload.getId().getValue());
    var aksId = aks.getId().getValue();

    assertGenericComponent(k8sWorkloadDto, k8sWorkload, CaaSWorkload.TYPE);
    assertSoftly(softly -> softly
        .assertThat(k8sWorkloadDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            k8sWorkload.getNamespace(),
            k8sWorkload.getPrivateSSHKeyPassphraseSecretId(),
            k8sWorkload.getPrivateSSHKeySecretId(),
            k8sWorkload.getSshRepositoryURI(),
            k8sWorkload.getRoles(),
            k8sWorkload.getRepoId(),
            k8sWorkload.getBranchName(),
            aksId
        ));
  }

  @Test
  public void blueprintComponentValid_when_AksLiveSystemComponentConverted() {
    var aks = TestUtils.getAksExample();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(aks));

    assertComponentSize(aks, blueprintComponentDtoList);
    var aksDto = getBlueprintComponentDto(blueprintComponentDtoList, aks.getId().getValue());
    assertGenericComponent(aksDto, aks, PaaSContainerPlatform.TYPE);
    assertSoftly(softly -> softly
        .assertThat(aksDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            aks.getAzureRegion(),
            aks.getAzureActiveDirectoryProfile(),
            aks.getNodePools(),
            aks.getServiceIpRange(),
            aks.getPodIpRange(),
            aks.getVnetAddressSpaceIpRange(),
            aks.getVnetSubnetAddressIpRange(),
            aks.getPriorityClasses(),
            aks.getPodManagedIdentity(),
            aks.getExternalWorkspaceResourceId(),
            aks.getOutboundIps(),
            aks.getAddonProfiles(),
            aks.getRoles(),
            aks.getKubernetesVersion(),
            aks.getTags(),
            aks.getWindowsAdminUsername(),
            aks.getWorkloadIdentityEnabled(),
            aks.getManagedClusterSkuTier()
        ));
  }

  @Test
  public void blueprintComponentValid_when_GkeLiveSystemComponentConverted() {
    var gke = TestUtils.getGkeExample();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(gke));

    assertComponentSize(gke, blueprintComponentDtoList);
    var gkeDto = getBlueprintComponentDto(blueprintComponentDtoList, gke.getId().getValue());
    assertGenericComponent(gkeDto, gke, PaaSContainerPlatform.TYPE);
    assertSoftly(softly -> softly
        .assertThat(gkeDto.getParameters().values())
        .as("Component Parameters")
        .containsExactlyInAnyOrder(
            gke.getNetworkName(),
            gke.getPodsRangeName(),
            gke.getRegion().getId(),
            gke.getServicesRangeName(),
            gke.getSubnetworkName(),
            gke.getNodePools(),
            gke.getServiceIpRange(),
            gke.getPodIpRange(),
            gke.getSubnetworkIpRange(),
            gke.getPriorityClasses()
            //gke.getPodManagedIdentity()
        ));
  }

  @Test
  public void blueprintComponentValid_when_azurePostgresLiveSystemComponentConverted() {
    var apg = TestUtils.getAzurePostgresExample();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(apg));

    assertThat(blueprintComponentDtoList).hasSize(3);

    //assert postgres server
    var azurePgBlueprintCompDto = blueprintComponentDtoList.getFirst();
    assertGenericComponent(azurePgBlueprintCompDto, apg, PaaSRelationalDbms.TYPE);
    assertSoftly(softly -> {
      softly.assertThat(azurePgBlueprintCompDto.getParameters().values()).as("Component Parameters")
          .containsExactlyInAnyOrder(
              apg.getAzureRegion(),
              apg.getRootUser(),
              apg.getSkuName().getId(),
              apg.getStorageAutoGrow().getId(),
              apg.getStorageMB(),
              apg.getBackupRetentionDays(),
              apg.getName()
          );
      softly.assertThat(azurePgBlueprintCompDto.getDependencies()).as("Component Dependencies").isEmpty();
      softly.assertThat(azurePgBlueprintCompDto.getLinks()).as("Component Links").isEmpty();
    });

    //assert postgres db#1
    var azurePgDbBlueprintCompDto = blueprintComponentDtoList.get(1);
    Optional<PaaSPostgreSqlDatabase> db1Optional = apg.getDatabases().stream().filter(x -> x.getId().getValue().equals(azurePgDbBlueprintCompDto.getId())).findFirst();
    assertThat(db1Optional).isPresent();
    var db1 = db1Optional.get();
    assertGenericComponent(azurePgDbBlueprintCompDto, db1, PaaSRelationalDatabase.TYPE);
    assertSoftly(softly -> {
      softly.assertThat(azurePgDbBlueprintCompDto.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
        db1.getName(),
        db1.getSchema(),
        ((AzureResourceEntity)db1).getAzureRegion());
      softly.assertThat(azurePgDbBlueprintCompDto.getDependencies()).as("Component Dependencies").containsExactly(apg.getId().getValue());
      softly.assertThat(azurePgDbBlueprintCompDto.getLinks()).as("Component Links").isEmpty();
    });

    //assert postgres db#2
    var azurePgDbBlueprintCompDto2 = blueprintComponentDtoList.get(2);
    Optional<PaaSPostgreSqlDatabase> db2Optional = apg.getDatabases().stream().filter(x -> x.getId().getValue().equals(azurePgDbBlueprintCompDto2.getId())).findFirst();
    assertThat(db2Optional).isPresent();
    var db2 = db2Optional.get();
    assertGenericComponent(azurePgDbBlueprintCompDto2, db2, PaaSRelationalDatabase.TYPE);
    assertSoftly(softly -> {
      softly.assertThat(azurePgDbBlueprintCompDto2.getParameters().values()).as("Component Parameters").containsExactlyInAnyOrder(
        db2.getSchema(),
        db2.getName(),
        ((AzureResourceEntity)db1).getAzureRegion());
      softly.assertThat(azurePgDbBlueprintCompDto2.getDependencies()).as("Component Dependencies").containsExactly(apg.getId().getValue());
      softly.assertThat(azurePgDbBlueprintCompDto2.getLinks()).as("Component Links").containsAll(apg.getLinks());
    });
  }

  @Test
  public void blueprintComponentValid_when_gcpPostgresLiveSystemComponentConverted() {
    var gcpPostgres = TestUtils.getGcpPostgresExample();
    var blueprintComponentDtoList = BlueprintComponentDto.fromLiveSystemComponents(List.of(gcpPostgres));

    assertThat(blueprintComponentDtoList).hasSize(2);

    var gcpPgBlueprintCompDto = blueprintComponentDtoList.getFirst();
    assertGenericComponent(gcpPgBlueprintCompDto, gcpPostgres, PaaSRelationalDbms.TYPE);
    assertSoftly(softly -> {
      softly.assertThat(gcpPgBlueprintCompDto.getParameters().values()).as("Component Parameters")
          .containsExactlyInAnyOrder(
              gcpPostgres.getRegion().getId(),
              gcpPostgres.getNetwork(),
              gcpPostgres.getPeeringNetworkAddress(),
              gcpPostgres.getPeeringNetworkAddressDescription(),
              gcpPostgres.getPeeringNetworkName(),
              gcpPostgres.getPeeringNetworkPrefix()
          );
      softly.assertThat(gcpPgBlueprintCompDto.getDependencies()).as("Component Dependencies").isEmpty();
      softly.assertThat(gcpPgBlueprintCompDto.getLinks()).as("Component Links").isEmpty();
    });
  }

  private BlueprintComponentDto getBlueprintComponentDto(List<BlueprintComponentDto> blueprintComponentDtoList, String compId) {
    return blueprintComponentDtoList.stream().filter(dto -> dto.getId().equals(compId)).findFirst().orElse(null);
  }

  private void assertComponentSize(KubernetesCluster kubernetesCluster, List<BlueprintComponentDto> blueprintComponentDtos) {
    int componentSize = 1; //containerPlatform itself
    componentSize += kubernetesCluster.getK8sWorkloadInstances().size();
    componentSize += kubernetesCluster.getMonitoringInstances().size();
    componentSize += kubernetesCluster.getApiGatewayInstances().size();
    componentSize += kubernetesCluster.getServiceMeshSecurityInstances().size();
    componentSize += kubernetesCluster.getLoggingInstances().size();
    componentSize += kubernetesCluster.getDocumentDBInstances().size();

    assertThat(blueprintComponentDtos).hasSize(componentSize);
  }

}