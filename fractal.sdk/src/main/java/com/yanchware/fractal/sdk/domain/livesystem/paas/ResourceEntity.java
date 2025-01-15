package com.yanchware.fractal.sdk.domain.livesystem.paas;

import java.util.Map;

public interface ResourceEntity {
    Map<String, String> getTags();
    void setTags(Map<String, String> tags);
}
