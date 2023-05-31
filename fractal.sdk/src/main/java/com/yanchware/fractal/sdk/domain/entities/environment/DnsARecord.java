package com.yanchware.fractal.sdk.domain.entities.environment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidIpV4Address;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class DnsARecord extends DnsRecord {
  private final static String IP_V4_ADDRESS_NOT_VALID = "ipV4Address does not contain a valid IP v4 address";
  
  private String ipV4Address;
  
  public static DnsARecordBuilder builder() {
    return new DnsARecordBuilder();
  }

  public static class DnsARecordBuilder extends DnsRecord.Builder<DnsARecord, DnsARecordBuilder> {
    @Override
    protected DnsARecord createRecord() {
      return new DnsARecord();
    }

    @Override
    protected DnsARecordBuilder getBuilder() {
      return this;
    }

    public DnsARecordBuilder withIpV4Address(String ipV4Address) {
      record.setIpV4Address(ipV4Address);
      return builder;
    }

    @Override
    public DnsARecord build() {
      return super.build();
    }
  }

  @Override
  public Collection<String> validate() {
    var errors = super.validate();

    if (StringUtils.isNotBlank(ipV4Address)) {
      var hasValidCharacters = isValidIpV4Address(ipV4Address);

      if (!hasValidCharacters) {
        errors.add(IP_V4_ADDRESS_NOT_VALID);
      }
    }

    return errors.stream()
        .map(error -> "[DnsARecord Validation] " + error)
        .collect(Collectors.toList());
  }
}