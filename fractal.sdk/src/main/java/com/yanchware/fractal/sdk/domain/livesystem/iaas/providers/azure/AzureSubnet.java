package com.yanchware.fractal.sdk.domain.livesystem.iaas.providers.azure;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.IaaSSubnet;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.IAAS_SUBNET;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLowercaseLettersNumbersAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class AzureSubnet extends IaaSSubnet implements AzureResourceEntity, LiveSystemComponent {
  private static final Integer ID_MIN_LENGTH = 1;
  private static final Integer ID_MAX_LENGTH = 80;

  private final static String NAME_LENGTH_MISMATCH_TEMPLATE = "[AzureSubnet validation] Subnet id is illegal. A valid" +
    " " +
    "VNet name must be between " + ID_MIN_LENGTH + " and " + ID_MAX_LENGTH + " characters of length.";
  private final static String MISSING_ADDRESS_SPACE_CIDR = "[AzureSubnet validation] Subnet address space is required" +
    " " +
    "but it has not been specified. It must be in the CIDR notation.";

  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;

  private String addressSpaceCidr;
  private List<AzureVirtualMachine> virtualMachines;
  private String delegation;

  @Setter(AccessLevel.PROTECTED)
  private AzureVNet network;

  @Setter(AccessLevel.PROTECTED)
  private AzureRouteTable routeTable;

  @Setter(AccessLevel.PROTECTED)
  private AzureNetworkSecurityGroup networkSecurityGroup;

  public static AzureSubnetBuilder builder() {
    return new AzureSubnetBuilder();
  }

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    var id = getId() == null ?
      null :
      getId().getValue();

    if (StringUtils.isNotBlank(id)) {
      var hasValidCharacters = isValidLowercaseLettersNumbersAndHyphens(id);
      var hasValidLengths = isValidStringLength(id, ID_MIN_LENGTH, ID_MAX_LENGTH);
      if (!hasValidCharacters || !hasValidLengths) {
        errors.add(NAME_LENGTH_MISMATCH_TEMPLATE);
      }
    }

    if (isBlank(addressSpaceCidr)) {
      errors.add(MISSING_ADDRESS_SPACE_CIDR);
    }

    // TODO check for CIDR is actual a subset of VNet CIDR

    return errors;
  }

  public static class AzureSubnetBuilder extends Component.Builder<AzureSubnet, AzureSubnetBuilder> {
    @Override
    protected AzureSubnet createComponent() {
      return new AzureSubnet();
    }

    @Override
    protected AzureSubnetBuilder getBuilder() {
      return this;
    }

    @Override
    public AzureSubnet build() {
      component.setType(IAAS_SUBNET);
      return super.build();
    }

    public AzureSubnetBuilder withAddressSpaceCidr(String addressSpaceCidr) {
      component.setAddressSpaceCidr(addressSpaceCidr);
      return this;
    }

    public AzureSubnetBuilder withDelegation(String delegation) {
      component.setDelegation(delegation);
      return this;
    }

    public AzureSubnetBuilder withVirtualMachines(List<AzureVirtualMachine> virtualMachines) {
      component.setVirtualMachines(virtualMachines);
      virtualMachines.forEach(x -> {
        x.setSubnet(component);
        x.getDependencies().add(component.getId());
      });

      return this;
    }

    public AzureSubnetBuilder withVirtualMachine(AzureVirtualMachine virtualMachine) {
      if (component.getVirtualMachines() == null) {
        component.setVirtualMachines(new ArrayList<>());
      }

      virtualMachine.setSubnet(this.component);
      virtualMachine.getDependencies().add(component.getId());

      component.getVirtualMachines().add(virtualMachine);
      return this;
    }

  }

}
