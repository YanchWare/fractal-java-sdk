package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.appservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public final class AzureConnectionStringType extends ExtendableEnum<AzureConnectionStringType> {
  public static final AzureConnectionStringType API_HUB = fromString("ApiHub");
  public static final AzureConnectionStringType CUSTOM = fromString("Custom");
  public static final AzureConnectionStringType DOC_DB = fromString("DocDb");
  public static final AzureConnectionStringType EVENT_HUB = fromString("EventHub");
  public static final AzureConnectionStringType MY_SQL = fromString("MySql");
  public static final AzureConnectionStringType NOTIFICATION_HUB = fromString("NotificationHub");
  public static final AzureConnectionStringType POSTGRE_SQL = fromString("PostgreSQL");
  public static final AzureConnectionStringType REDIS_CACHE = fromString("RedisCache");
  public static final AzureConnectionStringType SQL_AZURE = fromString("SQLAzure");
  public static final AzureConnectionStringType SQL_SERVER = fromString("SQLServer");
  public static final AzureConnectionStringType SERVICE_BUS = fromString("ServiceBus");

  public AzureConnectionStringType() {
  }

  @JsonCreator
  public static AzureConnectionStringType fromString(String name) {
    return fromString(name, AzureConnectionStringType.class);
  }

  public static Collection<AzureConnectionStringType> values() {
    
    return values(AzureConnectionStringType.class);
  }
}
