package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLettersNumbersUnderscoresDashesAndPeriods;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PUBLIC)
@ToString(callSuper = true)
public abstract class CaaSComponent extends Component {
  private final static String DNS_ZONE_NAME_NOT_VALID = "[DnsRecords Validation] The dnsZoneName must contain no more than 253 characters, excluding a trailing period and must be between 2 and 34 labels. Each label must only contain letters, numbers, underscores, and/or dashes. Each label should be separated from other labels by a period";

  private String containerPlatform;
  private String namespace;
  private Map<String, List<Object>> dnsZoneConfig;

  private String getNamespaceIsNullOrEmptyErrorMessage() {
    return String.format("[%s Validation] Namespace has not been defined and it is required", this.getClass().getSimpleName());
  }

  private String getContainerPlatformIsNullOrEmptyErrorMessage() {
    return String.format("[%s Validation] ContainerPlatform defined was either empty or blank and it is required", this.getClass().getSimpleName());
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (isBlank(namespace)) {
      errors.add(getNamespaceIsNullOrEmptyErrorMessage());
    }

    if (containerPlatform != null && isBlank(containerPlatform)) {
      errors.add(getContainerPlatformIsNullOrEmptyErrorMessage());
    }

    for (var dnsZoneName: dnsZoneConfig.keySet()) {
      if (StringUtils.isBlank(dnsZoneName)) {
        errors.add(DNS_ZONE_NAME_NOT_VALID);
      }

      if (StringUtils.isNotBlank(dnsZoneName)) {
        var nameWithoutTrailingPeriod = StringUtils.stripEnd(dnsZoneName, ".");

        var hasValidCharacters = isValidLettersNumbersUnderscoresDashesAndPeriods(dnsZoneName);

        if (!hasValidCharacters || nameWithoutTrailingPeriod.length() > 253) {
          errors.add(DNS_ZONE_NAME_NOT_VALID);
        }
      }
    }
      
    return errors;
  }
}
