package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.InitializationRunResponse;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.InitializationStepResponse;
import com.yanchware.fractal.sdk.domain.exceptions.EnvironmentInitializationException;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
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
    protected final EnvironmentService environmentService;
    protected final Map<String, String> tags;

    public abstract ProviderType getProvider();
    public abstract void initialize() throws InstantiatorException;
    protected abstract Map<String, Object> getConfigurationForEnvironmentParameters();

    protected CloudAgentEntity(
            EnvironmentIdValue environmentId,
            EnvironmentService environmentService,
            Map<String, String> tags) {
        this.tags = tags;
        if (environmentId == null || environmentService == null) {
            throw new IllegalArgumentException("Environment and EnvironmentService cannot be null");
        }

        this.environmentService = environmentService;
        this.environmentId = environmentId;
    }

    protected void checkInitializationStatus(Supplier<InitializationRunResponse> fetchCurrentInitialization) throws InstantiatorException {
        log.info("Starting operation [checkInitializationStatus] for Environment [id: '{}']", environmentId);

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
        switch (initializationRun.status()) {
            case "Completed" -> log.info("Initialization for Environment [id: '{}'] completed", environmentId);
            case "Failed" -> {
                var messageToThrow = getFailedStepsMessageToThrow(environmentId, initializationRun);
                if (isBlank(messageToThrow)) {
                    log.warn("Initialization for Environment [id: '{}'] failed but error message is not visible yet", environmentId);
                    throw new InstantiatorException("Initialization failed, but the error message is not visible yet.");
                } else {
                    log.error("Initialization for Environment [id: '{}'] failed", environmentId);
                    throw new EnvironmentInitializationException(messageToThrow);
                }
            }
            case "Cancelled" -> {
                log.warn("Initialization for Environment [id: '{}'] cancelled", environmentId);
                throw new EnvironmentInitializationException("Initialization was cancelled.");
            }
            case "InProgress" -> {
                log.info("Initialization for Environment [id: '{}'] is in progress", environmentId);
                throw new InstantiatorException("Initialization is in progress, retrying...");
            }
            default -> throw new EnvironmentInitializationException(
                    String.format("Unknown initialization status: [%s]", initializationRun.status()));
        }
    }

    private String getFailedStepsMessageToThrow(String environmentId, InitializationRunResponse initializationRun) {
        var failedSteps = initializationRun.steps()
                .stream()
                .filter(step -> "Failed".equals(step.status()))
                .toList();

        if (failedSteps.isEmpty()) {
            return "";
        } else {
            var messageToThrow = new StringBuilder();
            messageToThrow.append(
                    String.format("Initialization for Environment [id: '%s'] failed. Failed steps -> ", environmentId));
            for (var step : failedSteps) {
                var errorMessage = step.lastOperationStatusMessage();

                if (isBlank(errorMessage)) {
                    return "";
                }

                messageToThrow.append(String.format("id: '%s', status: '%s', errorMessage: '%s' - ",
                        step.resourceName(),
                        step.status(),
                        errorMessage));
            }

            return messageToThrow.substring(0, messageToThrow.length() - 3);
        }
    }

    private void printInitializationStatus(InitializationRunResponse initializationRun) {
        log.info("Initialization Run ID: {}", initializationRun.id());

        initializationRun.steps().sort(Comparator.comparingInt(InitializationStepResponse::order));

        initializationRun.steps()
                .forEach(step -> {
                    String status = step.status();
                    String resourceName = step.resourceName();
                    String resourceType = step.resourceType();

                    if ("Failed".equalsIgnoreCase(status)) {
                        log.error("Step - Name: '{}', Type: '{}', Status: '{}'",
                                resourceName, resourceType, status);
                    } else {
                        log.info("Step - Name: '{}', Type: '{}', Status: '{}'",
                                resourceName, resourceType, status);
                    }
                });
    }
}
