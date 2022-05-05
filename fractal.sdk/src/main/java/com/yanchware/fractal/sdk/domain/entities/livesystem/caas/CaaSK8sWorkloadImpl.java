package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSWorkload;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class CaaSK8sWorkloadImpl extends CaaSWorkload implements LiveSystemComponent {
    private final static String NAMESPACE_IS_NULL_OR_EMPTY = "[CaaSWorkload Validation] Namespace is either empty or blank and it is required";
    private final static String CONTAINER_PLATFORM_IS_EMPTY = "[CaaSWorkload Validation] ContainerPlatform is either empty or blank and it is required";
    private final static String SSH_KEY_PASS_SECRET_IS_EMPTY = "[CaaSWorkload Validation] privateSSHKeyPassphraseSecretId is either empty or blank and it is required";
    private final static String SSH_KEY_SECRET_IS_EMPTY = "[CaaSWorkload Validation] privateSSHKeySecretId is either empty or blank and it is required";
    private final static String SSH_KEY_IS_EMPTY = "[CaaSWorkload Validation] publicSSHKey is either empty or blank and it is required";
    private final static String SSH_REPO_URI_IS_EMPTY = "[CaaSWorkload Validation] sshRepositoryURI is either empty or blank and it is required";
    private final static String REPO_ID_IS_EMPTY = "[CaaSWorkload Validation] repoId is either empty or blank and it is required";
    private final static String WORKLOAD_SECRET_ID_KEY_IS_EMPTY = "[CaaSWorkload Validation] Workload Secret Id Key is either empty or blank and it is required";
    private final static String WORKLOAD_SECRET_PASSWORD_KEY_IS_EMPTY = "[CaaSWorkload Validation] Workload Secret Password Key is either empty or blank and it is required";

    private String namespace;
    private String containerPlatform;
    private String privateSSHKeyPassphraseSecretId;
    private String privateSSHKeySecretId;
    private String publicSSHKey;
    private String sshRepositoryURI;
    private String repoId;
    private List<String> roles;
    private List<String> workloadScopes;
    private String workloadSecretIdKey;
    private String workloadSecretPasswordKey;
    private ProviderType provider;

    protected CaaSK8sWorkloadImpl() {
        roles = new ArrayList<>();
    }

    @Override
    public ProviderType getProvider() {
        return provider;
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

        if (workloadSecretIdKey != null && StringUtils.isBlank(workloadSecretIdKey)) {
            errors.add(WORKLOAD_SECRET_ID_KEY_IS_EMPTY);
        }

        if (workloadSecretPasswordKey != null && StringUtils.isBlank(workloadSecretPasswordKey)) {
            errors.add(WORKLOAD_SECRET_PASSWORD_KEY_IS_EMPTY);
        }
        return errors;
    }
}
