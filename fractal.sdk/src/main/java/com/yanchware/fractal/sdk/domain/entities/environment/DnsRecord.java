package com.yanchware.fractal.sdk.domain.entities.environment;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.yanchware.fractal.sdk.domain.entities.environment.DnsRecordConstants.*;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLettersNumbersUnderscoresDashesAndPeriodsAndPeriodIsNotRequired;

@Getter
@Setter(AccessLevel.PROTECTED)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "@type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = DnsAaaaRecord.class, name = AAAA_DNS_RECORD_TYPE),
    @JsonSubTypes.Type(value = DnsARecord.class, name = A_DNS_RECORD_TYPE),
    @JsonSubTypes.Type(value = DnsCaaRecordData.class, name = CAA_DNS_RECORD_TYPE),
    @JsonSubTypes.Type(value = DnsCNameRecord.class, name = CNAME_DNS_RECORD_TYPE),
    @JsonSubTypes.Type(value = DnsMxRecord.class, name = MX_DNS_RECORD_TYPE),
    @JsonSubTypes.Type(value = DnsNsRecord.class, name = NS_DNS_RECORD_TYPE),
    @JsonSubTypes.Type(value = DnsPtrRecord.class, name = PTR_DNS_RECORD_TYPE),
    @JsonSubTypes.Type(value = DnsSrvRecord.class, name = SRV_DNS_RECORD_TYPE),
    @JsonSubTypes.Type(value = DnsTxtRecord.class, name = TXT_DNS_RECORD_TYPE)
})
public abstract class DnsRecord implements Validatable {
  private final static String NAME_NOT_VALID = "The Name must contain between 1 and 63 characters. " +
      "It must only contain letters, numbers, underscores, and/or dashes. " +
      "Each label should be separated from other labels by a period. " +
      "A wildcard ('*' character) is permitted either as the single character in the name, or as the first label in the name. " +
      "An empty value, or a single '@' character is permitted for record sets at the zone apex (except for CNAME record sets)";

  private Duration ttl;

  private String name;

  public abstract static class Builder<T extends DnsRecord, B extends Builder<T, B>> {
    protected final T record;
    protected final B builder;

    public Builder() {
      record = createRecord();
      builder = getBuilder();
    }

    protected abstract T createRecord();
    protected abstract B getBuilder();

    public B withName(String name) {
      record.setName(name);
      return builder;
    }

    public B withTtl(Duration ttl) {
      record.setTtl(ttl);
      return builder;
    }

    public T build() {
      Collection<String> errors = record.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format(
            "DnsRecord '%s' validation failed. Errors: %s",
            this.getClass().getSimpleName(),
            Arrays.toString(errors.toArray())));
      }

      return record;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (StringUtils.isBlank(name)) {
      errors.add(NAME_NOT_VALID);
    }

    if (StringUtils.isNotBlank(name)) {
      var hasValidCharacters = isValidLettersNumbersUnderscoresDashesAndPeriodsAndPeriodIsNotRequired(name);

      if (!hasValidCharacters || name.length() > 63) {
        errors.add(NAME_NOT_VALID);
      }
    }

    return errors;
  }
}
