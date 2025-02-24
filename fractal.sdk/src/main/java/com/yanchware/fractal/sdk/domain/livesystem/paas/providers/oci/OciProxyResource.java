package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.oci;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class OciProxyResource {
  private String name;
  private OciRegion region;
  private Map<String, String> tags;

  public OciProxyResource() {
  }


}
