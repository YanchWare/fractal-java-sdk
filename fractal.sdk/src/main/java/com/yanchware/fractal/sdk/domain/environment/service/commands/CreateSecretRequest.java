package com.yanchware.fractal.sdk.domain.environment.service.commands;

public record CreateSecretRequest(
  String name,
  String value)
{
}


