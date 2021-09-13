package com.yanchware.fractal.sdk.valueobjects;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ComponentIdNegativeTests
{
    public static Stream<Arguments> badInput() {
        return Stream.of(
          Arguments.of(null, List.of(String.format(ComponentId.ID_NULL_OR_EMPTY_TEMPLATE, "null"))),
          Arguments.of("", List.of(String.format(ComponentId.ID_NULL_OR_EMPTY_TEMPLATE, ""))),
          Arguments.of("   ", List.of(String.format(ComponentId.ID_NULL_OR_EMPTY_TEMPLATE, "   "))),
          Arguments.of("\t", List.of(String.format(ComponentId.ID_NULL_OR_EMPTY_TEMPLATE, "\t"))),
          Arguments.of("\n", List.of(String.format(ComponentId.ID_NULL_OR_EMPTY_TEMPLATE, "\n"))),
          Arguments.of("\r", List.of(String.format(ComponentId.ID_NULL_OR_EMPTY_TEMPLATE, "\r"))),
          Arguments.of("1test-compo", List.of(String.format(ComponentId.ILLEGAL_ID_TEMPLATE, "1test-compo"))),
          Arguments.of("1test\ncompo", List.of(String.format(ComponentId.ILLEGAL_ID_TEMPLATE, "1test\ncompo"))),
          Arguments.of("this-is-a-very-long-id-which-will-break", List.of(String.format(ComponentId.ID_LENGTH_MISMATCH_TEMPLATE, "this-is-a-very-long-id-which-will-break"))),
          Arguments.of("this-is-a-very--and-bad-id-which-will-break", List.of(
                    String.format(ComponentId.ILLEGAL_ID_TEMPLATE, "this-is-a-very--and-bad-id-which-will-break"),
                    String.format(ComponentId.ID_LENGTH_MISMATCH_TEMPLATE, "this-is-a-very--and-bad-id-which-will-break"))),
          Arguments.of("a-", List.of(String.format(ComponentId.ILLEGAL_ID_TEMPLATE, "a-"))),
          Arguments.of("a-strange--id", List.of(String.format(ComponentId.ILLEGAL_ID_TEMPLATE, "a-strange--id"))));
    }

    @ParameterizedTest
    @MethodSource("badInput")
    public void validationFails_when_badInput(String value, Collection<String> expectedErrors)
    {
        Collection<String> errors = ComponentId.validate(value);
        assertThat(errors).containsAll(expectedErrors);
    }
}
