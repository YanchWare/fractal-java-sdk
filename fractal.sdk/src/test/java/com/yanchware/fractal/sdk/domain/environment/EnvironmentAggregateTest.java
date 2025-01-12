package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.SecretResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentIdDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentTypeDto;
import com.yanchware.fractal.sdk.utils.LocalSdkConfiguration;
import io.github.resilience4j.retry.RetryRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EnvironmentAggregateTest {

  @Test
  public void noErrors_When_ManagingSecrets() throws InstantiatorException {
    var mockedEnvironmentService = mock(EnvironmentService.class);
    var aggregate = new EnvironmentAggregate(mockedEnvironmentService);

    var envId = new EnvironmentIdValue(
            EnvironmentType.PERSONAL,
            UUID.randomUUID(),
            "production-001");
    var managementEnvironment = ManagementEnvironment.builder()
            .withId(envId)
            .withResourceGroup(UUID.randomUUID())
            .build();
    aggregate.setManagementEnvironment(managementEnvironment);

    var secrets = new SecretResponse[]{
            new SecretResponse(
                    UUID.randomUUID(),
                    new EnvironmentIdDto(EnvironmentTypeDto.PERSONAL, envId.ownerId(), envId.shortName()),
                    "Secret-1",
                    null,
                    new Date(),
                    "Whoever",
                    new Date(),
                    "Whoever")
    };
    when(mockedEnvironmentService.getSecrets(envId)).thenReturn(secrets);

    aggregate.manageSecrets();
  }
}