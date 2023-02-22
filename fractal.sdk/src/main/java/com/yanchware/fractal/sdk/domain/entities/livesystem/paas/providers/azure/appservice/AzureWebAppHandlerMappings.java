package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureWebAppHandlerMappings {
  /**
   * Command-line arguments to be passed to the script processor.
   */
  private String arguments;
  /**
   * Requests with this extension will be handled using the specified FastCGI application.
   */
  private String extension;

  /**
   * The absolute path to the FastCGI application.
   */
  private String scriptProcessor;
}
