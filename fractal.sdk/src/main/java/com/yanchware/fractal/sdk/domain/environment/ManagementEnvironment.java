package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci.OciRegion;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
public class ManagementEnvironment extends BaseEnvironment {
  private final static String OWNER_ID_IS_NULL = "Environment OwnerId has not been defined and it is required";
  private final List<OperationalEnvironment> operationalEnvironments;
  @Setter(AccessLevel.PRIVATE)
  private EnvironmentIdValue id;

  public ManagementEnvironment() {
    operationalEnvironments = new ArrayList<>();
  }

  public static ManagementEnvironmentBuilder builder() {
    return new ManagementEnvironmentBuilder();
  }

  public EnvironmentDto toDto() {
    return new EnvironmentDto(getId().toDto(), getParameters());
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (isBlank(id.shortName())) {
      errors.add(SHORT_NAME_IS_NULL);
    } else {
      if (id.shortName().length() > 30) {
        errors.add("Environment ShortName must not be longer than 30 characters.");
      }
      if (!id.shortName().matches("[a-z0-9-]+")) {
        errors.add("Environment ShortName must only contain lowercase letters, numbers, and dashes.");
      }
    }


    if (id.ownerId() == null || id.ownerId().equals(new UUID(0L, 0L))) {
      errors.add(OWNER_ID_IS_NULL);
    }

    return errors;
  }

  @SuppressWarnings("unchecked")
  public static class ManagementEnvironmentBuilder extends EnvironmentBuilder<ManagementEnvironment,
    ManagementEnvironmentBuilder> { // Specify Builder type

    public ManagementEnvironmentBuilder withId(EnvironmentIdValue environmentId) {
      environment.setId(environmentId);
      return this;
    }

    public ManagementEnvironmentBuilder withOperationalEnvironment(OperationalEnvironment operationalEnvironment) {

      return withOperationalEnvironments(List.of(operationalEnvironment));
    }

    public ManagementEnvironmentBuilder withOperationalEnvironments(Collection<OperationalEnvironment> operationalEnvironments) {
      if (CollectionUtils.isBlank(operationalEnvironments)) {
        return this;
      }

      environment.getOperationalEnvironments().addAll(operationalEnvironments);
      return this;
    }

    public ManagementEnvironmentBuilder withAwsCloudAgent(AwsRegion region, String organizationId, String accountId) {
      environment.registerAwsCloudAgent(region, organizationId, accountId);
      return builder;
    }

    public ManagementEnvironmentBuilder withAzureCloudAgent(AzureRegion region, UUID tenantId, UUID subscriptionId) {
      environment.registerAzureCloudAgent(region, tenantId, subscriptionId);
      return builder;
    }

    public ManagementEnvironmentBuilder withGcpCloudAgent(GcpRegion region, String organizationId, String projectId) {
      environment.registerGcpCloudAgent(region, organizationId, projectId);
      return builder;
    }

    public ManagementEnvironmentBuilder withOciCloudAgent(OciRegion region, String tenancyId, String compartmentId) {
      environment.registerOciCloudAgent(region, tenancyId, compartmentId);
      return builder;
    }

    @Override
    protected ManagementEnvironment createEnvironment() {
      return new ManagementEnvironment();
    }

    @Override
    protected ManagementEnvironmentBuilder getBuilder() {
      return this;
    }

    @Override
    public ManagementEnvironment build() {
      environment.operationalEnvironments.forEach(this::configureOperationalEnvironment); // Use a method reference

      if (isBlank(environment.getName())) {
        environment.setName(environment.id.shortName());
      }

      return super.build();
    }

    private void configureOperationalEnvironment(OperationalEnvironment operationalEnvironment) {
      operationalEnvironment.setManagementEnvironmentId(environment.getId());

      for (var entry : operationalEnvironment.getCloudProviders().entrySet()) {
        ProviderType provider = entry.getKey();
        Map<String, Object> config = entry.getValue();

        CloudAgentEntity managementAgent = environment.getCloudAgentByProviderType().get(provider);

        if (managementAgent == null) {
          continue;
        }

        switch (provider) {
          case AWS:
            operationalEnvironment.registerAwsCloudAgent(
              (AwsRegion) config.get("region"),
              getPropertyFromManagementAgent(provider, "organizationId"),
              (String) config.get("accountId"));
            break;
          case AZURE:
            operationalEnvironment.registerAzureCloudAgent(
              (AzureRegion) config.get("region"),
              getPropertyFromManagementAgent(provider, "tenantId"),
              (UUID) config.get("subscriptionId"));
            break;
          case GCP:
            operationalEnvironment.registerGcpCloudAgent(
              (GcpRegion) config.get("region"),
              getPropertyFromManagementAgent(provider, "organizationId"),
              (String) config.get("projectId"));
            break;
          case OCI:
            operationalEnvironment.registerOciCloudAgent(
              (OciRegion) config.get("region"),
              getPropertyFromManagementAgent(provider, "tenancyId"),
              (String) config.get("compartmentId"));
            break;
          default:
            throw new IllegalArgumentException("Unsupported cloud provider: " + provider);
        }
      }
    }

    private <T> T getPropertyFromManagementAgent(ProviderType providerType, String propertyName) {
      CloudAgentEntity cloudAgent = environment.getCloudAgentByProviderType().get(providerType);
      if (cloudAgent == null) {
        throw new IllegalStateException(providerType + " Cloud Agent not found in Management Environment.");
      }

      // Use reflection to get the property value
      try {
        var field = cloudAgent.getClass().getDeclaredField(propertyName);
        field.setAccessible(true);

        return (T) field.get(cloudAgent);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        throw new RuntimeException("Error getting property " + propertyName + " from " + providerType + " Cloud " +
          "Agent", e);
      }
    }
  }
}
