package com.yanchware.fractal.sdk.domain.entities.environment;

public record CaaRecordData(byte flags, String tag, String value) {
  private static final String FLAGS_FIELD_NAME = "flags";
  private static final String TAG_FIELD_NAME = "tag";
  private static final String VALUE_FIELD_NAME = "value";
}
