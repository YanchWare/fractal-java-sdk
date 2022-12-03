package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PROMETHEUS;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class CaaSPrometheus extends CaaSMonitoringImpl implements LiveSystemComponent {
    private final static String API_GATEWAY_URL_IS_BLANK = "[CaaSPrometheus Validation] API Gateway URL has not been defined and it is required";

    private String apiGatewayUrl;

    public static PrometheusBuilder builder() {
        return new PrometheusBuilder();
    }

    public static class PrometheusBuilder extends Builder<CaaSPrometheus, PrometheusBuilder> {

        @Override
        protected CaaSPrometheus createComponent() {
            return new CaaSPrometheus();
        }

        @Override
        protected PrometheusBuilder getBuilder() {
            return this;
        }

        public PrometheusBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public PrometheusBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        public PrometheusBuilder withApiGatewayUrl(String apiGatewayUrl) {
            component.setApiGatewayUrl(apiGatewayUrl);
            return builder;
        }

        @Override
        public CaaSPrometheus build() {
            component.setType(PROMETHEUS);
            return super.build();
        }
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        if (isBlank(apiGatewayUrl)) {
            errors.add(API_GATEWAY_URL_IS_BLANK);
        }

        return errors;
    }
}
