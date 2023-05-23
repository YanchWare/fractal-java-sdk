package com.yanchware.fractal.sdk;

import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;

import java.util.List;

import static com.yanchware.fractal.sdk.utils.TestUtils.getLiveSystemExample;

public class LiveSystemFirstTest {

    @Test
    @Disabled
    @SetEnvironmentVariable(key = "CLIENT_ID", value = "xxx")
    @SetEnvironmentVariable(key = "CLIENT_SECRET", value = "xxx")
    public void liveSystemInstantiated_when_AutomatonCalledWithValidLiveSystemInformation() throws InstantiatorException {
        Automaton.instantiate(List.of(getLiveSystemExample()));
    }

}