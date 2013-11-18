package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class ApiObjectPropertyDoc implements Visitable {

    private final String jsondocId = UUID.randomUUID().toString();

    private String actualName;

    private String name;
    private String description;
    private String exampleValue;

    private String type;
    private String multiple;
    private String format;
    private String mapKeyObject;
    private String mapValueObject;
    private String map;
    private ApiVersionDoc version;
    private List<String> allowedvalues = new ArrayList<String>();

    public void setActualName(String actualName) {
        this.actualName = actualName;
    }

    public String getActualName() {
        return actualName;
    }

    public String getJsondocId() {
        return jsondocId;
    }

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

    public List<String> getAllowedvalues() {
        return allowedvalues;
    }

    public void setAllowedvalues(List<String> allowedvalues) {
        this.allowedvalues = allowedvalues;
    }

    public void addAllowedvalues(String... allowedvalues) {
        for (String allowedvalue : allowedvalues) {
            if (!this.allowedvalues.contains(allowedvalue)) {
                this.allowedvalues.add(allowedvalue);
            }
        }
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
