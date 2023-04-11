package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

import static com.yanchware.fractal.sdk.valueobjects.ComponentType.CAAS_PROMETHEUS;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * <p>
 * Builder class to represent a Prometheus component.
 * </p>
 * <br>
 * <p>
 * For more details about creating a Prometheus component using Fractal Cloud check out
 * our <a href="https://fractal.cloud/docs/docs-ht-create-prometheus">documentation page</a>
 * </p>
 */
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

    /**
     * Namespace where prometheus will be instantiated
     *
     * @param namespace
     */
    public PrometheusBuilder withNamespace(String namespace) {
      component.setNamespace(namespace);
      return builder;
    }

    /**
     * The id of the container platform where prometheus will be instantiated
     *
     * @param containerPlatform
     */
    public PrometheusBuilder withContainerPlatform(String containerPlatform) {
      component.setContainerPlatform(containerPlatform);
      return builder;
    }

    /**
     * If specified, Prometheus will be made available at the URL specified below. For example, if the parameter is set to
     * <i>api.yourdomain.com</i>, then Prometheus can be accessed at <i>api.yourdomain.com/prometheus</i>.
     * <p>
     * This applies for all components available (Kibana, Grafana, Alert Manager)
     *
     * @param apiGatewayUrl
     */
    public PrometheusBuilder withApiGatewayUrl(String apiGatewayUrl) {
      component.setApiGatewayUrl(apiGatewayUrl);
      return builder;
    }

    @Override
    public CaaSPrometheus build() {
      component.setType(CAAS_PROMETHEUS);
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
