package com.yanchware.fractal.sdk.domain.entities.environment;

public record SrvRecordData(String service, String protocolName, int priority, int weight, int port, String target) {
  private static final String PRIORITY_FIELD_NAME = "priority";
  private static final String SERVICE_FIELD_NAME = "service";
  private static final String PROTOCOL_NAME_FIELD_NAME = "protocol";
  private static final String WEIGHT_FIELD_NAME = "weight";
  private static final String PORT_FIELD_NAME = "port";
  private static final String TARGET_FIELD_NAME = "target";
}
