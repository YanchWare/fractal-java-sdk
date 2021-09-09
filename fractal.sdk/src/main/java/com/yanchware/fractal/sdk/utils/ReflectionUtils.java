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
        Map<String, Object> fieldValueMap = new HashMap<>();
        Map<String, Object> parametersMap = new HashMap<>();
        Class<?> clazz = component.getClass();
        while (clazz.getSuperclass() != null) {
            log.info("Class under: {}, fields: {}", clazz.getSimpleName(), clazz.getDeclaredFields());

            boolean isBlueprintComponent = Arrays.stream(clazz.getInterfaces()).anyMatch(x -> x.isAssignableFrom(BlueprintComponent.class));
            boolean isLiveSystemComponent = Arrays.stream(clazz.getInterfaces()).anyMatch(x -> x.isAssignableFrom(LiveSystemComponent.class));
            boolean isComponent = Arrays.stream(clazz.getInterfaces()).anyMatch(x -> x.isAssignableFrom(Validatable.class));

            for (Field f : clazz.getDeclaredFields()) {
                f.setAccessible(true);
                try {
                    if (isBlueprintComponent) {
                        fieldValueMap.put("blueprintType", f.get(component));
                    } else if (isLiveSystemComponent) {
                        if (isFieldTypeConstant(f)) {
                            fieldValueMap.put("liveSystemType", f.get(component));
                        } else {
                            fieldValueMap.put(f.getName(), f.get(component));
                        }
                    } else if (isComponent) {
                        //trying to avoid private constants
                        if (!isFieldPrivateStatic(f)) {
                            fieldValueMap.put(f.getName(), f.get(component));
                        }
                    } else {
                        if (!isFieldPrivateStatic(f)) {
                            parametersMap.put(f.getName(), f.get(component).toString());
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

    private static boolean isFieldPrivateStatic(Field f) {
        return Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers());
    }

    private static boolean isFieldTypeConstant(Field f) {
        return Modifier.isPublic(f.getModifiers()) && Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()) && f.getName().equals("TYPE");
    }
}
