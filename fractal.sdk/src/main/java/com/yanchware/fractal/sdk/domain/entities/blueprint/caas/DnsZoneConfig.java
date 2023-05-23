package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsRecord;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLettersNumbersUnderscoresDashesAndPeriods;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class DnsZoneConfig implements Validatable {
  private final static String NAME_NOT_VALID = "[DnsRecords Validation] The dnsZoneName must contain no more than 253 characters, excluding a trailing period and must be between 2 and 34 labels. Each label must only contain letters, numbers, underscores, and/or dashes. Each label should be separated from other labels by a period";

  private String dnsZoneName;
  private Collection<DnsRecord> records;

  public static DnsZoneConfigBuilder builder() {
    return new DnsZoneConfigBuilder();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (StringUtils.isBlank(dnsZoneName)) {
      errors.add(NAME_NOT_VALID);
    }

    if (StringUtils.isNotBlank(dnsZoneName)) {
      var nameWithoutTrailingPeriod = StringUtils.stripEnd(dnsZoneName, ".");

      var hasValidCharacters = isValidLettersNumbersUnderscoresDashesAndPeriods(dnsZoneName);

      if (!hasValidCharacters || nameWithoutTrailingPeriod.length() > 253) {
        errors.add(NAME_NOT_VALID);
      }
    }

    return errors;
  }

  public static class DnsZoneConfigBuilder {
    private final DnsZoneConfig config;
    private final DnsZoneConfigBuilder builder;

    public DnsZoneConfigBuilder() {
      this.config = new DnsZoneConfig();
      this.builder = this;
    }

    public DnsZoneConfigBuilder withDnsZoneName(String dnsZoneName) {
      config.setDnsZoneName(dnsZoneName);
      return builder;
    }

    public DnsZoneConfigBuilder withRecord(DnsRecord dnsRecord) {
      return withRecords(List.of(dnsRecord));
    }

    public DnsZoneConfigBuilder withRecords(Collection<? extends DnsRecord> records) {
      if (isBlank(records)) {
        return builder;
      }

      if (config.getRecords() == null) {
        config.setRecords(new ArrayList<>());
      }

      config.getRecords().addAll(records);
      return builder;
    }

    public DnsZoneConfig build() {
      var errors = config.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format("DnsRecords validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return config;
    }
  }
}
