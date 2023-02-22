package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AzureSiteLoadBalancingTest {
  @Test
  public void shouldReturnProperValue() {
    assertAll(
        () -> assertEquals(AzureSiteLoadBalancing.fromString("LeastRequests"), AzureSiteLoadBalancing.LEAST_REQUESTS),
        () -> assertEquals(AzureSiteLoadBalancing.fromString("LeastResponseTime"), AzureSiteLoadBalancing.LEAST_RESPONSE_TIME),
        () -> assertEquals(AzureSiteLoadBalancing.fromString("PerSiteRoundRobin"), AzureSiteLoadBalancing.PER_SITE_ROUND_ROBIN),
        () -> assertEquals(AzureSiteLoadBalancing.fromString("RequestHash"), AzureSiteLoadBalancing.REQUEST_HASH),
        () -> assertEquals(AzureSiteLoadBalancing.fromString("WeightedRoundRobin"), AzureSiteLoadBalancing.WEIGHTED_ROUND_ROBIN),
        () -> assertEquals(AzureSiteLoadBalancing.fromString("WeightedTotalTraffic"), AzureSiteLoadBalancing.WEIGHTED_TOTAL_TRAFFIC)
    );
  }
}