package com.yanchware.fractal.sdk.domain.entities.environment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class DnsCNameRecord extends DnsRecord {
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
}

