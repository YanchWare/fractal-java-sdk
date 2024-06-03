package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers;

import java.util.Map;

public interface ResourceEntity {
    Map<String, String> getTags();
    void setTags(Map<String, String> tags);

    String getName();
    void setName(String Name);
}
