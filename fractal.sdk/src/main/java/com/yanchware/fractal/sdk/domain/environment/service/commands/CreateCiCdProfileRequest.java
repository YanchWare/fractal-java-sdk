package com.yanchware.fractal.sdk.domain.environment.service.commands;

import com.yanchware.fractal.sdk.domain.environment.CiCdProfile;

public record CreateCiCdProfileRequest(
        String shortName,
        String displayName,
        String description,
        String sshPrivateKeyData,
        String sshPrivateKeyPassphrase){

    public static CreateCiCdProfileRequest fromProfile(CiCdProfile profile)
    {
        return new CreateCiCdProfileRequest(
                profile.shortName(),
                profile.displayName(),
                profile.description(),
                profile.sshPrivateKeyData(),
                profile.sshPrivateKeyPassphrase());
    }
}