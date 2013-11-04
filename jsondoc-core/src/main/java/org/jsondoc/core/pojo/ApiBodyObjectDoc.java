package org.jsondoc.core.pojo;

import java.util.UUID;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class ApiBodyObjectDoc implements Visitable {

    private String jsondocId = UUID.randomUUID().toString();

    private String object;
    private String multiple;
    private String mapKeyObject;
    private String mapValueObject;
    private String map;

    public ApiBodyObjectDoc(String object, String mapKeyObject, String mapValueObject, String multiple, String map) {
        this.object = object;
        this.multiple = multiple;
        this.mapKeyObject = mapKeyObject;
        this.mapValueObject = mapValueObject;
        this.map = map;
    }

    public String getObject() {
        return object;
    }

    public String getMultiple() {
        return multiple;
    }

    public String getMapKeyObject() {
        return mapKeyObject;
    }

    public String getMapValueObject() {
        return mapValueObject;
    }

    public String getMap() {
        return map;
    }

    public String getJsondocId() {
        return jsondocId;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
}
