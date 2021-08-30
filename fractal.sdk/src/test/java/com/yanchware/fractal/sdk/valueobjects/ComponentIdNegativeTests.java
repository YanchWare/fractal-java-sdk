package com.yanchware.fractal.sdk.valueobjects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(value = Parameterized.class)
public class ComponentIdNegativeTests
{
    @Parameterized.Parameter
    public String value;

    @Parameterized.Parameter(value = 1)
    public Collection<String> expectedErrors;

    @Parameterized.Parameters(name = "{index}: test id with {0}")
    public static Collection<Object[]> badInput() {
        return Arrays.asList(new Object[][]{
          {null, List.of(String.format(ComponentId.ID_NULL_OR_EMPTY_TEMPLATE, "null"))},
          {"", List.of(String.format(ComponentId.ID_NULL_OR_EMPTY_TEMPLATE, ""))},
          {"   ", List.of(String.format(ComponentId.ID_NULL_OR_EMPTY_TEMPLATE, "   "))},
          {"\t", List.of(String.format(ComponentId.ID_NULL_OR_EMPTY_TEMPLATE, "\t"))},
          {"\n", List.of(String.format(ComponentId.ID_NULL_OR_EMPTY_TEMPLATE, "\n"))},
          {"\r", List.of(String.format(ComponentId.ID_NULL_OR_EMPTY_TEMPLATE, "\r"))},
          {"1test-compo", List.of(String.format(ComponentId.ILLEGAL_ID_TEMPLATE, "1test-compo"))},
          {"1test\ncompo", List.of(String.format(ComponentId.ILLEGAL_ID_TEMPLATE, "1test\ncompo"))},
          {"this-is-a-very-long-id-which-will-break", List.of(String.format(ComponentId.ID_LENGTH_MISMATCH_TEMPLATE, "this-is-a-very-long-id-which-will-break"))},
          {"this-is-a-very--and-bad-id-which-will-break", List.of(
            String.format(ComponentId.ILLEGAL_ID_TEMPLATE, "this-is-a-very--and-bad-id-which-will-break"),
            String.format(ComponentId.ID_LENGTH_MISMATCH_TEMPLATE, "this-is-a-very--and-bad-id-which-will-break"))},
          {"a-", List.of(String.format(ComponentId.ILLEGAL_ID_TEMPLATE, "a-"))},
          {"a-strange--id", List.of(String.format(ComponentId.ILLEGAL_ID_TEMPLATE, "a-strange--id"))},
        });
    }

    @Test
    public void validationFailsForBadInput()
    {
        Collection<String> errors = ComponentId.validate(value);
        assertThat(errors).containsAll(expectedErrors);
    }
}
