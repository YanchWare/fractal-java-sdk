package com.yanchware.fractal.sdk.domain.values;

import com.fasterxml.jackson.annotation.JsonValue;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public record ResourceGroupId(
  @NotNull ResourceGroupType resourceGroupType,
  @NotNull UUID ownerId,
  @NotNull String shortName)
{
  public static ResourceGroupId fromString(String s) {
    var rdIdArr = s.split("/");
    if (rdIdArr.length != 3) {
      throw new IllegalArgumentException("Invalid resource group id");
    }

    return new ResourceGroupId(ResourceGroupType.valueOf(rdIdArr[0].toUpperCase()),  UUID.fromString(rdIdArr[1]), rdIdArr[2]);
  }

  @NotNull
  @Override
  @JsonValue
  public String toString() {
    return String.format("%s/%s/%s", resourceGroupType.getValue(), ownerId.toString(), shortName);
  }

  @Override
  public boolean equals(Object o) {
    if (o == this){
      return true;
    }
    if (!(o instanceof ResourceGroupId)) {
      return false;
    }

    ResourceGroupId other = (ResourceGroupId)o;
    return this.resourceGroupType.getValue().equals(other.resourceGroupType.getValue())
      && this.ownerId.toString().equals(other.ownerId.toString())
      && this.shortName.equals(other.shortName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(resourceGroupType.getValue(), ownerId.toString(), shortName);
  }


}
