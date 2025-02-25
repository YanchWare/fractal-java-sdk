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
   * <pre>
   * Sets the short name of the environment secret containing the passphrase protecting the SSH private key 
   * used to access the code repository.
   *
   * This secret should be defined at the Environment level.
   * </pre>
   *
   * @param privateSSHKeyPassphraseEnvironmentSecretShortName The short name of the environment secret.
   * @return The builder instance.
   */
  public B withPrivateSSHKeyPassphraseEnvironmentSecretShortName(String privateSSHKeyPassphraseEnvironmentSecretShortName) {
    component.setPrivateSSHKeyPassphraseEnvironmentSecretShortName(privateSSHKeyPassphraseEnvironmentSecretShortName);
    return builder;
  }

  /**
   * <pre>
   * Sets the short name of the environment secret containing the SSH private key used to access the code repository.
   *
   * This secret should be defined at the Environment level.
   * </pre>
   *
   * @param privateSSHKeyEnvironmentSecretShortName The short name of the environment secret.
   * @return The builder instance.
   */
  public B withPrivateSSHKeyEnvironmentSecretShortName(String privateSSHKeyEnvironmentSecretShortName) {
    component.setPrivateSSHKeyEnvironmentSecretShortName(privateSSHKeyEnvironmentSecretShortName);
    return builder;
  }

  /**
   * The SSH uri of the repository
   *
   * @param sshRepositoryURI the SSH URI of the repository.
   * @return The builder instance.
   */
  public B withSSHRepositoryURI(String sshRepositoryURI) {
    component.setSshRepositoryURI(sshRepositoryURI);
    return builder;
  }

  /**
   * <pre>
   * The ID of the repository as it is in the version control solution of your choice. 
   * The repoId needs to match the repository ID that is being sent by the webhook.
   * </pre>
   *
   * @param repoId The ID of the repository.
   * @return The builder instance.
   */
  public B withRepoId(String repoId) {
    component.setRepoId(repoId);
    return builder;
  }

  /**
   * <pre>
   * Adds a role to the Custom Workload. 
   * The role can vary from a specific Cloud Vendor Built-in role to an AppRoleAssignment or other custom role.
   * </pre>
   * <p>
   * See {@link com.yanchware.fractal.sdk.domain.livesystem.caas.RoleType RoleType} for all role types supported
   *
   * @param role The role to add.
   * @return The builder instance.
   */
  public B withRole(CustomWorkloadRole role) {
    return withRoles(List.of(role));
  }

  /**
   * <pre>
   * Adds a list of roles to the Custom Workload. 
   * The roles can vary from specific Cloud Vendor Built-in roles to AppRoleAssignments or other custom roles.
   * </pre>
   * <p>
   * See {@link com.yanchware.fractal.sdk.domain.livesystem.caas.RoleType RoleType} for all role types supported
   *
   * @param roles The list of roles to add.
   * @return The builder instance.
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
   * <pre>
   * Sets the ID of the vault secret containing the Ocelot client ID for this workload.
   * This is used for internal communication when <a href="https://fractal.cloud/docs/docs-ocelot">Ocelot</a> is also used in the environment.
   * If it is not provided, Fractal Cloud will generate it.
   * </pre>
   *
   * @param workloadSecretIdKey The ID of the vault secret.
   * @return The builder instance.
   */
  public B withSecretIdKey(String workloadSecretIdKey) {
    component.setWorkloadSecretIdKey(workloadSecretIdKey);
    return builder;
  }

  /**
   * <pre>
   * Sets the ID of the vault secret containing the Ocelot client secret for this workload.
   * This is used for internal communication when <a href="https://fractal.cloud/docs/docs-ocelot">Ocelot</a> is also used in the environment.
   * If it is not provided, Fractal Cloud will generate it.
   * </pre>
   *
   * @param workloadSecretPasswordKey The ID of the vault secret.
   * @return The builder instance.
   */
  public B withSecretPasswordKey(String workloadSecretPasswordKey) {
    component.setWorkloadSecretPasswordKey(workloadSecretPasswordKey);
    return builder;
  }

  /**
   * The branch name to indicate from where to clone the repository that will be deployed.
   *
   * @param branchName The branch name.
   * @return The builder instance.
   */
  public B withBranchName(String branchName) {
    component.setBranchName(branchName);
    return builder;
  }

  /**
   * <pre>
   * Adds a single secret short name to the component. 
   * This secret should be defined in the environment associated with this component.</pre>
   *
   * @param environmentSecretShortNames The short name of the secret to add.
   * @return The builder instance.
   */
  public B withEnvironmentSecretShortName(String environmentSecretShortNames) {
    return withEnvironmentSecretShortNames(List.of(environmentSecretShortNames));
  }

  /**
   * <pre>
   * Adds a list of secret names to the component. 
   * These secrets should be defined in the environment associated with this component.</pre>
   *
   * @param environmentSecretShortNames The list of secret names to add.
   * @return The builder instance.
   */
  public B withEnvironmentSecretShortNames(List<String> environmentSecretShortNames) {
    if (component.getEnvironmentSecretShortNames() == null) {
      component.setEnvironmentSecretShortNames(new ArrayList<>());
    }
    component.getEnvironmentSecretShortNames().addAll(environmentSecretShortNames);
    return builder;
  }

  /**
   * Adds a CI/CD profile short name to the component. 
   * This is to specify which CI/CD profile should be used by the component.
   * The profile should be defined at the Environment level.
   * This method only adds a reference to the profile by its short name.
   *
   * @param profileShortName The short name of the CI/CD profile to add.
   * @return The builder instance.
   */
  public B withCiCdProfileShortName(String profileShortName) {
    component.setCiCdProfileShortName(profileShortName);
    return builder;
  }
}
