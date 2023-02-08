package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureTlsVersion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.valueobjects.AzureAppServiceClientCertMode;
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
  Boolean corsAllowCredentials;
  Boolean http2Enabled;
  Boolean httpLoggingEnabled;
  AzureTlsVersion minimumTlsVersion;
  Boolean requestTracingEnabled;
  String tracingOptions;
  Boolean websocketEnabled;
  Boolean clientAffinityEnabled;
  Boolean clientCertEnabled;
  String clientCertExclusionPaths;
  AzureAppServiceClientCertMode clientCertMode;
  Boolean httpsOnly;
}
