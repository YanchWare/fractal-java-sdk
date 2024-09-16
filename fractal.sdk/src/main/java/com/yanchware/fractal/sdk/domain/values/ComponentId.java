package com.yanchware.fractal.sdk.domain.values;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
@NoArgsConstructor
public class ComponentId {
    
    protected final static Integer MIN_LENGTH = 3;
    protected final static Integer MAX_LENGTH = 63;

    private final static Pattern valueValidation = Pattern.compile("^(?!\\d)[a-z\\d]+((?:-[a-z\\d]+)*)+$");
    protected final static String ID_NULL_OR_EMPTY_TEMPLATE = "Component id '%s' is illegal. A valid component id cannot be null, empty or contain spaces";
    protected final static String ILLEGAL_ID_TEMPLATE = "Component id '%s' is illegal. A valid component id must start with a lowercase character and it can only contain lowercase characters, digits and hyphens";
    protected final static String ID_LENGTH_MISMATCH_TEMPLATE =
        "Component id '%s' is illegal. A valid component Id must be between " +  MIN_LENGTH + 
            " and " + MAX_LENGTH + " characters of length";
    
    private String value;

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

        var length = value.length();
        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            ret.add(String.format(ID_LENGTH_MISMATCH_TEMPLATE, value));
        }

        return ret;
    }
}
