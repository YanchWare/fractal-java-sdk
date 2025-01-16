package com.yanchware.fractal.sdk.domain.blueprint.iaas;

import com.yanchware.fractal.sdk.domain.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLettersNumbersUnderscoresDashesAndPeriods;

@Getter
@Setter(AccessLevel.PRIVATE)
public class DnsZone implements Validatable {
  public final static String DNS_ZONES_PARAM_KEY = "dnsZones";

  private final static String NAME_NOT_VALID = "[DnsZone Validation] The name must contain no more than 253 " +
    "characters, excluding a trailing period and must be between 2 and 34 labels. Each label must only contain " +
    "letters, numbers, underscores, and/or dashes. Each label should be separated from other labels by a period";

  private String name;
  private boolean isPrivate;
  private Map<String, Collection<DnsRecord>> records;
  private Map<String, Object> parameters;

  public static DnsZoneBuilder builder() {
    return new DnsZoneBuilder();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (StringUtils.isBlank(name)) {
      errors.add(NAME_NOT_VALID);
    }

    if (StringUtils.isNotBlank(name)) {
      var nameWithoutTrailingPeriod = StringUtils.stripEnd(name, ".");

      var hasValidCharacters = isValidLettersNumbersUnderscoresDashesAndPeriods(name);

      if (!hasValidCharacters || nameWithoutTrailingPeriod.length() > 253) {
        errors.add(NAME_NOT_VALID);
      }
    }

    return errors;
  }

  public static class DnsZoneBuilder {
    private final DnsZone dnsZone;
    private final DnsZoneBuilder builder;

    public DnsZoneBuilder() {
      this.dnsZone = new DnsZone();
      this.builder = this;
    }

    public DnsZoneBuilder withName(String name) {
      dnsZone.setName(name);
      return builder;
    }

    public DnsZoneBuilder isPrivate(boolean isPrivate) {
      dnsZone.setPrivate(isPrivate);
      return builder;
    }

    public DnsZoneBuilder withRecord(String componentId, DnsRecord dnsRecord) {
      if (dnsZone.getRecords() == null) {
        dnsZone.setRecords(new HashMap<>());
      }

      if (!dnsZone.getRecords().containsKey(componentId)) {
        dnsZone.getRecords().put(componentId, new ArrayList<>());
      }

      dnsZone.getRecords().get(componentId).add(dnsRecord);

      return builder;
    }

    public DnsZoneBuilder withRecord(String componentId, List<DnsRecord> dnsRecords) {
      dnsRecords.forEach(dnsRecord -> withRecord(componentId, dnsRecord));

      return builder;
    }

    public DnsZoneBuilder withRecords(Map<? extends String, ? extends Collection<DnsRecord>> dnsRecordsMap) {
      if (dnsRecordsMap == null || dnsRecordsMap.isEmpty()) {
        return builder;
      }

      dnsRecordsMap.forEach((key, value) -> {
        for (var dnsRecord : value) {
          withRecord(key, dnsRecord);
        }
      });

      return builder;
    }

    public DnsZoneBuilder withParameters(Map<String, Object> parameters) {
      if (parameters.isEmpty()) {
        return builder;
      }

      if (dnsZone.getParameters() == null) {
        dnsZone.setParameters(new HashMap<>());
      }

      parameters.forEach((key, value) -> dnsZone.getParameters().put(key, value));

      return builder;
    }

    public DnsZoneBuilder withParameter(String key, Object value) {
      return withParameters(Map.of(key, value));
    }

    public DnsZone build() {
      var errors = dnsZone.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format("DnsZone validation failed. Errors: %s",
          Arrays.toString(errors.toArray())));
      }

      return dnsZone;
    }
  }
}
