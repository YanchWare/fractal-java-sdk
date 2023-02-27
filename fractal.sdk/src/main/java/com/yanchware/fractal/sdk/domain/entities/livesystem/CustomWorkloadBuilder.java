package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.CustomWorkloadRole;

import java.util.ArrayList;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;

public abstract class CustomWorkloadBuilder<T extends Component & CustomWorkload, B extends CustomWorkloadBuilder<T, B>> extends Component.Builder<T, B> {

  /**
   * The private passphrase secret ID used to read from the value from secret manager/key vault
   * @param privateSSHKeyPassphraseSecretId
   */
  public B withPrivateSSHKeyPassphraseSecretId(String privateSSHKeyPassphraseSecretId) {
    component.setPrivateSSHKeyPassphraseSecretId(privateSSHKeyPassphraseSecretId);
    return builder;
  }

  /**
   * The passphrase SSH key secret ID used to read from the value from secret manager/key vault
   * @param privateSSHKeySecretId
   * @return
   */
  public B withPrivateSSHKeySecretId(String privateSSHKeySecretId) {
    component.setPrivateSSHKeySecretId(privateSSHKeySecretId);
    return builder;
  }

  /**
   * The SSH uri of the repository
   * @param sshRepositoryURI
   * @return
   */
  public B withSSHRepositoryURI(String sshRepositoryURI) {
    component.setSshRepositoryURI(sshRepositoryURI);
    return builder;
  }

  /**
   * The id of the repository
   * @param repoId
   */
  public B withRepoId(String repoId) {
    component.setRepoId(repoId);
    return builder;
  }

  /**
   * Custom role for the workload to be applied
   * @param role
   */
  public B withRole(CustomWorkloadRole role) {
    return withRoles(List.of(role));
  }

  /**
   * Custom roles for the workload to be applied
   * @param roles
   */
  public B withRoles(List<CustomWorkloadRole> roles) {
    if (isBlank(roles)) {
      return builder;
    }
    if (component.getRoles() == null) {
      component.setRoles(new ArrayList<>());
    }
    component.getRoles().addAll(roles);
    return builder;
  }

  /**
   * Workload secret id key. Used for internal communication.
   * @param workloadSecretIdKey
   */
  public B withSecretIdKey(String workloadSecretIdKey) {
    component.setWorkloadSecretIdKey(workloadSecretIdKey);
    return builder;
  }

  /**
   *
   * Workload secret password key. Used for internal communication.
   * @param workloadSecretPasswordKey
   */
  public B withSecretPasswordKey(String workloadSecretPasswordKey) {
    component.setWorkloadSecretPasswordKey(workloadSecretPasswordKey);
    return builder;
  }

  /**
   * Branch name from which to deploy
   * @param branchName
   */
  public B withBranchName(String branchName) {
    component.setBranchName(branchName);
    return builder;
  }
}
