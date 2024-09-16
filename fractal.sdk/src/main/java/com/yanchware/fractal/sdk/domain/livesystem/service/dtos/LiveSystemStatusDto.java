package com.yanchware.fractal.sdk.domain.livesystem.service.dtos;

public enum LiveSystemStatusDto {
  Unknown,
  Instantiating,
  Active,
  Failed,
  FailedMutation,
  Mutating,
  Deleting,
  Deleted,
  Cancelled
}
