package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos;

import com.yanchware.fractal.sdk.domain.blueprint.paas.PaaSRelationalDbms;
import com.yanchware.fractal.sdk.domain.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.livesystem.paas.PaaSPostgreSqlDbms;
import com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.*;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;
import lombok.Getter;
import lombok.Setter;

import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.yanchware.fractal.sdk.domain.values.ComponentType.PAAS_COSMOS_POSTGRESQL_CLUSTER;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLowercaseLettersNumbersAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;

@Getter
@Setter
@ToString(callSuper = true)
public class AzureCosmosPostgreSqlDbms extends PaaSPostgreSqlDbms implements LiveSystemComponent, AzureResourceEntity {

  public static final String TYPE = PAAS_COSMOS_POSTGRESQL_CLUSTER.getId();
  private final static String NAME_NOT_VALID = "[AzureCosmosPostgreSqlDbms Validation] The name must only contains lowercase letters, numbers, and hyphens. The name must not start or end in a hyphen and must be between 3 and 40 characters long";

  private String name;
  private String rootUser;
  private AzureCosmosPostgreSqlDbmsSkuName skuName;
  private AzureStorageAutoGrow storageAutoGrow;
  private Integer storageGb;
  private HighAvailabilityMode highAvailabilityMode;
  private ReplicationRole replicationRole;
  private String subnetAddressCidr;
  private boolean isPrivate;
  private Integer coordinatorStorageGb;
  private Integer workerStorageGb;
  private Integer coordinatorCores;
  private Integer workersCores;
  private Integer nodeCount;
  private String coordinatorServerEdition;
  private String nodeServerEdition;
  private Integer backupRetentionDays;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Map<String, String> tags;

  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  private AzureCosmosBackupPolicy backupPolicy;

  public static AzureCosmosPostgreSqlDbmsBuilder builder() {
    return new AzureCosmosPostgreSqlDbmsBuilder();
  }

  public static class AzureCosmosPostgreSqlDbmsBuilder extends PaaSRelationalDbms.Builder<AzureCosmosPostgreSqlDbms, AzureCosmosPostgreSqlDbmsBuilder> {

    @Override
    protected AzureCosmosPostgreSqlDbms createComponent() {
      return new AzureCosmosPostgreSqlDbms();
    }

    @Override
    protected AzureCosmosPostgreSqlDbmsBuilder getBuilder() {
      return this;
    }

    /**
     * Name of the root user to be set for the PostgreSql DBMS
     * @param rootUser
     */
    public AzureCosmosPostgreSqlDbmsBuilder withRootUser(String rootUser) {
      component.setRootUser(rootUser);
      return builder;
    }

    /**
     * The region in which the component will be created
     *
     * @param region Azure region
     */
    public AzureCosmosPostgreSqlDbmsBuilder withRegion(AzureRegion region) {
      component.setAzureRegion(region);
      return builder;
    }

    /**
     * SKU name for PostgreSql DBMS
     * @param skuName
     */
    public AzureCosmosPostgreSqlDbmsBuilder withSkuName(AzureCosmosPostgreSqlDbmsSkuName skuName) {
      component.setSkuName(skuName);
      return builder;
    }

    /**
     * Enable/Disable storage auto grow
     * @param storageAutoGrow
     */
    public AzureCosmosPostgreSqlDbmsBuilder withStorageAutoGrow(AzureStorageAutoGrow storageAutoGrow) {
      component.setStorageAutoGrow(storageAutoGrow);
      return builder;
    }

    /**
     * PostgreSql DBMS storage in GB
     * @param storageGb
     */
    public AzureCosmosPostgreSqlDbmsBuilder withStorageGb(int storageGb) {
      component.setStorageGb(storageGb);
      return builder;
    }

    /**
     * Setting for backup retention days
     * @param backupRetentionDays
     */
    public AzureCosmosPostgreSqlDbmsBuilder withBackupRetentionDays(int backupRetentionDays) {
      component.setBackupRetentionDays(backupRetentionDays);
      return builder;
    }

    /**
     * PostgreSql DBMS high replication role
     * @param replicationRole
     */
    public AzureCosmosPostgreSqlDbmsBuilder withReplicationRole(ReplicationRole replicationRole) {
      component.setReplicationRole(replicationRole);
      return builder;
    }

