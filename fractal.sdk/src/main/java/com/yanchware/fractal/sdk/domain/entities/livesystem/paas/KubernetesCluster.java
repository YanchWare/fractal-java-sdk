package com.yanchware.fractal.sdk.domain.entities.livesystem.paas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.*;
import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSContainerPlatform;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.*;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isPresentAndValidIpRange;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KUBERNETES;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class KubernetesCluster extends PaaSContainerPlatform implements LiveSystemComponent {
  private final static String SERVICE_IP_RANGE_NOT_VALID = "[KubernetesCluster Validation] Service IP Range does not contain a valid ip with mask";
  private final static String POD_RANGE_NOT_VALID = "[KubernetesCluster Validation] Pod IP Range does not contain a valid ip with mask";
  private String serviceIpRange;
  private String podIpRange;

  private Collection<PriorityClass> priorityClasses;
  private List<CaaSWorkloadImpl> k8sWorkloadInstances;
  private List<CaaSMonitoringImpl> monitoringInstances;
  private List<CaaSAPIGatewayImpl> apiGatewayInstances;
  private List<CaaSServiceMeshSecurityImpl> serviceMeshSecurityInstances;
  private List<CaaSLoggingImpl> loggingInstances;
  private List<CaaSDocumentDBImpl> documentDBInstances;
  private PodManagedIdentity podManagedIdentity;
  private String windowsAdminUsername;
  //private List<CaaSMessageBrokerImpl> messageBrokerInstances;
  //private List<CaaSTracingImpl> tracingInstances;

  public KubernetesCluster() {
    super();
    priorityClasses = new ArrayList<>();
    k8sWorkloadInstances = new ArrayList<>();
    monitoringInstances = new ArrayList<>();
    apiGatewayInstances = new ArrayList<>();
    serviceMeshSecurityInstances = new ArrayList<>();
    loggingInstances = new ArrayList<>();
    documentDBInstances = new ArrayList<>();
    //messageBrokerInstances = new ArrayList<>();
    //tracingInstances = new ArrayList<>();
  }

  public static abstract class Builder<T extends KubernetesCluster, B extends Builder<T, B>> extends Component.Builder<T, B> {

    public B withServiceIpRange(String serviceIpRange) {
      component.setServiceIpRange(serviceIpRange);
      return builder;
    }

    public B withPodIpRange(String podIpRange) {
      component.setPodIpRange(podIpRange);
      return builder;
    }

    public B withPriorityClass(PriorityClass priorityClass) {
      return withPriorityClasses(List.of(priorityClass));
    }

    public B withPriorityClasses(Collection<PriorityClass> priorityClasses) {
      if (isBlank(priorityClasses)) {
        return builder;
      }

      if (isBlank(component.getPriorityClasses())) {
        component.setPriorityClasses(new ArrayList<>());
      }

      component.getPriorityClasses().addAll(priorityClasses);
      return builder;
    }

    public B withK8sWorkload(CaaSKubernetesWorkload workload) {
      return withK8sWorkloadInstances(List.of(workload));
    }

    public B withK8sWorkloadInstances(Collection<? extends CaaSKubernetesWorkload> workloads) {
      if (isBlank(workloads)) {
        return builder;
      }

      if (isBlank(component.getK8sWorkloadInstances())) {
        component.setK8sWorkloadInstances(new ArrayList<>());
      }

      workloads.forEach(workload -> {
        workload.setProvider(component.getProvider());
        workload.setContainerPlatform(component.getId().getValue());
        workload.getDependencies().add(component.getId());
      });
      component.getK8sWorkloadInstances().addAll(workloads);
      return builder;
    }

    /*public B withMessageBroker(CaaSMessageBrokerImpl messageBroker) {
      return withMessageBroker(List.of(messageBroker));
    }

    public B withMessageBroker(Collection<? extends CaaSMessageBrokerImpl> messageBrokers) {
      if (isBlank(messageBrokers)) {
        return builder;
      }

      if (isBlank(component.getMessageBrokerInstances())) {
        component.setMessageBrokerInstances(new ArrayList<>());
      }

      messageBrokers.forEach(messageBroker -> messageBroker.initialiseParameters(component.getProvider(), component));
      component.getMessageBrokerInstances().addAll(messageBrokers);
      return builder;
    }*/

    public B withMonitoring(CaaSMonitoringImpl monitoring) {
      return withMonitoring(List.of(monitoring));
    }

    public B withMonitoring(Collection<? extends CaaSMonitoringImpl> monitoringInstances) {
      if (isBlank(monitoringInstances)) {
        return builder;
      }

      if (isBlank(component.getMonitoringInstances())) {
        component.setMonitoringInstances(new ArrayList<>());
      }

      monitoringInstances.forEach(monitoring -> {
        monitoring.setProvider(component.getProvider());
        monitoring.setContainerPlatform(component.getId().getValue());
        monitoring.getDependencies().add(component.getId());
      });
      component.getMonitoringInstances().addAll(monitoringInstances);
      return builder;
    }

    public B withAPIGateway(CaaSAPIGatewayImpl apiGateway) {
      return withAPIGateway(List.of(apiGateway));
    }

    public B withAPIGateway(List<CaaSAPIGatewayImpl> apiGatewayInstances) {
      if (isBlank(apiGatewayInstances)) {
        return builder;
      }

      if (isBlank(component.getApiGatewayInstances())) {
        component.setApiGatewayInstances(new ArrayList<>());
      }

      apiGatewayInstances.forEach(apiGateway -> {
        apiGateway.setProvider(component.getProvider());
        apiGateway.setContainerPlatform(component.getId().getValue());
        apiGateway.getDependencies().add(component.getId());
      });
      component.getApiGatewayInstances().addAll(apiGatewayInstances);
      return builder;
    }

    public B withServiceMeshSecurity(CaaSServiceMeshSecurityImpl serviceMeshSecurity) {
      return withServiceMeshSecurity(List.of(serviceMeshSecurity));
    }

    public B withServiceMeshSecurity(Collection<? extends CaaSServiceMeshSecurityImpl> serviceMeshSecurityInstances) {
      if (isBlank(serviceMeshSecurityInstances)) {
        return builder;
      }
      if (isBlank(component.getServiceMeshSecurityInstances())) {
        component.setServiceMeshSecurityInstances(new ArrayList<>());
      }
      serviceMeshSecurityInstances.forEach(serviceMeshSecurity -> {
        serviceMeshSecurity.setProvider(component.getProvider());
        serviceMeshSecurity.setContainerPlatform(component.getId().getValue());
        serviceMeshSecurity.getDependencies().add(component.getId());
      });
      component.getServiceMeshSecurityInstances().addAll(serviceMeshSecurityInstances);
      return builder;
    }

    /*public B withTracing(CaaSTracingImpl tracing) {
      return withTracing(List.of(tracing));
    }

    public B withTracing(Collection<? extends CaaSTracingImpl> tracingInstances) {
      if (isBlank(tracingInstances)) {
        return builder;
      }
      if (isBlank(component.getTracingInstances())) {
        component.setTracingInstances(new ArrayList<>());
      }
      tracingInstances.forEach(tracing -> {
        tracing.setProvider(component.getProvider());
        tracing.setContainerPlatform(component.getId().getValue());
        tracing.getDependencies().add(component.getId());
      });
      component.getTracingInstances().addAll(tracingInstances);
      return builder;
    }*/

    public B withLogging(CaaSLoggingImpl logging) {
      return withLogging(List.of(logging));
    }

    public B withLogging(Collection<? extends CaaSLoggingImpl> loggingInstances) {
      if (isBlank(loggingInstances)) {
        return builder;
      }
      if (isBlank(component.getLoggingInstances())) {
        component.setLoggingInstances(new ArrayList<>());
      }
      loggingInstances.forEach(logging -> {
        logging.setProvider(component.getProvider());
        logging.setContainerPlatform(component.getId().getValue());
        logging.getDependencies().add(component.getId());
      });
      component.getLoggingInstances().addAll(loggingInstances);
      return builder;
    }

    //TODO: try to throw validation/comment out KubernetesCluster priority classes/taints

    public B withDocumentDB(CaaSDocumentDBImpl documentDB) {
      return withDocumentDB(List.of(documentDB));
    }

    public B withDocumentDB(Collection<? extends CaaSDocumentDBImpl> documentDBInstances) {
      if (isBlank(documentDBInstances)) {
        return builder;
      }
      if (isBlank(component.getDocumentDBInstances())) {
        component.setDocumentDBInstances(new ArrayList<>());
      }
      documentDBInstances.forEach(documentDB -> {
        documentDB.setProvider(component.getProvider());
        documentDB.setContainerPlatform(component.getId().getValue());
        documentDB.getDependencies().add(component.getId());
      });
      component.getDocumentDBInstances().addAll(documentDBInstances);
      return builder;
    }

    public B withPodManagedIdentity(PodManagedIdentity podManagedIdentity) {
      if (podManagedIdentity == null) {
        return builder;
      }

      component.setPodManagedIdentity(podManagedIdentity);
      return builder;
    }

    public B withWindowsAdminUsername(String windowsAdminUsername) {
      component.setWindowsAdminUsername(windowsAdminUsername);
      return builder;
    }

    @Override
    public T build() {
      component.setType(KUBERNETES);
      return super.build();
    }

  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    isPresentAndValidIpRange(serviceIpRange, errors, SERVICE_IP_RANGE_NOT_VALID);
    isPresentAndValidIpRange(podIpRange, errors, POD_RANGE_NOT_VALID);

    priorityClasses.stream()
        .map(PriorityClass::validate)
        .forEach(errors::addAll);
    k8sWorkloadInstances.stream()
        .map(CaaSWorkload::validate)
        .forEach(errors::addAll);
    /*messageBrokerInstances.stream()
        .map(CaaSMessageBroker::validate)
        .forEach(errors::addAll);*/
    monitoringInstances.stream()
        .map(CaaSMonitoring::validate)
        .forEach(errors::addAll);
    apiGatewayInstances.stream()
        .map(CaaSAPIGateway::validate)
        .forEach(errors::addAll);
    serviceMeshSecurityInstances.stream()
        .map(CaaSServiceMeshSecurity::validate)
        .forEach(errors::addAll);
    /*tracingInstances.stream()
        .map(CaaSTracing::validate)
        .forEach(errors::addAll);*/
    loggingInstances.stream()
        .map(CaaSLogging::validate)
        .forEach(errors::addAll);
    documentDBInstances.stream()
        .map(CaaSDocumentDB::validate)
        .forEach(errors::addAll);

    if(podManagedIdentity != null) {
      errors.addAll(podManagedIdentity.validate());
    }

    //FRA-684 - Add error until we implement in GCP
    if(this.getProvider().equals(ProviderType.GCP)) {
      if(podManagedIdentity != null) errors.add("Pod Managed Identity is not fully supported yet for GCP");
      if(!priorityClasses.isEmpty()) errors.add("Priority classes are not fully supported yet for GCP");
    }
    return errors;
  }

}
