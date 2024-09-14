package com.yanchware.fractal.sdk.domain.values;

import lombok.Data;

@Data
public class ValidationOutput {
  private ValidationOutputType type;
  private String message;
}
