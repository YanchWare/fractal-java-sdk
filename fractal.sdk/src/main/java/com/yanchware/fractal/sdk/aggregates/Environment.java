package com.yanchware.fractal.sdk.aggregates;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsZone;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
public class Environment implements Validatable {
    private final static String ID_IS_NULL = "Environment id has not been defined and it is required";
    private final static String DNS_ZONES_PARAM_KEY = "dnsZones";
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

        public EnvironmentBuilder withDnsZones(List<DnsZone> dnsZones) {
            if (environment.parameters == null) {
                environment.parameters = new HashMap<>();
            }
            for(var dnsZone: dnsZones) {
                if (!environment.parameters.containsKey(DNS_ZONES_PARAM_KEY)) {
                    environment.parameters.put(DNS_ZONES_PARAM_KEY, new HashMap<String, Object>());
                }

                var dnsZonesMap = (Map<String, Object>)environment.parameters.get(DNS_ZONES_PARAM_KEY);
                if (!dnsZonesMap.containsKey(dnsZone)) {
                    dnsZonesMap.put(dnsZone.name(), new HashMap<String, Object>());
                }

                //TODO: finalize
                var dnsZoneMap = (HashMap<String, Object>)dnsZonesMap.get(dnsZone.name());
                dnsZoneMap.put(IS_PRIVATE_PARAM_KEY, dnsZone.isPrivate());
                dnsZoneMap.put(RECORDS_PARAM_KEY, dnsZone.records());
                dnsZoneMap.put(PARAMETERS_PARAM_KEY, dnsZone.parameters());

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
