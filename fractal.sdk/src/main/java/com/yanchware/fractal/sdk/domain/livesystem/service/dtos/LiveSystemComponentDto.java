package com.yanchware.fractal.sdk.domain.livesystem.service.dtos;

import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.services.contracts.ComponentDto;
import com.yanchware.fractal.sdk.utils.ReflectionUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

import static com.yanchware.fractal.sdk.configuration.Constants.COMPONENT_TYPE;
import static java.util.Collections.emptyMap;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class LiveSystemComponentDto extends ComponentDto {
    private LiveSystemComponentStatusDto status;
    private Map<String, Object> outputFields;
    private Date lastUpdated;
    private ProviderType provider;
    private String lastOperationStatusMessage;

    public static LiveSystemComponentDtoBuilder builder() {
        return new LiveSystemComponentDtoBuilder();
    }

    public static class LiveSystemComponentDtoBuilder extends Builder<LiveSystemComponentDto, LiveSystemComponentDtoBuilder> {

        @Override
        protected LiveSystemComponentDto createComponent() {
            return new LiveSystemComponentDto();
        }

        @Override
        protected LiveSystemComponentDtoBuilder getBuilder() {
            return this;
        }

        public LiveSystemComponentDtoBuilder withStatus(LiveSystemComponentStatusDto status) {
            componentDto.setStatus(status);
            return builder;
        }

        public LiveSystemComponentDtoBuilder withOutputFields(Map<String, Object> outputFields) {
            if (componentDto.getOutputFields() == null) {
                componentDto.setOutputFields(new HashMap<>());
            }

            componentDto.getOutputFields().putAll(outputFields);

            return builder;
        }

        public LiveSystemComponentDtoBuilder withLastUpdated(Date lastUpdated) {
            componentDto.setLastUpdated(lastUpdated);
            return builder;
        }

        public LiveSystemComponentDtoBuilder withProvider(ProviderType provider) {
            componentDto.setProvider(provider);
            return builder;
        }
    }
}
