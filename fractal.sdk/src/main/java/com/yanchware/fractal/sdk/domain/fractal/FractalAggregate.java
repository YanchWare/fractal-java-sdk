package com.yanchware.fractal.sdk.domain.fractal;

import com.yanchware.fractal.sdk.configuration.SdkConfiguration;
import com.yanchware.fractal.sdk.domain.Validatable;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.domain.fractal.service.BlueprintService;
import com.yanchware.fractal.sdk.domain.fractal.service.dtos.BlueprintComponentDto;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
@Setter(AccessLevel.PROTECTED)
public class FractalAggregate implements Validatable {
  private final static String ID_IS_NULL = "[Fractal Validation] Id has not been defined and it is required";
  private final static String NAME_IS_NULL = "[Fractal Validation] Name has not been defined and it is required";
  private final static String VERSION_IS_NULL = "[Fractal Validation] Version has not been defined and it is required";
  private final static String RESOURCE_GROUP_ID_IS_NULL = "[Fractal Validation] ResourceGroupId has not been " +
    "defined and it is required'";
  private final static String EMPTY_COMPONENT_LIST = "[Fractal Validation] Components list is null or empty and at" +
    " least one component is required";

  private final BlueprintService service;

  @Getter
  private FractalIdValue id;
  @Getter
  private String description;
  @Getter
  private boolean isPrivate;
  @Getter
  // TODO FRA-1870: We should find a way to fix this instead to have DTOs in the Aggregate 🤮
  private Collection<BlueprintComponentDto> components;

  public FractalAggregate(
    HttpClient client,
    SdkConfiguration sdkConfiguration,
    RetryRegistry retryRegistry)
  {
    this.service = new BlueprintService(client, sdkConfiguration, retryRegistry);
    components = new ArrayList<>();
  }

  public void createOrUpdate() throws InstantiatorException {
    if (components == null || components.isEmpty()) {
      throw new InstantiatorException(EMPTY_COMPONENT_LIST);
    }

    service.createOrUpdate(id, description, isPrivate, components);
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (id == null) {
      errors.add(ID_IS_NULL);
      return errors;
    }

    if (isBlank(id.name())) {
      errors.add(NAME_IS_NULL);
    }

    if (isBlank(id.version())) {
      errors.add(VERSION_IS_NULL);
    }

    if (id.resourceGroupId() == null) {
      errors.add(RESOURCE_GROUP_ID_IS_NULL);
    }

    return errors;
  }

}
