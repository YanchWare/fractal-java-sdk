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
public abstract class CaaSWorkloadImpl extends CaaSWorkload implements LiveSystemComponent {
    private final static String SSH_KEY_PASS_SECRET_IS_EMPTY = "[CaaSWorkload Validation] privateSSHKeyPassphraseSecretId is either empty or blank and it is required";
    private final static String SSH_KEY_SECRET_IS_EMPTY = "[CaaSWorkload Validation] privateSSHKeySecretId is either empty or blank and it is required";
    private final static String SSH_REPO_URI_IS_EMPTY = "[CaaSWorkload Validation] sshRepositoryURI is either empty or blank and it is required";
    private final static String REPO_ID_IS_EMPTY = "[CaaSWorkload Validation] repoId is either empty or blank and it is required";
    private final static String BRANCH_NAME_IS_EMPTY = "[CaaSWorkload Validation] branchName is either empty or blank and it is required";
    private final static String WORKLOAD_SECRET_ID_KEY_IS_EMPTY = "[CaaSWorkload Validation] Workload Secret Id Key is either empty or blank and it is required";
    private final static String WORKLOAD_SECRET_PASSWORD_KEY_IS_EMPTY = "[CaaSWorkload Validation] Workload Secret Password Key is either empty or blank and it is required";

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

    protected CaaSWorkloadImpl() {
        roles = new ArrayList<>();
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (StringUtils.isBlank(privateSSHKeyPassphraseSecretId)) {
            errors.add(SSH_KEY_PASS_SECRET_IS_EMPTY);
        }

        if (StringUtils.isBlank(privateSSHKeySecretId)) {
            errors.add(SSH_KEY_SECRET_IS_EMPTY);
        }

        if (StringUtils.isBlank(sshRepositoryURI)) {
            errors.add(SSH_REPO_URI_IS_EMPTY);
        }

        if (StringUtils.isBlank(repoId)) {
            errors.add(REPO_ID_IS_EMPTY);
        }

        if (StringUtils.isBlank(branchName)) {
            errors.add(BRANCH_NAME_IS_EMPTY);
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
