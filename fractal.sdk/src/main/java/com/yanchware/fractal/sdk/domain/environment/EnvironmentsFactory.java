package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsZone;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.aws.AwsRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.oci.OciRegion;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.*;

@Slf4j
@AllArgsConstructor
public class EnvironmentsFactory {

  private final HttpClient client;
  private final SdkConfiguration sdkConfiguration;
  private final RetryRegistry retryRegistry;

  public EnvironmentBuilder builder() {
    return new EnvironmentBuilder(client, sdkConfiguration, retryRegistry);
  }

  public static class EnvironmentBuilder {
    private final EnvironmentAggregate environment;
    private final EnvironmentBuilder builder;

    public EnvironmentBuilder(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {
      environment = create(client, sdkConfiguration, retryRegistry);
      builder = getBuilder();
    }

    protected EnvironmentAggregate create(HttpClient client, SdkConfiguration sdkConfiguration, RetryRegistry retryRegistry) {
      return new EnvironmentAggregate(client, sdkConfiguration, retryRegistry);
    }

    protected EnvironmentBuilder getBuilder() {
      return this;
    }

    public EnvironmentBuilder withEnvironmentType(EnvironmentType environmentType) {
      environment.setEnvironmentType(environmentType);
      return builder;
    }

    public EnvironmentBuilder withOwnerId(UUID ownerId) {
      environment.setOwnerId(ownerId);
      return builder;
    }

    public EnvironmentBuilder withShortName(String shortName) {
      environment.setShortName(shortName);
      return builder;
    }

    public EnvironmentBuilder withName(String name) {
      environment.setName(name);
      return builder;
    }

    public EnvironmentBuilder withResourceGroup(UUID resourceGroupId) {
      return withResourceGroups(List.of(resourceGroupId));
    }

    public EnvironmentBuilder withResourceGroups(Collection<UUID> resourceGroups) {
      if (CollectionUtils.isBlank(resourceGroups)) {
        return builder;
      }

      environment.getResourceGroups().addAll(resourceGroups);
      return builder;
    }

    public EnvironmentBuilder withDnsZone(DnsZone dnsZone) {
      return withDnsZones(List.of(dnsZone));
    }

    public EnvironmentBuilder withDnsZones(Collection<DnsZone> dnsZones) {
      environment.addDnsZones(dnsZones);
      return builder;
    }

    public EnvironmentBuilder withAwsCloudAgent(AwsRegion region, String organizationId, String accountId) {
      environment.registerAwsCloudAgent(region, organizationId, accountId);
      return this;
    }

    public EnvironmentBuilder withAzureCloudAgent(AzureRegion region, UUID tenantId, UUID subscriptionId) {
      environment.registerAzureCloudAgent(region, tenantId, subscriptionId);
      return this;
    }

    public EnvironmentBuilder withGcpCloudAgent(GcpRegion region, String organizationId, String projectId) {
      environment.registerGcpCloudAgent(region, organizationId, projectId);
      return this;
    }

    public EnvironmentBuilder withOciCloudAgent(OciRegion region, String tenancyId, String compartmentId) {
      environment.registerOciCloudAgent(region, tenancyId, compartmentId);
      return this;
    }

    /**
     * Tags are name/value pairs that enable you to categorize resources.
     */
    public EnvironmentBuilder withTags(Map<String, String> tags) {
      environment.addTags(tags);
      return this;
    }

    /**
     * Tag is name/value pairs that enable you to categorize resources.
     */
    public EnvironmentBuilder withTag(String key, String value) {
      return withTags(Map.of(key, value));
    }

    public EnvironmentAggregate build() {
      Collection<String> errors = environment.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
                "Environment validation failed. Errors: %s",
                Arrays.toString(errors.toArray())));
      }

      return environment;
    }
  }
}
