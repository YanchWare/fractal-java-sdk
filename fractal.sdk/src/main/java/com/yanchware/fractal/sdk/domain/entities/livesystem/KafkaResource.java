package com.yanchware.fractal.sdk.domain.entities.livesystem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KafkaResource {
    private String type;
    private String name;
    private String patternType;
}
