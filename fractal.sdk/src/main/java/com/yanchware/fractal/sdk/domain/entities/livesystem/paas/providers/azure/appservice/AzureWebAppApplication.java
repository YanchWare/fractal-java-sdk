package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureWebAppApplication {

  Map<String, String> appSettings;
  Boolean alwaysOn;
  String appCommandLine;
  String autoSwapSlotName;
  Map<String, String> connectionStrings;
  ArrayList<String> defaultDocuments;
  String documentRoot;
  Integer numberOfWorkers;
  Boolean use32BitWorkerProcess;
  Boolean remoteDebuggingEnabled;
  String remoteDebuggingVersion;
  String websiteTimezone;

}
