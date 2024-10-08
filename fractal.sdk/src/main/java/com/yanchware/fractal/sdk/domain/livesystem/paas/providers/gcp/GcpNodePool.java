package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.gcp;

import com.yanchware.fractal.sdk.domain.Validatable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class GcpNodePool implements Validatable {
  private final static String NAME_IS_BLANK = "[GcpNodePool Validation] Name has not been defined and it is required";

  private int diskSizeGb;
  private int initialNodeCount;
  private GcpMachine machineType;
  private int maxNodeCount;
  private int maxSurge;
  private int minNodeCount;
  private String name;

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (isBlank(name)) {
      errors.add(NAME_IS_BLANK);
    }

    return errors;
  }
}
