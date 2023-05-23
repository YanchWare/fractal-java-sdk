package com.yanchware.fractal.sdk.domain.entities.environment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidIpV6Address;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class DnsAaaaRecord extends DnsRecord {
  private final static String IP_V6_ADDRESS_NOT_VALID = "[DnsAaaaRecord Validation] ipV6Address does not contain a valid IP v6 address";
  
  private String ipV6Address;

  public static DnsAaaaRecordBuilder builder() {
    return new DnsAaaaRecordBuilder();
  }

  public static class DnsAaaaRecordBuilder extends DnsRecord.Builder<DnsAaaaRecord, DnsAaaaRecordBuilder> {
    @Override
    protected DnsAaaaRecord createRecord() {
      return new DnsAaaaRecord();
    }

    @Override
    protected DnsAaaaRecordBuilder getBuilder() {
      return this;
    }

    public DnsAaaaRecordBuilder withIpV6Address(String ipV6Address) {
      record.setIpV6Address(ipV6Address);
      return builder;
    }

    @Override
    public DnsAaaaRecord build() {
      return super.build();
    }
  }

  @Override
  public Collection<String> validate() {
    var errors = super.validate();

    if (StringUtils.isNotBlank(ipV6Address)) {
      var hasValidCharacters = isValidIpV6Address(ipV6Address);

      if (!hasValidCharacters) {
        errors.add(IP_V6_ADDRESS_NOT_VALID);
      }
    }

    return errors.stream()
        .map(error -> "[DnsAaaaRecord Validation] " + error)
        .collect(Collectors.toList());
  }
}
