package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;


public final class AzureSiteLoadBalancing extends ExtendableEnum<AzureSiteLoadBalancing>  {
  public static final AzureSiteLoadBalancing LEAST_REQUESTS = fromString("LeastRequests");
  public static final AzureSiteLoadBalancing LEAST_RESPONSE_TIME = fromString("LeastResponseTime");
  public static final AzureSiteLoadBalancing PER_SITE_ROUND_ROBIN = fromString("PerSiteRoundRobin");
  public static final AzureSiteLoadBalancing REQUEST_HASH = fromString("RequestHash");
  public static final AzureSiteLoadBalancing WEIGHTED_ROUND_ROBIN = fromString("WeightedRoundRobin");
  public static final AzureSiteLoadBalancing WEIGHTED_TOTAL_TRAFFIC = fromString("WeightedTotalTraffic");

  public AzureSiteLoadBalancing() {
  }

  @JsonCreator
  public static AzureSiteLoadBalancing fromString(String name) {
    return fromString(name, AzureSiteLoadBalancing.class);
  }

  public static Collection<AzureSiteLoadBalancing> values() {

    return values(AzureSiteLoadBalancing.class);
  }
  
}
