package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class ApiObjectDoc implements Comparable<ApiObjectDoc>, Visitable {

    private ApiVersionDoc version;
    private String jsondocId = UUID.randomUUID().toString();
    private String name;
    private String description;
    private List<ApiObjectFieldDoc> fields = new ArrayList<ApiObjectFieldDoc>();

    public ApiObjectDoc(String name, String description, List<ApiObjectFieldDoc> fields, ApiVersionDoc version) {
        this.name = name;
        this.description = description;
        this.fields = fields;
        this.version = version;
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

    public void removeField(ApiObjectFieldDoc field) {
        fields.remove(field);
    }

    @Override
    public int compareTo(ApiObjectDoc o) {
        return name.compareTo(o.getName());
    }

    public String getJsondocId() {
        return jsondocId;
    }

    public ApiVersionDoc getVersion() {
        return version;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
