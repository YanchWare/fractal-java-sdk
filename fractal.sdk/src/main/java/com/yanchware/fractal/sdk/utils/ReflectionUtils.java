package com.yanchware.fractal.sdk.utils;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ReflectionUtils {

    public static Map<String, Object> getAllFields(LiveSystemComponent component) {
        Class<?> clazz = component.getClass();
        Map<String, Object> fieldValueMap = new HashMap<>();
        while (Arrays.stream(clazz.getInterfaces()).noneMatch(x -> x.isAssignableFrom(Validatable.class))) {
            log.info("Class under: {}, fields: {}", clazz.getSimpleName(), clazz.getDeclaredFields());
            boolean isBlueprintComponent = Arrays.stream(clazz.getInterfaces()).anyMatch(x -> x.isAssignableFrom(BlueprintComponent.class));
            tryPutFieldsInMap(fieldValueMap, clazz.getDeclaredFields(), component, isBlueprintComponent);
            clazz = clazz.getSuperclass();
        }
        log.info("Final class: {}, fields: {}", clazz.getSimpleName(), clazz.getDeclaredFields());
        tryPutFieldsInMap(fieldValueMap, clazz.getDeclaredFields(), component, false);
        log.info("Final map: {}", fieldValueMap);
        return fieldValueMap;
    }

    public static Map<String, Object> getAllFields2(LiveSystemComponent component) {
        Class<?> clazz = component.getClass();
        Map<String, Object> fieldValueMap = new HashMap<>();
        Map<String, Object> parametersMap = new HashMap<>();
        while (clazz.getSuperclass() != null) {
            log.info("Class under: {}, fields: {}", clazz.getSimpleName(), clazz.getDeclaredFields());

            //extract type
            boolean isBlueprintComponent = Arrays.stream(clazz.getInterfaces()).anyMatch(x -> x.isAssignableFrom(BlueprintComponent.class));
            //is component
            boolean isComponent = Arrays.stream(clazz.getInterfaces()).anyMatch(x -> x.isAssignableFrom(Validatable.class));

            for (Field f : clazz.getDeclaredFields()) {
                f.setAccessible(true);
                try {
                    if (isBlueprintComponent) {
                        fieldValueMap.put("blueprintType", f.get(component));
                    } else if (isComponent) {
                        //trying to avoid private constants
                        if (!(Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()))) {
                            fieldValueMap.put(f.getName(), f.get(component));
                        }
                    } else {
                        if (!(Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()))) {
                            parametersMap.put(f.getName(), f.get(component));
                        }
                    }
                } catch (IllegalAccessException e) {
                    log.error("Error trying to access field", e);
                }
            }
            clazz = clazz.getSuperclass();
        }
        fieldValueMap.put("parameters", parametersMap);
        log.info("Final class: {}, fields: {}", clazz.getSimpleName(), clazz.getDeclaredFields());
        log.info("Final map: {}", fieldValueMap);
        return fieldValueMap;
    }

    private static void tryPutFieldsInMap(Map<String, Object> fieldValueMap, Field[] fields, Object component, boolean isBlueprintComponent) {
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                if (isBlueprintComponent) {
                    fieldValueMap.put("blueprintType", f.get(component));
                    continue;
                }
                //trying to avoid private constants
                if (!(Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()))) {
                    fieldValueMap.put(f.getName(), f.get(component));
                }
            } catch (IllegalAccessException e) {
                log.error("Error trying to access field", e);
            }
        }
    }
}
