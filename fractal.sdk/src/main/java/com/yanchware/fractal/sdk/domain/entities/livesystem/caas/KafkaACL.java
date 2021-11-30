package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class KafkaACL {
    private KafkaResource resource;
    private ACLOperation operation;
}
