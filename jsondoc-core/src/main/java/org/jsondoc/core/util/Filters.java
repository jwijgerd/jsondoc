package org.jsondoc.core.util;

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
 * @author Daniel Ostermeier
 */
public class Filters {

    public static void byVersion(JSONDoc doc, int version) {

        Set<ApiDoc> apis = doc.getApis();
        for (ApiDoc api : new ArrayList<ApiDoc>(apis)) {
            if (accept(api.getVersion(), version)) {
                byVersion(api, version);
            } else {
                apis.remove(api);
            }
        }

        Set<ApiObjectDoc> objects = doc.getObjects();
        for (ApiObjectDoc object : new ArrayList<ApiObjectDoc>(objects)) {
            if (accept(object.getVersion(), version)) {
                byVersion(object, version);
            } else {
                objects.remove(object);
            }
        }
    }

    public static void byVersion(ApiObjectDoc object, int version) {
        List<ApiObjectFieldDoc> fields = object.getFields();
        for (ApiObjectFieldDoc field : new ArrayList<ApiObjectFieldDoc>(fields)) {
            if (!accept(field.getVersion(), version)) {
                fields.remove(field);
            }
        }
    }

    public static void byVersion(ApiDoc api, int version) {
        List<ApiMethodDoc> methods = api.getMethods();
        for (ApiMethodDoc method : new ArrayList<ApiMethodDoc>(methods)) {
            if (accept(method.getVersion(), version)) {
                byVersion(method, version);
            } else {
                methods.remove(method);
            }
        }
    }

    public static void byVersion(ApiMethodDoc method, int version) {
        List<ApiParamDoc> params = method.getUrlparameters();
        for (ApiParamDoc param : new ArrayList<ApiParamDoc>(params)) {
            if (!accept(param.getVersion(), version)) {
                params.remove(param);
            }
        }
    }

    private static boolean accept(ApiVersionDoc version, int requiredVersion) {
        if (version == null) {
            return true;
        }
        if (version.getSince() < requiredVersion) {
            return true;
        }
        if (version.getUntil() == -1) {
            return true;
        }
        return requiredVersion < version.getUntil();
    }
}
