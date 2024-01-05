package com.yanchware.fractal.sdk;

import lombok.val;

public class FractalFirstTest {
  val businessDbmsProd = new RelationalDbmsPlatform.Builder()
    .id("businessDbmsProd");

  val containerPlatformProd1 = new ContainerPlatform.Builder()
    .id("prod1")
    .service(CaaSService.Builder()
      .id("product-service")
    )
    .logging()
    .monitoring()
    .tracing();

  // Extract the fractal/livesystem from as little code as possible

  val businessPlatformFractal = new Fractal.Builder()
    .id("yanchware/businessPlatfor:v1")
    .blueprint()
    .component(containerPlatformProd1)
    .component(containerPlatformProd2)
    .interface();
}