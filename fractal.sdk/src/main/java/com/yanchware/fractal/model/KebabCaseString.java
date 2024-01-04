package com.yanchware.fractal.model;

import static org.apache.commons.lang3.StringUtils.isBlank;

public record KebabCaseString(String value) {
    public KebabCaseString {
        if (isBlank(value)) {
            throw new IllegalArgumentException("Value cannot be blank");
        }

        if (!isKebabCase(value)){
            throw new IllegalArgumentException(
                    String.format("Value '%s' is not in Kebab case", value));
        }
    }

    private static boolean isKebabCase(String value) {

    }
}
