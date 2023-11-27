package com.yanchware.fractal.sdk.aggregates;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsZone;
import com.yanchware.fractal.sdk.utils.SerializationUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static com.yanchware.fractal.sdk.domain.entities.environment.DnsRecordConstants.DNS_ZONES_PARAM_KEY;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
public class Environment implements Validatable {
  private final static String OWNER_ID_IS_NULL = "Environment OwnerId has not been defined and it is required";
  private final static String SHORT_NAME_IS_NULL = "Environment ShortName has not been defined and it is required";
  private final static String IS_PRIVATE_PARAM_KEY = "isPrivate";
  private final static String RECORDS_PARAM_KEY = "records";
  private final static String PARAMETERS_PARAM_KEY = "parameters";

  private EnvironmentType environmentType;
  private String ownerId;
  private String shortName;
  private Map<String, Object> parameters;
  

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

    public EnvironmentBuilder withOwnerId(String ownerId) {
      environment.setOwnerId(ownerId);
      return builder;
    }

    public EnvironmentBuilder withShortName(String shortName) {
      environment.setShortName(shortName);
      return builder;
    }

    public EnvironmentBuilder withDnsZone(DnsZone dnsZone) {
      return withDnsZones(List.of(dnsZone));
    }

    public EnvironmentBuilder withDnsZones(Collection<DnsZone> dnsZones) {
      if (environment.parameters == null) {
        environment.parameters = new HashMap<>();
      }
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

    if (isBlank(ownerId)) {
      errors.add(OWNER_ID_IS_NULL);
    }
    
    return errors;
  }
}
