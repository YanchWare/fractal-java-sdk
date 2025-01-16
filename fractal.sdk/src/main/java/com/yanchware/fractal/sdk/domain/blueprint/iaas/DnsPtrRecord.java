package com.yanchware.fractal.sdk.domain.blueprint.iaas;

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
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLettersNumbersUnderscoresDashesAndPeriodsAndPeriodIsNotRequired;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class DnsPtrRecord extends DnsRecord {
  public static final String PTR_DNS_RECORD_TYPE = "PTR";

  private final static String DOMAIN_NAME_NOT_VALID_PATTERN = "The domainName value ['%s'], concatenated with its " +
    "zone name, must contain no more than 253 characters, excluding a trailing period. It must be between 2 and 34 " +
    "labels. Each label must only contain letters, numbers, underscores, and/or dashes. Each label should be " +
    "separated from other labels by a period. Each label must contain between 1 and 63 characters.";
  private final static String DOMAIN_NAME_LABEL_NOT_VALID_PATTERN = "The domainName ['%s'] label must contain between" +
    " 1 and 63 characters";

  private List<String> domainNames;

  public static DnsPtrRecordBuilder builder() {
    return new DnsPtrRecordBuilder();
  }

  @Override
  public Collection<String> validate() {
    var errors = super.validate();

    if (!isBlank(domainNames)) {
      for (var domainName : domainNames) {
        if (StringUtils.isBlank(domainName)) {
          continue;
        }

        var hasValidCharacters = isValidLettersNumbersUnderscoresDashesAndPeriodsAndPeriodIsNotRequired(domainName);

        if (domainName.contains(".")) {
          Arrays.stream(domainName.split("\\."))
            .filter(label -> label.length() > 63)
            .map(label -> String.format(DOMAIN_NAME_LABEL_NOT_VALID_PATTERN, label))
            .forEach(errors::add);
        }

        var withoutTrailingPeriod = StringUtils.stripEnd(domainName, ".");

        if (!hasValidCharacters || withoutTrailingPeriod.length() > 253) {
          errors.add(String.format(DOMAIN_NAME_NOT_VALID_PATTERN, domainName));
        }
      }
    }

    return errors;
  }

  public static class DnsPtrRecordBuilder extends DnsRecord.Builder<DnsPtrRecord, DnsPtrRecordBuilder> {
    @Override
    protected DnsPtrRecord createRecord() {
      return new DnsPtrRecord();
    }

    @Override
    protected DnsPtrRecordBuilder getBuilder() {
      return this;
    }

    public DnsPtrRecordBuilder withDomainName(String domainName) {
      if (StringUtils.isBlank(domainName)) {
        return builder;
      }

      return withDomainNames(List.of(domainName));
    }

    public DnsPtrRecordBuilder withDomainNames(Collection<? extends String> domainNames) {
      if (isBlank(domainNames)) {
        return builder;
      }

      if (record.getDomainNames() == null) {
        record.setDomainNames(new ArrayList<>());
      }

      record.getDomainNames().addAll(domainNames);
      return builder;
    }

    @Override
    public DnsPtrRecord build() {
      return super.build();
    }
  }
}