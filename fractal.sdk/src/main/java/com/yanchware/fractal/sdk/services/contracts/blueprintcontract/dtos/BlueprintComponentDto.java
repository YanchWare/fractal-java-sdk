package com.yanchware.fractal.sdk.services.contracts.blueprintcontract.dtos;

import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.LiveSystemComponent;
import com.yanchware.fractal.sdk.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.utils.ReflectionUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.yanchware.fractal.sdk.configuration.Constants.BLUEPRINT_TYPE;
import static java.util.Collections.emptySet;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Slf4j
public class BlueprintComponentDto extends ComponentDto {
    private Set<String> outputFields;

    public static BlueprintComponentDtoBuilder builder() {
        return new BlueprintComponentDtoBuilder();
    }

    public static class BlueprintComponentDtoBuilder extends Builder<BlueprintComponentDto, BlueprintComponentDtoBuilder> {

        @Override
        protected BlueprintComponentDto createComponent() {
            return new BlueprintComponentDto();
        }

        @Override
        protected BlueprintComponentDtoBuilder getBuilder() {
            return this;
        }

        public BlueprintComponentDtoBuilder withOutputFields(Collection<? extends String> outputFields) {
            if (componentDto.getOutputFields() == null) {
                componentDto.setOutputFields(new HashSet<>());
            }

            componentDto.getOutputFields().addAll(outputFields);

            return builder;
        }
    }

    public static List<BlueprintComponentDto> fromLiveSystemComponents(Collection<LiveSystemComponent> components) {
        List<BlueprintComponentDto> blueprintComponentDtoList = new ArrayList<>();
        for (LiveSystemComponent component : components) {
            List<Map<String, Object>> listOfComponents = ReflectionUtils.buildComponents(component);
            for (var componentMap : listOfComponents) {
                BlueprintComponentDto componentDto = BlueprintComponentDto.toBlueprintComponentDto(componentMap);
                blueprintComponentDtoList.add(componentDto);
            }
        }
        return blueprintComponentDtoList;
    }

    private static BlueprintComponentDto toBlueprintComponentDto(Map<String, Object> allFields) {
        return BlueprintComponentDto.builder()
                .withFields(allFields)
                .withType(String.valueOf(allFields.get(BLUEPRINT_TYPE)))
                .withOutputFields(emptySet())
                .build();
    }
}
