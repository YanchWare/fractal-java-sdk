package com.yanchware.fractal.sdk.domain.environment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.environment.service.commands.CreateCiCdProfileRequest;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.EnvironmentResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.utils.SerializationUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Setter(AccessLevel.PROTECTED)
public class EnvironmentAggregate {
    public static final String REGION_PARAM_KEY = "region";

    private final EnvironmentService service;

    @Getter
    private ManagementEnvironment managementEnvironment;

    public EnvironmentAggregate(EnvironmentService environmentService) {
        this.service = environmentService;
    }

    public void createOrUpdate() throws InstantiatorException {
        var managementEnvironmentId = managementEnvironment.getId();

        var existingEnvironment = service.fetch(managementEnvironmentId);
        if (shouldCreateEnvironment(existingEnvironment)) {
            log.info("Creating new Management Environment [id: '{}']", managementEnvironmentId);
            service.create(
                    null,
                    managementEnvironmentId,
                    managementEnvironment.getName(),
                    managementEnvironment.getResourceGroups(),
                    managementEnvironment.getParameters());
        } else {
            if (doesNotNeedUpdate(managementEnvironment, existingEnvironment)) {
                log.info("Management Environment [id: '{}'] already exists and is up-to-date.", managementEnvironmentId);
            } else {
                log.info("Updating existing Management Environment [id: '{}']", managementEnvironmentId);
                service.update(
                        managementEnvironmentId,
                        managementEnvironmentId,
                        managementEnvironment.getName(),
                        managementEnvironment.getResourceGroups(),
                        managementEnvironment.getParameters(),
                        existingEnvironment.defaultCiCdProfileShortName());
            }
        }

        for (var operationalEnvironment : managementEnvironment.getOperationalEnvironments()) {
            initializeOperationalEnvironment(operationalEnvironment);
        }
    }

    private boolean shouldCreateEnvironment(EnvironmentResponse existingEnvironment) {
        if (existingEnvironment == null) {
            return true;
        }

        return existingEnvironment.status().equalsIgnoreCase("deleted");
    }

    public void manageCiCdProfiles() throws InstantiatorException {
        manageProfilesForEnvironment(managementEnvironment); // Manage profiles for management environment

        for (var environment: managementEnvironment.getOperationalEnvironments()) {
            manageProfilesForEnvironment(environment); // Manage profiles for each operational environment
        }
    }

    void manageProfilesForEnvironment(BaseEnvironment environment) throws InstantiatorException {
        var environmentId = environment.getId();
        var defaultProfile = environment.getDefaultCiCdProfile();

        var existingEnvironment = service.fetch(environmentId);
        var currentDefaultProfileShortName = existingEnvironment.defaultCiCdProfileShortName();

        var environmentTier = environment instanceof ManagementEnvironment ? "Management" : "Operational";

        if (defaultProfile != null) {
            handleDefaultProfile(environmentTier, environment, environmentId, defaultProfile, currentDefaultProfileShortName);
        } else {
            handleRemovedDefaultProfile(environmentTier, environment, environmentId, currentDefaultProfileShortName);
        }
    }

    private void handleDefaultProfile(String environmentTier,
                                      BaseEnvironment environment, 
                                      EnvironmentIdValue environmentId, 
                                      CiCdProfile defaultProfile, 
                                      String currentDefaultProfileShortName) throws InstantiatorException {
        var requests = new ArrayList<CreateCiCdProfileRequest>();
        requests.add(CreateCiCdProfileRequest.fromProfile(defaultProfile));

        for (var profile: environment.getCiCdProfiles()) {
            requests.add(CreateCiCdProfileRequest.fromProfile(profile));
        }

        log.info("Managing CI/CD profiles for {} environment [id: {}]",
                environmentTier, environmentId);

        var profilesResponse = service.manageCiCdProfiles(environmentId, requests);

        log.info("Managed {} CI/CD profiles for {} environment [id: {}]", profilesResponse.length,
                environmentTier, environmentId);
        
        if (!defaultProfile.shortName().equals(currentDefaultProfileShortName)) {
            updateEnvironmentWithDefaultProfile(environmentTier, environment, environmentId, defaultProfile.shortName());
        }
    }

