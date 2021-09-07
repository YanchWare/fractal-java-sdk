package com.yanchware.fractal.sdk.valueobjects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(value = Parameterized.class)
public class ComponentIdPositiveTests
{
    @Parameterized.Parameter
    public String value;

    @Parameterized.Parameters(name = "{index}: test id with {0}")
    public static Collection<Object[]> badInput() {
        return Arrays.asList(new Object[][]{
          {"abcd"},
          {"a-c-d"},
          {"a-nice-id"},
        });
    }

    @Test
    public void validationFails_when_badInput()
    {
        Collection<String> errors = ComponentId.validate(value);
        assertThat(errors).isEmpty();
    }
}
