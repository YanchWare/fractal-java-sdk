package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public abstract class ExtendableEnum<T extends ExtendableEnum<T>> {
  private static final Map<Class<?>, ConcurrentHashMap<String, ? extends ExtendableEnum<?>>> VALUES = new ConcurrentHashMap<>();

  private String name;
  
  @JsonIgnore
  private Class<T> clazz;

  protected static <T extends ExtendableEnum<T>> T fromString(String name, Class<T> clazz) {
    if (name == null) {
      return null;
    } else {
      var clazzValues = (ConcurrentHashMap) VALUES.computeIfAbsent(clazz,
          (key) -> new ConcurrentHashMap());

      var value = (ExtendableEnum) clazzValues.get(name);
      if (value != null) {
        return (T)value;
      } else {
        try {
          value = clazz.getDeclaredConstructor().newInstance();
          return (T)value.nameAndAddValue(name, value, clazz);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException var5) {
          return null;
        }
      }
    }
  }

  T nameAndAddValue(String name, T value, Class<T> clazz) {
    this.name = name;
    this.clazz = clazz;
    ((ConcurrentHashMap) VALUES.get(clazz)).put(name, value);
    return (T)this;
  }

  protected static <T extends ExtendableEnum<T>> Collection<T> values(Class<T> clazz) {
    return new ArrayList(VALUES.getOrDefault(clazz, new ConcurrentHashMap<>()).values());
  }

  @JsonValue
  public String toString() {
    return this.name;
  }

  public int hashCode() {
    return Objects.hash(this.clazz, this.name);
  }

  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    } else if (this.clazz != null && this.clazz.isAssignableFrom(obj.getClass())) {
      if (obj == this) {
        return true;
      } else if (this.name == null) {
        return ((ExtendableEnum<?>) obj).name == null;
      } else {
        return this.name.equals(((ExtendableEnum<?>) obj).name);
      }
    } else {
      return false;
    }
  }
}
