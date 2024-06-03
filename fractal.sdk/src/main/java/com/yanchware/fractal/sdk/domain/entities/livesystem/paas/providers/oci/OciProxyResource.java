package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.oci;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
@Getter
@AllArgsConstructor
public class OciProxyResource {
  private String name;
  private OciRegion region;
  private Map<String, String> tags;

  public OciProxyResource() {
  }


}
