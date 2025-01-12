package com.yanchware.fractal.sdk.domain.environment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.EnvironmentResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.utils.SerializationUtils;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.*;
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

  public void createOrUpdate() throws InstantiatorException {
    var managementEnvironmentId = managementEnvironment.getId();


    var existingEnvironment = service.fetch(managementEnvironmentId);
    if (existingEnvironment != null) {
      if (doesNotNeedUpdate(managementEnvironment, existingEnvironment)) {
        log.info("Management Environment [id: '{}'] already exists and is up-to-date.", managementEnvironmentId);
      } else {
        log.info("Updating existing Management Environment [id: '{}']", managementEnvironmentId);
        service.update(
            managementEnvironmentId,
            managementEnvironment.getName(),
            managementEnvironment.getResourceGroups(),
            managementEnvironment.getParameters());
      }
    } else {
      log.info("Creating new Management Environment [id: '{}']", managementEnvironmentId);
      service.create(
          null,
          managementEnvironmentId,
          managementEnvironment.getName(),
          managementEnvironment.getResourceGroups(),
          managementEnvironment.getParameters());
    }

    for (var operationalEnvironment : managementEnvironment.getOperationalEnvironments()) {
      initializeOperationalEnvironment(operationalEnvironment);
    }
  }

  public void manageSecrets() throws InstantiatorException {
    manageEnvironmentSecrets(managementEnvironment.getId(), managementEnvironment.getSecrets());

    for (var environment : managementEnvironment.getOperationalEnvironments()) {
      manageEnvironmentSecrets(environment.getId(), environment.getSecrets());
    }
  }

  private void manageEnvironmentSecrets(EnvironmentIdValue environmentId, Collection<Secret> environmentSecrets) throws InstantiatorException {
    var existingSecrets = fetchExistingSecretIds(environmentId);
    
    for (var secret : environmentSecrets) {
      var secretName = secret.name();
      var secretValue = secret.value();

      if (existingSecrets.contains(secretName)) {
        service.updateSecret(environmentId, secretName, secretValue);
        existingSecrets.remove(secretName);
      } else {
        log.info("Creating secret [name: '{}', environmentId: '{}']", secretName, environmentId);
        service.createSecret(environmentId, secretName, secretValue);
      }
    }
    
    for (String secretName : existingSecrets) {
      log.info("Deleting secret [name: '{}', environmentId: '{}'] as it is not present any longer on the live environment definition", secretName, environmentId);
      service.deleteSecret(environmentId, secretName);
    }
  }

  private List<String> fetchExistingSecretIds(EnvironmentIdValue environmentId) throws InstantiatorException {
    List<String> existingSecrets = new ArrayList<>();

    var secrets = service.getSecrets(environmentId);

    if (secrets != null) {
      for (var secret : secrets) {
        existingSecrets.add(secret.name());
      }
    }
    return existingSecrets;
  }

  private void initializeOperationalEnvironment(OperationalEnvironment operationalEnvironment) throws InstantiatorException {
    var environmentId = operationalEnvironment.getId();

    var existingEnvironment = service.fetch(environmentId);
    if (existingEnvironment != null) {
      if (doesNotNeedUpdate(operationalEnvironment, existingEnvironment)) {
        log.info("Operational Environment [id: '{}'] already exists and is up-to-date.", environmentId);
      } else {
        log.info("Updating existing Operational Environment [id: '{}']", environmentId);
        service.update(
            environmentId,
            operationalEnvironment.getName(),
            operationalEnvironment.getResourceGroups(),
            operationalEnvironment.getParameters());
      }
    } else {
      log.info("Creating new Operational Environment [id: '{}']", environmentId);
      service.create(
          managementEnvironment.getId(),
          environmentId,
          operationalEnvironment.getName(),
          operationalEnvironment.getResourceGroups(),
          operationalEnvironment.getParameters());
    }
  }

  /**
   * Compares this environment response with another environment object.
   *
   * @param existingEnvironmentResponse the environment response to compare with current entity
   * @return true if the environments are equal, false otherwise
   */
  private boolean doesNotNeedUpdate(Environment environment, EnvironmentResponse existingEnvironmentResponse) {
    if (existingEnvironmentResponse == null) return false;

    var environmentIdInResponse = existingEnvironmentResponse.id();
    return Objects.equals(environmentIdInResponse.type().toString(), environment.getId().type().toString()) &&
        Objects.equals(environmentIdInResponse.ownerId(), environment.getId().ownerId()) &&
        Objects.equals(environmentIdInResponse.shortName(), environment.getId().shortName()) &&
        Objects.equals(existingEnvironmentResponse.name(), environment.getName()) &&
        Objects.equals(existingEnvironmentResponse.resourceGroups(), environment.getResourceGroups()) &&
        mapsEqual(existingEnvironmentResponse.parameters(), environment.getParameters());
  }

  /**
   * Compares two maps for equality using JSON serialization.
   *
   * @param map1 the first map to compare
   * @param map2 the second map to compare
   * @return true if the maps are equal, false otherwise
   */
  private boolean mapsEqual(Map<String, Object> map1, Map<String, Object> map2) {
    try {
      String json1 = SerializationUtils.serializeSortedJson(map1);
      String json2 = SerializationUtils.serializeSortedJson(map2);
      return json1.equals(json2);
    } catch (JsonProcessingException e) {
      return false;
    }
  }


  public void initializeAgents() {
    var managementAgents = managementEnvironment.getCloudAgentByProviderType().values();

    // 1. Initialize Management Environment Agents
    try (ExecutorService executor = Executors.newFixedThreadPool(managementAgents.size())) {
      for (var managementAgent : managementAgents) {
        executor.execute(() -> {
          try {

            var providerType = managementAgent.getProvider();

            managementAgent.initialize(service);

            for (var operationalEnvironment : managementEnvironment.getOperationalEnvironments()) {

              var operationalAgent = operationalEnvironment.getCloudAgentByProviderType()
                  .values()
                  .stream().filter(a -> a.getProvider() == providerType)
                  .findFirst();

              if (operationalAgent.isPresent()) {
                operationalAgent.get().initialize(service, managementEnvironment.getId());
              }
            }
          } catch (InstantiatorException e) {
            log.error(e.getMessage());
            System.exit(1);
          }
        });
      }
    }
  }
}
