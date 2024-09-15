package com.yanchware.fractal.sdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.domain.blueprint.BlueprintFactory;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentAggregate;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemAggregate;
import com.yanchware.fractal.sdk.configuration.EnvVarSdkConfiguration;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.configuration.instantiation.InstantiationConfiguration;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemsFactory;
import com.yanchware.fractal.sdk.domain.exceptions.ComponentInstantiationException;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.environment.EnvironmentsFactory;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.*;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

import static com.yanchware.fractal.sdk.configuration.Constants.GIT_COMMIT_ID_KEY;
import static com.yanchware.fractal.sdk.utils.OutputFieldUtils.printOutputFields;
import static com.yanchware.fractal.sdk.utils.SerializationUtils.serialize;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
public class Automaton {
  private static Automaton instance;
  private static BlueprintFactory blueprintFactory;
  private static LiveSystemsFactory liveSystemFactory;
  private static EnvironmentsFactory environmentsFactory;
  private static RetryRegistry serviceRetryRegistry;

  private Automaton(HttpClient httpClient, SdkConfiguration sdkConfiguration) {
    Automaton.serviceRetryRegistry = getDefaultRetryRegistry();
    Automaton.blueprintFactory = new BlueprintFactory(httpClient, sdkConfiguration, Automaton.serviceRetryRegistry);
    Automaton.liveSystemFactory = new LiveSystemsFactory(httpClient, sdkConfiguration, Automaton.serviceRetryRegistry);
    Automaton.environmentsFactory = new EnvironmentsFactory(httpClient, sdkConfiguration, Automaton.serviceRetryRegistry);
  }

  /**
   * Initializes the Automaton instance for unit testing.
   *
   * @param httpClient       the HTTP client to be used
   * @param sdkConfiguration the SDK configuration
   */
  protected static void initializeAutomaton(HttpClient httpClient, SdkConfiguration sdkConfiguration) {
    instance = new Automaton(httpClient, sdkConfiguration);
  }

  /**
   * Initializes the Automaton instance.
   *
   * @param sdkConfiguration the SDK configuration
   */
  protected static void initializeAutomaton(SdkConfiguration sdkConfiguration) {
    var builder = HttpClient
        .newBuilder()
        .version(HttpClient.Version.HTTP_2);

    initializeAutomaton(builder.build(), sdkConfiguration);
  }

  private static RetryRegistry getDefaultRetryRegistry() {
    return RetryRegistry.of(RetryConfig.custom()
        .maxAttempts(5)
        .intervalFunction(IntervalFunction.ofExponentialBackoff(5000L))
        .build());
  }

  private static void waitForMutationInstantiation(LiveSystemAggregate liveSystem, LiveSystemMutationDto mutation)
      throws InstantiatorException
  {
    liveSystem.checkLiveSystemMutationStatus(mutation.id());
  }

  private static String environmentToJsonString(EnvironmentAggregate environment) throws InstantiatorException {
    try {
      return serialize(environment);
    } catch (JsonProcessingException e) {
      var errorMessage = String.format("Unable to serialize Instantiate LiveSystem environment. %s",
          e.getLocalizedMessage());

      log.error(errorMessage, e);
      throw new InstantiatorException(errorMessage, e);
    }
  }

  private static LiveSystemMutationDto instantiateLiveSystem(LiveSystemAggregate liveSystem)
      throws InstantiatorException {
    log.info("Starting to instantiate live system [id: '{}']", liveSystem.getId());
    log.info("Environment -> {}", environmentToJsonString(liveSystem.getEnvironment()));

    createOrUpdateBlueprint(liveSystem);

    return liveSystem.instantiate();
  }

  private static void createOrUpdateBlueprint(LiveSystemAggregate liveSystem) throws InstantiatorException {
    var blueprintAggregate = blueprintFactory.getBlueprintAggregate(liveSystem);
    blueprintAggregate.createOrUpdate();
  }

  /**
   * Instantiates the given environment.
   *
   * @param environment the environment to be instantiated
   * @throws InstantiatorException if an error occurs during instantiation
   */
  public static void instantiate(EnvironmentAggregate environment) throws InstantiatorException {
    if (instance == null) {
      initializeAutomaton(getSdkConfiguration());
    }

    instantiateEnvironment(environment);
  }

