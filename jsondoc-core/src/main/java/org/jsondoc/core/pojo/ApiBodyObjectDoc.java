package org.jsondoc.core.pojo;

import java.util.UUID;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class ApiBodyObjectDoc implements Visitable {

    /**
     * A unique identifier for this instance.
     */
    private final String jsondocId = UUID.randomUUID().toString();

    /**
     * The object type.
     */
    private String object;

    /**
     * "True" if the object is a collection, false otherwise.
     */
    private String multiple;

    /**
     * If the body object is a map type, then this variable defines the type
     * of the map key.
     */
    private String mapKeyObject;

    /**
     * If the body object is a map type, then this variable defines the type
     * of the map value.
     */
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
