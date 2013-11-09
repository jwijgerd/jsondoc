package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.util.StringUtils;
import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class ApiMethodDoc implements Visitable {

    private final String jsondocId = UUID.randomUUID().toString();
    private String path;
    private String description;
    private ApiVersionDoc version;
    private ApiVerb verb;
    private ApiStatus status;
    private ApiBodyObjectDoc bodyobject;
    private ApiResponseObjectDoc response;
    private List<String> produces = new ArrayList<String>();
    private List<String> consumes = new ArrayList<String>();
    private List<ApiHeaderDoc> headers = new ArrayList<ApiHeaderDoc>();
    private List<ApiParamDoc> pathparameters = new ArrayList<ApiParamDoc>();
    private List<ApiParamDoc> queryparameters = new ArrayList<ApiParamDoc>();
    private List<ApiErrorDoc> apierrors = new ArrayList<ApiErrorDoc>();
    private List<ApiPermissionDoc> permissions = new ArrayList<ApiPermissionDoc>();

    public String getJsondocId() {
        return jsondocId;
    }

    public List<ApiHeaderDoc> getHeaders() {
        return headers;
    }

    public void setHeaders(List<ApiHeaderDoc> headers) {
        this.headers = headers;
    }

    public ApiHeaderDoc getHeader(String name) {
        for (ApiHeaderDoc header : headers) {
            if (header.getName().compareTo(name) == 0) {
                return header;
            }
        }
        return null;
    }

    public List<ApiPermissionDoc> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<ApiPermissionDoc> permissions) {
        this.permissions = permissions;
    }

    public ApiPermissionDoc getPermission(String name) {
        for (ApiPermissionDoc permission : permissions) {
            if (permission.getName().compareTo(name) == 0) {
                return permission;
            }
        }
        return null;
    }

    public List<String> getProduces() {
        return produces;
    }

    public void setProduces(List<String> produces) {
        this.produces = produces;
    }

    public void addProduces(String... produces) {
        for (String c : produces) {
            if (!this.produces.contains(c)) {
                this.produces.add(c);
            }
        }
    }

    public List<String> getConsumes() {
        return consumes;
    }

    public void setConsumes(List<String> consumes) {
        this.consumes = consumes;
    }

    public void addConsumes(String... consumes) {
        for (String c : consumes) {
            if (!this.consumes.contains(c)) {
                this.consumes.add(c);
            }
        }
    }

    public ApiVerb getVerb() {
        return verb;
    }

    public void setVerb(ApiVerb verb) {
        this.verb = verb;
    }

    public ApiStatus getStatus() {
        return status;
    }

    public void setStatus(ApiStatus status) {
        this.status = status;
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

    // Backward compatibility.
    public List<ApiParamDoc> getUrlparameters() {
        return getPathparameters();
    }

    public List<ApiParamDoc> getPathparameters() {
        return pathparameters;
    }

    public void setPathparameters(List<ApiParamDoc> pathparameters) {
        this.pathparameters = pathparameters;
    }

    public ApiParamDoc getPathparameter(String name) {
        return findApiParamDocByName(name, pathparameters);
    }

    public List<ApiParamDoc> getQueryparameters() {
        return queryparameters;
    }

    public void setQueryparameters(List<ApiParamDoc> queryparameters) {
        this.queryparameters = queryparameters;
    }

    public ApiParamDoc getQueryparameter(String name) {
        return findApiParamDocByName(name, queryparameters);
    }

    private ApiParamDoc findApiParamDocByName(String name, Iterable<ApiParamDoc> params) {
        for (ApiParamDoc paramDoc : params) {
            if (paramDoc.getName().compareTo(name) == 0) {
                return paramDoc;
            }
        }
        return null;
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

    public ApiErrorDoc getError(String code) {
        for (ApiErrorDoc error : apierrors) {
            if (error.getCode().compareTo(code) == 0) {
                return error;
            }
        }
        return null;
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

    public boolean isValid() {
        return StringUtils.hasText(path);
    }
}
