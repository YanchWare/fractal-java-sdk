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
@Setter(AccessLevel.PROTECTED)
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

        public KubernetesWorkloadBuilder withPublicSSHKey(String publicSSHKey) {
            component.setPublicSSHKey(publicSSHKey);
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

        public KubernetesWorkloadBuilder withRole(String role) {
            return withRoles(List.of(role));
        }

        public KubernetesWorkloadBuilder withRoles(List<String> roles) {
            if (isBlank(roles)) {
                return builder;
            }
            if (component.getRoles() == null) {
                component.setRoles(new ArrayList<>());
            }
            component.getRoles().addAll(roles);
            return builder;
        }

        public KubernetesWorkloadBuilder withScope(String workloadScope) {
            return withScopes(List.of(workloadScope));
        }

        public KubernetesWorkloadBuilder withScopes(List<String> workloadScopes) {
            if (isBlank(workloadScopes)) {
                return builder;
            }
            if (component.getWorkloadScopes() == null) {
                component.setWorkloadScopes(new ArrayList<>());
            }
            component.getWorkloadScopes().addAll(workloadScopes);
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

        @Override
        public KubernetesWorkload build() {
            component.setType(K8S_WORKLOAD);
            return super.build();
        }
    }

}
