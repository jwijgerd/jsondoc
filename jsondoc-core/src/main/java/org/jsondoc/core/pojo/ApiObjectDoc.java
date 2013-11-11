package org.jsondoc.core.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jsondoc.core.util.StringUtils;
import org.jsondoc.core.visitor.Visitable;
import org.jsondoc.core.visitor.Visitor;

public final class ApiObjectDoc implements Comparable<ApiObjectDoc>, Visitable {

    private final String jsondocId = UUID.randomUUID().toString();

    private String name;
    private String description;
    private String category;
    private ApiVersionDoc version;

    private List<ApiObjectPropertyDoc> fields = new ArrayList<ApiObjectPropertyDoc>();

    public String getJsondocId() {
        return jsondocId;
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

    public ApiVersionDoc getVersion() {
        return version;
    }

    public void setVersion(ApiVersionDoc version) {
        this.version = version;
    }

    public List<ApiObjectPropertyDoc> getFields() {
        return fields;
    }

    public void setFields(List<ApiObjectPropertyDoc> fields) {
        this.fields = fields;
    }

    public ApiObjectPropertyDoc getField(String actualName) {
        for (ApiObjectPropertyDoc field : fields) {
            if (field.getActualName().compareTo(actualName) == 0) {
                return field;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiObjectDoc o) {
        return name.compareTo(o.getName());
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public boolean isValid() {
        return StringUtils.hasText(name);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
