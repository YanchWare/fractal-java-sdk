package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.yanchware.fractal.sdk.utils.ExtendableEnum;

import java.util.Collection;

public class SourceControlType extends ExtendableEnum<SourceControlType> {
  public static final SourceControlType BITBUCKET_GIT = fromString("BitbucketGit");
  public static final SourceControlType BITBUCKET_HG = fromString("BitbucketHg");
  public static final SourceControlType CODE_PLEX_GIT = fromString("CodePlexGit");
  public static final SourceControlType CODE_PLEX_HG = fromString("CodePlexHg");
  public static final SourceControlType DROPBOX = fromString("Dropbox");
  public static final SourceControlType EXTERNAL_GIT = fromString("ExternalGit");
  public static final SourceControlType EXTERNAL_HG = fromString("ExternalHg");
  public static final SourceControlType GIT_HUB = fromString("GitHub");
  public static final SourceControlType LOCAL_GIT = fromString("LocalGit");
  public static final SourceControlType NONE = fromString("None");
  public static final SourceControlType ONE_DRIVE = fromString("OneDrive");
  public static final SourceControlType TFS = fromString("Tfs");
  public static final SourceControlType VSO = fromString("VSO");
  public static final SourceControlType VSTSRM = fromString("VSTSRM");
  
  public SourceControlType() {
  }

  @JsonCreator
  public static SourceControlType fromString(String name) {
    return fromString(name, SourceControlType.class);
  }

  public static Collection<SourceControlType> values() {
    return values(SourceControlType.class);
  }
}
