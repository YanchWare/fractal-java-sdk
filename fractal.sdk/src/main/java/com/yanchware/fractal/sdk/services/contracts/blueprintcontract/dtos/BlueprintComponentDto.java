package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.utils.ReflectionUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Slf4j
public class BlueprintComponentDto extends ComponentDto {
    private Map<String, Object> outputFields;

    public static BlueprintComponentDto fromLiveSystemComponent(LiveSystemComponent liveSystemComponent) {
        ReflectionUtils.findBlueprintComponentType(liveSystemComponent);
        Map<String, Object> allFields = ReflectionUtils.getAllFields(liveSystemComponent);
        /*ObjectMapper m = new ObjectMapper();
        Map<String, Object> mappedObject = m.convertValue(component, new TypeReference<>() {
        });*/
        return BlueprintComponentDto.builder()
                .id(String.valueOf(allFields.get("id")))
                .displayName(String.valueOf(allFields.get("displayName")))
                .description(String.valueOf(allFields.get("description")))
                .type(String.valueOf(allFields.get("type")))
                .version("0.0.1")
                .parameters(mappedObject)
                .build();

        return null;
    }
}
