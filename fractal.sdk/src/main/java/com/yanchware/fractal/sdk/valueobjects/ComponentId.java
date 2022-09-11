package com.yanchware.fractal.sdk.valueobjects;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
public class ComponentId {

    private final static Pattern valueValidation = Pattern.compile("^(?!\\d)[a-z\\d]+((?:-[a-z\\d]+)*)+$");
    protected final static String ID_NULL_OR_EMPTY_TEMPLATE = "Component id '%s' is illegal. A valid component id cannot be null, empty or contain spaces";
    protected final static String ILLEGAL_ID_TEMPLATE = "Component id '%s' is illegal. A valid component id must start with a lowercase character and it can only contain lowercase characters, digits and hyphens";
    protected final static String ID_LENGTH_MISMATCH_TEMPLATE = "Component id '%s' is illegal. A valid component Id must be between 3 and 15 characters of length";
    private final String value;

    protected ComponentId(String value) {
        Collection<String> validationErrors = validate(value);
        if (!validationErrors.isEmpty()) {
            throw new IllegalArgumentException(String.format(
                    "Component Id is illegal. Errors: %s",
                    Arrays.toString(validationErrors.toArray())));
        }
        this.value = value;
    }

    public static ComponentId from(String value) {
        return new ComponentId(value);
    }

    public static Collection<String> validate(String value) {
        List<String> ret = new ArrayList<>();
        if (isBlank(value)) {
            ret.add(String.format(ID_NULL_OR_EMPTY_TEMPLATE, value));
            return ret;
        }

        if (!valueValidation.matcher(value).matches()) {
            ret.add(String.format(ILLEGAL_ID_TEMPLATE, value));
        }

        if (value.length() < 3 || value.length() > 15) {
            ret.add(String.format(ID_LENGTH_MISMATCH_TEMPLATE, value));
        }

        return ret;
    }
}
