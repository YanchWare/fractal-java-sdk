package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.Validatable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Getter
public class TraefikTracing implements Validatable {
  private JaegerTracing jaeger;

  public static TraefikTracingBuilder builder() {
    return new TraefikTracingBuilder();
  }

  public static class TraefikTracingBuilder {
    private final TraefikTracing tracing;
    private final TraefikTracingBuilder builder;

    public TraefikTracingBuilder() {
      this.tracing = new TraefikTracing();
      this.builder = this;
    }

    public TraefikTracingBuilder withJaeger(JaegerTracing jaegerTracing) {
      tracing.jaeger = jaegerTracing;
      return builder;
    }

    public TraefikTracing build() {
      var errors = tracing.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format("TraefikTracingBuilder validation failed. Errors: %s",
            Arrays.toString(errors.toArray())));
      }

      return tracing;
    }
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (jaeger == null) {
      errors.add("JaegerTracing instance cannot be null.");
    }

    return errors;
  }
}
