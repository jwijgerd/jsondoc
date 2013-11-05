package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class ApiObjectDoc implements Comparable<ApiObjectDoc>, Visitable {

    private final String jsondocId = UUID.randomUUID().toString();

    private String name;
    private String description;
    private ApiVersionDoc version;
    private List<ApiObjectFieldDoc> fields = new ArrayList<ApiObjectFieldDoc>();

    public ApiObjectDoc() {

    }

    public ApiObjectDoc(String name, String description, List<ApiObjectFieldDoc> fields, ApiVersionDoc version) {
        this.name = name;
        this.description = description;
        this.fields = fields;
        this.version = version;
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

    public ApiVersionDoc getVersion() {
        return version;
    }

    public void setVersion(ApiVersionDoc version) {
        this.version = version;
    }

    public List<ApiObjectFieldDoc> getFields() {
        return fields;
    }

    public void setFields(List<ApiObjectFieldDoc> fields) {
        this.fields = fields;
    }

    public ApiObjectFieldDoc getField(String name) {
        for (ApiObjectFieldDoc field : fields) {
            if (field.getName().compareTo(name) == 0) {
                return field;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiObjectDoc o) {
        return name.compareTo(o.getName());
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
