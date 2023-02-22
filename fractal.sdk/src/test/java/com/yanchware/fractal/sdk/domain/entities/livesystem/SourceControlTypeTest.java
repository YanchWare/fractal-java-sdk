package com.yanchware.fractal.sdk.domain.entities.livesystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SourceControlTypeTest {
  @Test
  public void shouldReturnProperValue() {
    assertAll(
        () -> assertEquals(SourceControlType.fromString("BitbucketGit"), SourceControlType.BITBUCKET_GIT),
        () -> assertEquals(SourceControlType.fromString("BitbucketHg"), SourceControlType.BITBUCKET_HG),
        () -> assertEquals(SourceControlType.fromString("CodePlexGit"), SourceControlType.CODE_PLEX_GIT),
        () -> assertEquals(SourceControlType.fromString("CodePlexHg"), SourceControlType.CODE_PLEX_HG),
        () -> assertEquals(SourceControlType.fromString("Dropbox"), SourceControlType.DROPBOX),
        () -> assertEquals(SourceControlType.fromString("ExternalGit"), SourceControlType.EXTERNAL_GIT),
        () -> assertEquals(SourceControlType.fromString("ExternalHg"), SourceControlType.EXTERNAL_HG),
        () -> assertEquals(SourceControlType.fromString("GitHub"), SourceControlType.GIT_HUB),
        () -> assertEquals(SourceControlType.fromString("LocalGit"), SourceControlType.LOCAL_GIT),
        () -> assertEquals(SourceControlType.fromString("None"), SourceControlType.NONE),
        () -> assertEquals(SourceControlType.fromString("OneDrive"), SourceControlType.ONE_DRIVE),
        () -> assertEquals(SourceControlType.fromString("Tfs"), SourceControlType.TFS),
        () -> assertEquals(SourceControlType.fromString("VSO"), SourceControlType.VSO),
        () -> assertEquals(SourceControlType.fromString("VSTSRM"), SourceControlType.VSTSRM)
    );

  }
}