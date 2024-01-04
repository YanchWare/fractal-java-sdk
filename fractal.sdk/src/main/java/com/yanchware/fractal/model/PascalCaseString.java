package com.yanchware.fractal.model;

import static org.apache.commons.lang3.StringUtils.isBlank;

public record PascalCaseString(String value) {
    public PascalCaseString {
        if (isBlank(value)) {
            throw new IllegalArgumentException("Value cannot be blank");
        }

        if (!isPascalCase(value)){
            throw new IllegalArgumentException(
                    String.format("Value '%s' is not in Pascal case", value));
        }
    }

    private static boolean isPascalCase(String value) {

    }
}
