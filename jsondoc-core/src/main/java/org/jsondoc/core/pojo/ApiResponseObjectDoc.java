package org.jsondoc.core.pojo;

import java.util.UUID;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class ApiResponseObjectDoc implements Visitable {

    private String jsondocId = UUID.randomUUID().toString();
    private String object;
    private String multiple;
    private String mapKeyObject;
    private String mapValueObject;
    private String map;

    public ApiResponseObjectDoc() {

    }

    public ApiResponseObjectDoc(String object,
                                String mapKeyObject,
                                String mapValueObject,
                                String multiple,
                                String map) {
        this.object = object;
        this.multiple = multiple;
        this.mapKeyObject = mapKeyObject;
        this.mapValueObject = mapValueObject;
        this.map = map;
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

    public String getJsondocId() {
        return jsondocId;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
