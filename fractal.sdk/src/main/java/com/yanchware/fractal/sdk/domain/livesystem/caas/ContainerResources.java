package com.yanchware.fractal.sdk.domain.livesystem.caas;

import com.yanchware.fractal.sdk.domain.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Getter
@Setter(AccessLevel.PROTECTED)
public class ContainerResources implements Validatable {
  private String cpu;
  private String memory;

  protected ContainerResources() {
  }


  public static ContainerResourcesBuilder builder() {
    return new ContainerResourcesBuilder();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = new ArrayList<>();

    if (isNotBlank(cpu)) {
      if (!cpu.matches("\\d+[A-Za-z]|\\d+\\.\\d+|\\d+")) {
        errors.add("CPU quantity is not in a valid format");
      }

      if (cpu.endsWith("m")) {
        int value = Integer.parseInt(cpu.substring(0, cpu.length() - 1));
        if (value < 1) {
          throw new IllegalArgumentException("CPU quantity in milliCPU ('m') must be at least 1m");
        }
      } else {
        double value = Double.parseDouble(cpu);
        if (value < 0.001) {
          errors.add("CPU quantity must be at least 0.001 CPU (1m) for decimal values");
        }
      }
    }

    if (isNotBlank(memory)) {
      if (!memory.matches("\\d+(\\.\\d+)?[EPTGMk]?i?|\\d+")) {
        errors.add("Memory quantity is not in a valid format");
      }

      // Specific check for the incorrect 'm' suffix usage
      if (memory.toLowerCase().endsWith("m")) {
        errors.add("The 'm' suffix is not valid for memory quantities. Did you mean 'Mi' for mebibytes or 'M' for " +
          "megabytes?");
      }
    }

    return errors;
  }

  public static class ContainerResourcesBuilder {
    private final ContainerResources resources;
    private final ContainerResourcesBuilder builder;

    public ContainerResourcesBuilder() {
      this.resources = new ContainerResources();
      this.builder = this;
    }

    /**
     * <pre>
     * Sets the CPU resource request or limit for the container.
     * This value can be specified in CPU units, where 1000m represents 1 CPU core.
     *
     * For example, "250m" represents a quarter of a CPU core.</pre>
     *
     * @param cpu The amount of CPU to request or limit
     */
    public ContainerResourcesBuilder withCpu(String cpu) {
      resources.setCpu(cpu);
      return builder;
    }

    /**
     * <pre>
     * Sets the memory resource request or limit for the container. Memory can be specified in bytes or using
     * suffixes to denote fixed-point numbers. Supported decimal unit suffixes include E, P, T, G, M, k, and
     * their power-of-two equivalents are Ei, Pi, Ti, Gi, Mi, Ki. It's crucial to use the correct suffix to accurately
     * specify memory requirements.
     *
     * For example, "64Mi" (64 Mebibytes) and "1Gi" (1 Gibibyte) use binary suffixes, while "500M" (500 Megabytes)
     * uses a decimal suffix. The case of the suffix is significant and directly impacts the allocated resources.
     * For instance, specifying "400m" would request 0.4 bytes, likely a mistake for "400Mi" (400 Mebibytes) or
     * "400M" (400 Megabytes). This distinction is vital to ensure resources are requested as intended.
     *
     * Be mindful of the suffix case to avoid common pitfalls: 'M' denotes Megabytes, while 'Mi' denotes Mebibytes.
     * A lowercase 'm' is not valid for memory specifications and could lead to configuration errors.</pre>
     *
     * @param memory The amount of memory to request or limit, specified as a plain integer or as a fixed-point
     *               number with one of the supported suffixes.
     */
    public ContainerResourcesBuilder withMemory(String memory) {
      resources.setMemory(memory);
      return builder;
    }

    public ContainerResources build() {
      var errors = resources.validate();

      if (!errors.isEmpty()) {
        throw new IllegalArgumentException(String.format("ContainerResources validation failed. Errors: %s",
          Arrays.toString(errors.toArray())));
      }

      return resources;
    }
  }
}
