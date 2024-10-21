package com.yanchware.fractal.sdk.domain.environment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.domain.Validatable;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsZone;
import com.yanchware.fractal.sdk.domain.environment.aws.AwsCloudAgent;
import com.yanchware.fractal.sdk.domain.environment.azure.AzureCloudAgent;
import com.yanchware.fractal.sdk.domain.environment.gcp.GcpCloudAgent;
import com.yanchware.fractal.sdk.domain.environment.oci.OciCloudAgent;
import com.yanchware.fractal.sdk.domain.environment.service.dtos.EnvironmentResponse;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci.OciRegion;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import com.yanchware.fractal.sdk.utils.SerializationUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsZone.DNS_ZONES_PARAM_KEY;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter
public abstract class BaseEnvironment implements Environment, Validatable {
  private static final String TAGS_PARAM_KEY = "tags";
  private static final String CLOUD_AGENTS_PARAM_KEY = "agents";

  private final static String OWNER_ID_IS_NULL = "Environment OwnerId has not been defined and it is required";
  private final static String SHORT_NAME_IS_NULL = "Environment ShortName has not been defined and it is required";
  private final static String RESOURCE_GROUPS_IS_EMPTY = "Environment ResourceGroups has not been defined and it is required";

  private final Map<String, Object> parameters;
  
  private EnvironmentIdValue id;
  private String name;
  private Collection<UUID> resourceGroups;
  private Map<String, String> tags;
  private Map<ProviderType, CloudAgentEntity> cloudAgentByProviderType;


  @Override
  public EnvironmentIdValue getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Collection<UUID> getResourceGroups() {
    return resourceGroups;
  }

  @Override
  public Map<String, String> getTags() {
    return tags;
  }

  @Override
  public Map<ProviderType, CloudAgentEntity> getCloudAgentByProviderType() {
    return cloudAgentByProviderType;
  }

  @Override
  public Map<String, Object> getParameters() {
    return parameters;
  }

  protected BaseEnvironment() {
    this.resourceGroups = new ArrayList<>();
    this.parameters = new HashMap<>();
    this.cloudAgentByProviderType = new HashMap<>();
    this.tags = new HashMap<>();
  }

  public void registerAwsCloudAgent(AwsRegion region, String organizationId, String accountId) {
    var agent = new AwsCloudAgent(getId(), region, organizationId, accountId, tags);
    registerCloudAgent(agent);
  }

  public void registerAzureCloudAgent(AzureRegion region, UUID tenantId, UUID subscriptionId) {
    var agent = new AzureCloudAgent(getId(), region, tenantId, subscriptionId, tags);
    registerCloudAgent(agent);
  }

  public void registerGcpCloudAgent(GcpRegion region, String organizationId, String projectId) {
    var agent = new GcpCloudAgent(getId(), region, organizationId, projectId, tags);
    registerCloudAgent(agent);
  }

  public void registerOciCloudAgent(OciRegion region, String tenancyId, String compartmentId) {
    var agent = new OciCloudAgent(getId(), region, tenancyId, compartmentId, tags);
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
  public EnvironmentDto toDto() {
    return new EnvironmentDto(getId().toDto(), parameters);
  }

  /**
   * Compares this environment response with another environment object.
   *
   * @param existingEnvironmentResponse the environment response to compare with current entity
   * @return true if the environments are equal, false otherwise
   */
  @Override
  public boolean doesNotNeedUpdate(EnvironmentResponse existingEnvironmentResponse) {
    if (existingEnvironmentResponse == null) return false;

    var environmentIdInResponse = existingEnvironmentResponse.id();
    return Objects.equals(environmentIdInResponse.type().toString(), id.type().toString()) &&
        Objects.equals(environmentIdInResponse.ownerId(), id.ownerId()) &&
        Objects.equals(environmentIdInResponse.shortName(), id.shortName()) &&
        Objects.equals(existingEnvironmentResponse.name(), name) &&
        Objects.equals(existingEnvironmentResponse.resourceGroups(), resourceGroups) &&
        mapsEqual(existingEnvironmentResponse.parameters(), parameters);
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

  public static abstract class Builder<T extends BaseEnvironment, B extends Builder<T, B>>  {
    protected T environment; // Protected to allow access in subclasses
    protected B builder;

    public Builder() {
      environment = createEnvironment();
      builder = getBuilder();
    }
    

    protected abstract T createEnvironment();
    protected abstract B getBuilder();


    public B withId(EnvironmentIdValue environmentId) {
      environment.setId(environmentId);
      return builder;
    }

    public B withName(String name) {
      environment.setName(name);
      return builder;
    }

    public B withResourceGroup(UUID resourceGroupId) {
      return withResourceGroups(List.of(resourceGroupId));
    }

    public B withResourceGroups(Collection<UUID> resourceGroups) {
      if (CollectionUtils.isBlank(resourceGroups)) {
        return builder;
      }

      environment.getResourceGroups().addAll(resourceGroups);
      return builder;
    }

    public B withDnsZone(DnsZone dnsZone) {
      return withDnsZones(List.of(dnsZone));
    }

    public B withDnsZones(Collection<DnsZone> dnsZones) {
      environment.addDnsZones(dnsZones);
      return builder;
    }

    public B withAwsCloudAgent(AwsRegion region, String organizationId, String accountId) {
      environment.registerAwsCloudAgent(region, organizationId, accountId);
      return builder;
    }

    public B withAzureCloudAgent(AzureRegion region, UUID tenantId, UUID subscriptionId) {
      environment.registerAzureCloudAgent(region, tenantId, subscriptionId);
      return builder;
    }

    public B withGcpCloudAgent(GcpRegion region, String organizationId, String projectId) {
      environment.registerGcpCloudAgent(region, organizationId, projectId);
      return builder;
    }

    public B withOciCloudAgent(OciRegion region, String tenancyId, String compartmentId) {
      environment.registerOciCloudAgent(region, tenancyId, compartmentId);
      return builder;
    }

    /**
     * Tags are name/value pairs that enable you to categorize resources.
     */
    public B withTags(Map<String, String> tags) {
      environment.addTags(tags);
      return builder;
    }

    /**
     * Tag is name/value pairs that enable you to categorize resources.
     */
    public B withTag(String key, String value) {
      return withTags(Map.of(key, value));
    }

    public T build() {
      Collection<String> errors = environment.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
            "Environment validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return environment;
    }
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
}
