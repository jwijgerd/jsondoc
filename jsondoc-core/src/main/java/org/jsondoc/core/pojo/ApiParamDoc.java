package org.jsondoc.core.pojo;

import java.util.UUID;

public final class ApiParamDoc {

    private String jsondocId = UUID.randomUUID().toString();
    private String name;
    private String description;
    private String type;
    private String required;
    private String[] allowedvalues;
    private String format;

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
}
