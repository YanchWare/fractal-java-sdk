package com.yanchware.fractal.sdk.domain.blueprint.iaas;

import com.fasterxml.jackson.annotation.JsonTypeName;
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

import static com.yanchware.fractal.sdk.domain.blueprint.iaas.DnsAaaaRecord.AAAA_DNS_RECORD_TYPE;
import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidIpV6Address;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@JsonTypeName(AAAA_DNS_RECORD_TYPE)
public class DnsAaaaRecord extends DnsRecord {
  public static final String AAAA_DNS_RECORD_TYPE = "AAAA";

  private final static String IP_V6_ADDRESS_NOT_VALID = "[DnsAaaaRecord Validation] ipV6Address does not contain a valid IP v6 address";

  private Set<String> ipV6Addresses;

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
      return withIpV6Addresses(Collections.singleton(ipV6Address));
    }

    public DnsAaaaRecordBuilder withIpV6Addresses(Set<String> ipV6Addresses) {
      if (isBlank(ipV6Addresses)) {
        return builder;
      }

      if (record.getIpV6Addresses() == null) {
        record.setIpV6Addresses(new HashSet<>());
      }

      record.getIpV6Addresses().addAll(ipV6Addresses);
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

    if (!isBlank(ipV6Addresses)) {
      ipV6Addresses.stream()
          .filter(StringUtils::isNotBlank)
          .forEach(ipV6Address -> {
            var hasValidCharacters = isValidIpV6Address(ipV6Address);
            if (!hasValidCharacters) {
              errors.add(IP_V6_ADDRESS_NOT_VALID);
            }
          });
    }

    return errors.stream()
        .map(error -> "[DnsAaaaRecord Validation] " + error)
        .collect(Collectors.toList());
  }
}
