package com.yanchware.fractal.sdk.domain.entities.environment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

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
  private final static String SERVICE_NOT_DEFINED = "Service has not been defined and it is required";
  private final static String PROTOCOL_NAME_NOT_DEFINED= "ProtocolName has not been defined and it is required";
  
  private String service;
  private String protocolName;
  
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

    public DnsSrvRecordBuilder withService(String service) {
      record.setService(service);
      return builder;
    }

    public DnsSrvRecordBuilder withProtocolName(String protocolName) {
      record.setProtocolName(protocolName);
      return builder;
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
      if(StringUtils.isBlank(record.getName())) {
        record.setName(String.format("%s.%s", record.getService(), record.getProtocolName()));
      } else {
        record.setName(String.format("%s.%s.%s", record.getService(), record.getProtocolName(), record.getName()));
      }
      return super.build();
    }
  }

  @Override
  public Collection<String> validate() {
    var errors = super.validate();

    if(StringUtils.isBlank(service)) {
      errors.add(SERVICE_NOT_DEFINED);
    }

    if(StringUtils.isBlank(protocolName)) {
      errors.add(PROTOCOL_NAME_NOT_DEFINED);
    }
    
    

    if (!isBlank(recordData)) {
      for (var data : recordData) {
        errors.addAll(data.validate());
      }
    }

    

    return errors;
  }
}