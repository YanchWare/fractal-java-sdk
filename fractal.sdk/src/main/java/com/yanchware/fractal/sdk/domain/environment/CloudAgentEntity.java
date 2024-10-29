package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.InitializationRunResponse;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.InitializationStepResponse;
import com.yanchware.fractal.sdk.domain.exceptions.EnvironmentInitializationException;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.StringHelper;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Supplier;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
public abstract class CloudAgentEntity {
    protected static final Duration RETRIES_DELAY = Duration.ofSeconds(30);
    protected static final Duration TOTAL_ALLOWED_DURATION = Duration.ofMinutes(55);
    protected final EnvironmentIdValue environmentId;
    protected final Map<String, String> tags;

    public abstract ProviderType getProvider();
    public abstract void initialize(EnvironmentService environmentService, EnvironmentIdValue managementEnvironmentId) throws InstantiatorException;
    protected abstract Map<String, Object> getConfigurationForEnvironmentParameters();

    protected CloudAgentEntity(
            EnvironmentIdValue environmentId,
            Map<String, String> tags) {
        this.tags = tags;
        this.environmentId = environmentId;
    }

    protected void checkInitializationStatus(Supplier<InitializationRunResponse> fetchCurrentInitialization) throws InstantiatorException {
        log.info("Checking initialization status for environment [id: '{}']", environmentId);
        
        int maxAttempts = (int) (TOTAL_ALLOWED_DURATION.toMillis() / RETRIES_DELAY.toMillis());

        var retryConfig = RetryConfig.custom()
                .retryExceptions(InstantiatorException.class)
                .maxAttempts(maxAttempts)
                .waitDuration(RETRIES_DELAY)
                .build();

        var retry = RetryRegistry.of(retryConfig).retry("checkInitializationStatus");

        try {
            Retry.decorateCheckedSupplier(retry, () -> {
                InitializationRunResponse currentInitialization = fetchCurrentInitialization.get();
                printInitializationStatus(currentInitialization);
                validateInitializationStatus(currentInitialization);

                return currentInitialization;
            }).get();
        } catch (Throwable ex) {
            throw new InstantiatorException(ex.getLocalizedMessage());
        }
    }

    private void validateInitializationStatus(InitializationRunResponse initializationRun) throws InstantiatorException, EnvironmentInitializationException {
        var environmentId = initializationRun.environmentId().toString();
        var providerType = StringHelper.convertToTitleCase(getProvider().toString());
        
        switch (initializationRun.status()) {
            case "Completed" -> log.info("{} cloud agent initialized successfully [EnvironmentId: '{}']", 
                providerType,
                environmentId);
            case "Failed" -> {
                var messageToThrow = getFailedStepsMessageToThrow(providerType, environmentId, initializationRun);
                if (isBlank(messageToThrow)) {
                    log.info("{} cloud agent initialization in progress [EnvironmentId: '{}']", providerType, environmentId);
                    throw new InstantiatorException("Initialization is in progress, retrying...");
                } else {
                    throw new EnvironmentInitializationException(messageToThrow);
                }
            }
            case "Cancelled" -> {
                log.warn("{} cloud agent initialization cancelled [EnvironmentId: '{}']", providerType, environmentId);
                throw new EnvironmentInitializationException("Initialization was cancelled.");
            }
            case "InProgress" -> {
                log.info("{} cloud agent initialization in progress [EnvironmentId: '{}']", providerType, environmentId);
                throw new InstantiatorException("Initialization is in progress, retrying...");
            }
            default -> {
                log.warn("{} cloud agent initialization in an unknown state [EnvironmentId: '{}']", providerType,
                    environmentId);
                throw new EnvironmentInitializationException(
                    String.format("Unknown initialization status: [%s]", initializationRun.status()));
            }
        }
    }

    private String getFailedStepsMessageToThrow(
        String providerType,
        String environmentId, 
        InitializationRunResponse initializationRun) {
        var failedSteps = initializationRun.steps()
                .stream()
                .filter(step -> "Failed".equals(step.status()))
                .toList();

        if (failedSteps.isEmpty()) {
            return "";
        } else {
            var messageToThrow = new StringBuilder();
            messageToThrow.append(
                    String.format("%s cloud agent initialization failed [EnvironmentId: '%s'].\n   Failed steps:\n", 
                        providerType,
                        environmentId));
            
            for (var step : failedSteps) {
                var errorMessage = step.lastOperationStatusMessage();
                
                messageToThrow.append(String.format("      - Name: '%s', Status: '%s', ErrorMessage: '%s'\n",
                        step.resourceName(),
                        step.status(),
                        errorMessage));
            }

            messageToThrow.deleteCharAt(messageToThrow.length() - 1);
            return messageToThrow.toString();
        }
    }

    private void printInitializationStatus(InitializationRunResponse initializationRun) {

        initializationRun.steps().sort(Comparator.comparingInt(InitializationStepResponse::order));

        var stepsInfo = new StringBuilder();
        initializationRun.steps()
            .forEach(step -> {
                String status = step.status();
                String resourceName = step.resourceName();
                String resourceType = step.resourceType();

                // Append each step's information to the log message
                stepsInfo.append(String.format("  Step - Name: '%s', Type: '%s', Status: '%s'\n", 
                    resourceName, 
                    resourceType, 
                    status));
            });

        if (!stepsInfo.isEmpty()) {
            stepsInfo.deleteCharAt(stepsInfo.length() - 1);
            
            var status = String.format("%s cloud agent initialization status [id: '%s']:\n", 
                initializationRun.cloudProvider(),                
                initializationRun.id());

          log.info("{}{}", status, stepsInfo);
        }
    }
}
