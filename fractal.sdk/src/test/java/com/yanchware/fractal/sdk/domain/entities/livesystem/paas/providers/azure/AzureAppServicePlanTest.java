package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.AzureAppServicePlan;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AzureAppServicePlanTest {

  @Test
  public void exceptionThrown_when_appServicePlanBuiltWithNullName() {
    assertThatThrownBy(() ->  AzureAppServicePlan.builder().withName(null).build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters");
  }

  @Test
  public void exceptionThrown_when_appServicePlanBuiltWithEmptyName() {
    assertThatThrownBy(() ->  AzureAppServicePlan.builder().withName("").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters");
  }

  @Test
  public void exceptionThrown_when_appServicePlanBuiltWithToLongName() {
    var veryLongName = "aTfR2EbiZzWwaCLdGWy2pCujyFCbFzLgCkYQeU9zF";
    assertThatThrownBy(() ->  AzureAppServicePlan.builder().withName(veryLongName).build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters");
  }

  @Test
  public void exceptionThrown_when_appServicePlanBuiltWithNameThatStartsWithHyphen() {
    var veryLongName = "-name";
    assertThatThrownBy(() ->  AzureAppServicePlan.builder().withName(veryLongName).build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters");
  }

  @Test
  public void exceptionThrown_when_appServicePlanBuiltWithNameThatEndsWithHyphen() {
    var veryLongName = "name-";
    assertThatThrownBy(() ->  AzureAppServicePlan.builder().withName(veryLongName).build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters");
  }

  @Test
  public void exceptionThrown_when_appServicePlanBuiltWithNameThatContainsUnderscore() {
    var veryLongName = "name_test";
    assertThatThrownBy(() ->  AzureAppServicePlan.builder().withName(veryLongName).build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("The name only allow alphanumeric characters and hyphens, cannot start or end in a hyphen, and must be less than or equal to 40 characters");
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperName() {
    var appServicePlanName = "name-test";
    var appServicePlan = AzureAppServicePlan.builder()
        .withName(appServicePlanName)
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(appServicePlanName);
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperNameAndResourceGroup() {
    var appServicePlanName = "name-test";
    var resourceGroup = AzureResourceGroup.builder()
        .withName("new-resource-group")
        .withRegion(AzureRegion.US_EAST)
        .build();
    
    var appServicePlan = AzureAppServicePlan.builder()
        .withName(appServicePlanName)
        .withAzureResourceGroup(resourceGroup)
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(appServicePlanName);
    assertThat(appServicePlan.getAzureResourceGroup()).isEqualTo(resourceGroup);
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperNameResourceGroupAndRegion() {
    var appServicePlanName = "name-test";
    var selectedRegion = AzureRegion.US_EAST;
    var resourceGroup = AzureResourceGroup.builder()
        .withName("new-resource-group")
        .withRegion(selectedRegion)
        .build();

    var appServicePlan = AzureAppServicePlan.builder()
        .withName(appServicePlanName)
        .withAzureResourceGroup(resourceGroup)
        .withAzureRegion(selectedRegion)
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(appServicePlanName);
    assertThat(appServicePlan.getAzureResourceGroup()).isEqualTo(resourceGroup);
    assertThat(appServicePlan.getAzureRegion()).isEqualTo(selectedRegion);
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperNameResourceGroupRegionAndOperatingSystem() {
    var appServicePlanName = "name-test";
    var selectedRegion = AzureRegion.US_EAST;
    var selectedOperatingSystem = AzureOsType.LINUX;
    
    var resourceGroup = AzureResourceGroup.builder()
        .withName("new-resource-group")
        .withRegion(selectedRegion)
        .build();

    var appServicePlan = AzureAppServicePlan.builder()
        .withName(appServicePlanName)
        .withAzureResourceGroup(resourceGroup)
        .withAzureRegion(selectedRegion)
        .withOperatingSystem(selectedOperatingSystem)
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(appServicePlanName);
    assertThat(appServicePlan.getAzureResourceGroup()).isEqualTo(resourceGroup);
    assertThat(appServicePlan.getAzureRegion()).isEqualTo(selectedRegion);
    assertThat(appServicePlan.getOperatingSystem()).isEqualTo(selectedOperatingSystem);
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperNameResourceGroupRegionOperatingSystemAndPricingPlan() {
    var appServicePlanName = "name-test";
    var selectedRegion = AzureRegion.US_EAST;
    var selectedOperatingSystem = AzureOsType.LINUX;
    var selectedPricingPlan = AzurePricingPlan.BASIC_B1;

    var resourceGroup = AzureResourceGroup.builder()
        .withName("new-resource-group")
        .withRegion(selectedRegion)
        .build();

    var appServicePlan = AzureAppServicePlan.builder()
        .withName(appServicePlanName)
        .withAzureResourceGroup(resourceGroup)
        .withAzureRegion(selectedRegion)
        .withOperatingSystem(selectedOperatingSystem)
        .withPricingPlan(selectedPricingPlan)
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(appServicePlanName);
    assertThat(appServicePlan.getAzureResourceGroup()).isEqualTo(resourceGroup);
    assertThat(appServicePlan.getAzureRegion()).isEqualTo(selectedRegion);
    assertThat(appServicePlan.getOperatingSystem()).isEqualTo(selectedOperatingSystem);
    assertThat(appServicePlan.getPricingPlan()).isEqualTo(selectedPricingPlan);
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperNameResourceGroupRegionOperatingSystemPricingPlanAndEnabledZoneRedundancy() {
    var appServicePlanName = "name-test";
    var selectedRegion = AzureRegion.US_EAST;
    var selectedOperatingSystem = AzureOsType.LINUX;
    var selectedPricingPlan = AzurePricingPlan.BASIC_B1;

    var resourceGroup = AzureResourceGroup.builder()
        .withName("new-resource-group")
        .withRegion(selectedRegion)
        .build();

    var appServicePlan = AzureAppServicePlan.builder()
        .withName(appServicePlanName)
        .withAzureResourceGroup(resourceGroup)
        .withAzureRegion(selectedRegion)
        .withOperatingSystem(selectedOperatingSystem)
        .withPricingPlan(selectedPricingPlan)
        .enableZoneRedundancy()
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(appServicePlanName);
    assertThat(appServicePlan.getAzureResourceGroup()).isEqualTo(resourceGroup);
    assertThat(appServicePlan.getAzureRegion()).isEqualTo(selectedRegion);
    assertThat(appServicePlan.getOperatingSystem()).isEqualTo(selectedOperatingSystem);
    assertThat(appServicePlan.getPricingPlan()).isEqualTo(selectedPricingPlan);
    assertThat(appServicePlan.getEnableZoneRedundancy()).isEqualTo(true);
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperNameResourceGroupRegionOperatingSystemPricingPlanEnabledZoneRedundancyAndTags() {
    var appServicePlanName = "name-test";
    var selectedRegion = AzureRegion.US_EAST;
    var selectedOperatingSystem = AzureOsType.LINUX;
    var selectedPricingPlan = AzurePricingPlan.BASIC_B1;
    var tags = Map.of("tags_Key1", "tags_Value1", "tags_Key2", "tags_Value2");


    var resourceGroup = AzureResourceGroup.builder()
        .withName("new-resource-group")
        .withRegion(selectedRegion)
        .build();

    var appServicePlan = AzureAppServicePlan.builder()
        .withName(appServicePlanName)
        .withAzureResourceGroup(resourceGroup)
        .withAzureRegion(selectedRegion)
        .withOperatingSystem(selectedOperatingSystem)
        .withPricingPlan(selectedPricingPlan)
        .enableZoneRedundancy()
        .withTags(tags)
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(appServicePlanName);
    assertThat(appServicePlan.getAzureResourceGroup()).isEqualTo(resourceGroup);
    assertThat(appServicePlan.getAzureRegion()).isEqualTo(selectedRegion);
    assertThat(appServicePlan.getOperatingSystem()).isEqualTo(selectedOperatingSystem);
    assertThat(appServicePlan.getPricingPlan()).isEqualTo(selectedPricingPlan);
    assertThat(appServicePlan.getEnableZoneRedundancy()).isEqualTo(true);
    assertThat(appServicePlan.getTags().size()).isEqualTo(2);
    assertThat(appServicePlan.getTags()).isEqualTo(tags);
  }

  @Test
  public void returns_without_errors_when_appServicePlanBuiltWithProperNameResourceGroupRegionOperatingSystemPricingPlanEnabledZoneRedundancyAndTag() {
    var appServicePlanName = "name-test";
    var selectedRegion = AzureRegion.US_EAST;
    var selectedOperatingSystem = AzureOsType.LINUX;
    var selectedPricingPlan = AzurePricingPlan.BASIC_B1;
    var tagKey = "tagKey";
    var tagValue = "tagValue";
    

    var resourceGroup = AzureResourceGroup.builder()
        .withName("new-resource-group")
        .withRegion(selectedRegion)
        .build();

    var appServicePlan = AzureAppServicePlan.builder()
        .withName(appServicePlanName)
        .withAzureResourceGroup(resourceGroup)
        .withAzureRegion(selectedRegion)
        .withOperatingSystem(selectedOperatingSystem)
        .withPricingPlan(selectedPricingPlan)
        .enableZoneRedundancy()
        .withTag(tagKey, tagValue)
        .build();

    assertThat(appServicePlan.getName()).isEqualTo(appServicePlanName);
    assertThat(appServicePlan.getAzureResourceGroup()).isEqualTo(resourceGroup);
    assertThat(appServicePlan.getAzureRegion()).isEqualTo(selectedRegion);
    assertThat(appServicePlan.getOperatingSystem()).isEqualTo(selectedOperatingSystem);
    assertThat(appServicePlan.getPricingPlan()).isEqualTo(selectedPricingPlan);
    assertThat(appServicePlan.getEnableZoneRedundancy()).isEqualTo(true);
    assertThat(appServicePlan.getTags().size()).isEqualTo(1);
    assertThat(appServicePlan.getTags()).isEqualTo(Map.of(tagKey, tagValue));
  }
}
