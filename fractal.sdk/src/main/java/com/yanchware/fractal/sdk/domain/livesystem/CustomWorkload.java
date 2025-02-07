package com.yanchware.fractal.sdk.domain.livesystem;

import com.yanchware.fractal.sdk.domain.livesystem.caas.CustomWorkloadRole;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

  Map<String, List<Object>> getDnsZoneConfig();
  void setDnsZoneConfig(Map<String, List<Object>> getDnsZoneConfig);

  List<String> getSecrets();
  void setSecrets(List<String> secrets);

  String getCiCdProfileShortName();
  void setCiCdProfileShortName(String ciCdProfileShortName);

  static Collection<String> validateCustomWorkload(CustomWorkload customWorkload, String workloadType){
    final var SSH_REPO_URI_IS_EMPTY_TEMPLATE = "[%s Validation] sshRepositoryURI is either empty or blank and it is required";
    final var REPO_ID_IS_EMPTY_TEMPLATE = "[%s Validation] repoId is either empty or blank and it is required";
    final var BRANCH_NAME_IS_EMPTY_TEMPLATE = "[%s Validation] branchName is either empty or blank and it is required";
    final var WORKLOAD_SECRET_ID_KEY_IS_EMPTY_TEMPLATE = "[%s Validation] Workload Secret Id Key is either empty or blank and it is required";
    final var WORKLOAD_SECRET_PASSWORD_KEY_IS_EMPTY_TEMPLATE = "[%s Validation] Workload Secret Password Key is either empty or blank and it is required";

    var errors = new ArrayList<String>();

    if (StringUtils.isBlank(customWorkload.getSshRepositoryURI())) {
      errors.add(String.format(SSH_REPO_URI_IS_EMPTY_TEMPLATE, workloadType));
    }

    if (StringUtils.isBlank(customWorkload.getRepoId())) {
      errors.add(String.format(REPO_ID_IS_EMPTY_TEMPLATE, workloadType));
    }

    if (StringUtils.isBlank(customWorkload.getBranchName())) {
      errors.add(String.format(BRANCH_NAME_IS_EMPTY_TEMPLATE, workloadType));
    }

    var workloadSecretIdKey = customWorkload.getWorkloadSecretIdKey();
    if (workloadSecretIdKey != null && StringUtils.isBlank(workloadSecretIdKey)) {
      errors.add(String.format(WORKLOAD_SECRET_ID_KEY_IS_EMPTY_TEMPLATE, workloadType));
    }

    var workloadSecretPasswordKey = customWorkload.getWorkloadSecretPasswordKey();
    if (workloadSecretPasswordKey != null && StringUtils.isBlank(workloadSecretPasswordKey)) {
      errors.add(String.format(WORKLOAD_SECRET_PASSWORD_KEY_IS_EMPTY_TEMPLATE, workloadType));
    }

    return errors;
  }
}
