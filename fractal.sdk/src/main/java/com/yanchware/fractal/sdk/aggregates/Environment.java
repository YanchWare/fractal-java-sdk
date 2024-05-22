package com.yanchware.fractal.sdk.aggregates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsZone;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
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

  public Environment() {
    resourceGroups = new ArrayList<>();
    parameters = new HashMap<>();
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
        if (!environment.parameters.containsKey(DNS_ZONES_PARAM_KEY)) {
          environment.parameters.put(DNS_ZONES_PARAM_KEY, 
              SerializationUtils.deserialize(
                  SerializationUtils.serialize(dnsZones), 
                  DnsZone[].class));
        }

      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      
      return builder;
    }

    public EnvironmentBuilder withRegion(AzureRegion region) {
      if (environment.parameters.containsKey(REGION_PARAM_KEY)) {
        environment.parameters.replace(REGION_PARAM_KEY, region);
      } else {
        environment.parameters.put(REGION_PARAM_KEY, region);
      }

      return builder;
    }

    public EnvironmentBuilder withTenantId(UUID tenantId) {
      if (environment.parameters.containsKey(TENANT_ID_PARAM_KEY)) {
        environment.parameters.replace(TENANT_ID_PARAM_KEY, tenantId);
      } else {
        environment.parameters.put(TENANT_ID_PARAM_KEY, tenantId);
      }

      return builder;
    }

    public EnvironmentBuilder withSubscriptionId(UUID subscriptionId) {
      if (environment.parameters.containsKey(SUBSCRIPTION_ID_PARAM_KEY)) {
        environment.parameters.replace(SUBSCRIPTION_ID_PARAM_KEY, subscriptionId);
      } else {
        environment.parameters.put(SUBSCRIPTION_ID_PARAM_KEY, subscriptionId);
      }

      return builder;
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
    
    if(isBlank(name)) {
      name = shortName;
    }

    if (ownerId == null || ownerId.equals(new UUID(0L, 0L))) {
      errors.add(OWNER_ID_IS_NULL);
    }
    
    return errors;
  }
}
