package com.yanchware.fractal.sdk.services;

import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import com.yanchware.fractal.sdk.services.livesystemcontract.commands.InstantiateCommandRequest;
import org.junit.Test;

public class LiveSystemServiceTest {

    private LiveSystemService liveSystemService = new LiveSystemService(null);

    @Test
    public void test() throws InstantiatorException {
        InstantiateCommandRequest command = new InstantiateCommandRequest();
        liveSystemService.instantiate(command);
    }

}