package com.yanchware.fractal.sdk.domain.blueprint.iaas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidIpV4Address;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class DnsARecord extends DnsRecord {
  public static final String A_DNS_RECORD_TYPE = "A";

  private final static String IP_V4_ADDRESS_NOT_VALID = "ipV4Address does not contain a valid IP v4 address";

  private Set<String> ipV4Addresses;

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
      return withIpV4Addresses(Collections.singleton(ipV4Address));
    }

    public DnsARecordBuilder withIpV4Addresses(Set<String> ipV4Addresses) {
      if (isBlank(ipV4Addresses)) {
        return builder;
      }

      if (record.getIpV4Addresses() == null) {
        record.setIpV4Addresses(new HashSet<>());
      }

      record.getIpV4Addresses().addAll(ipV4Addresses);
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

    if (!isBlank(ipV4Addresses)) {
      ipV4Addresses.stream()
          .filter(StringUtils::isNotBlank)
          .forEach(ipV4Address -> {
            var hasValidCharacters = isValidIpV4Address(ipV4Address);
            if (!hasValidCharacters) {
              errors.add(IP_V4_ADDRESS_NOT_VALID);
            }
          });
    }
    
    return errors.stream()
        .map(error -> "[DnsARecord Validation] " + error)
        .collect(Collectors.toList());
  }
}