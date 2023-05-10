package com.yanchware.fractal.sdk.domain.entities.environment;

import java.time.Duration;

public interface DnsRecord {
  String DNS_RECORD_NAME_PARAM_KEY = "name";
  String DNS_RECORD_TTL_PARAM_KEY = "ttl";

  int DEFAULT_TTL_IN_MINUTES = 5;

  String A_DNS_RECORD_TYPE = "A";
  String AAAA_DNS_RECORD_TYPE = "AAAA";
  String CAA_DNS_RECORD_TYPE = "CAA";
  String CNAME_DNS_RECORD_TYPE = "CNAME";
  String MX_DNS_RECORD_TYPE = "MX";
  String NS_DNS_RECORD_TYPE = "NS";
  String PTR_DNS_RECORD_TYPE = "PTR";
  String SRV_DNS_RECORD_TYPE = "SRV";
  String TXT_DNS_RECORD_TYPE = "TXT";

  Duration ttl();
  String name();
}
