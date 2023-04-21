package com.yanchware.fractal.sdk.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;

public class Serialization {

  private static ObjectMapper objectMapper;

  public static <T> T deserialize(String jsonString, TypeReference<T> typeRef) throws JsonProcessingException {
    if (objectMapper == null) {
      initializeObjectMapper();
    }

   return objectMapper.readValue(jsonString, typeRef);
  }

  public static <T> T deserialize(String jsonString, Class<T> classRef) throws JsonProcessingException {
    if (objectMapper == null) {
      initializeObjectMapper();
    }

    return objectMapper.readValue(jsonString, classRef);
  }

  public static <T> T deserialize(InputStream is, Class<T> classRef) throws IOException {
    if (objectMapper == null) {
      initializeObjectMapper();
    }

    return objectMapper.readValue(is, classRef);
  }


  public static String serialize(Object obj) throws JsonProcessingException {
    if (objectMapper == null) {
      initializeObjectMapper();
    }

    return objectMapper.writeValueAsString(obj);
  }

  public static JsonNode getJsonNodeFromString(String jsonString) {
    if (objectMapper == null) {
      initializeObjectMapper();
    }

    try {
      return objectMapper.readTree(jsonString);
    } catch (JsonProcessingException e) {
      return null;
    }
  }

  public static ObjectMapper getDefaultObjectMapper() {
    if (objectMapper == null) {
      initializeObjectMapper();
    }

    return objectMapper;
  }

  private static void initializeObjectMapper() {
    objectMapper = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
  }
}
