package com.yanchware.fractal.sdk.domain.entities.environment;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
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
public class DnsSrvRecordData implements Validatable {
  private final static String PRIORITY_NOT_VALID = "The priority value must be less than or equal to 65535";
  private final static String WEIGHT_NOT_VALID = "The weight value must be less than or equal to 65535";
  private final static String PORT_NOT_VALID = "The port value must be less than or equal to 65535";
  private final static String TARGET_NOT_VALID = "The target value, concatenated with its zone name, must contain no more than 253 characters, excluding a trailing period. It must be between 2 and 34 labels. Each label must only contain letters, numbers, underscores, and/or dashes. Each label should be separated from other labels by a period. Each label must contain between 1 and 63 characters.";
  private final static String TARGET_LABEL_NOT_VALID = "The target label must contain between 1 and 63 characters";
  private final static String SERVICE_NOT_DEFINED = "Service has not been defined and it is required";
  private final static String PROTOCOL_NAME_NOT_DEFINED= "ProtocolName has not been defined and it is required";
  private final static String TARGET_NOT_DEFINED= "Target has not been defined and it is required";

  private String service; 
  private String protocolName; 
  private int priority; 
  private int weight;
  private int port;
  private String target;

  public static DnsSrvRecordDataBuilder builder() {
    return new DnsSrvRecordDataBuilder();
  }

  public static class DnsSrvRecordDataBuilder {
    private final DnsSrvRecordData recordData;
    private final DnsSrvRecordDataBuilder builder;

    public DnsSrvRecordDataBuilder() {
      recordData = new DnsSrvRecordData();
      builder = this;
    }

    public DnsSrvRecordDataBuilder withService(String service) {
      recordData.setService(service);
      return builder;
    }
    
    public DnsSrvRecordDataBuilder withProtocolName(String protocolName) {
      recordData.setProtocolName(protocolName);
      return builder;
    }
    
    public DnsSrvRecordDataBuilder withPriority(int priority) {
      recordData.setPriority(priority);
      return builder;
    }
    
    public DnsSrvRecordDataBuilder withWeight(int weight) {
      recordData.setWeight(weight);
      return builder;
    }
    public DnsSrvRecordDataBuilder withPort(int port) {
      recordData.setPort(port);
      return builder;
    }
    public DnsSrvRecordDataBuilder withTarget(String target) {
      recordData.setTarget(target);
      return builder;
    }

    public DnsSrvRecordData build() {
      Collection<String> errors = recordData.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
            "CaaRecordData validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return recordData;
    }
  }

  @Override
  public Collection<String> validate() {

    var errors = new ArrayList<String>();

    if(priority < 0 || priority > 65535) {
      errors.add(PRIORITY_NOT_VALID);
    }

    if(weight < 0 || weight > 65535) {
      errors.add(WEIGHT_NOT_VALID);
    }

    if(port < 0 || port > 65535) {
      errors.add(PORT_NOT_VALID);
    }

    if(StringUtils.isBlank(service)) {
      errors.add(SERVICE_NOT_DEFINED);
    }

    if(StringUtils.isBlank(protocolName)) {
      errors.add(PROTOCOL_NAME_NOT_DEFINED);
    }

    if(StringUtils.isBlank(target)) {
      errors.add(TARGET_NOT_DEFINED);
    } else {
      var hasValidCharacters = isValidLettersNumbersUnderscoresDashesAndPeriodsAndPeriodIsNotRequired(target);

      if (target.contains(".")) {
        Arrays.stream(target.split("\\."))
            .filter(label -> label.length() > 63)
            .map(label -> TARGET_LABEL_NOT_VALID)
            .forEach(errors::add);
      }

      var withoutTrailingPeriod = StringUtils.stripEnd(target, ".");

      if (!hasValidCharacters || withoutTrailingPeriod.length() > 253) {
        errors.add(TARGET_NOT_VALID);
      }
    }

    return errors;
  }
}