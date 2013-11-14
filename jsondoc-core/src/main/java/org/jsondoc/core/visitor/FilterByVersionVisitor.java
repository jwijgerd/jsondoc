package org.jsondoc.core.visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectPropertyDoc;
import org.jsondoc.core.pojo.ApiVersionDoc;
import org.jsondoc.core.pojo.JSONDoc;

/**
 * This visitor implements an inplace filter based on the version details of the
 * documentation.
 *
 * @author Daniel Ostermeier
 */
public class FilterByVersionVisitor extends AbstractDocVisitor<Void> {

    private final int version;

    public FilterByVersionVisitor(int version) {
        this.version = version;
    }

    @Override
    public Void visit(JSONDoc doc) {

        List<ApiDoc> apis = doc.getApis();
        for (ApiDoc api : new ArrayList<ApiDoc>(apis)) {
            if (acceptVersion(api.getVersion(), version)) {
                api.accept(this);
            } else {
                apis.remove(api);
            }
        }

        Map<String,Collection<ApiObjectDoc>> objects = doc.getObjects();
        for (Map.Entry<String,Collection<ApiObjectDoc>> collectionEntry : objects.entrySet()) {
            for (ApiObjectDoc object:collectionEntry.getValue()){
                if (acceptVersion(object.getVersion(), version)) {
                    object.accept(this);
                } else {
                    collectionEntry.getValue().remove(object);
                }
            }

        }
        return null;
    }

    @Override
    public Void visit(ApiDoc api) {
        List<ApiMethodDoc> methods = api.getMethods();
        for (ApiMethodDoc method : new ArrayList<ApiMethodDoc>(methods)) {
            if (acceptVersion(method.getVersion(), version)) {
                method.accept(this);
            } else {
                methods.remove(method);
            }
        }
        return null;
    }

    @Override
    public Void visit(ApiObjectDoc object) {
        List<ApiObjectPropertyDoc> fields = object.getFields();
        for (ApiObjectPropertyDoc field : new ArrayList<ApiObjectPropertyDoc>(fields)) {
            if (acceptVersion(field.getVersion(), version)) {
                field.accept(this);
            } else {
                fields.remove(field);
            }
        }
        return null;
    }

    private static boolean acceptVersion(ApiVersionDoc version, int requiredVersion) {
        if (version == null) {
            return true;
        }
        if (version.getUntil() == -1) {
            return version.getSince() <= requiredVersion;
        }
        return version.getSince() <= requiredVersion && requiredVersion <= version.getUntil();
    }
}
