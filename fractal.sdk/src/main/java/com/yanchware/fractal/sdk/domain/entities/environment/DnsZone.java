package com.yanchware.fractal.sdk.domain.entities.environment;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLettersNumbersUnderscoresDashesAndPeriods;

@Getter
@Setter(AccessLevel.PRIVATE)
public class DnsZone implements Validatable {
  private final static String NAME_NOT_VALID = "[DnsZone Validation] The name must contain no more than 253 characters, excluding a trailing period and must be between 2 and 34 labels. Each label must only contain letters, numbers, underscores, and/or dashes. Each label should be separated from other labels by a period";

  private String name;
  private boolean isPrivate;
  private Collection<DnsRecord> records;
  private Map<String, Object> parameters;

  public static DnsZoneBuilder builder() {
    return new DnsZoneBuilder();
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

    public DnsZoneBuilder withRecord(DnsRecord dnsRecord) {
      return withRecords(List.of(dnsRecord));
    }

    public DnsZoneBuilder withRecords(Collection<? extends DnsRecord> dnsRecords) {
      if (isBlank(dnsRecords)) {
        return builder;
      }

      if (dnsZone.getRecords() == null) {
        dnsZone.setRecords(new ArrayList<>());
      }

      dnsZone.getRecords().addAll(dnsRecords);
      return builder;
    }

    public DnsZoneBuilder withParameters(Map<String, Object> parameters) {
      dnsZone.setParameters(parameters);
      return builder;
    }

    public DnsZoneBuilder withParameter(String key, Object value) {
      if (dnsZone.getParameters() == null) {
        withParameters(new HashMap<>());
      }

      dnsZone.getParameters().put(key, value);
      return builder;
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
}
