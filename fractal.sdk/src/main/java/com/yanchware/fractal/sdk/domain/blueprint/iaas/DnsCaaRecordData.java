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

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLettersAndNumbers;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class DnsCaaRecordData implements Validatable {
  private final static String FLAGS_NOT_VALID = "The Flags value must be between 0 and 255";
  private final static String TAG_NOT_DEFINED = "The tag has not been defined and it is required";
  private final static String TAG_NOT_VALID = "The Tag value must have a length of at most 15. It must contain characters 'a' through 'z', 'A' through 'Z', and the numbers 0 through 9";
  private final static String VALUE_NOT_DEFINED = "The value has not been defined and it is required";
  private final static String VALUE_NOT_VALID = "The value must have a length of at most 1024";
  
  public int flags;
  public String tag;
  public String value;

  public static DnsCaaRecordDataBuilder builder() {
    return new DnsCaaRecordDataBuilder();
  }

  public static class DnsCaaRecordDataBuilder {
    private final DnsCaaRecordData recordData;
    private final DnsCaaRecordDataBuilder builder;

    public DnsCaaRecordDataBuilder() {
      recordData = new DnsCaaRecordData();
      builder = this;
    }

    public DnsCaaRecordDataBuilder withFlags(int flags) {
      recordData.setFlags(flags);
      return builder;
    }

    public DnsCaaRecordDataBuilder withTag(String tag) {
      recordData.setTag(tag);
      return builder;
    }

    public DnsCaaRecordDataBuilder withValue(String value) {
      recordData.setValue(value);
      return builder;
    }

    public DnsCaaRecordData build() {
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
    
    if(flags < 0 || flags > 255) {
      errors.add(FLAGS_NOT_VALID);
    }
    
    if(StringUtils.isBlank(tag)) {
      errors.add(TAG_NOT_DEFINED);
    } else {
      var hasValidCharacters = isValidLettersAndNumbers(tag);

      if (!hasValidCharacters || tag.length() > 15) {
        errors.add(TAG_NOT_VALID);
      }
    }

    if(StringUtils.isBlank(value)) {
      errors.add(VALUE_NOT_DEFINED);
    } else {
      if (value.length() > 1024) {
        errors.add(VALUE_NOT_VALID);
      }
    }

    return errors;
  }
}