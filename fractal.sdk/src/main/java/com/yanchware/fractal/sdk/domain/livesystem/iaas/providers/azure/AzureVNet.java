package com.yanchware.fractal.sdk.domain.livesystem.iaas.providers.azure;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.IaaSNetwork;
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

import static com.yanchware.fractal.sdk.domain.values.ComponentType.IAAS_NETWORK;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLowercaseLettersNumbersAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class AzureVNet extends IaaSNetwork implements AzureResourceEntity, LiveSystemComponent {
  private static final Integer ID_MIN_LENGTH = 1;
  private static final Integer ID_MAX_LENGTH = 80;

  private final static String NAME_LENGTH_MISMATCH_TEMPLATE = "[AzureVNet validation] VNet id is illegal. A valid " +
    "VNet name must be between " + ID_MIN_LENGTH + " and " + ID_MAX_LENGTH + " characters of length.";
  private final static String MISSING_ADDRESS_SPACE_CIDR = "[AzureVNet validation] VNet address space is required " +
    "but it has not been specified. It must be in the CIDR notation.";

  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;

  private String addressSpaceCidr;
  private List<AzureSubnet> subnets;
  private String dnsServerIpAddress;

  public static AzureVNetBuilder builder() {
    return new AzureVNetBuilder();
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

    // TODO check subnet CIDRs do not overlap

    return errors;
  }

  public static class AzureVNetBuilder extends Component.Builder<AzureVNet, AzureVNetBuilder> {
    @Override
    protected AzureVNet createComponent() {
      return new AzureVNet();
    }

    @Override
    protected AzureVNetBuilder getBuilder() {
      return this;
    }

    @Override
    public AzureVNet build() {
      component.setType(IAAS_NETWORK);
      return super.build();
    }

    /**
     * Set address space specified using the CIDR notation
     */
    public AzureVNetBuilder withAddressSpaceCidr(String addressSpaceCidr) {
      component.setAddressSpaceCidr(addressSpaceCidr);
      return this;
    }

    /**
     * Set the list of subnets contained in this resource
     */
    public AzureVNetBuilder withSubnets(List<AzureSubnet> subnets) {
      component.setSubnets(subnets);
      subnets.forEach(x -> {
        x.setNetwork(component);
        x.getDependencies().add(component.getId());
      });

      return this;
    }

    /**
     * Add a Subnet to the current resource
     */
    public AzureVNetBuilder withSubnet(AzureSubnet subnet) {
      if (component.getSubnets() == null) {
        component.setSubnets(new ArrayList<>());
      }

      subnet.setNetwork(this.component);
      subnet.getDependencies().add(component.getId());

      component.getSubnets().add(subnet);
      return this;
    }

    /**
     * Specify the IP Address of the DNS server to use for this resource
     */
    public AzureVNetBuilder withDnsServerIpAddress(String dnsServerIpAddress) {
      component.setDnsServerIpAddress(dnsServerIpAddress);
      return this;
    }
  }
}
