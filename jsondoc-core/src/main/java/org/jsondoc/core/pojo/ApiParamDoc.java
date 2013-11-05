package org.jsondoc.core.pojo;

import java.util.UUID;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class ApiParamDoc implements Visitable {

    private final String jsondocId = UUID.randomUUID().toString();

    private String name;
    private String description;

    private String type;
    private String required;
    private String[] allowedvalues = new String[0];
    private String format;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String[] getAllowedvalues() {
        return allowedvalues;
    }

    public void setAllowedvalues(String[] allowedvalues) {
        this.allowedvalues = allowedvalues;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getJsondocId() {
        return jsondocId;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
