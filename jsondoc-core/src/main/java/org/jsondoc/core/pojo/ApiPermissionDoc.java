package org.jsondoc.core.pojo;

import java.util.UUID;

import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

/**
 * @author Daniel Ostermeier
 */
public class ApiPermissionDoc implements Visitable {

    private String jsondocId = UUID.randomUUID().toString();
    private String name;
    private String description;

    public ApiPermissionDoc(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getJsondocId() {
        return jsondocId;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
