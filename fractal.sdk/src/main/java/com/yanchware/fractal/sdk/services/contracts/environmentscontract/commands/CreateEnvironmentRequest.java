package com.yanchware.fractal.sdk.services.contracts.environmentscontract.commands;

import com.yanchware.fractal.sdk.aggregates.Environment;
import lombok.Data;
import lombok.ToString;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Data
@ToString
public class CreateEnvironmentRequest {
  private String name;
  private Collection<UUID> resourceGroups;
  private Map<String, Object> parameters;

  public static CreateEnvironmentRequest fromEnvironment(Environment environment) {
    var command = new CreateEnvironmentRequest();
    command.setName(environment.getName());
    command.setResourceGroups(environment.getResourceGroups());
    command.setParameters(environment.getParameters());

    return command;
  }
}
