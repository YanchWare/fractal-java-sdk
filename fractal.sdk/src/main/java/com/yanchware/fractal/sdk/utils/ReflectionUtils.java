package com.yanchware.fractal.sdk.utils;

import com.google.common.reflect.TypeToken;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ReflectionUtils {

    public static void findBlueprintComponentType(LiveSystemComponent liveSystemComponent) {
        var typeTokenLiveSystemComponent = TypeToken.of(liveSystemComponent.getClass()).getTypes();
        typeTokenLiveSystemComponent.stream().filter(type -> type.isSubtypeOf(BlueprintComponent.class)).forEachOrdered(type -> {
            boolean isInstanceOfBlueprint = Arrays.stream(type.getRawType().getInterfaces()).anyMatch(x -> x.isAssignableFrom(BlueprintComponent.class));
            if (isInstanceOfBlueprint) {
                log.info("For class '{}' the equivalent blueprint component type is '{}'", liveSystemComponent.getClass().getSimpleName(), type.getRawType().getSimpleName());
            }
        });
        Field[] declaredFields = liveSystemComponent.getClass().getDeclaredFields();
        log.info("Fields {}", List.of(declaredFields));
        try {
            log.info("Fields {}", liveSystemComponent.getClass().getDeclaredField("serviceRange"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> getAllFields(LiveSystemComponent component) {
        Class<?> clazz = component.getClass();
        Map<String, Object> fieldValueMap = new HashMap<>();
        while (Arrays.stream(clazz.getInterfaces()).noneMatch(x -> x.isAssignableFrom(BlueprintComponent.class))) {
            tryPutFieldsInMap(fieldValueMap, clazz.getDeclaredFields(), component);
            clazz = clazz.getSuperclass();
        }
        tryPutFieldsInMap(fieldValueMap, clazz.getDeclaredFields(), component);
        log.info("Final class: {}, fields: {}", clazz.getSimpleName(), clazz.getFields());
        log.info("Final map: {}", fieldValueMap);
        return fieldValueMap;
    }

    private static void tryPutFieldsInMap(Map<String, Object> fieldValueMap, Field[] fields, Object component) {
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                //trying to avoid constants
                if (!(Modifier.isPrivate(f.getModifiers()) && Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()))) {
                    fieldValueMap.put(f.getName(), f.get(component));
                }
            } catch (IllegalAccessException e) {
                log.error("Error trying to access field", e);
            }
        }
    }
}
