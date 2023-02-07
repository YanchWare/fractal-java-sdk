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
  boolean alwaysOn;
  String appCommandLine;
  String autoSwapSlotName;
  Map<String, String> connectionStrings;
  ArrayList<String> defaultDocuments;
  String documentRoot;
  int numberOfWorkers;
  boolean use32BitWorkerProcess;
  boolean remoteDebuggingEnabled;
  String remoteDebuggingVersion;
  String websiteTimezone;

}
