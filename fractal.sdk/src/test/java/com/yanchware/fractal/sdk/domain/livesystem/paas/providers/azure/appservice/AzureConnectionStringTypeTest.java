package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AzureConnectionStringTypeTest {
  @Test
  public void shouldReturnProperValue() {
    assertAll(
        () -> assertEquals(AzureConnectionStringType.fromString("ApiHub"), AzureConnectionStringType.API_HUB),
        () -> assertEquals(AzureConnectionStringType.fromString("Custom"), AzureConnectionStringType.CUSTOM),
        () -> assertEquals(AzureConnectionStringType.fromString("DocDb"), AzureConnectionStringType.DOC_DB),
        () -> assertEquals(AzureConnectionStringType.fromString("EventHub"), AzureConnectionStringType.EVENT_HUB),
        () -> assertEquals(AzureConnectionStringType.fromString("MySql"), AzureConnectionStringType.MY_SQL),
        () -> assertEquals(AzureConnectionStringType.fromString("NotificationHub"), AzureConnectionStringType.NOTIFICATION_HUB),
        () -> assertEquals(AzureConnectionStringType.fromString("PostgreSQL"), AzureConnectionStringType.POSTGRE_SQL),
        () -> assertEquals(AzureConnectionStringType.fromString("RedisCache"), AzureConnectionStringType.REDIS_CACHE),
        () -> assertEquals(AzureConnectionStringType.fromString("SQLAzure"), AzureConnectionStringType.SQL_AZURE),
        () -> assertEquals(AzureConnectionStringType.fromString("SQLServer"), AzureConnectionStringType.SQL_SERVER),
        () -> assertEquals(AzureConnectionStringType.fromString("ServiceBus"), AzureConnectionStringType.SERVICE_BUS)
    );
  }
}