package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice;

import com.yanchware.fractal.sdk.domain.entities.blueprint.paas.PaaSWorkload;
import com.yanchware.fractal.sdk.domain.entities.environment.DnsRecord;
import com.yanchware.fractal.sdk.domain.entities.livesystem.CustomWorkload;
import com.yanchware.fractal.sdk.domain.entities.livesystem.CustomWorkloadBuilder;
import com.yanchware.fractal.sdk.domain.entities.livesystem.LiveSystemComponent;
import com.yanchware.fractal.sdk.domain.entities.livesystem.caas.CustomWorkloadRole;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureOsType;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureRegion;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceEntity;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.AzureResourceGroup;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.valueobjects.AzureAppServiceClientCertMode;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.valueobjects.AzureAppServiceRedundancyMode;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import com.yanchware.fractal.sdk.utils.CollectionUtils;
import com.yanchware.fractal.sdk.utils.SerializationUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidAlphanumericsHyphens;
import static com.yanchware.fractal.sdk.utils.RegexValidationUtils.isValidLettersNumbersPeriodsAndHyphens;
import static com.yanchware.fractal.sdk.utils.ValidationUtils.isValidStringLength;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_WEBAPP;

/**
 * <pre>
 *  Implements a builder for Azure Web App instances, facilitating the configuration and deployment
 *  of web applications on Azure. This class provides a fluent API to set up various aspects of an
 *  Azure Web App, including but not limited to, service plans, deployment slots, scaling options,
 *  and application settings.
 * </pre>
 */
