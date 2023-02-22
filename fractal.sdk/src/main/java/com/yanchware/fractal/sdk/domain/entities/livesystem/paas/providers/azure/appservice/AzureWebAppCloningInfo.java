package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureWebAppCloningInfo {
  /**
   * Application setting overrides for cloned app. 
   * If specified, these settings override the settings cloned from source app. 
   * Otherwise, application settings from source app are retained.
   */
  Map<String, String> appSettingsOverrides;

  /**
   * true to clone custom hostnames from source app; otherwise, false.
   */
  Boolean cloneCustomHostNames;

  /**
   * true to clone source control from source app; otherwise, false.
   */
  Boolean cloneSourceControl;

  /**
   * true to configure load balancing for source and destination app.
   */
  Boolean configureLoadBalancing;

  /**
   * Correlation ID of cloning operation. 
   * This ID ties multiple cloning operations together to use the same snapshot.
   */
  String correlationId;

  /**
   * App Service Environment.
   */
  String hostingEnvironment;

  /**
   * true to overwrite destination app; otherwise, false.
   */
  Boolean overwrite;

  /**
   * ARM resource ID of the source app. 
   * App resource ID is of the form /subscriptions/{subId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Web/sites/{siteName} for production slots 
   * and /subscriptions/{subId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Web/sites/{siteName}/slots/{slotName} for other slots.
   */
  String sourceWebAppId;

  /**
   * Location of source app ex: West US or North Europe.
   */
  String sourceWebAppLocation;

  /**
   * ARM resource ID of the Traffic Manager profile to use, if it exists. 
   * Traffic Manager resource ID is of the form /subscriptions/{subId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/trafficManagerProfiles/{profileName}.
   */
  String trafficManagerProfileId;

  /**
   * Name of Traffic Manager profile to create. 
   * This is only needed if Traffic Manager profile does not already exist.
   */
  String trafficManagerProfileName;
  
}
