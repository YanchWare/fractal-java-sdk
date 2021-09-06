package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.Validatable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter(AccessLevel.PRIVATE)
public class LiveSystem implements Validatable {
    @Override
    public Collection<String> validate() {
        return null;
    }
}
