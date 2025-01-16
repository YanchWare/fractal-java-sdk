package com.yanchware.fractal.sdk.domain.livesystem.iaas;

import java.util.List;

public record Rule(
  int priority,
  String name,
  RuleType ruleType,
  List<String> fromAddressSpaceCidrs,
  List<Integer> fromPorts,
  List<String> toAddressSpaceCidrs,
  List<Integer> toPorts,
  Protocol protocol,
  String description)
{
  public enum RuleType {
    ALLOW_INBOUND,
    ALLOW_OUTBOUND,
    DENY_INBOUND,
    DENY_OUTBOUND
  }

  public enum Protocol {
    ANY,
    TCP,
    UDP,
    ICMP,
    ESP,
    AH
  }
}
