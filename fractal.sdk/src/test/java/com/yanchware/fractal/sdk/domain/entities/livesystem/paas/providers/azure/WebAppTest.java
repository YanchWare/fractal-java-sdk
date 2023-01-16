package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.AzureWebApp;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.AzureWebAppHosting;
import org.junit.jupiter.api.Test;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.appservice.AzureWebApp.builder;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.PAAS_AZURE_WEBAPP;
import static org.assertj.core.api.Assertions.*;

public class WebAppTest {

  @Test
  public void exceptionThrown_when_workloadBuiltWithNullId() {
    assertThatThrownBy(() -> builder().withId("").build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Component Id is illegal");
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithEmptyValues() {
    assertThatThrownBy(() -> generateBuilder().build()).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContainingAll(
            "privateSSHKeyPassphraseSecretId is either empty or blank",
            "privateSSHKeySecretId is either empty or blank",
            "sshRepositoryURI is either empty or blank",
            "repoId is either empty or blank");
  }

  @Test
  public void typeIsWebApp_when_workloadBuiltWithAllRequiredValues() {
    var builder = generateBuilder()
        .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
        .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withBranchName("branch-name")
        .withHosting(
            AzureWebAppHosting.builder().withDotnetVersion("DOTNET:***"
        ).build());
    assertThat(builder.build().getType()).isEqualTo(PAAS_AZURE_WEBAPP);
    assertThatCode(builder::build).doesNotThrowAnyException();
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithWorkloadSecretIdKeyEmpty() {
    var builder = generateBuilder()
        .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
        .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withSecretIdKey("");
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Workload Secret Id Key is either empty or blank");
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithBranchNameKeyEmpty() {
    var builder = generateBuilder()
        .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
        .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withBranchName("");
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("branchName is either empty or blank and it is required");
  }

  @Test
  public void exceptionThrown_when_workloadBuiltWithWorkloadSecretPasswordKeyEmpty() {
    var builder = generateBuilder()
        .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
        .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withSecretPasswordKey("");
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("Workload Secret Password Key is either empty or blank");
  }

  @Test
  public void exceptionThrown_when_mixingJavaAndDotnetHosting() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withDotnetVersion("DOTNETCORE:7.0")
            .withJavaVersion("JAVA:17-java17")
            .build()
        );
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("[javaVersion Validation] Only one hosting configuration can be set. [dotnet] has already been set");
  }

  @Test
  public void exceptionThrown_when_mixingJavaAndPhpHosting() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withJavaVersion("JAVA:17-java17")
            .withPhpVersion("PHP:8.1")
            .build()
        );
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("[phpVersion Validation] Only one hosting configuration can be set. [java] has already been set");
  }

  @Test
  public void hostingIsDotnet_when_settingDotnetHosting() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withDotnetVersion("DOTNETCORE:7.0")
            .build()
        );
    assertThat(builder.build().getHosting().getDotnetVersion()).isEqualTo("DOTNETCORE:7.0");
    assertThatCode(builder::build).doesNotThrowAnyException();
  }

  @Test
  public void hostingIsPhp_when_settingPhpHosting() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withPhpVersion("PHP:8.1")
            .build()
        );
    assertThat(builder.build().getHosting().getPhpVersion()).isEqualTo("PHP:8.1");
    assertThat(builder.build().getHosting().getPythonVersion()).isEqualTo(null);
    assertThatCode(builder::build).doesNotThrowAnyException();
  }

  @Test
  public void hostingIsJavaContainer_when_settingJavaContainerHosting() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withJavaContainer("xxx")
            .withJavaContainerVersion("yyy")
            .build()
        );
    assertThat(builder.build().getHosting().getJavaContainer()).isEqualTo("xxx");
    assertThat(builder.build().getHosting().getJavaContainerVersion()).isEqualTo("yyy");
    assertThat(builder.build().getHosting().getDotnetVersion()).isEqualTo(null);
    assertThat(builder.build().getHosting().getJavaVersion()).isEqualTo(null);
    assertThat(builder.build().getHosting().getPhpVersion()).isEqualTo(null);
    assertThat(builder.build().getHosting().getNodeVersion()).isEqualTo(null);
    assertThatCode(builder::build).doesNotThrowAnyException();
  }

  @Test
  public void exceptionThrown_when_missingJavaContainerVersionHosting() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withJavaContainer("xxx")
            .build()
        );
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("[javaContainerVersion Validation] Incomplete hosting types definition. Both [javaContainer] and [javaContainerVersion] must be set");
  }

  @Test
  public void exceptionThrown_when_missingJavaContainerHosting() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder()
            .withJavaContainerVersion("yyy")
            .build()
        );
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("[javaContainer Validation] Incomplete hosting types definition. Both [javaContainer] and [javaContainerVersion] must be set");
  }

  @Test
  public void exceptionThrown_when_missingHostingConfiguration() {
    var builder = generateSampleBuilder()
        .withHosting(AzureWebAppHosting.builder().build()
        );
    assertThatThrownBy(builder::build).
        isInstanceOf(IllegalArgumentException.class).
        hasMessageContaining("[hosting Validation] No hosting types (dotnet, java, java container, nodejs, python, php) have been set");
  }
  
  private AzureWebApp.AzureWebAppBuilder generateBuilder() {
    return builder().withId("webapp");
  }

  private AzureWebApp.AzureWebAppBuilder generateSampleBuilder() {
    return generateBuilder()
        .withPrivateSSHKeyPassphraseSecretId("svc-private-ssh-key-pass")
        .withPrivateSSHKeySecretId("svc-private-ssh-key-secret")
        .withSSHRepositoryURI("ssh")
        .withRepoId("repo-id")
        .withBranchName("env/test")
        .withSecretPasswordKey("***");
  }
  
}
