package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ApiDoc implements Comparable<ApiDoc> {

    private String jsondocId = UUID.randomUUID().toString();
    private String name;
    private String description;
    private List<ApiMethodDoc> methods;

    public ApiDoc() {
        this.methods = new ArrayList<ApiMethodDoc>();
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
        this.methods.add(apiMethod);
    }

    @Override
    public int compareTo(ApiDoc o) {
        return name.compareTo(o.getName());
    }

    public String getJsondocId() {
        return jsondocId;
    }
}
