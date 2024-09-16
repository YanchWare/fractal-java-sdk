package com.yanchware.fractal.sdk.domain.blueprint.iaas;

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
public class DnsMxRecord extends DnsRecord {
  public static final String MX_DNS_RECORD_TYPE = "MX";

  private Collection<DnsMxRecordData> recordData;

  public static DnsMxRecordBuilder builder() {
    return new DnsMxRecordBuilder();
  }

  public static class DnsMxRecordBuilder extends Builder<DnsMxRecord, DnsMxRecordBuilder> {
    @Override
    protected DnsMxRecord createRecord() {
      return new DnsMxRecord();
    }

    @Override
    protected DnsMxRecordBuilder getBuilder() {
      return this;
    }

    public DnsMxRecordBuilder withRecordData(DnsMxRecordData recordData) {
      return withRecordData(List.of(recordData));
    }

    public DnsMxRecordBuilder withRecordData(Collection<? extends DnsMxRecordData> recordData) {
      if (isBlank(recordData)) {
        return builder;
      }

      if (record.getRecordData() == null) {
        record.setRecordData(new ArrayList<>());
      }

      record.getRecordData().addAll(recordData);
      return builder;
    }

    @Override
    public DnsMxRecord build() {
      return super.build();
    }
  }

  @Override
  public Collection<String> validate() {
    var errors = super.validate();

    if (!isBlank(recordData)) {
      for (var data : recordData) {
        errors.addAll(data.validate());
      }
    }

    return errors;
  }
}