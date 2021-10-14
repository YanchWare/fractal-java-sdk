package com.yanchware.fractal.sdk.domain.entities.livesystem;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KafkaACL {
    private KafkaResource resource;
    private String operation;
}
