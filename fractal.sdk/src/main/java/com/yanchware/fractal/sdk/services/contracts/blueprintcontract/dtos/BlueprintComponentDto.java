package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.utils.ReflectionUtils;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptySet;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor()
@Slf4j
public class BlueprintComponentDto extends ComponentDto {
    private Set<String> outputFields;

    public static BlueprintComponentDto fromLiveSystemComponent(LiveSystemComponent liveSystemComponent) {
        Map<String, Object> allFields = ReflectionUtils.getAllFields(liveSystemComponent);
        String liveSystemId = ((ComponentId) allFields.get("id")).getValue();
        return BlueprintComponentDto.builder()
                .id(liveSystemId)
                .displayName(String.valueOf(allFields.get("displayName")))
                .description(String.format("Blueprint created via SDK by LiveSystem with ID: %s", liveSystemId))
                .type(String.valueOf(allFields.get("blueprintType")))
                .version("")
                .parameters((Map<String, Object>) allFields.get("parameters"))
                .dependencies((Set<String>) allFields.get("dependencies"))
                .links((Set<String>) allFields.get("links"))
                .outputFields(emptySet())
                .build();
    }
}
