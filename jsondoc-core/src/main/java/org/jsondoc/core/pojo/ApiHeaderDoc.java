package org.jsondoc.core.pojo;

import java.util.UUID;

public final class ApiHeaderDoc {

    private String jsondocId = UUID.randomUUID().toString();
    private String name;
    private String description;

    public ApiHeaderDoc(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getJsondocId() {
        return jsondocId;
    }
}
