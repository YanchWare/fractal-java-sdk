package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

/**
 * <pre>
 * Specifies the OS SKU used by the agent pool. 
 * The default is Ubuntu if OSType is Linux. 
 * The default is Windows2019 when Kubernetes &lt;= 1.24 or 
 * Windows2022 when Kubernetes &gt;= 1.25 if OSType is Windows.
 * </pre>
 */
public class AzureOsSku extends ExtendableEnum<AzureOsSku> {

  /**
   * Use AzureLinux as the OS for node images. 
   * Azure Linux is a container-optimized Linux distro built by Microsoft.
   * For more information, visit <a href="https://aka.ms/azurelinux">https://aka.ms/azurelinux</a>.
   */
  public static final AzureOsSku AZURE_LINUX = fromString("AzureLinux");

  /**
   * Deprecated OSSKU. Microsoft recommends that new deployments choose 'AzureLinux' instead.
   */
  @Deprecated
  public static final AzureOsSku CBLMARINER = fromString("CBLMariner");

  /**
   * Use Ubuntu as the OS for node images.
   */
  public static final AzureOsSku UBUNTU = fromString("Ubuntu");

  /**
   * Use Windows2019 as the OS for node images. 
   * Unsupported for system node pools. 
   * Windows2019 only supports Windows2019 containers; it cannot run Windows2022 containers and vice versa.
   */
  public static final AzureOsSku WINDOWS2019 = fromString("Windows2019");

  /**
   * Use Windows2022 as the OS for node images. 
   * Unsupported for system node pools. 
   * Windows2022 only supports Windows2022 containers; it cannot run Windows2019 containers and vice versa.
   */
  public static final AzureOsSku WINDOWS2022 = fromString("Windows2022");

  public AzureOsSku() {
  }

  @JsonCreator
  public static AzureOsSku fromString(String name) {
    return fromString(name, AzureOsSku.class);
  }

  public static Collection<AzureOsSku> values() {
    return values(AzureOsSku.class);
  }
}
