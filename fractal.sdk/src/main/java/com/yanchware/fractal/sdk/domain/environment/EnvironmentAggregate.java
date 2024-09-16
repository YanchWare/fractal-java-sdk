package com.yanchware.fractal.sdk.domain.environment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.Validatable;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsZone;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci.OciRegion;
import com.yanchware.fractal.sdk.domain.environment.aws.AwsCloudAgent;
import com.yanchware.fractal.sdk.domain.environment.azure.AzureCloudAgent;
import com.yanchware.fractal.sdk.domain.environment.gcp.GcpCloudAgent;
import com.yanchware.fractal.sdk.domain.environment.oci.OciCloudAgent;
import com.yanchware.fractal.sdk.domain.environment.service.EnvironmentService;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.EnvironmentResponse;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
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

import static com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsZone.DNS_ZONES_PARAM_KEY;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Setter(AccessLevel.PROTECTED)
public class EnvironmentAggregate implements Validatable {
  public static final String REGION_PARAM_KEY = "region";
  private static final String TAGS_PARAM_KEY = "tags";
  private static final String CLOUD_AGENTS_PARAM_KEY = "agents";

  private final static String OWNER_ID_IS_NULL = "Environment OwnerId has not been defined and it is required";
  private final static String SHORT_NAME_IS_NULL = "Environment ShortName has not been defined and it is required";
  private final static String RESOURCE_GROUPS_IS_EMPTY = "Environment ResourceGroups has not been defined and it is required";
  private final EnvironmentService service;
  private final Map<String, Object> parameters;

  @Getter
  private EnvironmentIdValue id;
  @Getter
  private String name;
  @Getter
  private Collection<UUID> resourceGroups;
  @Getter
  private Map<String, String> tags;
  @Getter
  private Map<ProviderType, CloudAgentEntity> cloudAgentByProviderType;

  public EnvironmentAggregate(
          HttpClient client,
          SdkConfiguration sdkConfiguration,
          RetryRegistry retryRegistry)
  {
    this.resourceGroups = new ArrayList<>();
    this.parameters = new HashMap<>();
    this.cloudAgentByProviderType = new HashMap<>();
    this.service = new EnvironmentService(client, sdkConfiguration, retryRegistry);
    this.tags = new HashMap<>();
  }

  public void registerAwsCloudAgent(AwsRegion region, String organizationId, String accountId) {
    var agent = new AwsCloudAgent(getId(), service, region, organizationId, accountId, tags);
    registerCloudAgent(agent);
  }

  public void registerAzureCloudAgent(AzureRegion region, UUID tenantId, UUID subscriptionId) {
    var agent = new AzureCloudAgent(getId(), service, region, tenantId, subscriptionId, tags);
    registerCloudAgent(agent);
  }

  public void registerGcpCloudAgent(GcpRegion region, String organizationId, String projectId) {
    var agent = new GcpCloudAgent(getId(), service, region, organizationId, projectId, tags);
    registerCloudAgent(agent);
  }

  public void registerOciCloudAgent(OciRegion region, String tenancyId, String compartmentId) {
    var agent = new OciCloudAgent(getId(), service, region, tenancyId, compartmentId, tags);
    registerCloudAgent(agent);
  }

  public void addTags(Map<String, String> tags) {
    Map<String, String> existingTags = (Map<String, String>) parameters.get(TAGS_PARAM_KEY);
    if (existingTags == null) {
      existingTags = new HashMap<>();
      parameters.put(TAGS_PARAM_KEY, existingTags);
    }

    existingTags.putAll(tags);
    this.tags.putAll(tags);
  }

  public void addDnsZones(Collection<DnsZone> dnsZones){
    try {
      parameters.put(DNS_ZONES_PARAM_KEY,
              SerializationUtils.deserialize(
                      SerializationUtils.serialize(dnsZones),
                      DnsZone[].class));

    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private void registerCloudAgent(CloudAgentEntity cloudAgent){
    if (cloudAgentByProviderType.containsKey(cloudAgent.getProvider())) {
      throw new IllegalArgumentException(
              String.format("A Cloud agent for Provider %s has already been defined", cloudAgent.getProvider()));
    }

    cloudAgentByProviderType.put(ProviderType.AZURE, cloudAgent);

    Collection<Map<String, Object>> existingAgents = (Collection<Map<String, Object>>) parameters.get(CLOUD_AGENTS_PARAM_KEY);
    if (existingAgents == null) {
      existingAgents = new ArrayList<>();
      parameters.put(CLOUD_AGENTS_PARAM_KEY, existingAgents);
    }

    existingAgents.add(cloudAgent.getConfigurationForEnvironmentParameters());
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

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

    if (CollectionUtils.isBlank(resourceGroups)) {
      errors.add(RESOURCE_GROUPS_IS_EMPTY);
    }

    if (isBlank(name)) {
      name = id.shortName();
    }

    if (id.ownerId() == null || id.ownerId().equals(new UUID(0L, 0L))) {
      errors.add(OWNER_ID_IS_NULL);
    }

    return errors;
  }

  public EnvironmentResponse createOrUpdate() throws InstantiatorException {
    var environmentId = getId();
    log.info("Starting createOrUpdateEnvironment for Environment [id: '{}']", environmentId);

    log.info("Fetching environment details [id: '{}']", environmentId);
    var existingEnvironment = service.fetch(environmentId);
    if (existingEnvironment != null) {
      if (doesNotNeedUpdate(existingEnvironment)) {
        log.info("No changes detected for Environment [id: '{}']. Update not required.", environmentId);
        return existingEnvironment;
      } else {
        log.info("Environment [id: '{}'] exists, updating ...", environmentId);
        return service.update(
                environmentId,
                name,
                resourceGroups,
                parameters);
      }
    }

    log.info("Environment does not exist, creating new environment [id: '{}']", environmentId);
    return service.create(
            environmentId,
            name,
            resourceGroups,
            parameters);
  }

  /**
   * Compares this environment response with another environment object.
   *
   * @param existingEnvironmentResponse the environment response to compare with current entity
   * @return true if the environments are equal, false otherwise
   */
  private boolean doesNotNeedUpdate(EnvironmentResponse existingEnvironmentResponse) {
    if (existingEnvironmentResponse == null) return false;

    var environmentIdInResponse = existingEnvironmentResponse.id();
    return Objects.equals(environmentIdInResponse.type().toString(), id.type().toString()) &&
            Objects.equals(environmentIdInResponse.ownerId(), id.ownerId()) &&
            Objects.equals(environmentIdInResponse.shortName(), id.shortName()) &&
            Objects.equals(existingEnvironmentResponse.name(), name) &&
            Objects.equals(existingEnvironmentResponse.resourceGroups(), resourceGroups) &&
            mapsEqual(parameters, parameters);
  }

  public void initializeAgents() {
    var agents = cloudAgentByProviderType.values();
    try (ExecutorService executor = Executors.newFixedThreadPool(agents.size())) {
      for (var agent : agents) {
        executor.execute(() -> {
            try {
                agent.initialize();
            } catch (InstantiatorException e) {
                throw new RuntimeException(e);
            }
        });
      }
    }
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

  public EnvironmentDto toDto() {
      return new EnvironmentDto(getId().toDto(), parameters);
  }
}
