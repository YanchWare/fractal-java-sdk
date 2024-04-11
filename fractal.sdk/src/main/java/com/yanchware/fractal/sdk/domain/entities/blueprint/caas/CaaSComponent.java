package com.yanchware.fractal.sdk.domain.entities.blueprint.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.ResourceManagement;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.Toleration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLettersNumbersUnderscoresDashesAndPeriods;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PUBLIC)
@ToString(callSuper = true)
public abstract class CaaSComponent extends Component {
  private final static String DNS_ZONE_NAME_NOT_VALID = "[DnsRecords Validation] The dnsZoneName must contain no more than 253 characters, excluding a trailing period and must be between 2 and 34 labels. Each label must only contain letters, numbers, underscores, and/or dashes. Each label should be separated from other labels by a period";

  private String containerPlatform;
  private String namespace;
  private Map<String, List<Object>> dnsZoneConfig;
  private ResourceManagement resourceManagement;
  private Map<String, String> nodeSelectors;
  private List<Toleration> tolerations;
  private String priorityClassName;

  private String getNamespaceIsNullOrEmptyErrorMessage() {
    return String.format("[%s Validation] Namespace has not been defined and it is required", this.getClass().getSimpleName());
  }

  private String getContainerPlatformIsNullOrEmptyErrorMessage() {
    return String.format("[%s Validation] ContainerPlatform defined was either empty or blank and it is required", this.getClass().getSimpleName());
  }

  public static abstract class Builder<T extends CaaSComponent, B extends Builder<T, B>> extends Component.Builder<T, B> {

    /**
     * <pre>
     * Configures resource management settings for the component, specifying CPU and memory requests and limits.
     * This ensures the component is allocated the necessary resources for optimal performance and does not exceed
     * specified usage thresholds.</pre>
     * 
     * @param resourceManagement The ResourceManagement settings for the component.
     *                           
     * @important.note <strong>Customizing resource management values offers flexibility, but you need to carefully consider
     * the implications. Fractal Cloud cannot guarantee the functionality of the deployment if these customizations
     * lead to resource constraints or conflicts. You are responsible for ensuring that your configurations are
     * appropriate for your application's needs and compatible with your cluster's capabilities.</strong>
     */
    public B withResourceManagement(ResourceManagement resourceManagement) {
      component.setResourceManagement(resourceManagement);
      return builder;
    }
    
     /**
     * <pre>
     * Sets multiple node selectors at once. 
     * Each entry in the map represents a label and its desired value.</pre>
     *
     * @param selectors A map of label keys to label values defining the node selection criteria.
     *
     * @important.note <strong>Node selectors provide powerful control over pod scheduling, but incorrect or overly restrictive
     * configurations may limit your deployment options. Fractal Cloud cannot guarantee the functionality of the deployment
     * if node selector configurations prevent scheduling. You need to verify the accuracy and applicability of your
     * node label selections.</strong>
     */
    public B withNodeSelectors(Map<String, String> selectors) {
      if (component.getNodeSelectors() == null) {
        component.setNodeSelectors(new HashMap<>());
      }
      
      component.getNodeSelectors().putAll(selectors);
      return builder;
    }

    /**
     * <pre>
     * Adds a single node selector. 
     * This method allows you to specify one label and its desired value at a time.
     * Can be called multiple times to add multiple selectors.</pre>
     *
     * @param label The label key representing a specific characteristic of nodes.
     * @param value The label value that nodes must have to match the selector.
     *
     * @important.note <strong>Node selectors provide powerful control over pod scheduling, but incorrect or overly restrictive
     * configurations may limit your deployment options. Fractal Cloud cannot guarantee the functionality of the deployment
     * if node selector configurations prevent scheduling. You need to verify the accuracy and applicability of your
     * node label selections.</strong>
     */
    public B withNodeSelector(String label, String value) {
      if (component.getNodeSelectors() == null) {
        component.setNodeSelectors(new HashMap<>());
      }
      
      component.getNodeSelectors().put(label, value);
      return builder;
    }
    

    /**
     * <pre>
     * Adds a list of tolerations for the component, enabling it to be scheduled on nodes with specific taints.
     * Tolerations are essential for running applications in environments with strict security or operational requirements.</pre>
     * 
     * @param tolerations A list of Toleration objects for the component.
     *                    
     * @important.note <strong>Toleration configurations must be carefully crafted to ensure compatibility with cluster taints.
     * Fractal Cloud cannot guarantee the functionality of the deployment if tolerations cause the component to be
     * unscheduled or scheduled on unsuitable nodes. You need to carefully review and test your toleration settings
     * to ensure they align with your deployment strategy and security requirements.</strong>
     */
    public B withTolerations(List<Toleration> tolerations) {
      if (component.getTolerations() == null) {
        component.setTolerations(new ArrayList<>());
      }
      component.getTolerations().addAll(tolerations);
      return builder;
    }

    /**
     * <pre>
     * Adds a single toleration to the component, providing granular control over scheduling on nodes with certain taints.</pre>
     * 
     * @param toleration A Toleration object to add to the component.
     *                   
     * @important.note <strong>Adding individual tolerations requires precision to avoid unintended scheduling outcomes.
     * Fractal Cloud cannot guarantee the functionality of the deployment if improper tolerations lead to deployment
     * challenges. You need to ensure that tolerations are correctly configured for your operational environment.</strong>
     */
    public B withToleration(Toleration toleration) {
      if (component.getTolerations() == null) {
        component.setTolerations(new ArrayList<>());
      }
      component.getTolerations().add(toleration);
      return builder;
    }

    /**
     * <pre>
     * Specifies the priority class name for the component, affecting the scheduling and preemption of pods.</pre>
     * 
     * @param priorityClassName The name of the PriorityClass for the component.
     *                          
     * @important.note <strong>Priority class names influence the scheduling and lifecycle of pods significantly.
     * Fractal Cloud cannot guarantee the functionality of the deployment if priority class names conflict with
     * cluster policies or other deployed applications. You need to ensure that priority class names are chosen
     * with an understanding of your cluster's scheduling policies.</strong>
     */
    public B withPriorityClassName(String priorityClassName) {
      component.setPriorityClassName(priorityClassName);
      return builder;
    }
  }
  
  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if (isBlank(namespace)) {
      errors.add(getNamespaceIsNullOrEmptyErrorMessage());
    }

    if (containerPlatform != null && isBlank(containerPlatform)) {
      errors.add(getContainerPlatformIsNullOrEmptyErrorMessage());
    }

    if (dnsZoneConfig != null) {
      dnsZoneConfig.keySet()
          .forEach(dnsZoneName -> {
            if (isBlank(dnsZoneName)) {
              errors.add(DNS_ZONE_NAME_NOT_VALID);
            }
            if (StringUtils.isNotBlank(dnsZoneName)) {
              var nameWithoutTrailingPeriod = StringUtils.stripEnd(dnsZoneName, ".");

              var hasValidCharacters = isValidLettersNumbersUnderscoresDashesAndPeriods(dnsZoneName);

              if (!hasValidCharacters || nameWithoutTrailingPeriod.length() > 253) {
                errors.add(DNS_ZONE_NAME_NOT_VALID);
              }
            }
          });
    }

    return errors;
  }
}
