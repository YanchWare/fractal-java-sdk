package com.yanchware.fractal.sdk.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yanchware.fractal.sdk.domain.Validatable;
import com.yanchware.fractal.sdk.domain.blueprint.BlueprintComponent;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.*;
import java.util.*;

import static com.yanchware.fractal.sdk.configuration.Constants.BLUEPRINT_TYPE;

@Slf4j
public class ReflectionUtils {

  /**
   * Build component list together with dependencies
   * #1 run "dependencyId" is null because we start at the top level of components (in most cases, a ContainerPlatform)
   *
   * @param component component to build from - can result in multiple components, depending on hierarchy
   * @return a list of all components mapped as key-value
   */

  public static List<Map<String, Object>> buildComponents(LiveSystemComponent component) {
    List<Map<String, Object>> listOfComponentJson = new ArrayList<>();

    //get fields of current component
    Map<String, Object> allFields = getAllFields(component);
    listOfComponentJson.add(allFields);

    //start looping through all the fields of all classes in the hierarchy
    Class<?> clazz = component.getClass();
    while (clazz.getSuperclass() != null) {
      for (Field f : clazz.getDeclaredFields()) {
        if (!isFieldTypeComponent(f)) {
          continue;
        }
        f.setAccessible(true);
        if (isFieldCollection(f)) {
          ParameterizedType ptype = (ParameterizedType) f.getGenericType();
          Class<?> typeArgClazz = (Class<?>) ptype.getActualTypeArguments()[0];
          var fieldClazzUnder = determineClassType(typeArgClazz);

          if (fieldClazzUnder.isLiveSystemComponent()) {
            try {
              Object o = f.get(component);
              if (o == null) {
                log.trace("Field '{}' of component '{}' is null. Will skip.", f.getName(),
                  component.getClass().getSimpleName());
                continue;
              }
              List<LiveSystemComponent> listOfComp = (List<LiveSystemComponent>) o;
              for (LiveSystemComponent comp : listOfComp) {
                log.trace("Component to map: {}, with SuperComp: {}", comp.getClass().getSimpleName(),
                  component.getClass().getSimpleName());
                listOfComponentJson.addAll(buildComponents(comp));
              }
            } catch (IllegalAccessException e) {
              log.error("Error when trying to map field '{}' for class '{}", f.getName(), clazz.getSimpleName(), e);
            }
          }
        } else {
          var fieldClazzUnder = determineClassType(f.getType());
          if (fieldClazzUnder.isLiveSystemComponent()) {
            try {
              Object o = f.get(component);
              if (o == null) {
                log.trace("Field '{}' of component '{}' is null. Will skip.", f.getName(),
                  component.getClass().getSimpleName());
                continue;
              }
              LiveSystemComponent comp = (LiveSystemComponent) o;
              log.trace("Component to map: {}, with SuperComp: {}", comp.getClass().getSimpleName(),
                component.getClass().getSimpleName());
              listOfComponentJson.addAll(buildComponents(comp));
            } catch (IllegalAccessException e) {
              log.error("Error when trying to map field '{}' for class '{}", f.getName(), clazz.getSimpleName(), e);
            }
          }
        }
      }
      clazz = clazz.getSuperclass();
    }
    return listOfComponentJson;
  }

  /**
   * Takes in a component and generates a map with generic component fields and parameters.
   * Loop through all fields of a class and skips constants.
   *
   * @param component component to be mapped
   * @return map of all fields and parameters of component
   */
  public static Map<String, Object> getAllFields(LiveSystemComponent component) {
    Map<String, Object> fieldValueMap = new HashMap<>();
    Map<String, Object> parametersMap = new HashMap<>();
    Class<?> clazz = component.getClass();
    while (clazz.getSuperclass() != null) {
      log.trace("Mapping class: {}", clazz.getSimpleName());
      var classUnder = determineClassType(clazz);

      for (Field f : clazz.getDeclaredFields()) {
        f.setAccessible(true);
        if (isFieldTypeComponent(f)) {
          log.trace("Field '{}' is a component. Skipped.", f.getName());
          continue;
        }
        try {
          // New logic to handle specific methods
          handleMethodInvocation(component, parametersMap, "getKind", "kind");
          if (classUnder.isBlueprintComponent()) {
            handleParams(component, fieldValueMap, parametersMap, f, classUnder);
          } else if (classUnder.isLiveSystemComponent()) {
            handleParams(component, fieldValueMap, parametersMap, f, classUnder);
          } else if (classUnder.isValidatable() && !isFieldPrivateStaticFinal(f)) {
            Object componentObj = f.get(component);
            if (componentObj == null) {
              log.trace("Field '{}' of component '{}' is null. Will skip.", f.getName(),
                component.getClass().getSimpleName());
              continue;
            }
            if (componentObj.getClass().isEnum()) {
              fieldValueMap.put(f.getName(), componentObj.toString());
            } else {
              fieldValueMap.put(f.getName(), componentObj);
            }
          } else {
            //for now, we don't have a "TYPE" in other classes except ones that implement BlueprintComponent or
            // LiveSystemComponent
            handleParams(component, fieldValueMap, parametersMap, f, classUnder);
          }
        } catch (IllegalAccessException e) {
          log.error("Error trying to access field: {}", f.getName(), e);
        }
      }
      clazz = clazz.getSuperclass();
    }
    fieldValueMap.put("parameters", parametersMap);
    return fieldValueMap;
  }

