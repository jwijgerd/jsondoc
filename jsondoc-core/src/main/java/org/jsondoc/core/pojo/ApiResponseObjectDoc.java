package org.jsondoc.core.pojo;

import java.util.UUID;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class ApiResponseObjectDoc implements Visitable {

    private final String jsondocId = UUID.randomUUID().toString();
    private String object;
    private String multiple;
    private String mapKeyObject;
    private String mapValueObject;
    private String map;

    public String getJsondocId() {
        return jsondocId;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
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

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
