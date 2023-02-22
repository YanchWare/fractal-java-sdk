package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureAction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class AzureWebAppIpSecurityRestriction {
  /**
   * Allow or Deny access for this IP range.
   */
  private AzureAction action;

  /**
   * IP restriction rule description.
   */
  private String description;

  /**
   * IP restriction rule headers.
   */
  private Map<String, List<String>> headers;

  /**
   * IP address the security restriction is valid for. 
   * It can be in form of pure ipv4 address (required SubnetMask property) or CIDR notation such as ipv4/mask (leading bit match). 
   * For CIDR, SubnetMask property must not be specified.
   */
  private String ipAddress;
  
  /**
   * IP restriction rule name.
   */
  private String name;
  
  /**
   * Priority of IP restriction rule.
   */
  private Integer priority;
  
  /**
   * Subnet mask for the range of IP addresses the restriction is valid for.
   */
  private String subnetMask;

  /**
   * Defines what this IP filter will be used for. This is to support IP filtering on proxies.
   */
  private AzureIpFilterTag ipFilterTag;

  /**
   * Virtual network resource id
   */
  private String vnetSubnetResourceId;
}
