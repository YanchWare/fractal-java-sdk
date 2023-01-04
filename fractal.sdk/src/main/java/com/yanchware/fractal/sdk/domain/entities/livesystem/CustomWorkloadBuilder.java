package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.CustomWorkloadRole;

import java.util.ArrayList;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;

public abstract class CustomWorkloadBuilder<T extends Component & CustomWorkload, B extends CustomWorkloadBuilder<T, B>> extends Component.Builder<T, B> {
  public B withPrivateSSHKeyPassphraseSecretId(String privateSSHKeyPassphraseSecretId) {
    component.setPrivateSSHKeyPassphraseSecretId(privateSSHKeyPassphraseSecretId);
    return builder;
  }

  public B withPrivateSSHKeySecretId(String privateSSHKeySecretId) {
    component.setPrivateSSHKeySecretId(privateSSHKeySecretId);
    return builder;
  }

  public B withSSHRepositoryURI(String sshRepositoryURI) {
    component.setSshRepositoryURI(sshRepositoryURI);
    return builder;
  }

  public B withRepoId(String repoId) {
    component.setRepoId(repoId);
    return builder;
  }

  public B withRole(CustomWorkloadRole role) {
    return withRoles(List.of(role));
  }

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

  public B withSecretIdKey(String workloadSecretIdKey) {
    component.setWorkloadSecretIdKey(workloadSecretIdKey);
    return builder;
  }

  public B withSecretPasswordKey(String workloadSecretPasswordKey) {
    component.setWorkloadSecretPasswordKey(workloadSecretPasswordKey);
    return builder;
  }

  public B withBranchName(String branchName) {
    component.setBranchName(branchName);
    return builder;
  }
}
