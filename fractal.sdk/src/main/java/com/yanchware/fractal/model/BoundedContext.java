package com.yanchware.fractal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BoundedContext {
    private Id id;
    protected String displayName;
    protected String description;

    public record Id(OwnerType ownerType, OwnerId ownerId, KebabCaseString name) { }
}
