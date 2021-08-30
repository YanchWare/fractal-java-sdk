package com.yanchware.fractal.sdk.valueobjects;

import lombok.Data;

@Data
public class ValidationOutput {
  private ValidationOutputType type;
  private String message;
}
