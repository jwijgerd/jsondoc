package org.jsondoc.core.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
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

        Set<ApiDoc> apis = doc.getApis();
        for (ApiDoc api : new ArrayList<ApiDoc>(apis)) {
            if (acceptVersion(api.getVersion(), version)) {
                api.accept(this);
            } else {
                apis.remove(api);
            }
        }

        Set<ApiObjectDoc> objects = doc.getObjects();
        for (ApiObjectDoc object : new ArrayList<ApiObjectDoc>(objects)) {
            if (acceptVersion(object.getVersion(), version)) {
                object.accept(this);
            } else {
                objects.remove(object);
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
        List<ApiObjectFieldDoc> fields = object.getFields();
        for (ApiObjectFieldDoc field : new ArrayList<ApiObjectFieldDoc>(fields)) {
            if (acceptVersion(field.getVersion(), version)) {
                field.accept(this);
            } else {
                fields.remove(field);
            }
        }
        return null;
    }

    @Override
    public Void visit(ApiMethodDoc method) {
        List<ApiParamDoc> params = method.getUrlparameters();
        for (ApiParamDoc param : new ArrayList<ApiParamDoc>(params)) {
            if (acceptVersion(param.getVersion(), version)) {
                param.accept(this);
            } else {
                params.remove(param);
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
