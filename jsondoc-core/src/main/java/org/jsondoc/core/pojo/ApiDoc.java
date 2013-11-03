package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ApiDoc implements Comparable<ApiDoc> {

    private String jsondocId = UUID.randomUUID().toString();
    private String name;
    private String description;
    private List<ApiMethodDoc> methods;
    private ApiVersionDoc version;

    public ApiDoc() {
        methods = new ArrayList<ApiMethodDoc>();
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

    @Override
    public int compareTo(ApiDoc o) {
        return name.compareTo(o.getName());
    }

    public String getJsondocId() {
        return jsondocId;
    }

    public ApiVersionDoc getVersion() {
        return version;
    }

    public void setVersion(ApiVersionDoc version) {
        this.version = version;
    }
}