  /**
   * Instantiates the given list of live systems.
   *
   * @param liveSystems the list of live systems to be instantiated
   * @throws InstantiatorException if an error occurs during instantiation
   */
  public static void instantiate(List<LiveSystemAggregate> liveSystems) throws InstantiatorException {
    if (instance == null) {
      initializeAutomaton(getSdkConfiguration());
    }

    for (LiveSystemAggregate liveSystem : liveSystems) {
      instantiateLiveSystem(liveSystem);
    }
  }

  /**
   * Instantiates the given list of live systems with the provided instantiation configuration.
   *
   * @param liveSystems the list of live systems to be instantiated
   * @param config      the instantiation configuration
   * @throws InstantiatorException if an error occurs during instantiation
   */
  public static void instantiate(List<LiveSystemAggregate> liveSystems, InstantiationConfiguration config)
      throws InstantiatorException {

    if (instance == null) {
      initializeAutomaton(getSdkConfiguration());
    }

    var liveSystemsMutations = new ArrayList<ImmutablePair<LiveSystemAggregate, LiveSystemMutationDto>>();

    for (LiveSystemAggregate liveSystem : liveSystems) {
      liveSystemsMutations.add(new ImmutablePair<>(liveSystem, instantiateLiveSystem(liveSystem)));
    }

    if (config != null && config.waitConfiguration != null && config.getWaitConfiguration().waitForInstantiation) {
      for (var liveSystemMutation : liveSystemsMutations) {
        waitForMutationInstantiation(
            liveSystemMutation.getKey(),
            liveSystemMutation.getValue());
      }
    }
  }

  /**
   * Deploys a custom workload component within a specified live system, optionally waiting for completion 
   * and verifying the deployed commit ID.
   *
   * <p>This method performs the following steps:</p>
   *
   * <ol>
   *   <li><strong>Parameter Validation:</strong> Ensures that all input parameters are valid and not null or empty.</li>
   *   <li><strong>Component Instantiation:</strong> Initiates the instantiation of the custom workload component within the live system.</li>
   *   <li><strong>Optional Deployment Wait:</strong> If the provided `config` specifies waiting for instantiation, the method will wait until the deployment is complete.</li>
   *   <li><strong>Commit ID Verification:</strong> If waiting is enabled, the method verifies that the deployed component's commit ID matches the `commitId` parameter. 
   *       If there's a mismatch but the component is active, the deployment is re-triggered.</li>
   *   <li><strong>Output Fields Logging:</strong> If the deployment is successful, the component's output fields are logged for informational purposes.</li>
   * </ol>
   *
   * @param resourceGroupId            The ID of the resource group containing the live system.
   * @param liveSystemName             The name of the live system.
   * @param customWorkloadComponentId  The ID of the custom workload component to deploy.
   * @param commitId                  The expected commit ID to be deployed.
   * @param config                    The instantiation configuration, which can specify whether to wait for deployment completion.
   *
   * @throws ComponentInstantiationException If ny of the required parameters are null or empty, the component is not found, the deployment fails, or an error occurs while waiting for deployment completion.
   */
  public static void deployCustomWorkload(String resourceGroupId,
                                          String liveSystemName,
                                          String customWorkloadComponentId,
                                          String commitId,
                                          InstantiationConfiguration config) throws ComponentInstantiationException, InstantiatorException {

    if (isBlank(resourceGroupId)) {
      throw new ComponentInstantiationException("Resource group ID cannot be blank.");
    }

    if (isBlank(liveSystemName)) {
      throw new ComponentInstantiationException("Live system name cannot be blank.");
    }

    if (isBlank(customWorkloadComponentId)) {
      throw new ComponentInstantiationException("Custom workload component ID cannot be blank.");
    }

    if (isBlank(commitId)) {
      throw new ComponentInstantiationException("Commit ID cannot be blank.");
    }

    if (instance == null) {
      initializeAutomaton(getSdkConfiguration());
    }

    var liveSystem = liveSystemFactory.builder()
            .withResourceGroupId(resourceGroupId).withName(liveSystemName)
            .build();

    var componentMutationDto = liveSystem.instantiateComponent(customWorkloadComponentId);

    if (componentMutationDto == null) {
      throw new ComponentInstantiationException(
          String.format("Component [id: '%s'] not found in LiveSystem [name: '%s', resource group id: '%s']",
              customWorkloadComponentId, liveSystemName, resourceGroupId));
    }

    // Optional waiting based on configuration
    if (config != null && config.waitConfiguration != null && config.getWaitConfiguration().waitForInstantiation) {
      try {
        var updatedComponentMutation = waitForCustomWorkloadDeploymentCompletion(liveSystem, componentMutationDto);

        var component = updatedComponentMutation.component();
        var componentStatus = component.getStatus();

        if (componentStatus == LiveSystemComponentStatusDto.Active &&
            (!component.getOutputFields().containsKey(GIT_COMMIT_ID_KEY) ||
                !component.getOutputFields().get(GIT_COMMIT_ID_KEY).equals(commitId))) {
          
          log.info("Component is active but at a different commit ID. Expected: '{}', Current: '{}'. Re-triggering deployment",
              commitId, component.getOutputFields().get(GIT_COMMIT_ID_KEY));

          deployCustomWorkload(resourceGroupId, liveSystemName, customWorkloadComponentId, commitId, config);
        } else if (componentStatus != LiveSystemComponentStatusDto.Active) {
          printOutputFields(component.getOutputFields());
          throw new ComponentInstantiationException("Component deployment failed with status: " + updatedComponentMutation.status());
        } else {
          log.info("Component deployment completed successfully.");

          printOutputFields(component.getOutputFields());
        }
      } catch (InstantiatorException e) {
        throw new ComponentInstantiationException(e.getLocalizedMessage(), e);
      }
    }
  }