  /**
   * Method that will take a field in and determine how to handle it based on the type of the field
   *
   * @param component     that holds the field we are mapping
   * @param fieldValueMap map that holds generic component fields
   * @param parametersMap map that holds parameters of a component
   * @param f             field we are handling
   * @throws IllegalAccessException Illegal Access Exception
   */
  private static void handleParams(
    LiveSystemComponent component,
    Map<String, Object> fieldValueMap,
    Map<String, Object> parametersMap,
    Field f,
    ReflectionClassUnder classUnder) throws IllegalAccessException
  {
    if (f.isAnnotationPresent(JsonIgnore.class)) {
      log.trace("Field '{}' is annotated with @JsonIgnore, so will be ignored.", f.getName());
      return;
    }

    if (f.getType() == ProviderType.class) {
      log.trace("Found a provider type for component: {}", component.getClass().getSimpleName());
      return;
    }

    Object componentObject = f.get(component);
    if (componentObject == null) {
      log.trace("Field '{}' of component '{}' is null. Will skip.", f.getName(), component.getClass().getSimpleName());
      return;
    }

    if (classUnder.isBlueprintComponent() && isFieldTypeConstant(f)) {
      fieldValueMap.put(BLUEPRINT_TYPE, componentObject);
      return;
    }

    if (isFieldPrivateStaticFinal(f)) {
      log.trace("Field '{}' is private-static-final, so will be ignored.", f.getName());
      return;
    }

    if (componentObject.getClass().isEnum()) {
      parametersMap.put(f.getName(), componentObject.toString());
      return;
    }

    parametersMap.put(f.getName(), componentObject);
  }

  // New method to handle invocation of specific methods like getKind
  private static void handleMethodInvocation(
    LiveSystemComponent component,
    Map<String, Object> parametersMap,
    String methodName,
    String paramKey)
  {
    try {
      Method method = component.getClass().getMethod(methodName);
      if (method != null) {
        Object result = method.invoke(component);
        if (result != null) {
          parametersMap.put(paramKey, result);
        }
      }
    } catch (NoSuchMethodException e) {
      log.trace("Method '{}' not found in component: {}", methodName, component.getClass().getSimpleName());
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      log.error("Error invoking method '{}' on component: {}", methodName, component.getClass().getSimpleName(), e);
    }
  }

  /**
   * Determine if a field is private static final (private constant) or not.
   *
   * @param f field to be determined
   * @return true if it is a private static final, false otherwise
   */
  private static boolean isFieldPrivateStaticFinal(Field f) {
    int fieldModifiers = f.getModifiers();
    return Modifier.isPrivate(fieldModifiers) && Modifier.isStatic(fieldModifiers) && Modifier.isFinal(fieldModifiers);
  }

  private static boolean isFieldTypeConstant(Field f) {
    int fieldModifiers = f.getModifiers();
    return Modifier.isPublic(fieldModifiers) && Modifier.isStatic(fieldModifiers) && Modifier.isFinal(fieldModifiers) && f.getName().equals("TYPE");
  }

  /**
   * Loops through all the interface of the class and returns an object that holds said information.
   *
   * @param clazz class to be determined
   * @return a {@link ReflectionClassUnder} object that holds information about what type of class it is
   */
  private static ReflectionClassUnder determineClassType(Class<?> clazz) {
    for (Class<?> aClass : clazz.getInterfaces()) {
      if (aClass.isAssignableFrom(BlueprintComponent.class)) {
        return ReflectionClassUnder.builder().isBlueprintComponent(true).build();
      }
      if (aClass.isAssignableFrom(LiveSystemComponent.class)) {
        return ReflectionClassUnder.builder().isLiveSystemComponent(true).build();
      }
      if (aClass.isAssignableFrom(Validatable.class)) {
        return ReflectionClassUnder.builder().isValidatable(true).build();
      }
    }
    return ReflectionClassUnder.builder().build();
  }

  private static boolean isClassSimpleType(Class<?> clazz) {
    return clazz.isPrimitive() || clazz.isEnum() || clazz.isAssignableFrom(String.class);
  }

  private static boolean isFieldCollection(Field f) {
    return Collection.class.isAssignableFrom(f.getType());
  }

  private static boolean isFieldTypeComponent(Field f) {
    Class<?> fieldClass = f.getType();
    if (isFieldCollection(f)) {
      ParameterizedType ptype = (ParameterizedType) f.getGenericType();
      fieldClass = (Class<?>) ptype.getActualTypeArguments()[0];
    }
    if (isClassSimpleType(fieldClass)) {
      return false;
    }
    var clazzUnder = determineClassType(fieldClass);
    if (clazzUnder.isAnyComponent()) {
      return true;
    }
    while (fieldClass.getSuperclass() != null) {
      var checkClazz = fieldClass.getSuperclass();
      if (determineClassType(checkClazz).isAnyComponent()) {
        return true;
      }
      fieldClass = fieldClass.getSuperclass();
    }
    return false;
  }
}
