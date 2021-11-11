package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KafkaResource {
    private KafkaACLType type;
    private String name;
    private KafkaACLPatternType patternType;
}