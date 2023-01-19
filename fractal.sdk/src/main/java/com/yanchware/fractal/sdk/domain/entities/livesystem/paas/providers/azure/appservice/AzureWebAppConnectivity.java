package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureAppServiceClientCertMode;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureTlsVersion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureWebAppConnectivity {
  String apiManagementConfigId;
  String apiDefinitionUrl;
  ArrayList<String> corsAllowedOrigins;
  boolean corsAllowCredentials;
  boolean http2Enabled;
  boolean httpLoggingEnabled;
  AzureTlsVersion minimumTlsVersion;
  boolean requestTracingEnabled;
  String tracingOptions;
  boolean websocketEnabled;
  boolean clientAffinityEnabled;
  boolean clientCertEnabled;
  String clientCertExclusionPaths;
  AzureAppServiceClientCertMode clientCertMode;
  boolean httpsOnly;
}
