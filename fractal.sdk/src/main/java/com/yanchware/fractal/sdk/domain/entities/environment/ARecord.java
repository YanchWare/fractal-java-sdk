package com.yanchware.fractal.sdk.domain.entities.environment;

import java.time.Duration;
import java.util.regex.Pattern;

public record ARecord(String name, String ipV4Address, Duration ttl) implements DnsRecord {
  public static final String DNS_RECORD_IP_V4_PARAM_KEY = "ipV4Address";

  private static final Pattern ipv4Pattern = Pattern.compile("^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$");

  private static boolean isIpV4Valid(String address) {
    var m = ipv4Pattern.matcher(address);
    return m.matches();
  }
}
