package com.yanchware.fractal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BoundedContext {
    private final Id id;
    private String displayName;
    private String description;

    public record Id(OwnerType ownerType, OwnerId ownerId, KebabCaseString name) { }
}