@Getter
@Setter
@ToString(callSuper = true)
public class AzureWebApp extends PaaSWorkload implements AzureResourceEntity, LiveSystemComponent, CustomWorkload {
  private final static Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9][a-zA-Z0-9-]{0,57}[a-zA-Z0-9]$");
  private final static String RUNTIME_STACK_AND_OPERATING_SYSTEM_MISMATCH_PATTERN = "[AzureWebApp Validation] The Runtime Stack and Operating System mismatches. Please choose %s or change Operating System to %s";
  private final static String NAME_NOT_VALID = "[AzureWebApp Validation] The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be between 2 and 60 characters";
  private final static String CUSTOM_DOMAIN_NOT_VALID = "[AzureWebApp Validation] The CustomDomain must contain at least one period, cannot start or end with a period. CustomDomain are made up of letters, numbers, periods, and dashes.";
  private final static String RUNTIME_STACK_IS_EMPTY = "[AzureWebApp Validation] The Runtime Stack is either empty or blank and it is required";
  private final static String OPERATING_SYSTEM_IS_EMPTY = "[AzureWebApp Validation] The Operating System is either empty or blank and it is required";

  private String name;
  private String privateSSHKeyPassphraseSecretId;
  private String privateSSHKeySecretId;
  private String sshRepositoryURI;
  private String repoId;
  private String branchName;
  private List<CustomWorkloadRole> roles;
  private String workloadSecretIdKey;
  private String workloadSecretPasswordKey;
  private AzureRegion azureRegion;
  private AzureResourceGroup azureResourceGroup;
  private Boolean clientAffinityEnabled;
  private Boolean clientCertEnabled;
  private String clientCertExclusionPaths;
  private AzureAppServiceClientCertMode clientCertMode;
  private AzureWebAppCloningInfo cloningInfo;
  private Integer containerSize;
  private String customDomainVerificationId;
  private Integer dailyMemoryTimeQuota;

  private Boolean enabled;
  private Boolean hostNamesDisabled;

  private String hostingEnvironmentProfileId;
  private Boolean httpsOnly;
  private Boolean hyperV;
  private AzureAppServiceRedundancyMode redundancyMode;
  private Boolean reserved;
  private Boolean scmSiteAlsoStopped;
  private Boolean storageAccountRequired;
  private String virtualNetworkSubnetId;
  private Boolean vnetContentShareEnabled;
  private Boolean vnetImagePullEnabled;
  private AzureWebAppConfiguration configuration;
  private Map<String, String> tags;
  private AzureAppServicePlan appServicePlan;
  private Collection<AzureKeyVaultCertificate> certificates;
  private Collection<String> customDomains;
  private AzureOsType operatingSystem;
  private AzureWebAppRuntimeStack runtimeStack;
  private Map<String, List<Object>> dnsZoneConfig;
  private Collection<AzureWebAppDeploymentSlot> deploymentSlots;


  @Override
  public ProviderType getProvider() {
    return ProviderType.AZURE;
  }

  protected AzureWebApp() {
    
    roles = new ArrayList<>();
    deploymentSlots = new ArrayList<>();
  }

  @Override
  public Collection<String> validate() {
    Collection<String> errors = super.validate();
    errors.addAll(CustomWorkload.validateCustomWorkload(this, "AzureWebApp"));


    if (configuration == null && operatingSystem == null) {
      errors.add(OPERATING_SYSTEM_IS_EMPTY);
    }

    if (configuration == null && runtimeStack == null) {
      errors.add(RUNTIME_STACK_IS_EMPTY);
    }

    if (configuration != null
        && StringUtils.isBlank(configuration.getDotnetVersion())
        && StringUtils.isBlank(configuration.getJavaVersion())
        && StringUtils.isBlank(configuration.getJavaContainerVersion())
        && StringUtils.isBlank(configuration.getLinuxFxVersion())
        && StringUtils.isBlank(configuration.getNodeVersion())
        && StringUtils.isBlank(configuration.getPhpVersion())
        && StringUtils.isBlank(configuration.getPythonVersion())
        && StringUtils.isBlank(configuration.getWindowsFxVersion())) {
      
      if (operatingSystem == null) {
        errors.add(OPERATING_SYSTEM_IS_EMPTY);
      }

      if (runtimeStack == null) {
        errors.add(RUNTIME_STACK_IS_EMPTY);
      }
    }

    if (operatingSystem == AzureOsType.LINUX
        && runtimeStack instanceof AzureWebAppWindowsRuntimeStack) {
      errors.add(String.format(RUNTIME_STACK_AND_OPERATING_SYSTEM_MISMATCH_PATTERN, "AzureWebAppLinuxRuntimeStack", "WINDOWS"));
    }

    if (operatingSystem == AzureOsType.WINDOWS
        && runtimeStack instanceof AzureWebAppLinuxRuntimeStack) {
      errors.add(String.format(RUNTIME_STACK_AND_OPERATING_SYSTEM_MISMATCH_PATTERN, "AzureWebAppWindowsRuntimeStack", "LINUX"));
    }


    if (StringUtils.isNotBlank(name)) {
      var hasValidCharacters = isValidAlphanumericsHyphens(name);
      var hasValidLengths = isValidStringLength(name, 2, 60);
      if (!hasValidCharacters || !hasValidLengths) {
        errors.add(NAME_NOT_VALID);
      }
    }

    if (ObjectUtils.isNotEmpty(customDomains)) {
      for (var customDomain : customDomains) {
        if (StringUtils.isNotBlank(customDomain)) {
          var hasValidCharacters = isValidLettersNumbersPeriodsAndHyphens(customDomain);
          if (!hasValidCharacters) {
            errors.add(CUSTOM_DOMAIN_NOT_VALID);
          }
        }
      }
    }

    if (configuration != null) {
      errors.addAll(configuration.validate());
    }

    return errors;
  }

  public static AzureWebAppBuilder builder() {
    return new AzureWebAppBuilder();
  }

  public static class AzureWebAppBuilder extends CustomWorkloadBuilder<AzureWebApp, AzureWebAppBuilder> {

    @Override
    protected AzureWebApp createComponent() {
      return new AzureWebApp();
    }

    @Override
    protected AzureWebAppBuilder getBuilder() {
      return this;
    }

    @Override
    public AzureWebApp build() {
      component.setType(PAAS_WEBAPP);
      return super.build();
    }

    /**
     * <pre>
     * Configures the Azure region where the Web App will be deployed. The region is a critical
     * configuration that dictates the geographical location of the deployed resources, affecting
     * latency, availability, and compliance. Utilizing the {@link AzureRegion} {@code ExtendableEnum}
     * allows for flexibility in specifying regions, accommodating cases where specific regions might
     * not be explicitly listed in the enum.
     * 
     * In scenarios where automation scripts or tools require dynamic region specification or if a
     * particular region is known to exist but is not present in the {@code AzureRegion} enum,
     * {@code AzureRegion.fromString(String name)} can be used to extend the enum dynamically.
     * This approach ensures that the builder remains compatible with future Azure regions without
     * needing constant updates.
     * 
     * Usage example:
     * builder.withRegion(AzureRegion.EAST_US); // Using a predefined region
     * builder.withRegion(AzureRegion.fromString("custom_region_name")); // Extending the enum for a custom or future region
     * </pre>
     * @param region the Azure region as an {@link AzureRegion} where the Web App will be deployed.
     *               This can either be one of the predefined regions or an extended enum value
     *               created through {@code AzureRegion.fromString(String name)}.
     * @return the {@code AzureWebAppBuilder} instance for chaining further configuration calls.
     */
    public AzureWebAppBuilder withRegion(AzureRegion region) {
      component.setAzureRegion(region);
      return builder;
    }

    /**
     * <pre>
     * Sets the Azure resource group for the Web App. The resource group name must adhere to Azure's
     * naming conventions, specifically matching the regex pattern {@code ^[-\w\._\(\)]+[^\.]$}, which
     * ensures the name is valid and conforms to Azure's standards for resource naming.
     * 
     * The resource group is a crucial organizational unit within Azure that holds related resources for
     * an Azure solution. Specifying the correct resource group is essential for resource management and
     * billing. This method enables setting the resource group to which the Web App belongs.
     * 
     * Example usage:
     * 
     * {@code AzureResourceGroup.builder().withName("rg-sample-group").withRegion(AzureRegion.WEST_EUROPE).build();}
     * </pre>
     *
     * @param resourceGroup the {@code AzureResourceGroup} representing the resource group to which the
     *                      Web App will belong.
     * @return the {@code AzureWebAppBuilder} instance for chaining further configuration calls.
     */
    public AzureWebAppBuilder withResourceGroup(AzureResourceGroup resourceGroup) {
      component.setAzureResourceGroup(resourceGroup);
      return builder;
    }

    /**
     * <pre>
     * Sets the configuration for the Azure Web App using an {@link AzureWebAppConfiguration} instance.
     * This configuration encompasses a wide range of settings applicable to the web app, including
     * runtime versions, app settings, connection strings, scaling options, security settings, and more.
     * 
     * The {@link AzureWebAppConfiguration} object should be prepared in advance, with all necessary
     * configurations set via its builder pattern. This approach allows for a flexible, detailed
     * specification of the web app's behavior and properties.
     *
     * Example usage:
     * <code>
     * {@code AzureWebAppConfiguration config = AzureWebAppConfiguration.builder()
     *       .withJavaVersion("11")
     *       .withAppSettings(myAppSettings)
     *       .withConnectionStrings(myConnectionStrings)
     *     .build();}
     *
     * builder.withConfiguration(config);
     * </code>
     *
     * It's important to validate the configuration to ensure all required fields are set and
     * comply with Azure's constraints. The {@link AzureWebAppConfiguration} includes validation
     * logic to check for common configuration errors.</pre>
     *
     * @param configuration the {@link AzureWebAppConfiguration} instance containing all configuration
     *                      settings for the Azure Web App.
     * @return the {@code AzureWebAppBuilder} instance for chaining further configuration calls.
     * @throws IllegalArgumentException if the provided configuration is invalid or incomplete.
     */
    public AzureWebAppBuilder withConfiguration(AzureWebAppConfiguration configuration) {
      component.setConfiguration(configuration);
      return builder;
    }

    /**
     * <pre>
     * Sets the unique name for the Azure Web App to be created or updated
     * </pre>
     * @param name the unique name for the Azure Web App. This name must be unique across the Azure
     *             environment and adhere to Azure's naming conventions and restrictions.
     */
    public AzureWebAppBuilder withName(String name) {
      component.setName(name);
      return builder;
    }

    /**
     * <pre>
     * Assigns a set of tags to the Azure Web App as name/value pairs. Tags are instrumental in organizing 
     * Azure resources, enabling you to categorize resources across your subscription. By applying consistent tags 
     * to resources and resource groups, you can streamline management tasks such as monitoring and automation. 
     * Additionally, tags facilitate more effective cost tracking and analysis, allowing for consolidated billing 
     * reports by tag.
     * 
     * This method allows for the specification of multiple tags at once. 
     * Each tag consists of a unique key and a value pair, both of which are strings. 
     * If a resource already has tags assigned, calling this method will overwrite existing tags with 
     * the new set provided.
     * 
     * Example usage:
     * {@code Map<String, String> tags = new HashMap<>();
     * tags.put("Environment", "Production");
     * tags.put("Project", "ProjectName");
     *
     * builder.withTags(tags);}
     * </pre>
     *
     * @param tags a {@link Map} containing the tags to be assigned to the Azure Web App, where each key is the tag name
     *             and each value is the tag value.
     * @return the {@code AzureWebAppBuilder} instance, allowing for chaining of multiple configuration methods in a fluent manner.
     */
    public AzureWebAppBuilder withTags(Map<String, String> tags) {
      component.setTags(tags);
      return builder;
    }

    /**
     * <pre>
     * Adds a single tag to the Azure Web App as a name/value pair. Tags are key to organizing Azure resources,
     * allowing for categorization across your subscription. This can simplify management tasks like monitoring
     * and automation and aid in creating more accurate billing reports by grouping resources and resource groups
     * with common tags.
     * 
     * This method facilitates the addition of tags incrementally. If no tags are currently assigned to the Web App,
     * a new tag collection is initiated. Otherwise, the provided tag is added to the existing collection. If a tag
     * with the given key already exists, its value will be updated with the provided value.
     * 
     * Example usage:
     * {@code builder.withTag("Environment", "Production")
     *        .withTag("Project", "ProjectName");}  </pre>
     *
     * @param key   the name of the tag to add or update. This acts as a unique identifier for the tag.
     * @param value the value of the tag. This provides context or categorization information for the tag.
     * @return the {@code AzureWebAppBuilder} instance, allowing for the fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withTag(String key, String value) {
      if (component.getTags() == null) {
        withTags(new HashMap<>());
      }

      component.getTags().put(key, value);
      return builder;
    }

    /**
     * <pre>
     * Configures the Azure Web App with a specific {@link AzureAppServicePlan}. An App Service Plan
     * defines a set of compute resources for a web app to run. It specifies the region, operating system,
     * pricing tier, and the capacity of the infrastructure. This method is crucial for aligning the web
     * app's performance and cost-efficiency with business requirements.
     * 
     * The {@link AzureAppServicePlan} encapsulates various configurations such as the operating system
     * (Windows or Linux), pricing plan, whether zone redundancy is enabled, and the number of workers.
     * It is validated to ensure that the plan's name and other properties comply with Azure's constraints,
     * such as naming rules and resource limitations.
     * 
     * Usage example:
     * {@code AzureAppServicePlan appServicePlan = AzureAppServicePlan.builder()
     *     .withName("myAppServicePlan")
     *     .withRegion(AzureRegion.EUROPE_WEST)
     *     .withOperatingSystem(AzureOsType.LINUX)
     *     .withPricingPlan(AzurePricingPlan.P1V2)
     *     .withZoneRedundancyEnabled(true)
     *     .withNumberOfWorkers(3)
     *     .build();
     *
     * builder.withAppServicePlan(appServicePlan);}</pre>
     *
     * @param appServicePlan the {@link AzureAppServicePlan} instance to be applied to the Azure Web App.
     * @return the {@code AzureWebAppBuilder} instance, allowing for fluent chaining of configuration methods.
     * @throws IllegalArgumentException if the provided app service plan is invalid.
     */
    public AzureWebAppBuilder withAppServicePlan(AzureAppServicePlan appServicePlan) {
      component.setAppServicePlan(appServicePlan);
      return builder;
    }

    /**
     * <pre>
     * Configures the Azure Web App to use a specified Azure Key Vault certificate for SSL/TLS. 
     * Azure Key Vault certificates are utilized to secure communications to and from the web app, 
     * enabling encrypted connections via HTTPS. This method allows for the specification of a single 
     * certificate, identified by its Key Vault reference, to be applied to the web app.
     * 
     * The certificate must be a private key certificate (.pfx) and already stored in Azure Key Vault. 
     * It is identified by the Key Vault's ID and optionally protected by a password. The web app will 
     * use this certificate for its TLS/SSL bindings, ensuring that all data transmitted to and from the 
     * web app is encrypted.
     * 
     * Example usage:
     * {@code AzureKeyVaultCertificate certificate = AzureKeyVaultCertificate.builder()
     *     .withName("myCertificateName")
     *     .withRegion(AzureRegion.NORTH_EUROPE)
     *     .withKeyVaultId("myKeyVaultId")
     *     .withPassword("optionalPassword")
     *     .build();
     *
     * builder.withCertificate(certificate);}</pre>
     *
     * @param certificate an {@link AzureKeyVaultCertificate} instance to be used by the web app for SSL/TLS.
     * @return the {@code AzureWebAppBuilder} instance, allowing for fluent chaining of configuration methods.
     * @throws IllegalArgumentException if the provided certificate is invalid or if validation fails.
     */
    public AzureWebAppBuilder withCertificate(AzureKeyVaultCertificate certificate) {
      return withCertificates(List.of(certificate));
    }

    /**
     * <pre>
     * Configures the Azure Web App to use a collection of Azure Key Vault certificates for SSL/TLS. 
     * This method enables the web app to establish encrypted connections via HTTPS by utilizing one or more 
     * certificates stored in Azure Key Vault. Using multiple certificates can be useful for supporting different 
     * domains or subdomains, or for rotating certificates without downtime.
     * 
     * Each certificate in the collection must be a private key certificate (.pfx) already stored in Azure Key Vault. 
     * These certificates are identified by their Key Vault IDs and can be optionally protected by passwords. The web app 
     * will use these certificates for its TLS/SSL bindings, securing data transmission.
     * 
     * If no certificates are currently assigned to the web app, a new collection will be initialized. Otherwise, the 
     * provided certificates will be added to the existing collection. This allows for incremental updates to the web app's 
     * certificate configuration.
     * 
     * Example usage:
     * {@code List<AzureKeyVaultCertificate> certificates = Arrays.asList(
     *     AzureKeyVaultCertificate.builder()
     *         .withName("certificate1")
     *         .withRegion(AzureRegion.EUROPE_WEST)
     *         .withKeyVaultId("keyVaultId1")
     *         .withPassword("password1")
     *         .build(),
     *     AzureKeyVaultCertificate.builder()
     *         .withName("certificate2")
     *         .withRegion(AzureRegion.US_EAST)
     *         .withKeyVaultId("keyVaultId2")
     *         .build()
     * );
     *
     * builder.withCertificates(certificates);}</pre>
     *
     * @param certificates a collection of {@link AzureKeyVaultCertificate} instances to be used by the web app for SSL/TLS.
     * @return the {@code AzureWebAppBuilder} instance, allowing for fluent chaining of configuration methods.
     * @throws IllegalArgumentException if any of the provided certificates are invalid or if validation fails.
     */
    public AzureWebAppBuilder withCertificates(Collection<? extends AzureKeyVaultCertificate> certificates) {
      if (isBlank(certificates)) {
        return builder;
      }

      if (component.getCertificates() == null) {
        component.setCertificates(new ArrayList<>());
      }

      component.getCertificates().addAll(certificates);
      return builder;
    }

    /**
     * <pre>
     * Configures the Azure Web App to use a specific custom domain. Custom domains enhance the branding 
     * and visibility of your web app. The specified domain must adhere to specific formatting rules: it 
     * must contain at least one period ('.') to denote a subdomain or TLD, cannot start or end with a 
     * period, and can only include letters, numbers, periods, and dashes.
     * 
     * This method supports configuring a single custom domain. For applications requiring multiple custom 
     * domains, consider using {@link #withCustomDomains(Collection)}.
     * 
     * Example usage:
     * {@code builder.withCustomDomain("example.mydomain.com");}</pre>
     *
     * @param customDomain the custom domain to associate with the Azure Web App, following specific format rules.
     * @return the {@code AzureWebAppBuilder} instance, allowing for fluent chaining of configuration methods.
     * @throws IllegalArgumentException if the provided custom domain is invalid based on the defined rules.
     */
    public AzureWebAppBuilder withCustomDomain(String customDomain) {
      return withCustomDomains(List.of(customDomain));
    }

    /**
     * <pre>
     * Configures the Azure Web App to use a collection of custom domains. Assigning custom domains 
     * enhances the branding and accessibility of your web app. Each domain in the collection must 
     * adhere to specific formatting rules: it must contain at least one period ('.') to denote 
     * subdomains or top-level domains (TLDs), cannot start or end with a period, and can only include 
     * letters, numbers, periods, and dashes.
     * 
     * This method enables the configuration of multiple custom domains at once, providing flexibility 
     * for applications requiring several domain names. If specific formatting rules are not met, the 
     * method may throw an {@code IllegalArgumentException}.
     * 
     * Example usage:
     * {@code Collection<String> customDomains = List.of("www.example.com", "api.example.com");
     * builder.withCustomDomains(customDomains);}</pre>
     *
     * @param customDomains a collection of custom domain names to associate with the Azure Web App, 
     *        adhering to the defined formatting rules.
     * @return the {@code AzureWebAppBuilder} instance, allowing for fluent chaining of configuration methods.
     * @throws IllegalArgumentException if any provided custom domain is invalid based on the defined rules.
     */
    public AzureWebAppBuilder withCustomDomains(Collection<? extends String> customDomains) {
      if (isBlank(customDomains)) {
        return builder;
      }

      if (component.getCustomDomains() == null) {
        component.setCustomDomains(new ArrayList<>());
      }

      component.getCustomDomains().addAll(customDomains);
      return builder;
    }

    /**
     * <pre>
     * Configures client affinity for the Azure Web App. Client affinity keeps a user session on the same
     * server instance, thereby improving user experience by maintaining session state across requests.
     * This is achieved by sending session affinity cookies to clients, which route requests from the
     * same session to the same instance.
     * 
     * Enabling client affinity is beneficial for applications where maintaining user session state
     * on the server side is crucial. Disabling client affinity can be useful for stateless applications
     * where load distribution is more important than session stickiness.
     * 
     * The default behavior is to enable client affinity, ensuring session stickiness.
     * 
     * Example usage:
     * {@code builder.withClientAffinityEnabled(true);  // To enable client affinity
     * builder.withClientAffinityEnabled(false); // To disable client affinity}</pre>
     *
     * @param clientAffinityEnabled true to enable client affinity, ensuring that requests from the same
     *        session are routed to the same instance. False to disable client affinity, allowing requests
     *        to be distributed across all instances without session stickiness.
     * @return the {@code AzureWebAppBuilder} instance, allowing for fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withClientAffinityEnabled(Boolean clientAffinityEnabled) {
      component.setClientAffinityEnabled(clientAffinityEnabled);
      return builder;
    }

    /**
     * <pre>
     * Enables or disables client certificate authentication for the Azure Web App. Client certificate
     * authentication, also known as TLS mutual authentication, requires clients to present a certificate
     * when making requests to the server. This method of authentication adds an extra layer of security
     * by ensuring that only clients with a valid certificate can access the web app.
     * 
     * This feature is particularly useful for scenarios where you need to secure sensitive operations or
     * enforce stricter access controls. Enabling client certificate authentication can help prevent unauthorized
     * access and ensure that only trusted clients can communicate with the web app.
     * 
     * Example usage:
     * {@code builder.withClientCertEnabled(true);  // To enable client certificate authentication
     * builder.withClientCertEnabled(false); // To keep client certificate authentication disabled}</pre>
     *
     * @param clientCertEnabled true to enable client certificate authentication, requiring clients to
     *        present a valid certificate for access. False to disable this feature, allowing access
     *        without client certificates.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withClientCertEnabled(Boolean clientCertEnabled) {
      component.setClientCertEnabled(clientCertEnabled);
      return builder;
    }

    /**
     * <pre>
     * Specifies paths that are excluded from client certificate authentication in a comma-separated list.
     * While client certificate authentication adds an extra layer of security, there may be specific paths
     * within your application that do not require, or should not enforce, this level of authentication. 
     * These could include public resources, health check endpoints, or other paths intended for unauthenticated access.
     * 
     * Setting exclusion paths allows these specific routes to bypass client certificate authentication, ensuring
     * they remain accessible without presenting a client certificate. This approach enables a more granular
     * control over security and access within your Azure Web App.
     * 
     * Example usage:
     * {@code builder.withClientCertExclusionPaths("/public,/healthcheck,/api/public");}
     *
     * Note: Paths should be specified relative to the web app's root URL and separated by commas.</pre>
     *
     * @param clientCertExclusionPaths a comma-separated list of paths to exclude from client certificate 
     *        authentication. Each path should be specified relative to the web app's root.
     * @return the {@code AzureWebAppBuilder} instance, allowing for fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withClientCertExclusionPaths(String clientCertExclusionPaths) {
      component.setClientCertExclusionPaths(clientCertExclusionPaths);
      return builder;
    }

    /**
     * <pre>
     * Sets the mode for client certificate authentication in the Azure Web App, determining how client
     * certificates are handled. This setting works in conjunction with {@code ClientCertEnabled} to
     * define the behavior for client certificate authentication.
     * 
     * When {@code ClientCertEnabled} is set to {@code false}, the client certificate is ignored regardless
     * of the {@code ClientCertMode} setting. If {@code ClientCertEnabled} is {@code true} and
     * {@code ClientCertMode} is set to {@code Required}, client certificates are mandatory for accessing
     * the web app. When {@code ClientCertEnabled} is {@code true} and {@code ClientCertMode} is
     * {@code Optional}, client certificates, if provided, are accepted but not required for access.
     * 
     * This configuration allows for flexible handling of client certificates, accommodating scenarios
     * ranging from strict security requirements to more open access with the option of client certificate
     * authentication.
     * 
     * Example usage:
     * {@code builder.withClientCertMode(AzureAppServiceClientCertMode.Required); // Client certificates are required
     * builder.withClientCertMode(AzureAppServiceClientCertMode.Optional); // Client certificates are optional}</pre>
     *
     * @param clientCertMode the {@link AzureAppServiceClientCertMode} value defining the handling of client certificates.
     * @return the {@code AzureWebAppBuilder} instance, facilitating fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withClientCertMode(AzureAppServiceClientCertMode clientCertMode) {
      component.setClientCertMode(clientCertMode);
      return builder;
    }

    /**
     * <pre>
     * Configures the web app to be cloned from an existing source app with specific cloning parameters.
     * Cloning an app involves creating a new app with the same configuration as the source app, including
     * application settings, custom hostnames, source control, and more. This method allows for detailed
     * customization of the cloning process, including application setting overrides, whether to clone
     * custom hostnames and source control, and whether to configure load balancing between the source
     * and destination apps.
     * 
     * Cloning is particularly useful for creating staging environments, testing changes, or replicating
     * app configurations across different regions or environments. If specified during app creation,
     * the new app is cloned from the provided source app based on the {@link AzureWebAppCloningInfo}
     * configuration.
     * 
     * Example usage:
     * {@code AzureWebAppCloningInfo cloningInfo = AzureWebAppCloningInfo.builder()
     *     .withSourceWebAppId("/subscriptions/{subId}/.../sites/{siteName}")
     *     .withAppSettingsOverrides(Map.of("SettingKey", "NewValue"))
     *     .withCloneCustomHostNames(true)
     *     .withCloneSourceControl(false)
     *     .build();}
     *
     * builder.withCloningInfo(cloningInfo);</pre>
     *
     * @param cloningInfo an {@link AzureWebAppCloningInfo} instance containing the configurations for cloning the app.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withCloningInfo(AzureWebAppCloningInfo cloningInfo) {
      component.setCloningInfo(cloningInfo);
      return builder;
    }

    /**
     * <pre>
     * Sets the size of the container for the Azure Web App, which directly impacts the amount of memory
     * and computational resources allocated to the app. This configuration is particularly important for
     * applications with specific performance and resource requirements.
     * 
     * The container size is specified in megabytes and should be selected based on the expected workload
     * and performance criteria of the application. Adjusting the container size can help manage the balance
     * between performance and cost-efficiency.
     * 
     * Example usage:
     * {@code builder.withContainerSize(1536); // Sets the container size to 1536 MB}</pre>
     *
     * @param containerSize the size of the container in megabytes. Must be a valid size supported by Azure Web Apps.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withContainerSize(Integer containerSize) {
      component.setContainerSize(containerSize);
      return builder;
    }

    /**
     * <pre>
     * Sets the unique identifier used for verifying custom domains assigned to the Azure Web App. This
     * verification ID must be added by the customer to a TXT record in their domain's DNS settings as
     * part of the domain verification process. The presence of this TXT record with the correct
     * verification ID proves ownership of the domain and is a necessary step for assigning custom
     * domains to the web app.
     * 
     * Custom domain verification is an essential security measure to ensure that only domains owned by
     * the web app owner are associated with the app. This process prevents unauthorized use of domains
     * and helps maintain the integrity and trustworthiness of the web app.
     * 
     * Example usage:
     * {@code builder.withCustomDomainVerificationId("uniqueVerificationId");}</pre>
     *
     * @param customDomainVerificationId the unique verification ID provided by Azure for the custom domain.
     * @return the {@code AzureWebAppBuilder} instance, allowing for fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withCustomDomainVerificationId(String customDomainVerificationId) {
      component.setCustomDomainVerificationId(customDomainVerificationId);
      return builder;
    }

    /**
     * <pre>
     * Sets the maximum allowed daily memory-time quota for the Azure Web App, applicable only to dynamic
     * app service plans. This quota is defined in terms of memory-time units (MTU), which are calculated
     * by multiplying the average memory usage in GB by the app's running time in hours, aggregated daily.
     * 
     * Setting a daily memory-time quota is useful for controlling the resource consumption of your app,
     * preventing unexpected charges, especially in dynamic hosting environments where scalability and
     * resource optimization are crucial. Once the daily quota is reached, the app will be stopped until
     * the quota is reset the following day.
     * 
     * Example usage:
     * {@code builder.withDailyMemoryTimeQuota(1024); // Sets the daily memory-time quota to 1024 MB-hours}</pre>
     *
     * @param dailyMemoryTimeQuota the daily memory-time quota in MB-hours. A null or zero value indicates
     *        no quota is applied.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withDailyMemoryTimeQuota(Integer dailyMemoryTimeQuota) {
      component.setDailyMemoryTimeQuota(dailyMemoryTimeQuota);
      return builder;
    }

    /**
     * <pre>
     * Enables or disables the Azure Web App. This setting determines whether the web app is available
     * and accessible. Setting the web app to disabled takes it offline, making it inaccessible to users
     * and potentially useful for maintenance periods or controlling access.
     * 
     * When the app is disabled, it will not serve any web pages or handle incoming traffic, effectively
     * taking the app offline without altering its configuration or removing deployed content. This can
     * be particularly useful for temporary outages or to prevent access while performing significant updates
     * or changes.
     * 
     * Example usage:
     * {@code builder.withEnabled(true);  // Enables the web app, making it accessible
     * builder.withEnabled(false); // Disables the web app, taking it offline}</pre>
     *
     * @param enabled true to enable the web app, making it accessible to users; false to disable the web app,
     *        taking it offline.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withEnabled(Boolean enabled) {
      component.setEnabled(enabled);
      return builder;
    }

    /**
     * <pre>
     * Configures whether the public hostnames for the Azure Web App are enabled or disabled. Disabling
     * the public hostnames makes the app inaccessible through standard web browsers, restricting access
     * to APIs or backend processes only. This setting can be used for apps that should not be directly
     * accessed by users, serving instead as backend services or APIs.
     * 
     * When {@code hostNamesDisabled} is set to {@code true}, the app is only accessible through an API
     * management process, providing an additional layer of control over how the app is exposed.
     * 
     * Example usage:
     * {@code builder.withHostNamesDisabled(true); // Disables public hostnames, limiting access to APIs
     * builder.withHostNamesDisabled(false); // Enables public hostnames, allowing web access}</pre>
     *
     * @param hostNamesDisabled {@code true} to disable public hostnames and restrict access to the app;
     *        {@code false} to enable public hostnames and allow web access.
     * @return the {@code AzureWebAppBuilder} instance, facilitating fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withHostNamesDisabled(Boolean hostNamesDisabled) {
      component.setHostNamesDisabled(hostNamesDisabled);
      return builder;
    }


    /**
     * <pre>
     * Sets the App Service Environment (ASE) ID for the Azure Web App. The App Service Environment
     * provides a fully isolated and dedicated environment for securely running the web app at high
     * scale. Specifying an ASE is crucial for apps that require enhanced isolation, custom network
     * configurations, or are subject to compliance and security standards that cannot be met by the
     * multi-tenant public cloud environment.
     * 
     * Using an ASE can significantly impact the app's networking capabilities, access control, and
     * scalability options. This method associates the web app with the specified ASE using its unique
     * ID, which is typically obtained from the ASE's resource properties in Azure.
     * 
     * Example usage:
     * {@code builder.withHostingEnvironmentProfileId("your-ase-resource-id");}</pre>
     *
     * @param hostingEnvironmentProfileId the resource ID of the App Service Environment to use for the app.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withHostingEnvironmentProfileId(String hostingEnvironmentProfileId) {
      component.setHostingEnvironmentProfileId(hostingEnvironmentProfileId);
      return builder;
    }

    /**
     * <pre>
     * Configures the Azure Web App to accept only HTTPS requests, enhancing the security of the web app
     * by ensuring that all communication is encrypted. When enabled, all HTTP requests are automatically
     * redirected to HTTPS, preventing unencrypted access to the app. If this option is not explicitly
     * set, Fractal Cloud configures the web app to use HTTPS-only mode by default, aligning with security
     * best practices.
     * 
     * Enabling HTTPS-only mode is strongly recommended to protect user data, prevent man-in-the-middle
     * attacks, and maintain the confidentiality and integrity of information exchanged between the user
     * and the web app.
     * 
     * Example usage:
     * {@code builder.withHttpsOnly(true); // Explicitly enforces HTTPS, redirecting HTTP requests to HTTPS
     * builder.withHttpsOnly(false); // Disables HTTPS-only mode, allowing both HTTP and HTTPS requests}
     * 
     * Note: In the absence of explicit configuration, Fractal Cloud sets HTTPS-only mode to {@code true}
     * by default, ensuring enhanced security for your web application.</pre>
     *
     * @param httpsOnly {@code true} to enable HTTPS-only mode, ensuring all requests use HTTPS;
     *                  {@code false} to allow both HTTP and HTTPS requests. If not defined, Fractal Cloud
     *                  defaults to {@code true}.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withHttpsOnly(Boolean httpsOnly) {
      component.setHttpsOnly(httpsOnly);
      return builder;
    }

    /**
     * <pre>
     * Enables or disables the Hyper-V sandbox for the Azure Web App. The Hyper-V sandbox provides
     * an additional layer of isolation by running the app in a virtualized environment. This setting
     * is particularly useful for applications requiring enhanced security, compatibility with
     * specific Windows features, or isolation from other apps running on the same infrastructure.
     * 
     * Enabling the Hyper-V sandbox can impact the app's performance and resource consumption,
     * so it should be used judiciously based on the application's specific requirements.
     * 
     * Example usage:
     * {@code builder.withHyperV(true); // Enables the Hyper-V sandbox for enhanced isolation
     * builder.withHyperV(false); // Disables the Hyper-V sandbox}</pre>
     *
     * @param hyperV {@code true} to enable the Hyper-V sandbox for the app; {@code false} to disable it.
     * @return the {@code AzureWebAppBuilder} instance, allowing for fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withHyperV(Boolean hyperV) {
      component.setHyperV(hyperV);
      return builder;
    }

    /**
     * <pre>
     * Configures the redundancy mode for the Azure Web App, determining the strategy for high availability
     * and fault tolerance. This setting allows the selection of an appropriate redundancy mode based on the
     * application's requirements for uptime, data replication, and failover capabilities.
     * 
     * The available redundancy modes are:
     *   - {@code NONE}: No additional redundancy; the app is hosted in a single location.
     *   - {@code MANUAL}: Redundancy is managed manually by the user, allowing for custom failover processes.
     *   - {@code FAILOVER}: Automatic failover to a secondary location in case of failure in the primary location.
     *   - {@code ACTIVE_ACTIVE}: The app runs simultaneously in multiple locations, offering high availability.
     *   - {@code GEO_REDUNDANT}: The app is hosted across multiple geographic regions for maximum availability 
     *                    and disaster recovery.
     *
     * Example usage:
     * {@code builder.withRedundancyMode(AzureAppServiceRedundancyMode.GEO_REDUNDANT); // Configures geo-redundant hosting}</pre>
     *
     * @param redundancyMode the {@link AzureAppServiceRedundancyMode} specifying the desired redundancy strategy.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withRedundancyMode(AzureAppServiceRedundancyMode redundancyMode) {
      component.setRedundancyMode(redundancyMode);
      return builder;
    }

    /**
     * <pre>
     * Specifies whether the Azure Web App is reserved for Linux-based hosting. Setting this to {@code true}
     * indicates that the web app is intended to run on Linux, while {@code false} typically indicates Windows-based hosting.
     * This distinction is crucial for ensuring that the app is deployed on the correct operating system, which can 
     * affect compatibility, performance, and available features.
     * 
     * It's important to set this flag correctly according to the application's hosting requirements and the target 
     * operating system environment. Incorrect settings may lead to deployment issues or runtime errors.
     * 
     * Example usage:
     * {@code builder.withReserved(true); // Specifies that the web app is intended for Linux-based hosting
     * builder.withReserved(false); // Specifies that the web app is intended for Windows-based hosting}</pre>
     *
     * @param reserved {@code true} to indicate Linux-based hosting; {@code false} for Windows-based hosting.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withReserved(Boolean reserved) {
      component.setReserved(reserved);
      return builder;
    }

    /**
     * <pre>
     * Configures whether the SCM (KUDU) site associated with the Azure Web App is also stopped when
     * the web app is stopped. Stopping the SCM site along with the web app can help conserve resources
     * and reduce costs, especially for environments that do not require continuous SCM site availability.
     * 
     * By default, the SCM site remains operational even when the web app is stopped, allowing access to
     * SCM functions such as deployment and logging. Setting this option to {@code true} synchronizes the
     * operational state of the SCM site with the web app, ensuring both are stopped or started together.
     * 
     * Example usage:
     * {@code builder.withScmSiteAlsoStopped(true); // Stops the SCM site when the web app is stopped
     * builder.withScmSiteAlsoStopped(false); // Keeps the SCM site running when the web app is stopped}</pre>
     *
     * @param scmSiteAlsoStopped {@code true} to stop the SCM site when the web app is stopped;
     *                           {@code false} to keep the SCM site operational regardless of the web app's state.
     *                           The default is {@code false}.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withScmSiteAlsoStopped(Boolean scmSiteAlsoStopped) {
      component.setScmSiteAlsoStopped(scmSiteAlsoStopped);
      return builder;
    }

    /**
     * <pre>
     * Specifies whether a customer-provided storage account is required for the Azure Web App. This
     * setting is important for applications that rely on external storage solutions for storing data,
     * session state, or logs outside the default web app environment. Enabling this option indicates
     * that the web app's functionality depends on access to a separate storage account, which must be
     * configured separately.
     * 
     * Setting this to {@code true} is necessary for apps that need to utilize custom storage capabilities
     * beyond what is provided by Azure Web Apps by default. It ensures that the app configuration includes
     * the necessary integrations with Azure Storage or other compatible storage services.
     * 
     * Example usage:
     * {@code builder.withStorageAccountRequired(true); // Indicates the app requires a customer-provided storage account
     * builder.withStorageAccountRequired(false); // Indicates the app does not require a separate storage account}</pre>
     *
     * @param storageAccountRequired {@code true} to indicate that the web app requires integration with a
     *        customer-provided storage account; {@code false} if the app can operate with the default
     *        storage provisions.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withStorageAccountRequired(Boolean storageAccountRequired) {
      component.setStorageAccountRequired(storageAccountRequired);
      return builder;
    }


    /**
     * <pre>
     * Configures the Azure Web App to integrate with a specific virtual network and subnet by specifying
     * the Azure Resource Manager (ARM) ID of the virtual network subnet. Regional VNET Integration allows
     * the web app to securely access resources within a virtual network, facilitating scenarios such as
     * accessing Azure services or databases securely within a private network.
     * 
     * The ARM ID provided must follow the specific format for virtual networks and subnets:
     * {@code /subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Network/virtualNetworks
     * /{vnetName}/subnets/{subnetName}}.
     * This ensures that the web app is correctly associated with the intended virtual network and subnet.
     * 
     * Example usage:
     * {@code builder.withVirtualNetworkSubnetId("/subscriptions/your-subscription-id/resourceGroups/your-resource-group/providers/Microsoft.Network/virtualNetworks/your-vnet-name/subnets/your-subnet-name");}</pre>
     *
     * @param virtualNetworkSubnetId the ARM ID of the virtual network and subnet for Regional VNET Integration.
     * @return the {@code AzureWebAppBuilder} instance, allowing for fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withVirtualNetworkSubnetId(String virtualNetworkSubnetId) {
      component.setVirtualNetworkSubnetId(virtualNetworkSubnetId);
      return builder;
    }

    /**
     * <pre>
     * Enables or disables the capability for the Azure Web App to access content over a virtual network
     * (VNet). When enabled, this feature allows the web app to securely access content shared within the
     * virtual network, such as files, databases, or services that are not publicly accessible outside the
     * VNet. This is particularly useful for web apps that require secure access to resources within a
     * corporate network or a managed service environment.
     * 
     * Enabling VNet content share is recommended for applications that rely on secure, high-speed access
     * to content or services hosted within a VNet. This setting enhances the security posture by ensuring
     * that sensitive content is accessed over the protected network infrastructure.
     * 
     * Example usage:
     * {@code builder.withVnetContentShareEnabled(true); // Enables VNet content sharing
     * builder.withVnetContentShareEnabled(false); // Disables VNet content sharing}</pre>
     *
     * @param vnetContentShareEnabled {@code true} to enable accessing content over a virtual network; {@code false} to
     *        disable this capability. The default setting is typically {@code false}, requiring explicit
     *        enabling as needed.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withVnetContentShareEnabled(Boolean vnetContentShareEnabled) {
      component.setVnetContentShareEnabled(vnetContentShareEnabled);
      return builder;
    }

    /**
     * <pre>
     * Enables or disables the capability for the Azure Web App to pull images over a Virtual Network (VNet).
     * When enabled, this feature allows the web app to securely access and pull images from a container registry
     * or other image repositories hosted within a VNet. This is essential for applications that depend on custom
     * images stored in private networks or require enhanced security for accessing images.
     * 
     * Enabling VNet image pull is recommended for scenarios where the web app's container images are not hosted
     * on public registries or when there is a need to ensure secure, private access to images. This setting
     * facilitates the use of custom or proprietary images that are critical for the application's operation
     * without exposing them on public networks.
     * 
     * Example usage:
     * {@code builder.withVnetImagePullEnabled(true); // Enables pulling images over VNet
     * builder.withVnetImagePullEnabled(false); // Disables pulling images over VNet}</pre>
     *
     * @param vnetImagePullEnabled {@code true} to enable pulling images over a Virtual Network; {@code false} to
     *        disable this capability. The default setting is typically {@code false}, requiring explicit
     *        enabling as needed.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withVnetImagePullEnabled(Boolean vnetImagePullEnabled) {
      component.setVnetImagePullEnabled(vnetImagePullEnabled);
      return builder;
    }

    /**
     * <pre>
     * Sets the operating system for the Azure Web App. This configuration determines the runtime
     * environment of the web app, affecting available features, deployment methods, and the overall
     * performance profile. The operating system can be set to either Linux or Windows, each offering
     * distinct advantages and capabilities.
     * 
     * Linux is often chosen for its performance, security, and support for container-based deployments,
     * while Windows is preferred for applications that require specific Windows-based technologies or
     * integrations. The choice should align with the application's requirements and the development
     * team's expertise.
     * 
     * Example usage:
     * {@code builder.withOperatingSystem(AzureOsType.LINUX); // Configures the web app to run on Linux
     * builder.withOperatingSystem(AzureOsType.WINDOWS); // Configures the web app to run on Windows}</pre>
     *
     * @param operatingSystem the {@link AzureOsType} specifying the operating system (Linux or Windows).
     * @return the {@code AzureWebAppBuilder} instance, allowing for fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withOperatingSystem(AzureOsType operatingSystem) {
      component.setOperatingSystem(operatingSystem);
      return builder;
    }

    /**
     * <pre>
     * Configures the runtime stack for the Azure Web App, specifying the development framework and version
     * for the application's environment. This method supports both predefined runtime stacks and the ability
     * to specify newer runtime versions not listed, thanks to the {code ExtendableEnum} functionality.
     * 
     * Predefined runtime stacks include various versions of .NET, ASP.NET, Node.js, Java, Python, PHP, and more,
     * for both Windows and Linux operating systems. If a new runtime stack version is available in Azure and not
     * yet defined in this SDK, it can be specified using the {@code fromString} method of the respective runtime stack
     * class ({@link AzureWebAppWindowsRuntimeStack} or {@link AzureWebAppLinuxRuntimeStack}).
     * 
     * Example usage for a predefined Windows runtime stack:
     * {@code builder.withRuntimeStack(AzureWebAppWindowsRuntimeStack.DOTNET_8);}
     *
     * Example usage for a newer Linux runtime stack not predefined:
     * {@code builder.withRuntimeStack(AzureWebAppLinuxRuntimeStack.fromString("DOTNETCORE|9.0"));}
     * </pre>
     *
     * @param runtimeStack the {@link AzureWebAppRuntimeStack} specifying the desired runtime environment, either
     *        a predefined option or a custom string for newer runtimes.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withRuntimeStack(AzureWebAppRuntimeStack runtimeStack) {
      component.setRuntimeStack(runtimeStack);
      return builder;
    }

    /**
     * <pre>
     * Configures DNS zone settings for the Azure Web App by associating DNS records with a specified
     * DNS zone name. This method allows for the addition of various types of DNS records (A, AAAA, CNAME,
     * MX, PTR, SRV, TXT, etc.) to support different DNS configurations necessary for the web app's
     * routing, mail delivery, and service discovery.
     * 
     * The DNS zone configuration is key to ensuring the web app is accessible under the desired domain
     * names and that other DNS-related services are correctly routed. Each record type supports different
     * configurations, such as target IP addresses for A records, canonical names for CNAME records, and
     * priority and weight for MX and SRV records.
     * 
     * Example usage:
     * {@code DnsRecord aRecord = new DnsARecord.builder()
     *     .withName("www")
     *     .withTtl(Duration.ofHours(1))
     *     .withIpAddress("203.0.113.5")
     *     .build();
     * builder.withDnsZoneConfig("example.com", aRecord);}</pre>
     *
     * @param dnsZoneName the name of the DNS zone to which the records should be added. This should match
     *        the domain name configured in your DNS provider.
     * @param dnsRecord an instance of {@link DnsRecord} representing the DNS record to be added to the zone.
     *        This can be any of the supported DNS record types.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withDnsZoneConfig(String dnsZoneName, DnsRecord dnsRecord) {
      if (component.getDnsZoneConfig() == null) {
        component.setDnsZoneConfig(new HashMap<>());
      }

      if (!component.getDnsZoneConfig().containsKey(dnsZoneName)) {
        component.getDnsZoneConfig().put(dnsZoneName, new ArrayList<>());
      }

      component.getDnsZoneConfig().get(dnsZoneName).add(SerializationUtils.convertValueToMap(dnsRecord));

      return builder;
    }

    /**
     * <pre>
     * Configures multiple DNS records for a specified DNS zone associated with the Azure Web App. This
     * method allows for batch addition of various types of DNS records (A, AAAA, CNAME, MX, PTR, SRV, TXT, etc.),
     * supporting comprehensive DNS configurations for domain routing, service discovery, and mail delivery.
     * 
     * Utilizing this method simplifies the process of adding multiple DNS records to a single DNS zone, 
     * enhancing the efficiency of DNS setup tasks. Each type of DNS record supports configurations specific
     * to its purpose, such as IP addresses for A and AAAA records, canonical names for CNAME records, and 
     * priority and weight specifications for MX and SRV records.
     * 
     * Example usage:
     * {@code Collection<DnsRecord> dnsRecords = Arrays.asList(
     *     new DnsARecord.builder()
     *       .withName("www")
     *       .withTtl(Duration.ofHours(1))
     *       .withIpAddress("203.0.113.5")
     *       .build(),
     *     new DnsCNameRecord.builder()
     *       .withName("mail")
     *       .withTtl(Duration.ofHours(1))
     *       .withCanonicalName("mail.example.com")
     *       .build());
     * builder.withDnsZoneConfig("example.com", dnsRecords);}</pre>
     *
     * @param dnsZoneName the name of the DNS zone to which the records are to be added, matching the domain
     *        name configured with your DNS provider.
     * @param dnsRecords a collection of {@link DnsRecord} instances representing the DNS records to be added
     *        to the zone. Supports a variety of DNS record types for flexible configuration.
     * @return the {@code AzureWebAppBuilder} instance, allowing for fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withDnsZoneConfig(String dnsZoneName, Collection<DnsRecord> dnsRecords) {
      dnsRecords.forEach(dnsRecord -> withDnsZoneConfig(dnsZoneName, dnsRecord));
      return builder;
    }

    /**
     * <pre>
     * Configures DNS zones with their respective DNS records for the Azure Web App. This method allows
     * for the bulk association of DNS records with multiple DNS zones, facilitating complex DNS setups
     * involving multiple domain names or subdomains.
     * 
     * Each entry in the provided map represents a DNS zone and its associated DNS records. This approach
     * is useful for configuring a comprehensive DNS setup in a single operation, enhancing efficiency
     * and reducing the potential for configuration errors.
     * 
     * Example usage:
     * {@code Map<String, Collection<DnsRecord>> dnsRecordsMap = Map.of(
     *     "example.com", Arrays.asList(
     *         new DnsARecord.builder()
     *           .withName("www")
     *           .withTtl(Duration.ofHours(1))
     *           .withIpAddress("203.0.113.5")
     *           .build()
     *     ),
     *     "anotherdomain.com", Arrays.asList(
     *         new DnsCNameRecord.builder()
     *           .withName("blog")
     *           .withTtl(Duration.ofHours(1))
     *           .withCanonicalName("username.github.io")
     *           .build()
     *     )
     * );
     * builder.withDnsZoneConfig(dnsRecordsMap);}</pre>
     *
     * @param dnsRecordsMap a map where each key is a DNS zone name and each value is a collection of {@link DnsRecord}
     *        instances to be associated with that DNS zone.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withDnsZoneConfig(Map<? extends String, ? extends Collection<DnsRecord>> dnsRecordsMap) {
      if (dnsRecordsMap == null || dnsRecordsMap.isEmpty()) {
        return builder;
      }

      dnsRecordsMap.forEach((key, value) -> {
        for (var dnsRecord : value) {
          withDnsZoneConfig(key, dnsRecord);
        }
      });

      return builder;
    }


    /**
     * <pre>
     * Adds and configures deployment slots for the Azure Web App. Deployment slots enable the hosting
     * of different versions of your web app in separate environments, allowing for testing, staging,
     * and rolling out changes with minimal impact on the production environment. Each slot can be
     * configured independently and swapped with the production slot to facilitate seamless updates.
     * 
     * This method ensures that all provided deployment slots are associated with the current web app,
     * inheriting settings such as the Azure region and resource group from the parent app. This
     * association is crucial for maintaining organizational and operational consistency across
     * environments.
     * 
     * Example usage:
     * {@code Collection<AzureWebAppDeploymentSlot> deploymentSlots = Arrays.asList(
     *     new AzureWebAppDeploymentSlot.builder()
     *         .withName("staging")
     *         .build(),
     *     new AzureWebAppDeploymentSlot.builder()
     *         .withName("testing")
     *         .build()
     * );
     * builder.withDeploymentSlots(deploymentSlots);} 
     * </pre>
     *
     * @param deploymentSlots a collection of {@link AzureWebAppDeploymentSlot} instances to be added
     *        to the web app. Each slot is configured and linked to the parent web app.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withDeploymentSlots(Collection<AzureWebAppDeploymentSlot> deploymentSlots) {
      if (CollectionUtils.isBlank(deploymentSlots)) {
        return builder;
      }

      this.component.deploymentSlots.addAll(deploymentSlots);
      return builder;
    }

    /**
     * <pre>
     * Adds and configures a single deployment slot for the Azure Web App. Deployment slots are
     * environments where different versions of the web app can be hosted, allowing for seamless
     * testing, staging, and production swap operations. This method enables the configuration of
     * a deployment slot that inherits settings from the parent web app, including the Azure region
     * and resource group, while allowing for environment-specific settings.
     * 
     * Deployment slots facilitate A/B testing, staging environments for new features, and hotfix
     * deployments, without impacting the live production environment. Configuring slots with this
     * method ensures they are directly associated with the current web app instance.
     * 
     * Example usage:
     * {@code AzureWebAppDeploymentSlot deploymentSlot = new AzureWebAppDeploymentSlot.builder()
     *     .withName("pre-production")
     *     .build();
     * builder.withDeploymentSlot(deploymentSlot);}</pre>
     *
     * @param deploymentSlot an {@link AzureWebAppDeploymentSlot} instance to be added to the web app.
     *        The slot is set up to align with the web app's configuration while allowing for specific
     *        adjustments.
     * @return the {@code AzureWebAppBuilder} instance, enabling fluent chaining of configuration methods.
     */
    public AzureWebAppBuilder withDeploymentSlot(AzureWebAppDeploymentSlot deploymentSlot) {
      return withDeploymentSlots(List.of(deploymentSlot));
    }
  }

}
