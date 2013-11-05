package org.jsondoc.core.pojo;

import java.util.UUID;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class ApiHeaderDoc implements Visitable {

    private final String jsondocId = UUID.randomUUID().toString();

    private String name;
    private String description;

    public ApiHeaderDoc(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getJsondocId() {
        return jsondocId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
