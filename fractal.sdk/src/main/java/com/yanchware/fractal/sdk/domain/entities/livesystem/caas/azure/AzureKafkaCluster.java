package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.KafkaCluster;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;
import static com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType.AZURE;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KAFKA;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class AzureKafkaCluster extends KafkaCluster {

    public static AzureKafkaClusterBuilder builder() {
        return new AzureKafkaClusterBuilder();
    }


    @Override
    public ProviderType getProvider() {
        return AZURE;
    }

    public static class AzureKafkaClusterBuilder extends Builder<AzureKafkaCluster, AzureKafkaClusterBuilder> {

        @Override
        protected AzureKafkaCluster createComponent() {
            return new AzureKafkaCluster();
        }

        @Override
        protected AzureKafkaClusterBuilder getBuilder() {
            return this;
        }

        @Override
        public AzureKafkaCluster build() {
            component.setType(KAFKA);
            component.setVersion(DEFAULT_VERSION);
            return super.build();
        }
    }
}
