package com.yanchware.fractal.sdk.domain.entities.livesystem.paas;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(setterPrefix = "with")
public class PodManagedIdentity implements Validatable {
  private final static String NAME_IS_BLANK = "[PodManagedIdentity Validation] Name has not been defined and it is required";
  private final static String NAMESPACE_IS_BLANK = "[PodManagedIdentity Validation] Namespace has not been defined and it is required";
  private final static String ENABLE_IS_BLANK = "[PodManagedIdentity Validation] Enable has not been defined and it is required";
  private final static String EXCEPTION_POD_LABELS_IS_BLANK = "[PodManagedIdentity Validation] Exception pod labels has not been defined and it is required";
  private final static String ALLOW_NETWORK_PLUGIN_KUBE_NET_IS_BLANK = "[PodManagedIdentity Validation] AllowNetworkPluginKubeNet has not been defined and it is required";

  private String name;
  private String namespace;
  private Map<String, String> exceptionPodLabels;
  private Boolean enable;
  private Boolean allowNetworkPluginKubeNet;

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (isBlank(this.name)) {
      errors.add(NAME_IS_BLANK);
    }

    if (isBlank(this.namespace)) {
      errors.add(NAMESPACE_IS_BLANK);
    }

    if (this.enable == null) {
      errors.add(ENABLE_IS_BLANK);
    }

    if (this.exceptionPodLabels == null || this.exceptionPodLabels.isEmpty()) {
      errors.add(EXCEPTION_POD_LABELS_IS_BLANK);
    }

    if (this.allowNetworkPluginKubeNet == null) {
      errors.add(ALLOW_NETWORK_PLUGIN_KUBE_NET_IS_BLANK);
    }

    return errors;
  }
}
