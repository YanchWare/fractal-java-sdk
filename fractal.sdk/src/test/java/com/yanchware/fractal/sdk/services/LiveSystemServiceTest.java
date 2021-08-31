package com.yanchware.fractal.sdk.services;

import com.yanchware.fractal.sdk.services.livesystemcontract.commands.InstantiateCommandRequest;
import org.junit.Test;

public class LiveSystemServiceTest {

    private LiveSystemService liveSystemService = new LiveSystemService();

    @Test
    public void test() {
        InstantiateCommandRequest command = new InstantiateCommandRequest();
        liveSystemService.instantiate(command);
    }

}