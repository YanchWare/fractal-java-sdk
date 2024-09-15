package com.yanchware.fractal.sdk.domain.blueprint.iaas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class DnsCaaRecord extends DnsRecord {
  public static final String CAA_DNS_RECORD_TYPE = "CAA";

  private Collection<DnsCaaRecordData> recordData;

  public static DnsCaaRecordBuilder builder() {
    return new DnsCaaRecordBuilder();
  }

  public static class DnsCaaRecordBuilder extends Builder<DnsCaaRecord, DnsCaaRecordBuilder> {
    @Override
    protected DnsCaaRecord createRecord() {
      return new DnsCaaRecord();
    }

    @Override
    protected DnsCaaRecordBuilder getBuilder() {
      return this;
    }

    public DnsCaaRecordBuilder withRecordData(DnsCaaRecordData recordData) {
      return withRecordData(List.of(recordData));
    }

    public DnsCaaRecordBuilder withRecordData(Collection<? extends DnsCaaRecordData> recordData) {
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
    public DnsCaaRecord build() {
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

    return errors.stream()
        .map(error -> "[DnsCaaRecord Validation] " + error)
        .collect(Collectors.toList());
  }
}
