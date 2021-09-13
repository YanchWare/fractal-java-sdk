package com.yanchware.fractal.sdk.valueobjects;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ComponentIdPositiveTests
{
    public static Stream<Arguments> badInput() {
        return Stream.of(
          Arguments.of("abcd"),
          Arguments.of("a-c-d"),
          Arguments.of("a-nice-id"));
    }

    @ParameterizedTest
    @MethodSource("badInput")
    public void validationFails_when_badInput(String value)
    {
        Collection<String> errors = ComponentId.validate(value);
        assertThat(errors).isEmpty();
    }
}
