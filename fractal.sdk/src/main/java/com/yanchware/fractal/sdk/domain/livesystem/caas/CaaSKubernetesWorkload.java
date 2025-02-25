package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.blueprint.caas.CaaSWorkload;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsRecord;
import com.yanchware.fractal.sdk.domain.livesystem.CustomWorkload;
import com.yanchware.fractal.sdk.domain.livesystem.CustomWorkloadBuilder;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.SerializationUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.*;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.CAAS_K8S_WORKLOAD;

@Getter
@Setter
@ToString(callSuper = true)
public class CaaSKubernetesWorkload extends CaaSWorkload implements LiveSystemComponent, CustomWorkload {
    private String privateSSHKeyPassphraseEnvironmentSecretShortName;
    private String privateSSHKeyEnvironmentSecretShortName;
    private String sshRepositoryURI;
    private String repoId;
    private String branchName;
    private List<CustomWorkloadRole> roles;
    private String workloadSecretIdKey;
    private String workloadSecretPasswordKey;
    private String serviceAccountName;
    private Boolean workloadIdentityEnabled;
    private List<String> environmentSecretShortNames;
    private String ciCdProfileShortName;

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
         * <pre>
         * Sets the namespace where the workload will be instantiated.
         * </pre>
         *
         * @param namespace The namespace for the workload.
         * @return The builder instance.
         */
        public KubernetesWorkloadBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        /**
         * <pre>
         * Sets the ID of the container platform where the workload will be instantiated.
         * </pre>
         *
         * @param containerPlatform The ID of the container platform.
         * @return The builder instance.
         */
        public KubernetesWorkloadBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        /**
         * Adds a DNS zone configuration with a single DNS record.
         *
         * @param dnsZoneName The name of the DNS zone.
         * @param dnsRecord   The DNS record to add.
         * @return The builder instance.
         */
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

        /**
         * Adds a DNS zone configuration with a collection of DNS records.
         *
         * @param dnsZoneName The name of the DNS zone.
         * @param dnsRecords  The collection of DNS records to add.
         * @return The builder instance.
         */
        public KubernetesWorkloadBuilder withDnsZoneConfig(String dnsZoneName, Collection<DnsRecord> dnsRecords) {
            dnsRecords.forEach(dnsRecord -> withDnsZoneConfig(dnsZoneName, dnsRecord));
            return builder;
        }

        /**
         * Adds DNS zone configurations from a map of DNS zone names to collections of DNS records.
         *
         * @param dnsRecordsMap The map of DNS zone configurations.
         * @return The builder instance.
         */
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
         *
         * @param serviceAccountName The name of the ServiceAccount.
         * @return The builder instance.
         */
        public KubernetesWorkloadBuilder withServiceAccountName(String serviceAccountName) {
            component.setServiceAccountName(serviceAccountName);
            return builder;
        }

        /**
         * <pre>
         * Enables or disables the Workload Identity for the Kubernetes workload.
         * By default, Fractal Cloud Agent sets this to true.</pre>
         *
         * @param workloadIdentityEnabled A boolean flag to enable or disable Workload Identity.
         * @return The builder instance.
         */
        public KubernetesWorkloadBuilder withWorkloadIdentityEnabled(boolean workloadIdentityEnabled) {
            component.setWorkloadIdentityEnabled(workloadIdentityEnabled);
            return builder;
        }

        @Override
        public CaaSKubernetesWorkload build() {
            component.setType(CAAS_K8S_WORKLOAD);
            return super.build();
        }
    }
}
