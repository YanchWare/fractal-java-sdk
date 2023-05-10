package com.yanchware.fractal.sdk.domain.entities.environment;

public record MxRecordData(int priority, String mailExchange) {
  private static final String PRIORITY_FIELD_NAME = "priority";
  private static final String MAIL_EXCHANGE_FIELD_NAME = "mailExchange";
}
