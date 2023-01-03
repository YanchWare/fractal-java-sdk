package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.CustomWorkloadRole;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface CustomWorkload {
  String getPrivateSSHKeyPassphraseSecretId();
  void setPrivateSSHKeyPassphraseSecretId(String privateSSHKeyPassphraseSecretId);

  String getPrivateSSHKeySecretId();
  void setPrivateSSHKeySecretId(String privateSSHKeySecretId);

  String getSshRepositoryURI();
  void setSshRepositoryURI(String SshRepositoryURI);

  String getRepoId();
  void setRepoId(String repoId);

  String getBranchName();
  void setBranchName(String branchName);

  String getWorkloadSecretIdKey();
  void setWorkloadSecretIdKey(String workloadSecretIdKey);

  String getWorkloadSecretPasswordKey();
  void setWorkloadSecretPasswordKey(String WorkloadSecretPasswordKey);

  List<CustomWorkloadRole> getRoles();
  void setRoles(List<CustomWorkloadRole> roles);

  static Collection<String> validateCustomWorkload(CustomWorkload customWorkload){
    final var SSH_KEY_PASS_SECRET_IS_EMPTY = "[CaaSWorkload Validation] privateSSHKeyPassphraseSecretId is either empty or blank and it is required";
    final var SSH_KEY_SECRET_IS_EMPTY = "[CaaSWorkload Validation] privateSSHKeySecretId is either empty or blank and it is required";
    final var SSH_REPO_URI_IS_EMPTY = "[CaaSWorkload Validation] sshRepositoryURI is either empty or blank and it is required";
    final var REPO_ID_IS_EMPTY = "[CaaSWorkload Validation] repoId is either empty or blank and it is required";
    final var BRANCH_NAME_IS_EMPTY = "[CaaSWorkload Validation] branchName is either empty or blank and it is required";
    final var WORKLOAD_SECRET_ID_KEY_IS_EMPTY = "[CaaSWorkload Validation] Workload Secret Id Key is either empty or blank and it is required";
    final var WORKLOAD_SECRET_PASSWORD_KEY_IS_EMPTY = "[CaaSWorkload Validation] Workload Secret Password Key is either empty or blank and it is required";

    var errors = new ArrayList<String>();

    if (StringUtils.isBlank(customWorkload.getPrivateSSHKeyPassphraseSecretId())) {
      errors.add(SSH_KEY_PASS_SECRET_IS_EMPTY);
    }

    if (StringUtils.isBlank(customWorkload.getPrivateSSHKeySecretId())) {
      errors.add(SSH_KEY_SECRET_IS_EMPTY);
    }

    if (StringUtils.isBlank(customWorkload.getSshRepositoryURI())) {
      errors.add(SSH_REPO_URI_IS_EMPTY);
    }

    if (StringUtils.isBlank(customWorkload.getRepoId())) {
      errors.add(REPO_ID_IS_EMPTY);
    }

    if (StringUtils.isBlank(customWorkload.getBranchName())) {
      errors.add(BRANCH_NAME_IS_EMPTY);
    }

    var workloadSecretIdKey = customWorkload.getWorkloadSecretIdKey();
    if (workloadSecretIdKey != null && StringUtils.isBlank(workloadSecretIdKey)) {
      errors.add(WORKLOAD_SECRET_ID_KEY_IS_EMPTY);
    }

    var workloadSecretPasswordKey = customWorkload.getWorkloadSecretPasswordKey();
    if (workloadSecretPasswordKey != null && StringUtils.isBlank(workloadSecretPasswordKey)) {
      errors.add(WORKLOAD_SECRET_PASSWORD_KEY_IS_EMPTY);
    }
    return errors;
  }
}
