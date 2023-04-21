package com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos;

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
