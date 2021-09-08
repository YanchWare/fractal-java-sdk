package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.utils.ReflectionUtils;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
public class BlueprintComponentDto extends ComponentDto {
    private Map<String, Object> outputFields;

    public static BlueprintComponentDto fromLiveSystemComponent(LiveSystemComponent liveSystemComponent) {
        Map<String, Object> allFields = ReflectionUtils.getAllFields(liveSystemComponent);
        return BlueprintComponentDto.builder()
                .id(((ComponentId) allFields.get("id")).getValue())
                .displayName(String.valueOf(allFields.get("displayName"))) //TODO: this is sitll displayName fo LiveSystem I guess
                .description(String.valueOf(allFields.get("description"))) //TODO: this is still description of LiveSystem
                .type(String.valueOf(allFields.get("blueprintType")))
                .version("0.0.1")
                .parameters((Map<String, Object>) allFields.get("parameters"))
                .dependencies(emptySet())
                .links(emptySet())
                .outputFields(emptyMap())
                .build();
    }
}
