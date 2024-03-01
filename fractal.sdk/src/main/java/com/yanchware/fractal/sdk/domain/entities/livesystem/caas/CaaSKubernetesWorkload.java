package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSWorkload;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsRecord;
import com.yanchware.fractal.sdk.domain.entities.livesystem.CustomWorkload;
import com.yanchware.fractal.sdk.domain.entities.livesystem.CustomWorkloadBuilder;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.SerializationUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CAAS_K8S_WORKLOAD;

@Getter
@Setter
@ToString(callSuper = true)
public class CaaSKubernetesWorkload extends CaaSWorkload implements LiveSystemComponent, CustomWorkload {
    private String privateSSHKeyPassphraseSecretId;
    private String privateSSHKeySecretId;
    private String sshRepositoryURI;
    private String repoId;
    private String branchName;
    private List<CustomWorkloadRole> roles;
    private String workloadSecretIdKey;
    private String workloadSecretPasswordKey;
    private String serviceAccountName;

    @Override
    public ProviderType getProvider(){
        return ProviderType.CAAS;
    }

    protected CaaSKubernetesWorkload() {
        
        roles = new ArrayList<>();
        this.setRecreateOnFailure(true);
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();
        errors.addAll(CustomWorkload.validateCustomWorkload(this, "Kubernetes Workload"));
        return errors;
    }

    public static KubernetesWorkloadBuilder builder() {
        return new KubernetesWorkloadBuilder();
    }


    public static class KubernetesWorkloadBuilder extends CustomWorkloadBuilder<CaaSKubernetesWorkload, KubernetesWorkloadBuilder> {

        @Override
        protected CaaSKubernetesWorkload createComponent() {
            return new CaaSKubernetesWorkload();
        }

        @Override
        protected KubernetesWorkloadBuilder getBuilder() {
            return this;
        }

        /**
         * Namespace where the workload will be instantiated
         * @param namespace
         */
        public KubernetesWorkloadBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        /**
         * The id of the container platform where the workload will be instantiated
         * @param containerPlatform
         */
        public KubernetesWorkloadBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        public KubernetesWorkloadBuilder withDnsZoneConfig(String dnsZoneName, DnsRecord dnsRecord) {
            if (component.getDnsZoneConfig() == null) {
                component.setDnsZoneConfig(new HashMap<>());
            }

            if (!component.getDnsZoneConfig().containsKey(dnsZoneName)) {
                component.getDnsZoneConfig().put(dnsZoneName, new ArrayList<>());
            }

            component.getDnsZoneConfig().get(dnsZoneName).add(SerializationUtils.convertValueToMap(dnsRecord));

            return builder;
        }

        public KubernetesWorkloadBuilder withDnsZoneConfig(String dnsZoneName, Collection<DnsRecord> dnsRecords) {
            dnsRecords.forEach(dnsRecord -> withDnsZoneConfig(dnsZoneName, dnsRecord));
            return builder;
        }

        public KubernetesWorkloadBuilder withDnsZoneConfig(Map<? extends String, ? extends Collection<DnsRecord>> dnsRecordsMap) {
            if (dnsRecordsMap == null || dnsRecordsMap.isEmpty()) {
                return builder;
            }

            dnsRecordsMap.forEach((key, value) -> {
                for (var dnsRecord : value) {
                    withDnsZoneConfig(key, dnsRecord);
                }
            });

            return builder;
        }

        /**
         * <pre>
         * Sets the ServiceAccount name for the Kubernetes workload. 
         * This name is crucial for configuring workload identity by linking the Kubernetes ServiceAccount 
         * to Azure User Managed Identity.</pre>
         */
        public KubernetesWorkloadBuilder withServiceAccountName(String serviceAccountName) {
            component.setServiceAccountName(serviceAccountName);
            return builder;
        }

        @Override
        public CaaSKubernetesWorkload build() {
            component.setType(CAAS_K8S_WORKLOAD);
            return super.build();
        }
    }
}
