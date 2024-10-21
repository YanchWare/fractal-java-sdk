package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.EnvironmentResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Setter(AccessLevel.PROTECTED)
public class EnvironmentAggregate {
  public static final String REGION_PARAM_KEY = "region";

  private final EnvironmentService service;

  @Getter
  private ManagementEnvironment managementEnvironment;

  public EnvironmentAggregate(
      HttpClient client,
      SdkConfiguration sdkConfiguration,
      RetryRegistry retryRegistry) {
    this.service = new EnvironmentService(client, sdkConfiguration, retryRegistry);
  }

  public EnvironmentResponse createOrUpdate(Environment environment) throws InstantiatorException {
    return createOrUpdate(null, environment);
  }

  public EnvironmentResponse createOrUpdate(EnvironmentIdValue managementEnvironmentId,
                                            Environment environment) throws InstantiatorException {
    var environmentId = environment.getId();
    log.info("Starting createOrUpdateEnvironment for Environment [id: '{}']", environmentId);

    log.info("Fetching environment details [id: '{}']", environmentId);
    var existingEnvironment = service.fetch(environmentId);
    if (existingEnvironment != null) {
      if (environment.doesNotNeedUpdate(existingEnvironment)) {
        log.info("No changes detected for Environment [id: '{}']. Update not required.", environmentId);
        return existingEnvironment;
      } else {
        log.info("Environment [id: '{}'] exists, updating ...", environmentId);
        return service.update(
            environmentId,
            environment.getName(),
            environment.getResourceGroups(),
            environment.getParameters());
      }
    }

    log.info("Environment does not exist, creating new environment [id: '{}']", environmentId);
    return service.create(
        managementEnvironmentId,
        environmentId,
        environment.getName(),
        environment.getResourceGroups(),
        environment.getParameters());
  }


  public void initializeAgents() {
    var managementAgents = managementEnvironment.getCloudAgentByProviderType().values();

    // 1. Initialize Management Environment Agents
    try (ExecutorService executor = Executors.newFixedThreadPool(managementAgents.size())) {
      for (var managementAgent : managementAgents) {
        executor.execute(() -> {
          try {
            managementAgent.initialize(service, null);
          } catch (InstantiatorException e) {
            throw new RuntimeException(e);
          }
        });
      }
    }

    // 2. Initialize Operational Environment Agents (after management agents are initialized)
    var operationalEnvironments = managementEnvironment.getOperationalEnvironments();
    if (operationalEnvironments != null) {
      for (var operationalEnvironment : operationalEnvironments) {
        var operationalAgents = operationalEnvironment.getCloudAgentByProviderType().values();
        try (ExecutorService executor = Executors.newFixedThreadPool(operationalAgents.size())) {
          for (var operationalAgent : operationalAgents) {
            executor.execute(() -> {
              try {
                operationalAgent.initialize(service, managementEnvironment.getId());
              } catch (InstantiatorException e) {
                throw new RuntimeException(e);
              }
            });
          }
        }
      }
    }
  }

  public void initializeEnvironments() throws InstantiatorException {
    var managementEnvironment = getManagementEnvironment();
    var response = createOrUpdate(managementEnvironment);

    for (OperationalEnvironment operationalEnvironment : managementEnvironment.getOperationalEnvironments()) {
      createOrUpdate(managementEnvironment.getId(), operationalEnvironment);
    }
  }
}