    /**
     * PostgreSql DBMS high availability mode
     * @param highAvailabilityMode
     */
    public AzureCosmosPostgreSqlDbmsBuilder withHighAvailabilityMode(HighAvailabilityMode highAvailabilityMode) {
      component.setHighAvailabilityMode(highAvailabilityMode);
      return builder;
    }

    /**
     * PostgreSql DBMS subnet address cidr
     * @param subnetAddressCidr
     */
    public AzureCosmosPostgreSqlDbmsBuilder withSubnetAddressCidr(String subnetAddressCidr) {
      component.setSubnetAddressCidr(subnetAddressCidr);
      return builder;
    }

    /**
     * PostgreSql DBMS private cluster
     * @param isPrivate
     */
    public AzureCosmosPostgreSqlDbmsBuilder withIsPrivate(boolean isPrivate) {
      component.setPrivate(isPrivate);
      return builder;
    }

    /**
     * PostgreSql DBMS coordinator storage GB
     * @param coordinatorStorageGb
     */
    public AzureCosmosPostgreSqlDbmsBuilder withCoordinatorStorageGb(Integer coordinatorStorageGb) {
      component.setCoordinatorStorageGb(coordinatorStorageGb);
      return builder;
    }

    /**
     * PostgreSql DBMS workers storage GB
     * @param workerStorageGb
     */
    public AzureCosmosPostgreSqlDbmsBuilder withWorkerStorageGb(Integer workerStorageGb) {
      component.setWorkerStorageGb(workerStorageGb);
      return builder;
    }

    /**
     * PostgreSql DBMS coordinator cores
     * @param coordinatorCores
     */
    public AzureCosmosPostgreSqlDbmsBuilder withCoordinatorCores(Integer coordinatorCores) {
      component.setCoordinatorCores(coordinatorCores);
      return builder;
    }

    /**
     * PostgreSql DBMS workers cores
     * @param workerCores
     */
    public AzureCosmosPostgreSqlDbmsBuilder withWorkerCores(Integer workerCores) {
      component.setWorkersCores(workerCores);
      return builder;
    }

    /**
     * PostgreSql DBMS node count
     * @param nodeCount
     */
    public AzureCosmosPostgreSqlDbmsBuilder withNodeCount(Integer nodeCount) {
      component.setNodeCount(nodeCount);
      return builder;
    }

    /**
     * PostgreSql DBMS coordinator server edition
     * @param coordinatorServerEdition
     */
    public AzureCosmosPostgreSqlDbmsBuilder withCoordinatorServerEdition(String coordinatorServerEdition) {
      component.setCoordinatorServerEdition(coordinatorServerEdition);
      return builder;
    }

    /**
     * PostgreSql DBMS node server edition
     * @param nodeServerEdition
     */
    public AzureCosmosPostgreSqlDbmsBuilder withNodeServerEdition(String nodeServerEdition) {
      component.setNodeServerEdition(nodeServerEdition);
      return builder;
    }

    /**
     * Name of the PostgreSql DBms
     * @param name
     */
    public AzureCosmosPostgreSqlDbmsBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    /**
     * Tags are name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public AzureCosmosPostgreSqlDbmsBuilder withTags(Map<String, String> tags) {
      component.setTags(tags);
      return builder;
    }

    /**
     * Tag is name/value pairs that enable you to categorize resources and view consolidated billing by
     * applying the same tag to multiple resources and resource groups.
     */
    public AzureCosmosPostgreSqlDbmsBuilder withTag(String key, String value) {
      if (component.getTags() == null) {
        withTags(new HashMap<>());
      }

      component.getTags().put(key, value);
      return builder;
    }

    public AzureCosmosPostgreSqlDbmsBuilder withAzureResourceGroup(AzureResourceGroup azureResourceGroup) {
      component.setAzureResourceGroup(azureResourceGroup);
      return builder;
    }

    @Override
    public AzureCosmosPostgreSqlDbms build() {
      component.setType(PAAS_COSMOS_POSTGRESQL_CLUSTER);
      return super.build();
    }
  }

  public Collection<String> validate() {
    Collection<String> errors = super.validate();

    if(StringUtils.isNotBlank(name)) {
      var hasValidCharacters = isValidLowercaseLettersNumbersAndHyphens(name);
      var hasValidLengths = isValidStringLength(name, 3, 40);
      if(!hasValidCharacters || !hasValidLengths) {
        errors.add(NAME_NOT_VALID);
      }
    }
    
    return errors;
  }

}
