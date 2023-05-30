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
public class DnsSrvRecord extends DnsRecord {
  private final static String DOMAIN_NAME_NOT_VALID_PATTERN = "The domainName value ['%s'], concatenated with its zone name, must contain no more than 253 characters, excluding a trailing period. It must be between 2 and 34 labels. Each label must only contain letters, numbers, underscores, and/or dashes. Each label should be separated from other labels by a period. Each label must contain between 1 and 63 characters.";
  private final static String DOMAIN_NAME_LABEL_NOT_VALID_PATTERN = "The domainName ['%s'] label must contain between 1 and 63 characters";

  private List<DnsSrvRecordData> recordData;

  public static DnsSrvRecordBuilder builder() {
    return new DnsSrvRecordBuilder();
  }

  public static class DnsSrvRecordBuilder extends DnsRecord.Builder<DnsSrvRecord, DnsSrvRecordBuilder> {
    @Override
    protected DnsSrvRecord createRecord() {
      return new DnsSrvRecord();
    }

    @Override
    protected DnsSrvRecordBuilder getBuilder() {
      return this;
    }

    public DnsSrvRecordBuilder withRecordData(DnsSrvRecordData recordData) {
      return withRecordData(List.of(recordData));
    }

    public DnsSrvRecordBuilder withRecordData(Collection<? extends DnsSrvRecordData> recordData) {
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
    public DnsSrvRecord build() {
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