package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSWorkload;
import com.yanchware.fractal.sdk.domain.entities.livesystem.CustomWorkload;
import com.yanchware.fractal.sdk.domain.entities.livesystem.CustomWorkloadBuilder;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Override
    public ProviderType getProvider(){
        return ProviderType.CAAS;
    }

    protected CaaSKubernetesWorkload() {
        roles = new ArrayList<>();
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();
        errors.addAll(CustomWorkload.validateCustomWorkload(this));
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

        public KubernetesWorkloadBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public KubernetesWorkloadBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        @Override
        public CaaSKubernetesWorkload build() {
            component.setType(CAAS_K8S_WORKLOAD);
            return super.build();
        }
    }

}
