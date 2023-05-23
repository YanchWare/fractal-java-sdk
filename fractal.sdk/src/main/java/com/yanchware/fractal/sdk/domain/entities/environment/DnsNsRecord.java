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
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLettersNumbersUnderscoresDashesAndPeriods;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class DnsNsRecord extends DnsRecord {
  private final String NAME_SERVER_NOT_VALID_PATTERN = "The nameServer value ['%s'], concatenated with its zone name, must contain no more than 253 characters, excluding a trailing period. It must be between 2 and 34 labels. Each label must only contain letters, numbers, underscores, and/or dashes. Each label should be separated from other labels by a period. Each label must contain between 1 and 63 characters.";
  private final String NAME_SERVER_LABEL_NOT_VALID_PATTERN = "The nameServer ['%s'] label must contain between 1 and 63 characters";

  private List<String> nameServers;

  public static DnsNsRecordBuilder builder() {
    return new DnsNsRecordBuilder();
  }

  public static class DnsNsRecordBuilder extends DnsRecord.Builder<DnsNsRecord, DnsNsRecordBuilder> {
    @Override
    protected DnsNsRecord createRecord() {
      return new DnsNsRecord();
    }

    @Override
    protected DnsNsRecordBuilder getBuilder() {
      return this;
    }

    public DnsNsRecordBuilder withNameServer(String nameServer) {
      return withNameServers(List.of(nameServer));
    }

    public DnsNsRecordBuilder withNameServers(Collection<? extends String> nameServers) {
      if (isBlank(nameServers)) {
        return builder;
      }

      if (record.getNameServers() == null) {
        record.setNameServers(new ArrayList<>());
      }

      record.getNameServers().addAll(nameServers);
      return builder;
    }

    @Override
    public DnsNsRecord build() {
      return super.build();
    }
  }

  @Override
  public Collection<String> validate() {
    var errors = super.validate();

    if (!isBlank(nameServers)) {
      for (var nameServer : nameServers) {
        var hasValidCharacters = isValidLettersNumbersUnderscoresDashesAndPeriods(nameServer);

        if (nameServer.contains(".")) {
          for (String label : nameServer.split("\\.")) {
            if (label.length() > 63) {
              errors.add(String.format(NAME_SERVER_LABEL_NOT_VALID_PATTERN, label));
            }
          }
        }

        var withoutTrailingPeriod = StringUtils.stripEnd(nameServer, ".");

        if (!hasValidCharacters || withoutTrailingPeriod.length() > 253) {
          errors.add(String.format(NAME_SERVER_NOT_VALID_PATTERN, nameServer));
        }
      }
    }

    return errors;
  }
}