  /**
   * Deploys a custom workload component within a specified live system in a "fire-and-forget" manner.
   *
   * <p>This method performs the following steps:</p>
   *
   * <ol>
   *   <li><strong>Parameter Validation:</strong> Ensures that all input parameters are valid and not null or empty.</li>
   *   <li><strong>Component Instantiation:</strong> Initiates the instantiation of the custom workload component within the live system.</li>
   * </ol>
   *
   * <p>Note: This method does not wait for deployment completion or verify the commit ID.</p>
   *
   * @param resourceGroupId            The ID of the resource group containing the live system.
   * @param liveSystemName             The name of the live system.
   * @param customWorkloadComponentId  The ID of the custom workload component to deploy.
   *
   * @throws ComponentInstantiationException If any of the required parameters are null or empty or the component is not found.
   */
  public static void deployCustomWorkload(String resourceGroupId,
                                          String liveSystemName,
                                          String customWorkloadComponentId) throws ComponentInstantiationException, InstantiatorException {
    if (isBlank(resourceGroupId)) {
      throw new ComponentInstantiationException("Resource group ID cannot be blank.");
    }

    if (isBlank(liveSystemName)) {
      throw new ComponentInstantiationException("Live system name cannot be blank.");
    }

    if (isBlank(customWorkloadComponentId)) {
      throw new ComponentInstantiationException("Custom workload component ID cannot be blank.");
    }

    if (instance == null) {
      initializeAutomaton(getSdkConfiguration());
    }

    var liveSystem = liveSystemFactory.builder()
            .withResourceGroupId(resourceGroupId).withName(liveSystemName)
            .build();

    var componentMutationDto = liveSystem.instantiateComponent(customWorkloadComponentId);

    if (componentMutationDto == null) {
      throw new ComponentInstantiationException(
          String.format("Component [id: '%s'] not found in LiveSystem [name: '%s', resource group id: '%s']",
              customWorkloadComponentId, liveSystemName, resourceGroupId));
    }
  }

  private static LiveSystemComponentMutationDto waitForCustomWorkloadDeploymentCompletion(
          LiveSystemAggregate liveSystemAggregate,
          LiveSystemComponentMutationDto componentMutationDto) throws InstantiatorException {
    return liveSystemAggregate.getComponentMutationStatus(
        componentMutationDto.component().getId(),
        componentMutationDto.id());
  }

  private static void instantiateEnvironment(EnvironmentAggregate environment) throws InstantiatorException {
    environment.initializeAgents();
  }

  private static EnvVarSdkConfiguration getSdkConfiguration() throws InstantiatorException {
    EnvVarSdkConfiguration configuration;
    try {
      configuration = new EnvVarSdkConfiguration();
    } catch (URISyntaxException e) {
      throw new InstantiatorException("Error with Sdk configuration", e);
    }
    return configuration;
  }
}
