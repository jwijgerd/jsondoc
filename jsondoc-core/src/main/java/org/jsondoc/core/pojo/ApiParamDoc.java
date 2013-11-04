package org.jsondoc.core.pojo;

import java.util.UUID;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class ApiParamDoc implements Visitable {

    private String jsondocId = UUID.randomUUID().toString();
    private String name;
    private String description;
    private String type;
    private String required;
    private String[] allowedvalues = new String[0];
    private String format;
    private ApiVersionDoc version;

    public ApiParamDoc(String name,
                       String description,
                       String type,
                       String required,
                       String[] allowedvalues,
                       String format) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.required = required;
        this.allowedvalues = allowedvalues;
        this.format = format;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getRequired() {
        return required;
    }

    public String[] getAllowedvalues() {
        return allowedvalues;
    }

    public String getFormat() {
        return format;
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

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
