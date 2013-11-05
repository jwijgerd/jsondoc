package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class ApiMethodDoc implements Visitable {

    private String jsondocId = UUID.randomUUID().toString();
    private String path;
    private String description;
    private ApiVerb verb;
    private List<String> produces = new ArrayList<String>();
    private List<String> consumes = new ArrayList<String>();
    private List<ApiHeaderDoc> headers = new ArrayList<ApiHeaderDoc>();
    private List<ApiParamDoc> urlparameters = new ArrayList<ApiParamDoc>();
    private ApiBodyObjectDoc bodyobject;
    private ApiResponseObjectDoc response;
    private List<ApiErrorDoc> apierrors = new ArrayList<ApiErrorDoc>();
    private ApiVersionDoc version;

    private List<ApiPermissionDoc> permissions = new ArrayList<ApiPermissionDoc>();

    public ApiMethodDoc() {
        this.headers = new ArrayList<ApiHeaderDoc>();
        this.urlparameters = new ArrayList<ApiParamDoc>();
        this.apierrors = new ArrayList<ApiErrorDoc>();
    }

    public List<ApiHeaderDoc> getHeaders() {
        return headers;
    }

    public void setHeaders(List<ApiHeaderDoc> headers) {
        this.headers = headers;
    }

    public List<ApiPermissionDoc> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<ApiPermissionDoc> permissions) {
        this.permissions = permissions;
    }

    public List<String> getProduces() {
        return produces;
    }

    public void setProduces(List<String> produces) {
        this.produces = produces;
    }

    public List<String> getConsumes() {
        return consumes;
    }

    public void setConsumes(List<String> consumes) {
        this.consumes = consumes;
    }

    public ApiVerb getVerb() {
        return verb;
    }

    public void setVerb(ApiVerb verb) {
        this.verb = verb;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ApiParamDoc> getUrlparameters() {
        return urlparameters;
    }

    public void setUrlparameters(List<ApiParamDoc> urlparameters) {
        this.urlparameters = urlparameters;
    }

    public ApiResponseObjectDoc getResponse() {
        return response;
    }

    public void setResponse(ApiResponseObjectDoc response) {
        this.response = response;
    }

    public ApiBodyObjectDoc getBodyobject() {
        return bodyobject;
    }

    public void setBodyobject(ApiBodyObjectDoc bodyobject) {
        this.bodyobject = bodyobject;
    }

    public List<ApiErrorDoc> getApierrors() {
        return apierrors;
    }

    public void setApierrors(List<ApiErrorDoc> apierrors) {
        this.apierrors = apierrors;
    }

    public String getJsondocId() {
        return jsondocId;
    }

    public void setVersion(ApiVersionDoc version) {
        this.version = version;
    }

    public ApiVersionDoc getVersion() {
        return version;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
