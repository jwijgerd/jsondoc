package org.jsondoc.core.pojo;

import java.util.UUID;

public final class ApiObjectFieldDoc {

    private String jsondocId = UUID.randomUUID().toString();
    private String name;
    private String type;
    private String multiple;
    private String description;
    private String format;
    private String[] allowedvalues;
    private String mapKeyObject;
    private String mapValueObject;
    private String map;

    public String getMapKeyObject() {
        return mapKeyObject;
    }

    public void setMapKeyObject(String mapKeyObject) {
        this.mapKeyObject = mapKeyObject;
    }

    public String getMapValueObject() {
        return mapValueObject;
    }

    public void setMapValueObject(String mapValueObject) {
        this.mapValueObject = mapValueObject;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
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

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ApiObjectFieldDoc() {
        super();
    }

    public String getJsondocId() {
        return jsondocId;
    }
}
