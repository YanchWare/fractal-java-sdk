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
  private final static String ID_IS_NULL = "Environment id has not been defined and it is required";
  private final static String IS_PRIVATE_PARAM_KEY = "isPrivate";
  private final static String RECORDS_PARAM_KEY = "records";
  private final static String PARAMETERS_PARAM_KEY = "parameters";

  private String id;
  private String displayName;
  private String parentId;
  private String parentType;
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

    public EnvironmentBuilder withId(String id) {
      environment.setId(id);
      return builder;
    }

    public EnvironmentBuilder withDisplayName(String displayName) {
      environment.setDisplayName(displayName);
      return builder;
    }

    public EnvironmentBuilder withParentId(String parentId) {
      environment.setParentId(parentId);
      return builder;
    }

    public EnvironmentBuilder withParentType(String parentType) {
      environment.setParentType(parentType);
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

    if (isBlank(id)) {
      errors.add(ID_IS_NULL);
    }
    return errors;
  }
}
