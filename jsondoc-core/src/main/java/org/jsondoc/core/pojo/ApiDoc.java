package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class ApiDoc implements Comparable<ApiDoc>, Visitable {

    private final String jsondocId = UUID.randomUUID().toString();

    private String name;
    private String description;
    private ApiVersionDoc version;
    private List<ApiMethodDoc> methods = new ArrayList<ApiMethodDoc>();

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

    public List<ApiMethodDoc> getMethods() {
        return methods;
    }

    public void setMethods(List<ApiMethodDoc> methods) {
        this.methods = methods;
    }

    public void addMethod(ApiMethodDoc apiMethod) {
        methods.add(apiMethod);
    }

    public void removeMethod(ApiMethodDoc apiMethod) {
        methods.remove(apiMethod);
    }

    public ApiVersionDoc getVersion() {
        return version;
    }

    public void setVersion(ApiVersionDoc version) {
        this.version = version;
    }

    @Override
    public int compareTo(ApiDoc o) {
        return name.compareTo(o.getName());
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
