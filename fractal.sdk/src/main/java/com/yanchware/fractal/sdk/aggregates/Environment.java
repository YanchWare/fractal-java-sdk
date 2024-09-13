package com.yanchware.fractal.sdk.aggregates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsZone;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import com.yanchware.fractal.sdk.utils.SerializationUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static com.yanchware.fractal.sdk.domain.entities.environment.DnsRecordConstants.DNS_ZONES_PARAM_KEY;
import static com.yanchware.fractal.sdk.domain.entities.environment.EnvironmentConstants.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
public class Environment implements Validatable {
  private final static String OWNER_ID_IS_NULL = "Environment OwnerId has not been defined and it is required";
  private final static String SHORT_NAME_IS_NULL = "Environment ShortName has not been defined and it is required";
  private final static String RESOURCE_GROUPS_IS_EMPTY = "Environment ResourceGroups has not been defined and it is required";
  private final static String IS_PRIVATE_PARAM_KEY = "isPrivate";

  private EnvironmentType environmentType;
  private UUID ownerId;
  private String shortName;
  private String name;
  private Collection<UUID> resourceGroups;
  private Map<String, Object> parameters;
  private Map<ProviderType, CloudAgent> cloudAgentByProviderType;

  public Environment() {
    resourceGroups = new ArrayList<>();
    parameters = new HashMap<>();
    cloudAgentByProviderType = new HashMap<>();
  }


  public static EnvironmentBuilder builder() {
    return new EnvironmentBuilder();
  }

  public static class EnvironmentBuilder {
    private final Environment environment;
    private final EnvironmentBuilder builder;

    public EnvironmentBuilder() {
      environment = createComponent();
      builder = getBuilder();
    }

    protected Environment createComponent() {
      return new Environment();
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
      try {
        environment.parameters.put(DNS_ZONES_PARAM_KEY,
            SerializationUtils.deserialize(
                SerializationUtils.serialize(dnsZones),
                DnsZone[].class));

      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }

      return builder;
    }

    public EnvironmentBuilder withCloudAgent(CloudAgent cloudAgent) {
      return withCloudAgents(List.of(cloudAgent));
    }

    public EnvironmentBuilder withCloudAgents(Collection<CloudAgent> cloudAgents) {
      cloudAgents.forEach(x -> {
        x.injectIntoEnvironmentParameters(environment.parameters);
        environment.cloudAgentByProviderType.put(x.getProvider(), x);
      });
      return this;
    }

    /**
     * Tags are name/value pairs that enable you to categorize resources.
     */
    public EnvironmentBuilder withTags(Map<String, String> tags) {
      Map<String, String> existingTags = (Map<String, String>) environment.parameters.get(TAGS_PARAM_KEY);
      if (existingTags == null) {
        existingTags = new HashMap<>();
        environment.parameters.put(TAGS_PARAM_KEY, existingTags);
      }

      existingTags.putAll(tags);


      return builder;
    }

    /**
     * Tag is name/value pairs that enable you to categorize resources.
     */
    public EnvironmentBuilder withTag(String key, String value) {
      return withTags(Map.of(key, value));
    }

    public Environment build() {
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

    if (CollectionUtils.isBlank(resourceGroups)) {
      errors.add(RESOURCE_GROUPS_IS_EMPTY);
    }

    if (isBlank(name)) {
      name = shortName;
    }

    if (ownerId == null || ownerId.equals(new UUID(0L, 0L))) {
      errors.add(OWNER_ID_IS_NULL);
    }

    return errors;
  }
}
