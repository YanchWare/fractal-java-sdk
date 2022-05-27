package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.*;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KUBERNETES;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class KubernetesCluster extends CaaSContainerPlatform implements LiveSystemComponent {
  private List<CaaSK8sWorkloadImpl> k8sWorkloadInstances;
  private List<CaaSMessageBrokerImpl> messageBrokerInstances;
  private List<CaaSMonitoringImpl> monitoringInstances;
  private List<CaaSAPIGatewayImpl> apiGatewayInstances;
  private List<CaaSServiceMeshSecurityImpl> serviceMeshSecurityInstances;
  private List<CaaSTracingImpl> tracingInstances;
  private List<CaaSLoggingImpl> loggingInstances;
  private List<CaaSDocumentDBImpl> documentDBInstances;

  public KubernetesCluster() {
    super();
    k8sWorkloadInstances = new ArrayList<>();
    messageBrokerInstances = new ArrayList<>();
    monitoringInstances = new ArrayList<>();
    apiGatewayInstances = new ArrayList<>();
    serviceMeshSecurityInstances = new ArrayList<>();
    tracingInstances = new ArrayList<>();
    loggingInstances = new ArrayList<>();
    documentDBInstances = new ArrayList<>();
  }

  public static abstract class Builder<T extends KubernetesCluster, B extends Builder<T, B>> extends Component.Builder<T, B> {

    public B withK8sWorkload(KubernetesWorkload workload) {
      return withK8sWorkloadInstances(List.of(workload));
    }

    public B withK8sWorkloadInstances(Collection<? extends KubernetesWorkload> workloads) {
      if (isBlank(workloads)) {
        return builder;
      }

      if (component.getK8sWorkloadInstances() == null) {
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

    public B withMessageBroker(CaaSMessageBrokerImpl messageBroker) {
      return withMessageBroker(List.of(messageBroker));
    }

    public B withMessageBroker(Collection<? extends CaaSMessageBrokerImpl> messageBrokers) {
      if (isBlank(messageBrokers)) {
        return builder;
      }

      if (component.getMessageBrokerInstances() == null) {
        component.setMessageBrokerInstances(new ArrayList<>());
      }

      messageBrokers.forEach(messageBroker -> messageBroker.initialiseParameters(component.getProvider(), component));
      component.getMessageBrokerInstances().addAll(messageBrokers);
      return builder;
    }

    public B withMonitoring(CaaSMonitoringImpl monitoring) {
      return withMonitoring(List.of(monitoring));
    }

    public B withMonitoring(Collection<? extends CaaSMonitoringImpl> monitoringInstances) {
      if (isBlank(monitoringInstances)) {
        return builder;
      }

      if (component.getMonitoringInstances() == null) {
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

      if (component.getApiGatewayInstances() == null) {
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
      if (component.getServiceMeshSecurityInstances() == null) {
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

    public B withTracing(CaaSTracingImpl tracing) {
      return withTracing(List.of(tracing));
    }

    public B withTracing(Collection<? extends CaaSTracingImpl> tracingInstances) {
      if (isBlank(tracingInstances)) {
        return builder;
      }
      if (component.getTracingInstances() == null) {
        component.setTracingInstances(new ArrayList<>());
      }
      tracingInstances.forEach(tracing -> {
        tracing.setProvider(component.getProvider());
        tracing.setContainerPlatform(component.getId().getValue());
        tracing.getDependencies().add(component.getId());
      });
      component.getTracingInstances().addAll(tracingInstances);
      return builder;
    }

    public B withLogging(CaaSLoggingImpl logging) {
      return withLogging(List.of(logging));
    }

    public B withLogging(Collection<? extends CaaSLoggingImpl> loggingInstances) {
      if (isBlank(loggingInstances)) {
        return builder;
      }
      if (component.getLoggingInstances() == null) {
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

    public B withDocumentDB(CaaSDocumentDBImpl documentDB) {
      return withDocumentDB(List.of(documentDB));
    }

    public B withDocumentDB(Collection<? extends CaaSDocumentDBImpl> documentDBInstances) {
      if (isBlank(documentDBInstances)) {
        return builder;
      }
      if (component.getDocumentDBInstances() == null) {
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

    @Override
    public T build() {
      component.setType(KUBERNETES);
      return super.build();
    }

  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    k8sWorkloadInstances.stream()
        .map(CaaSWorkload::validate)
        .forEach(errors::addAll);
    messageBrokerInstances.stream()
        .map(CaaSMessageBroker::validate)
        .forEach(errors::addAll);
    monitoringInstances.stream()
        .map(CaaSMonitoring::validate)
        .forEach(errors::addAll);
    apiGatewayInstances.stream()
        .map(CaaSAPIGateway::validate)
        .forEach(errors::addAll);
    serviceMeshSecurityInstances.stream()
        .map(CaaSServiceMeshSecurity::validate)
        .forEach(errors::addAll);
    tracingInstances.stream()
        .map(CaaSTracing::validate)
        .forEach(errors::addAll);
    loggingInstances.stream()
        .map(CaaSLogging::validate)
        .forEach(errors::addAll);
    documentDBInstances.stream()
        .map(CaaSDocumentDB::validate)
        .forEach(errors::addAll);
    return errors;
  }

}
