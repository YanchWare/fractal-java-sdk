package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


@Getter
public class JaegerTracing implements Validatable {

  private String collectorEndpoint;
  private Integer localAgentPort;
  private String localAgentUrlPath;

  public static JaegerTracingBuilder builder() {
    return new JaegerTracingBuilder();
  }

  public static class JaegerTracingBuilder {
    private final JaegerTracing jaeger;
    private final JaegerTracingBuilder builder;

    public JaegerTracingBuilder() {
      this.jaeger = new JaegerTracing();
      this.builder = this;
    }

    public JaegerTracingBuilder withCollectorEndpoint(String collectorEndpoint) {
      jaeger.collectorEndpoint = collectorEndpoint;
      return builder;
    }
    

    public JaegerTracingBuilder withLocalAgentPort(Integer localAgentPort) {
      jaeger.localAgentPort = localAgentPort;
      return builder;
    }

    public JaegerTracingBuilder withLocalAgentUrlPath(String localAgentUrlPath) {
      jaeger.localAgentUrlPath = localAgentUrlPath;
      return builder;
    }

    public JaegerTracing build() {
      var errors = jaeger.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format("JaegerTracingBuilder validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return jaeger;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (collectorEndpoint != null && !collectorEndpoint.isEmpty()) {
      if (localAgentPort != null) {
        errors.add("localAgentPort should not be provided when collectorEndpoint is provided");
      }
      if (localAgentUrlPath != null && !localAgentUrlPath.isEmpty()) {
        errors.add("localAgentUrlPath should not be provided when collectorEndpoint is provided");
      }
    } else {
      if (localAgentUrlPath == null || localAgentUrlPath.isEmpty()) {
        errors.add("localAgentUrlPath must be defined");
      }
     
      if (localAgentPort == null) {
        errors.add("localAgentPort must be defined");
      } else if (localAgentPort < 1 || localAgentPort > 65535) {
        errors.add("localAgentPort must be between 1 and 65535");
      }
    }

    return errors;
  }
}
