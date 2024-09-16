package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureWebAppPushSettings {

  /**
   * JSON string containing a list of dynamic tags that will be evaluated from user claims in the push registration endpoint.
   */
  private String dynamicTagsJson;

  /**
   * A flag indicating whether the Push endpoint is enabled.
   */
  private Boolean isPushEnabled;

  /**
   * JSON string containing a list of tags that are in the allowed list for use by the push registration endpoint.
   */
  private String tagWhitelistJson;

  /**
   * JSON string containing a list of tags that require user authentication to be used in the push registration endpoint.
   */
  private String tagsRequiringAuth;
}
