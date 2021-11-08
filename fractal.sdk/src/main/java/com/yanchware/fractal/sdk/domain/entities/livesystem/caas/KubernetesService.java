package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSService;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.K8S_SERVICE;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class KubernetesService extends CaaSService implements LiveSystemComponent {
    private final static String NAMESPACE_IS_NULL_OR_EMPTY = "[KubernetesService Validation] Namespace is either empty or blank and it is required";
    private final static String CONTAINER_PLATFORM_IS_EMPTY = "[KubernetesService Validation] ContainerPlatform is either empty or blank and it is required";
    private final static String SSH_KEY_PASS_SECRET_IS_EMPTY = "[KubernetesService Validation] privateSSHKeyPassphraseSecretId is either empty or blank and it is required";
    private final static String SSH_KEY_SECRET_IS_EMPTY = "[KubernetesService Validation] privateSSHKeySecretId is either empty or blank and it is required";
    private final static String SSH_KEY_IS_EMPTY = "[KubernetesService Validation] publicSSHKey is either empty or blank and it is required";
    private final static String SSH_REPO_URI_IS_EMPTY = "[KubernetesService Validation] sshRepositoryURI is either empty or blank and it is required";
    private final static String REPO_ID_IS_EMPTY = "[KubernetesService Validation] repoId is either empty or blank and it is required";

    private String namespace;
    private String containerPlatform;
    private String privateSSHKeyPassphraseSecretId;
    private String privateSSHKeySecretId;
    private String publicSSHKey;
    private String sshRepositoryURI;
    private String repoId;
    private List<String> roles;
    private ProviderType provider;

    protected KubernetesService() {
        roles = new ArrayList<>();
    }

    public static KubernetesServiceBuilder builder() {
        return new KubernetesServiceBuilder();
    }

    @Override
    public ProviderType getProvider() {
        return provider;
    }

    public static class KubernetesServiceBuilder extends Builder<KubernetesService, KubernetesServiceBuilder> {

        @Override
        protected KubernetesService createComponent() {
            return new KubernetesService();
        }

        @Override
        protected KubernetesServiceBuilder getBuilder() {
            return this;
        }

        public KubernetesServiceBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public KubernetesServiceBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        public KubernetesServiceBuilder withPrivateSSHKeyPassphraseSecretId(String privateSSHKeyPassphraseSecretId) {
            component.setPrivateSSHKeyPassphraseSecretId(privateSSHKeyPassphraseSecretId);
            return builder;
        }

        public KubernetesServiceBuilder withPrivateSSHKeySecretId(String privateSSHKeySecretId) {
            component.setPrivateSSHKeySecretId(privateSSHKeySecretId);
            return builder;
        }

        public KubernetesServiceBuilder withPublicSSHKey(String publicSSHKey) {
            component.setPublicSSHKey(publicSSHKey);
            return builder;
        }

        public KubernetesServiceBuilder withSshRepositoryURI(String sshRepositoryURI) {
            component.setSshRepositoryURI(sshRepositoryURI);
            return builder;
        }

        public KubernetesServiceBuilder withRepoId(String repoId) {
            component.setRepoId(repoId);
            return builder;
        }

        public KubernetesServiceBuilder withRole(String role) {
            return withRoles(List.of(role));
        }

        public KubernetesServiceBuilder withRoles(List<String> roles) {
            if (isBlank(roles)) {
                return builder;
            }
            if (component.getRoles() == null) {
                component.setRoles(new ArrayList<>());
            }
            component.getRoles().addAll(roles);
            return builder;
        }

        @Override
        public KubernetesService build() {
            component.setType(K8S_SERVICE);
            return super.build();
        }
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (StringUtils.isBlank(namespace)) {
            errors.add(NAMESPACE_IS_NULL_OR_EMPTY);
        }

        if (containerPlatform != null && StringUtils.isBlank(containerPlatform)) {
            errors.add(CONTAINER_PLATFORM_IS_EMPTY);
        }

        if (StringUtils.isBlank(privateSSHKeyPassphraseSecretId)) {
            errors.add(SSH_KEY_PASS_SECRET_IS_EMPTY);
        }

        if (StringUtils.isBlank(privateSSHKeySecretId)) {
            errors.add(SSH_KEY_SECRET_IS_EMPTY);
        }

        if (StringUtils.isBlank(publicSSHKey)) {
            errors.add(SSH_KEY_IS_EMPTY);
        }

        if (StringUtils.isBlank(sshRepositoryURI)) {
            errors.add(SSH_REPO_URI_IS_EMPTY);
        }

        if (StringUtils.isBlank(repoId)) {
            errors.add(REPO_ID_IS_EMPTY);
        }
        return errors;
    }

}
