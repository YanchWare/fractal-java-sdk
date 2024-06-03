package com.yanchware.fractal.sdk.services.contracts.environmentscontract.dtos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yanchware.fractal.sdk.aggregates.Environment;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.EnvironmentIdDto;
import com.yanchware.fractal.sdk.utils.SerializationUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class EnvironmentResponse {
  private EnvironmentIdDto id;
  private String name;
  private Collection<UUID> resourceGroups;
  private Map<String, Object> parameters;
  private String status;
  private Date createdAt;
  private String createdBy;
  private Date updatedAt;
  private String updatedBy;

  /**
   * Compares this environment response with another environment object.
   *
   * @param environment the environment object to compare with
   * @return true if the environments are equal, false otherwise
   */
  public boolean equalsTo(Environment environment) {
    if (environment == null) return false;
    
    return Objects.equals(id.getType().toString(), environment.getEnvironmentType().toString()) &&
        Objects.equals(id.getOwnerId(), environment.getOwnerId()) &&
        Objects.equals(id.getShortName(), environment.getShortName()) &&
        Objects.equals(name, environment.getName()) &&
        Objects.equals(resourceGroups, environment.getResourceGroups()) &&
        mapsEqual(parameters, environment.getParameters());
  }

  /**
   * Compares two maps for equality using JSON serialization.
   *
   * @param map1 the first map to compare
   * @param map2 the second map to compare
   * @return true if the maps are equal, false otherwise
   */
  private boolean mapsEqual(Map<String, Object> map1, Map<String, Object> map2) {
    try {
      String json1 = SerializationUtils.serializeSortedJson(map1);
      String json2 = SerializationUtils.serializeSortedJson(map2);
      return json1.equals(json2);
    } catch (JsonProcessingException e) {
      return false;
    }
  }
}

