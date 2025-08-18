package com.yanchware.fractal.sdk.domain.environment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.domain.Validatable;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsZone;
import com.yanchware.fractal.sdk.domain.environment.aws.AwsCloudAgent;
import com.yanchware.fractal.sdk.domain.environment.azure.AzureCloudAgent;
import com.yanchware.fractal.sdk.domain.environment.gcp.GcpCloudAgent;
import com.yanchware.fractal.sdk.domain.environment.hetzner.HetznerCloudAgent;
import com.yanchware.fractal.sdk.domain.environment.oci.OciCloudAgent;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.aws.AwsRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp.GcpRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.hetzner.HetznerRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci.OciRegion;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import com.yanchware.fractal.sdk.utils.SerializationUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsZone.DNS_ZONES_PARAM_KEY;

@Getter
@Setter
public abstract class BaseEnvironment implements Environment, Validatable {
  private static final String CLOUD_AGENTS_PARAM_KEY = "agents";
  private static final String TAGS_PARAM_KEY = "tags";
  protected final static String SHORT_NAME_IS_NULL = "Environment ShortName has not been defined and it is required";
  private final static String RESOURCE_GROUPS_IS_EMPTY = "Environment ResourceGroups has not been defined and it is required";
  private final static String SECRET_IS_NULL = "[Secret Validation] The secret cannot be null";
  private final static String DEFAULT_CI_CD_PROFILE_IS_NULL = "[CI/CD Profile Validation] The default CI/CD profile cannot be null";
  private final static String DEFAULT_CI_CD_PROFILE_IS_MISSING = "[CI/CD Profile Validation] A default CI/CD profile must be set if additional CI/CD profiles are defined";
  private final static String CI_CD_PROFILE_SHORT_NAME_NOT_UNIQUE = "[CI/CD Profile Validation] CI/CD profile short names must be unique, including the default profile";
  private final static String SECRET_SHORT_NAMES_NOT_UNIQUE = "[Secret Validation] Secret short names must be unique";

  private final Map<String, Object> parameters;
  private String name;
  private Collection<UUID> resourceGroups;
  private Map<String, String> tags;
  private Collection<Secret> secrets;
  private CiCdProfile defaultCiCdProfile;
  private Collection<CiCdProfile> ciCdProfiles;
  private final Map<ProviderType, CloudAgentEntity> cloudAgentByProviderType;

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
  public Map<String, Object> getParameters() {
    return parameters;
  }

