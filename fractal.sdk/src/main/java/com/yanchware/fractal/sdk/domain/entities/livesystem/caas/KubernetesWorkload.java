package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.K8S_WORKLOAD;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class KubernetesWorkload extends CaaSK8sWorkloadImpl implements LiveSystemComponent {

    public static KubernetesWorkloadBuilder builder() {
        return new KubernetesWorkloadBuilder();
    }


    public static class KubernetesWorkloadBuilder extends Builder<KubernetesWorkload, KubernetesWorkloadBuilder> {

        @Override
        protected KubernetesWorkload createComponent() {
            return new KubernetesWorkload();
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

        public KubernetesWorkloadBuilder withPrivateSSHKeyPassphraseSecretId(String privateSSHKeyPassphraseSecretId) {
            component.setPrivateSSHKeyPassphraseSecretId(privateSSHKeyPassphraseSecretId);
            return builder;
        }

        public KubernetesWorkloadBuilder withPrivateSSHKeySecretId(String privateSSHKeySecretId) {
            component.setPrivateSSHKeySecretId(privateSSHKeySecretId);
            return builder;
        }

        public KubernetesWorkloadBuilder withSSHRepositoryURI(String sshRepositoryURI) {
            component.setSshRepositoryURI(sshRepositoryURI);
            return builder;
        }

        public KubernetesWorkloadBuilder withRepoId(String repoId) {
            component.setRepoId(repoId);
            return builder;
        }

        public KubernetesWorkloadBuilder withRole(CustomWorkloadRole role) {
            return withRoles(List.of(role));
        }

        public KubernetesWorkloadBuilder withRoles(List<CustomWorkloadRole> roles) {
            if (isBlank(roles)) {
                return builder;
            }
            if (component.getRoles() == null) {
                component.setRoles(new ArrayList<>());
            }
            component.getRoles().addAll(roles);
            return builder;
        }

        public KubernetesWorkloadBuilder withSecretIdKey(String workloadSecretIdKey) {
            component.setWorkloadSecretIdKey(workloadSecretIdKey);
            return builder;
        }

        public KubernetesWorkloadBuilder withSecretPasswordKey(String workloadSecretPasswordKey) {
            component.setWorkloadSecretPasswordKey(workloadSecretPasswordKey);
            return builder;
        }

        public KubernetesWorkloadBuilder withBranchName(String branchName) {
            component.setBranchName(branchName);
            return builder;
        }

        @Override
        public KubernetesWorkload build() {
            component.setType(K8S_WORKLOAD);
            return super.build();
        }
    }

}
