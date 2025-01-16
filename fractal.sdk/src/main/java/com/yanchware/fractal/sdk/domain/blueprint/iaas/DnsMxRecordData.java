package com.yanchware.fractal.sdk.domain.blueprint.iaas;

import com.yanchware.fractal.sdk.domain.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLettersNumbersUnderscoresDashesAndPeriodsAndPeriodIsNotRequired;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class DnsMxRecordData implements Validatable {
  private final static String PRIORITY_NOT_VALID = "The Priority value must be between 0 and 65535";
  private final static String MAIL_EXCHANGE_NOT_VALID = "The mailExchange value, concatenated with its zone name, " +
    "must contain no more than 253 characters, excluding a trailing period. It must be between 2 and 34 labels. Each " +
    "label must only contain letters, numbers, underscores, and/or dashes. Each label should be separated from other " +
    "labels by a period. Each label must contain between 1 and 63 characters.";
  private final static String MAIL_EXCHANGE_LABEL_NOT_VALID = "The mailExchange label must contain between 1 and 63 " +
    "characters";
  private final static String MAIL_EXCHANGE_NOT_DEFINED = "The mailExchange has not been defined and it is required";

  public int priority;
  public String mailExchange;

  public static DnsMxRecordDataBuilder builder() {
    return new DnsMxRecordDataBuilder();
  }

  @Override
  public Collection<String> validate() {

    var errors = new ArrayList<String>();

    if (priority < 0 || priority > 65535) {
      errors.add(PRIORITY_NOT_VALID);
    }

    if (StringUtils.isBlank(mailExchange)) {
      errors.add(MAIL_EXCHANGE_NOT_DEFINED);
    } else {
      var hasValidCharacters = isValidLettersNumbersUnderscoresDashesAndPeriodsAndPeriodIsNotRequired(mailExchange);

      if (mailExchange.contains(".")) {
        Arrays.stream(mailExchange.split("\\."))
          .filter(label -> label.length() > 63)
          .map(label -> MAIL_EXCHANGE_LABEL_NOT_VALID)
          .forEach(errors::add);
      }

      var withoutTrailingPeriod = StringUtils.stripEnd(mailExchange, ".");

      if (!hasValidCharacters || withoutTrailingPeriod.length() > 253) {
        errors.add(MAIL_EXCHANGE_NOT_VALID);
      }
    }

    return errors;
  }

  public static class DnsMxRecordDataBuilder {
    private final DnsMxRecordData recordData;
    private final DnsMxRecordDataBuilder builder;

    public DnsMxRecordDataBuilder() {
      recordData = new DnsMxRecordData();
      builder = this;
    }

    public DnsMxRecordDataBuilder withPriority(int priority) {
      recordData.setPriority(priority);
      return builder;
    }

    public DnsMxRecordDataBuilder withMailExchange(String mailExchange) {
      recordData.setMailExchange(mailExchange);
      return builder;
    }

    public DnsMxRecordData build() {
      Collection<String> errors = recordData.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
          "MxRecordData validation failed. Errors: %s",
          Arrays.toString(errors.toArray())));
      }

      return recordData;
    }
  }
}