  protected BaseEnvironment() {
    this.resourceGroups = new ArrayList<>();
    this.parameters = new HashMap<>();
    this.tags = new HashMap<>();
    this.secrets = new ArrayList<>();
    this.ciCdProfiles = new ArrayList<>();
    this.cloudAgentByProviderType = new HashMap<>();
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

  protected void registerAwsCloudAgent(AwsRegion region, String organizationId, String accountId) {
    var agent = new AwsCloudAgent(getId(), region, organizationId, accountId, getTags());
    registerCloudAgent(agent);
  }

  protected void registerAzureCloudAgent(AzureRegion region, UUID tenantId, UUID subscriptionId) {
    var agent = new AzureCloudAgent(getId(), region, tenantId, subscriptionId, getTags());
    registerCloudAgent(agent);
  }

  protected void registerGcpCloudAgent(GcpRegion region, String organizationId, String projectId) {
    var agent = new GcpCloudAgent(getId(), region, organizationId, projectId, getTags());
    registerCloudAgent(agent);
  }

  protected void registerOciCloudAgent(OciRegion region, String tenancyId, String compartmentId) {
    var agent = new OciCloudAgent(getId(), region, tenancyId, compartmentId, getTags());
    registerCloudAgent(agent);
  }

  protected void registerHetznerCloudAgent(HetznerRegion region, String projectId) {
    var agent = new HetznerCloudAgent(getId(), region, projectId, getTags());
    registerCloudAgent(agent);
  }

  private void registerCloudAgent(CloudAgentEntity cloudAgent){
    if (cloudAgentByProviderType.containsKey(cloudAgent.getProvider()))
    {
      if (!cloudAgent.equals(cloudAgentByProviderType.get(cloudAgent.getProvider()))) {
        throw new IllegalArgumentException(
          String.format("A Cloud agent for Provider %s has already been defined", cloudAgent.getProvider()));
      } else {
        cloudAgentByProviderType.remove(cloudAgent.getProvider());
      }
    }

    cloudAgentByProviderType.put(cloudAgent.getProvider(), cloudAgent);

    Collection<Map<String, Object>> existingAgents = (Collection<Map<String, Object>>) getParameters().get(CLOUD_AGENTS_PARAM_KEY);
    if (existingAgents == null) {
      existingAgents = new ArrayList<>();
      getParameters().put(CLOUD_AGENTS_PARAM_KEY, existingAgents);
    }

    existingAgents.add(cloudAgent.getConfigurationForEnvironmentParameters());
  }

  public static abstract class EnvironmentBuilder<T extends BaseEnvironment, B extends EnvironmentBuilder<T, B>>  {
    protected T environment; // Protected to allow access in subclasses
    protected B builder;

    public EnvironmentBuilder() {
      environment = createEnvironment();
      builder = getBuilder();
    }
    

    protected abstract T createEnvironment();
    protected abstract B getBuilder();

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

    /**
     * <pre>
     * Adds a single secret to the environment. This secret can be referenced by its name in 
     * custom workload components that require access to sensitive information.</pre>
     *
     * @param secret The secret to add.
     *
     * @return The builder instance.
     */
    public B withSecret(Secret secret) {
      if (secret == null) {
        throw new IllegalArgumentException(SECRET_IS_NULL);
      }
      
      return withSecrets(List.of(secret));
    }

    /**
     * <pre>
     * Adds a collection of secrets to the environment. These secrets can be referenced by their 
     * names in custom workload components that require access to sensitive information.</pre>
     *
     * @param secrets The collection of secrets to add.
     *
     * @return The builder instance.
     */
    public B withSecrets(Collection<Secret> secrets) {
      if (CollectionUtils.isBlank(secrets)) {
        return builder;
      }

      environment.getSecrets().addAll(secrets);
      return builder;
    }

    /**
     * Adds a default CI/CD profile to the environment.
     *
     * @param ciCdProfile The CI/CD profile to add.
     * @return The builder instance.
     */
    public B withDefaultCiCdProfile(CiCdProfile ciCdProfile) {
      if (ciCdProfile == null) {
        throw new IllegalArgumentException(DEFAULT_CI_CD_PROFILE_IS_NULL);
      }
      
      environment.setDefaultCiCdProfile(ciCdProfile);
      return builder;
    }

    /**
     * Adds a single CI/CD profile to the environment.
     *
     * @param ciCdProfile The CI/CD profile to add.
     * @return The builder instance.
     */
    public B withCiCdProfile(CiCdProfile ciCdProfile) {
      return withCiCdProfiles(List.of(ciCdProfile));
    }

    /**
     * Adds a collection of CI/CD profiles to the environment.
     *
     * @param ciCdProfiles The collection of CI/CD profiles to add.
     * @return The builder instance.
     */
    public B withCiCdProfiles(Collection<CiCdProfile> ciCdProfiles) {
      if (CollectionUtils.isBlank(ciCdProfiles)) {
        return builder;
      }

      environment.getCiCdProfiles().addAll(ciCdProfiles);
      return builder;
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

    if (CollectionUtils.isBlank(resourceGroups)) {
      errors.add(RESOURCE_GROUPS_IS_EMPTY);
    }

    if (!CollectionUtils.isBlank(secrets)) {
      if (secrets.size() != new HashSet<>(secrets.stream().map(Secret::shortName).toList()).size()) {
        errors.add(SECRET_SHORT_NAMES_NOT_UNIQUE);
      }
    }

    if (!CollectionUtils.isBlank(ciCdProfiles)) {
      if (defaultCiCdProfile == null) {
        errors.add(DEFAULT_CI_CD_PROFILE_IS_MISSING);
      } else {
        // Check for duplicate profile names, including the default profile
        Set<String> profileNames = new HashSet<>();
        profileNames.add(defaultCiCdProfile.shortName());
        profileNames.addAll(ciCdProfiles.stream().map(CiCdProfile::shortName).toList());
        if (profileNames.size() != ciCdProfiles.size() + 1) { // +1 for the default profile
          errors.add(CI_CD_PROFILE_SHORT_NAME_NOT_UNIQUE);
        }
      }
    }

    return errors;
  }
}
