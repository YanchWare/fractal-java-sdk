package com.yanchware.fractal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class LiveSystem {
    private final Id id;
    private String displayName;
    private String description;
    private Fractal.Id fractalId;
    private Mutation.Id currentMutationId;
    private final List<Mutation> mutations;
    private final Environment.Id environmentId;
    private Status status;

    public record Id(BoundedContext.Id boundedContextId, KebabCaseString name){
        @Override
        public String toString() {
            return String.format("%s/%s",
                    boundedContextId.toString(),
                    name.value());
        }
    }

    public enum Status {
        ACTIVE,
        MUTATING,
        FAILED
    }

    public record Mutation(Id id, List<LiveSystemComponent> components, Status status) {
        public record Id (UUID value) { }

        public enum Status {
            IN_PROGRESS,
            COMPLETED,
            FAILED,
            CANCELLED
        }
    }
}
