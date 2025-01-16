package com.yanchware.fractal.sdk.domain.environment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci.OciRegion;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
public class OperationalEnvironment extends BaseEnvironment {
  @JsonIgnore
  private final Map<ProviderType, Map<String, Object>> cloudProviders = new HashMap<>();

  @Setter(AccessLevel.PROTECTED)
  private EnvironmentIdValue managementEnvironmentId;


  @Setter(AccessLevel.PRIVATE)
  private String shortName;

  public static OperationalEnvironmentBuilder builder() {
    return new OperationalEnvironmentBuilder();
  }

  @Override
  public EnvironmentIdValue getId() {
    if (this.managementEnvironmentId == null) {
      return null;
    }

    return new EnvironmentIdValue(managementEnvironmentId.type(),
      managementEnvironmentId.ownerId(),
      shortName);
  }

  @Override
  public EnvironmentDto toDto() {
    return new EnvironmentDto(getId().toDto(), getParameters());
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (isBlank(shortName)) {
      errors.add(SHORT_NAME_IS_NULL);
    } else {
      if (shortName.length() > 30) {
        errors.add("Environment ShortName must not be longer than 30 characters.");
      }
      if (!shortName.matches("[a-z0-9-]+")) {
        errors.add("Environment ShortName must only contain lowercase letters, numbers, and dashes.");
      }
    }

    return errors;
  }

  public static class OperationalEnvironmentBuilder extends EnvironmentBuilder<OperationalEnvironment,
    OperationalEnvironmentBuilder> {

    public OperationalEnvironmentBuilder withShortName(String shortName) {
      environment.setShortName(shortName);
      return builder;
    }

    public OperationalEnvironmentBuilder withAwsAccount(AwsRegion region, String accountId) {
      var config = new HashMap<String, Object>();
      config.put("region", region);
      config.put("accountId", accountId);
      environment.cloudProviders.put(ProviderType.AWS, config);
      return builder;
    }

    public OperationalEnvironmentBuilder withAzureSubscription(AzureRegion region, UUID subscriptionId) {
      var config = new HashMap<String, Object>();
      config.put("region", region);
      config.put("subscriptionId", subscriptionId);
      environment.cloudProviders.put(ProviderType.AZURE, config);
      return builder;
    }

    public OperationalEnvironmentBuilder withGcpProject(GcpRegion region, String projectId) {
      var config = new HashMap<String, Object>();
      config.put("region", region);
      config.put("projectId", projectId);
      environment.cloudProviders.put(ProviderType.GCP, config);
      return builder;
    }

    public OperationalEnvironmentBuilder withOciCompartment(OciRegion region, String compartmentId) {
      var config = new HashMap<String, Object>();
      config.put("region", region);
      config.put("compartmentId", compartmentId);
      environment.cloudProviders.put(ProviderType.OCI, config);
      return builder;
    }

    @Override
    protected OperationalEnvironment createEnvironment() {
      return new OperationalEnvironment();
    }

    @Override
    protected OperationalEnvironmentBuilder getBuilder() {
      return this;
    }

    @Override
    public OperationalEnvironment build() {
      if (isBlank(environment.getName())) {
        environment.setName(environment.getShortName());
      }

      return super.build();
    }
  }

}
