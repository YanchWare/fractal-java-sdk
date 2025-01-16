package com.yanchware.fractal.sdk.domain.livesystem.iaas.providers.azure;

import com.yanchware.fractal.sdk.domain.Component;
import com.yanchware.fractal.sdk.domain.blueprint.iaas.IaaSVirtualMachine;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.iaas.providers.OperativeSystem;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.IAAS_VIRTUAL_MACHINE;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLowercaseLettersNumbersAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Getter
@Setter(AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class AzureVirtualMachine extends IaaSVirtualMachine implements AzureResourceEntity, LiveSystemComponent {
  private static final Integer ID_MIN_LENGTH = 1;
  private static final Integer ID_MAX_LENGTH_LINUX = 64;
  private static final Integer ID_MAX_LENGTH_WINDOWS = 15;
  private final static String NAME_LENGTH_MISMATCH_TEMPLATE =
    "[AzureVirtualMachine validation] Virtual Machine id is illegal. A valid Virtual Machine name must be between "
      + ID_MIN_LENGTH + " and " + ID_MAX_LENGTH_LINUX + " characters of length for Linux machines or between "
      + ID_MIN_LENGTH + " and " + ID_MAX_LENGTH_WINDOWS + " characters of length for Windows machines.";
  private final static String DYNAMIC_AND_STATIC_IP_ADDRESS = "[AzureVirtualMachine validation] You cannot specify a " +
    "static IP Address and specify dynamic allocation";
  private final static String MISSING_ROOT_USERNAME = "[AzureVirtualMachine validation] Root username is required and" +
    " " +
    "it has not been defined";
  private final static String MISSING_ROOT_SECRET = "[AzureVirtualMachine validation] Either Root password or Root " +
    "SSH Public" +
    " Key are required and none has been defined";

  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;
  private String staticPrivateIpAddress;
  private String staticPublicIpAddress;
  private boolean dynamicPrivateIpAllocation;
  private boolean dynamicPublicIpAllocation;
  private OperativeSystem operativeSystem;
  private String rootUsername;
  private String rootPassword;
  private String sshPublicKey;
  private int storageInGb;
  private AzureVmSize size;

  @Setter(AccessLevel.PROTECTED)
  private AzureSubnet subnet;

  public static AzureVirtualMachineBuilder builder() {
    return new AzureVirtualMachineBuilder();
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

    if (isNotBlank(id)) {
      var idMaxLength = operativeSystem == OperativeSystem.LINUX
        ? ID_MAX_LENGTH_LINUX
        : ID_MAX_LENGTH_WINDOWS;
      var hasValidCharacters = isValidLowercaseLettersNumbersAndHyphens(id);
      var hasValidLengths = isValidStringLength(id, ID_MIN_LENGTH, idMaxLength);
      if (!hasValidCharacters || !hasValidLengths) {
        errors.add(NAME_LENGTH_MISMATCH_TEMPLATE);
      }
    }

    if ((dynamicPrivateIpAllocation && staticPrivateIpAddress != null)
      || (dynamicPublicIpAllocation && staticPublicIpAddress != null))
    {
      errors.add(DYNAMIC_AND_STATIC_IP_ADDRESS);
    }

    if (isBlank(rootUsername)) {
      errors.add(MISSING_ROOT_USERNAME);
    }

    if (isBlank(rootPassword) && isBlank(sshPublicKey)) {
      errors.add(MISSING_ROOT_SECRET);
    }

    return errors;
  }

  public static class AzureVirtualMachineBuilder extends Component.Builder<AzureVirtualMachine,
    AzureVirtualMachine.AzureVirtualMachineBuilder> {
    @Override
    protected AzureVirtualMachine createComponent() {
      return new AzureVirtualMachine();
    }

    @Override
    protected AzureVirtualMachineBuilder getBuilder() {
      return this;
    }

    @Override
    public AzureVirtualMachine build() {
      component.setType(IAAS_VIRTUAL_MACHINE);
      return super.build();
    }

    /**
     * The region in which the component will be created
     *
     * @param region Azure region
     */
    public AzureVirtualMachineBuilder withRegion(AzureRegion region) {
      component.setAzureRegion(region);
      return builder;
    }

    /**
     * The resource group in which the component will be created
     *
     * @param azureResourceGroup Azure Resource Group reference
     */
    public AzureVirtualMachineBuilder withAzureResourceGroup(AzureResourceGroup azureResourceGroup) {
      component.setAzureResourceGroup(azureResourceGroup);
      return builder;
    }

    /**
     * Tags are name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public AzureVirtualMachineBuilder withTags(Map<String, String> tags) {
      component.setTags(tags);
      return builder;
    }

    /**
     * Tag is name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public AzureVirtualMachineBuilder withTag(String key, String value) {
      if (component.getTags() == null) {
        withTags(new HashMap<>());
      }

      component.getTags().put(key, value);
      return builder;
    }

    /**
     * Assign a static private ip address. When assigned, the dynamic allocation of a private ip for this resource
     * must be disabled
     */
    public AzureVirtualMachineBuilder withStaticPrivateIpAddress(String staticPrivateIpAddress) {
      component.setStaticPrivateIpAddress(staticPrivateIpAddress);
      return builder;
    }

    /**
     * Assign a static public ip address. When assigned, the dynamic allocation of a public ip for this resource
     * must be disabled
     */
    public AzureVirtualMachineBuilder withStaticPublicIpAddress(String staticPublicIpAddress) {
      component.setStaticPublicIpAddress(staticPublicIpAddress);
      return builder;
    }


    /**
     * Activate or deactivate the dynamic private ip allocation for this resource
     */
    public AzureVirtualMachineBuilder withDynamicPrivateIpAllocation(boolean dynamicPrivateIpAllocation) {
      component.setDynamicPrivateIpAllocation(dynamicPrivateIpAllocation);
      return builder;
    }

    ;

    /**
     * Activate or deactivate the dynamic public ip allocation for this resource
     */
    public AzureVirtualMachineBuilder withDynamicPublicIpAllocation(boolean dynamicPublicIpAllocation) {
      component.setDynamicPublicIpAllocation(dynamicPublicIpAllocation);
      return builder;
    }

    ;


    /**
     * Select the operative system of the resource
     */
    public AzureVirtualMachineBuilder withOperativeSystem(OperativeSystem operativeSystem) {
      component.setOperativeSystem(operativeSystem);
      return builder;
    }

    ;

    /**
     * Specify the root username for the resource
     */
    public AzureVirtualMachineBuilder withRootUsername(String rootUsername) {
      component.setRootUsername(rootUsername);
      return builder;
    }

    ;

    /**
     * Specify the root password for the resource
     */
    public AzureVirtualMachineBuilder withRootPassword(String rootPassword) {
      component.setRootPassword(rootPassword);
      return builder;
    }

    ;

    /**
     * Specify the public SSH key to utilize for the root account
     */
    public AzureVirtualMachineBuilder withSshPublicKey(String sshPublicKey) {
      component.setSshPublicKey(sshPublicKey);
      return builder;
    }

    ;

    /**
     * Specify the size in Gigabytes of the Hard Disk of the resource
     */
    public AzureVirtualMachineBuilder withStorageInGb(int storageInGb) {
      component.setStorageInGb(storageInGb);
      return builder;
    }

    ;

    /**
     * Specify the size of the compute resources available
     */
    public AzureVirtualMachineBuilder withSize(AzureVmSize size) {
      component.setSize(size);
      return builder;
    }

    ;
  }

}
