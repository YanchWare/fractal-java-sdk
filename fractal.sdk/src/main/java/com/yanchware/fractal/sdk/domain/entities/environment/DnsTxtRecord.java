package com.yanchware.fractal.sdk.domain.entities.environment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;


@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class DnsTxtRecord extends DnsRecord {
  private final static String VALUES_NOT_VALID = "The combined length of all TXT records in a record set may not exceed 1024 characters";

  private List<String> values;

  public static DnsTxtRecordBuilder builder() {
    return new DnsTxtRecordBuilder();
  }

  public static class DnsTxtRecordBuilder extends DnsRecord.Builder<DnsTxtRecord, DnsTxtRecordBuilder> {
    @Override
    protected DnsTxtRecord createRecord() {
      return new DnsTxtRecord();
    }

    @Override
    protected DnsTxtRecordBuilder getBuilder() {
      return this;
    }

    public DnsTxtRecordBuilder withValue(String value) {
      return withValues(List.of(value));
    }

    public DnsTxtRecordBuilder withValues(Collection<? extends String> values) {
      if (isBlank(values)) {
        return builder;
      }

      if (record.getValues() == null) {
        record.setValues(new ArrayList<>());
      }

      record.getValues().addAll(values);
      return builder;
    }

    @Override
    public DnsTxtRecord build() {
      return super.build();
    }
  }

  @Override
  public Collection<String> validate() {
    var errors = super.validate();

    if (!isBlank(values)) {
      var combinedTxt = String.join("", values);
      
      if(combinedTxt.length() > 1024) {
        errors.add(VALUES_NOT_VALID);
      }
    }

    return errors;
  }
}