    private void updateEnvironmentWithDefaultProfile(String environmentTier,
                                                     BaseEnvironment environment, 
                                                     EnvironmentIdValue environmentId, 
                                                     String shortName) throws InstantiatorException {
        var managementEnvironmentId = environmentId;

        if (environment instanceof OperationalEnvironment operationalEnvironment) {
            managementEnvironmentId = operationalEnvironment.getManagementEnvironmentId();
        }

        log.info("Updating {} Environment [id: '{}'] with new default CI/CD profile short name",
                environmentTier,
                environmentId);

        service.update(
                managementEnvironmentId,
                environmentId,
                environment.getName(),
                environment.getResourceGroups(),
                environment.getParameters(),
                shortName);
    }

    private void handleRemovedDefaultProfile(String environmentTier,
                                             BaseEnvironment environment, 
                                             EnvironmentIdValue environmentId, 
                                             String currentDefaultProfileShortName) throws InstantiatorException {
        if (StringUtils.isNotBlank(currentDefaultProfileShortName)) {
            var managementEnvironmentId = environmentId;

            if (environment instanceof OperationalEnvironment operationalEnvironment) {
                managementEnvironmentId = operationalEnvironment.getManagementEnvironmentId();
                environmentTier = "Operational";
            }

            log.info("Updating {} Environment [id: '{}'] to remove default CI/CD profile", environmentTier, environmentId);

            service.update(
                    managementEnvironmentId,
                    environmentId,
                    environment.getName(),
                    environment.getResourceGroups(),
                    environment.getParameters(),
                    null);
        }
    }

    public void manageSecrets() throws InstantiatorException {
        var managementEnvironmentId = managementEnvironment.getId();

        log.info("Managing Secrets for Management environment [id: {}]", managementEnvironmentId);

        var secretsResponse = service.manageSecrets(managementEnvironmentId, managementEnvironment.getSecrets());

        log.info("Managed {} Secrets for Management environment [id: {}]", secretsResponse.length, managementEnvironmentId);

        for (var environment : managementEnvironment.getOperationalEnvironments()) {
            var environmentId = environment.getId();
            log.info("Managing Secrets for Operational environment [id: {}]", environmentId);

            secretsResponse = service.manageSecrets(environmentId, environment.getSecrets());

            log.info("Managed {} Secrets for Operational environment [id: {}]", secretsResponse.length, environmentId);
        }
    }

    private void initializeOperationalEnvironment(OperationalEnvironment operationalEnvironment) throws InstantiatorException {
        var environmentId = operationalEnvironment.getId();

        var existingEnvironment = service.fetch(environmentId);

        if (shouldCreateEnvironment(existingEnvironment)) {
            log.info("Creating new Operational Environment [id: '{}']", environmentId);
            service.create(
                    managementEnvironment.getId(),
                    environmentId,
                    operationalEnvironment.getName(),
                    operationalEnvironment.getResourceGroups(),
                    operationalEnvironment.getParameters());
        } else {
            if (doesNotNeedUpdate(operationalEnvironment, existingEnvironment)) {
                log.info("Operational Environment [id: '{}'] already exists and is up-to-date.", environmentId);
            } else {
                log.info("Updating existing Operational Environment [id: '{}']", environmentId);
                service.update(
                        managementEnvironment.getId(),
                        environmentId,
                        operationalEnvironment.getName(),
                        operationalEnvironment.getResourceGroups(),
                        operationalEnvironment.getParameters(),
                        existingEnvironment.defaultCiCdProfileShortName());
            }
        }
    }

    /**
     * Compares this environment response with another environment object.
     *
     * @param existingEnvironmentResponse the environment response to compare with current entity
     * @return true if the environments are equal, false otherwise
     */
    private boolean doesNotNeedUpdate(Environment environment,
                                      EnvironmentResponse existingEnvironmentResponse) {
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
