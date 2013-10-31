package org.jsondoc.core.pojo;

import java.util.List;
import java.util.UUID;

public final class ApiObjectDoc implements Comparable<ApiObjectDoc> {

    private String jsondocId = UUID.randomUUID().toString();
    private String name;
    private String description;
    private List<ApiObjectFieldDoc> fields;

    public ApiObjectDoc(String name, String description, List<ApiObjectFieldDoc> fields) {
        this.name = name;
        this.description = description;
        this.fields = fields;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<ApiObjectFieldDoc> getFields() {
        return fields;
    }

    @Override
    public int compareTo(ApiObjectDoc o) {
        return name.compareTo(o.getName());
    }

    public String getJsondocId() {
        return jsondocId;
    }
}
