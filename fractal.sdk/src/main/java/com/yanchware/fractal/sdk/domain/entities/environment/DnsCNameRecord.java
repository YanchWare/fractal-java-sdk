package com.yanchware.fractal.sdk.domain.entities.environment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLettersNumbersUnderscoresDashesAndPeriodsAndPeriodIsNotRequired;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class DnsCNameRecord extends DnsRecord {
  private final static String ALIAS_NOT_VALID = "The alias value, concatenated with its zone name, must contain no more than 253 characters, excluding a trailing period. It must be between 2 and 34 labels. Each label must only contain letters, numbers, underscores, and/or dashes. Each label should be separated from other labels by a period. Each label must contain between 1 and 63 characters.";
  private final static String ALIAS_LABEL_NOT_VALID = "The alias label must contain between 1 and 63 characters";
  private final static String ALIAS_NOT_DEFINED = "The alias has not been defined and it is required";
  
  private String alias;


  public static DnsCNameRecordBuilder builder() {
    return new DnsCNameRecordBuilder();
  }

  public static class DnsCNameRecordBuilder extends DnsRecord.Builder<DnsCNameRecord, DnsCNameRecordBuilder> {

    @Override
    protected DnsCNameRecord createRecord() {
      return new DnsCNameRecord();
    }

    @Override
    protected DnsCNameRecordBuilder getBuilder() {
      return this;
    }

    public DnsCNameRecordBuilder withAlias(String alias) {
      record.setAlias(alias);
      return builder;
    }

    @Override
    public DnsCNameRecord build() {
      return super.build();
    }

  }

  @Override
  public Collection<String> validate() {
    var errors = super.validate();

    if(StringUtils.isBlank(alias)) {
      errors.add(ALIAS_NOT_DEFINED);
    } else {
      var hasValidCharacters = isValidLettersNumbersUnderscoresDashesAndPeriodsAndPeriodIsNotRequired(alias);

      if (alias.contains(".")) {
        Arrays.stream(alias.split("\\."))
            .filter(label -> label.length() > 63)
            .map(label -> ALIAS_LABEL_NOT_VALID)
            .forEach(errors::add);
      }

      var withoutTrailingPeriod = StringUtils.stripEnd(alias, ".");

      if (!hasValidCharacters || withoutTrailingPeriod.length() > 253) {
        errors.add(ALIAS_NOT_VALID);
      }
    }

    return errors.stream()
        .map(error -> "[DnsCNameRecord Validation] " + error)
        .collect(Collectors.toList());
  }
}

