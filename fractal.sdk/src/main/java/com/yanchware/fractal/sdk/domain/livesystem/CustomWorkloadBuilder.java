package com.yanchware.fractal.sdk.domain.livesystem;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.livesystem.caas.CustomWorkloadRole;

import java.util.ArrayList;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;

/**
 * <p>
 * Builder class to represent a Custom Workload in Fractal Cloud.
 * </p>
 * <br>
 * <p>
 * For more details about creating a custom workload using Fractal Cloud check out
 * our <a href="https://fractal.cloud/docs/docs-ht-create-kubernetes-workload">documentation page</a>
 * </p>
 */
public abstract class CustomWorkloadBuilder<T extends Component & CustomWorkload, B extends CustomWorkloadBuilder<T, B>> extends Component.Builder<T, B> {

  /**
   * The id of the vault secret containing the passphrase protecting the SSH private key used to access the code repository
   *
   * @param privateSSHKeyPassphraseSecretId
   */
  public B withPrivateSSHKeyPassphraseSecretId(String privateSSHKeyPassphraseSecretId) {
    component.setPrivateSSHKeyPassphraseSecretId(privateSSHKeyPassphraseSecretId);
    return builder;
  }

  /**
   * The id of the vault secret containing the SSH private key used to access the code repository
   *
   * @param privateSSHKeySecretId
   */
  public B withPrivateSSHKeySecretId(String privateSSHKeySecretId) {
    component.setPrivateSSHKeySecretId(privateSSHKeySecretId);
    return builder;
  }

  /**
   * The SSH uri of the repository
   *
   * @param sshRepositoryURI
   */
  public B withSSHRepositoryURI(String sshRepositoryURI) {
    component.setSshRepositoryURI(sshRepositoryURI);
    return builder;
  }

  /**
   * The ID of the repository as it is in the version control solution of your choice. The repoId needs to match the
   * repository id that is being sent by the webhook
   *
   * @param repoId
   */
  public B withRepoId(String repoId) {
    component.setRepoId(repoId);
    return builder;
  }

  /**
   * This role will be applied to the Custom Workload. It can vary from a specific Cloud Vendor BuiltIn role to an
   * AppRoleAssignment or other custom role.
   * <p>
   * See {@link com.yanchware.fractal.sdk.domain.livesystem.caas.RoleType RoleType} for all role types supported
   *
   * @param role
   */
  public B withRole(CustomWorkloadRole role) {
    return withRoles(List.of(role));
  }

  /**
   * This list of roles will be applied to the Custom Workload. It can vary from a specific Cloud Vendor BuiltIn role to an
   * AppRoleAssignment or other custom role.
   * <p>
   * See {@link com.yanchware.fractal.sdk.domain.livesystem.caas.RoleType RoleType} for all role types supported
   *
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
   * The id of the vault secret containing the Ocelot client id for this workload
   * This is used for internal communication when <a href="https://fractal.cloud/docs/docs-ocelot">Ocelot</a> is also used in the environment
   * If it is not provided, Fractal Cloud will generate it
   *
   * @param workloadSecretIdKey
   */
  public B withSecretIdKey(String workloadSecretIdKey) {
    component.setWorkloadSecretIdKey(workloadSecretIdKey);
    return builder;
  }

  /**
   * The id of the vault secret containing the Ocelot client secret for this workload
   * This is used for internal communication when <a href="https://fractal.cloud/docs/docs-ocelot">Ocelot</a> is also used in the environment
   * If it is not provided, Fractal Cloud will generate it
   *
   * @param workloadSecretPasswordKey
   */
  public B withSecretPasswordKey(String workloadSecretPasswordKey) {
    component.setWorkloadSecretPasswordKey(workloadSecretPasswordKey);
    return builder;
  }

  /**
   * The branch name to indicate from where to clone the repository that will be deployed
   *
   * @param branchName
   */
  public B withBranchName(String branchName) {
    component.setBranchName(branchName);
    return builder;
  }
}
