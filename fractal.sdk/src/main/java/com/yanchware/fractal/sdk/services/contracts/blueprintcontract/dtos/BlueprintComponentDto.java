package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.ComponentLink;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.utils.ReflectionUtils;
import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.yanchware.fractal.sdk.configuration.Constants.*;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toSet;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Slf4j
public class BlueprintComponentDto extends ComponentDto {
    private Set<String> outputFields;

    public static List<BlueprintComponentDto> fromLiveSystemComponents(Collection<LiveSystemComponent> lsComponents) {
        List<BlueprintComponentDto> blueprintComponentDtoList = new ArrayList<>();
        for(LiveSystemComponent comp : lsComponents) {
            List<Map<String, Object>> listOfComponents = ReflectionUtils.buildComponents(comp);
            for(var component : listOfComponents) {
                BlueprintComponentDto componentDto = toBlueprintComponentDto(component);
                blueprintComponentDtoList.add(componentDto);
            }
        }
        return blueprintComponentDtoList;
    }

    private static BlueprintComponentDto toBlueprintComponentDto(Map<String, Object> allFields) {
        String liveSystemId = ((ComponentId) allFields.get(ID_KEY)).getValue();
        Set<ComponentId> componentIds = (Set<ComponentId>) allFields.get(DEPENDENCIES_KEY);
        return BlueprintComponentDto.builder()
                .id(liveSystemId)
                .displayName(String.valueOf(allFields.get(DISPLAY_NAME_KEY)))
                .description(String.valueOf(allFields.get(DESCRIPTION_KEY)))
                .type(String.valueOf(allFields.get(BLUEPRINT_TYPE)))
                .version(String.valueOf(allFields.get(VERSION_KEY)))
                .parameters((Map<String, Object>) allFields.get(PARAMETERS_KEY))
                .dependencies(componentIds.stream().map(ComponentId::getValue).collect(toSet()))
                .links((Set<ComponentLink>) allFields.get(LINKS_KEY))
                .outputFields(emptySet())
                .build();
    }
}
