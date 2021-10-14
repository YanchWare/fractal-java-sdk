package com.yanchware.fractal.sdk.utils;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import com.yanchware.fractal.sdk.domain.entities.blueprint.BlueprintComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import static com.yanchware.fractal.sdk.configuration.Constants.BLUEPRINT_TYPE;
import static com.yanchware.fractal.sdk.configuration.Constants.LIVESYSTEM_TYPE;

@Slf4j
public class ReflectionUtils {

    public static List<Map<String, Object>> buildComponents(LiveSystemComponent component) {
        return buildComponents(component, null);
    }

    //loop through all fields of this component
    //if fields are components, build them
    //add all to a list including this component
    private static List<Map<String, Object>> buildComponents(LiveSystemComponent component, String dependencyId) {
        List<Map<String, Object>> listOfComponentJson = new ArrayList<>();
        Map<String, Object> allFields = getAllFields(component);
        if(dependencyId != null) {
            allFields.put("dependencies", Set.of(dependencyId));
        }
        listOfComponentJson.add(allFields);
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
                            log.debug("compo: {}", o);
                            if (o == null) {
                                log.debug("Field '{}' of component '{}' is null. Will skipp.", f, component);
                                continue;
                            }
                            List<LiveSystemComponent> listOfComp = (List<LiveSystemComponent>) o;
                            for (LiveSystemComponent comp : listOfComp) {
                                log.debug("Component to map: {}, with SuperComp: {}", comp.getClass().getSimpleName(), component.getClass().getSimpleName());
                                listOfComponentJson.addAll(buildComponents(comp, getIdOfComponent(component)));
                            }
                        } catch (IllegalAccessException e) {
                            log.error("Error when trying to map field '{}' for class '{}", f, clazz, e);
                        }
                    }
                } else {
                    var fieldClazzUnder = determineClassType(f.getType());
                    if (fieldClazzUnder.isLiveSystemComponent()) {
                        try {
                            Object o = f.get(component);
                            if (o == null) {
                                log.debug("Field '{}' of component '{}' is null. Will skipp.", f, component);
                                continue;
                            }
                            LiveSystemComponent comp = (LiveSystemComponent) o;
                            log.debug("Component to map: {}, with SuperComp: {}", comp.getClass().getSimpleName(), component.getClass().getSimpleName());
                            listOfComponentJson.addAll(buildComponents(comp, getIdOfComponent(component)));
                        } catch (IllegalAccessException e) {
                            log.error("Error when trying to map field '{}' for class '{}", f, clazz, e);
                        }
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return listOfComponentJson;
    }

    public static Map<String, Object> getAllFields(LiveSystemComponent component) {
        Map<String, Object> fieldValueMap = new HashMap<>();
        Map<String, Object> parametersMap = new HashMap<>();
        Class<?> clazz = component.getClass();
        while (clazz.getSuperclass() != null) {
            log.debug("Class under: {}, fields: {}", clazz.getSimpleName(), clazz.getDeclaredFields());
            var classUnder = determineClassType(clazz);

            for (Field f : clazz.getDeclaredFields()) {
                f.setAccessible(true);
                if (isFieldTypeComponent(f)) {
                    log.debug("Field '{}' is a component. Skipped.", f);
                    continue;
                }
                try {
                    if (classUnder.isBlueprintComponent()) {
                        handleParams(component, fieldValueMap, parametersMap, f, BLUEPRINT_TYPE);
                    } else if (classUnder.isLiveSystemComponent()) {
                        handleParams(component, fieldValueMap, parametersMap, f, LIVESYSTEM_TYPE);
                    } else if (classUnder.isValidatable() && !isFieldPrivateStaticFinal(f)) {
                        Object componentObj = f.get(component);
                        if (componentObj == null) {
                            log.debug("Field '{}' of component '{}' is null. Will skipp.", f, component);
                            continue;
                        }
                        if (componentObj.getClass().isEnum()) {
                            fieldValueMap.put(f.getName(), componentObj.toString());
                        } else {
                            fieldValueMap.put(f.getName(), componentObj);
                        }
                    } else {
                        //for now, we don't have a "TYPE" in other classes except ones that implement BlueprintComponent or LiveSystemComponent
                        handleParams(component, fieldValueMap, parametersMap, f, null);
                    }
                } catch (IllegalAccessException e) {
                    log.error("Error trying to access field: {}", f, e);
                }
            }
            clazz = clazz.getSuperclass();
        }
        fieldValueMap.put("parameters", parametersMap);
        return fieldValueMap;
    }

    private static String getIdOfComponent(LiveSystemComponent component) {
        Class<?> clazz = component.getClass();
        while (clazz.getSuperclass() != null) {
            try {
                Field idField = clazz.getDeclaredField("id");
                idField.setAccessible(true);
                ComponentId id = (ComponentId) idField.get(component);
                return id.getValue();
            } catch (Exception e) {
                log.debug("No id found yet.");
            }
            clazz = clazz.getSuperclass();
        }
        log.debug("No ID found in component: {}", component);
        return null;
    }

    private static void handleParams(LiveSystemComponent component, Map<String, Object> fieldValueMap, Map<String, Object> parametersMap, Field f, String type) throws IllegalAccessException {
        if (f.getType() == ProviderType.class) {
            log.debug("Found a provider type: {}, {}", f, component);
            return;
        }
        Object componentObject = f.get(component);
        if (componentObject == null) {
            log.debug("Field '{}' of component '{}' is null. Will skipp.", f, component);
            return;
        }
        if (isFieldTypeConstant(f)) {
            fieldValueMap.put(type, componentObject);
            return;
        }
        if (isFieldPrivateStaticFinal(f)) {
            log.debug("Field '{}' is private-static-final, so will be ignored.", f);
            return;
        }
        if (componentObject.getClass().isEnum()) {
            parametersMap.put(f.getName(), componentObject.toString());
        } else {
            parametersMap.put(f.getName(), componentObject);
        }
    }

    //private constants
    private static boolean isFieldPrivateStaticFinal(Field f) {
        int fieldModifiers = f.getModifiers();
        return Modifier.isPrivate(fieldModifiers) && Modifier.isStatic(fieldModifiers) && Modifier.isFinal(fieldModifiers);
    }

    private static boolean isFieldTypeConstant(Field f) {
        int fieldModifiers = f.getModifiers();
        return Modifier.isPublic(fieldModifiers) && Modifier.isStatic(fieldModifiers) && Modifier.isFinal(fieldModifiers) && f.getName().equals("TYPE");
    }

    //determine type of class. For now, we can break exit quickly, as we don't implement multiple interfaces.
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
