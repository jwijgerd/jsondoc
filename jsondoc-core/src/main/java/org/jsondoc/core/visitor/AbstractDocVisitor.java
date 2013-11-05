package org.jsondoc.core.visitor;

import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiErrorDoc;
import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiPermissionDoc;
import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.jsondoc.core.pojo.ApiVersionDoc;
import org.jsondoc.core.pojo.JSONDoc;

/**
 * @author Daniel Ostermeier
 */
public abstract class AbstractDocVisitor<T> implements Visitor<T> {

    @Override
    public T visit(ApiBodyObjectDoc apiBodyObject) {
        return null;
    }

    @Override
    public T visit(ApiDoc api) {
        return null;
    }

    @Override
    public T visit(ApiErrorDoc apiError) {
        return null;
    }

    @Override
    public T visit(ApiHeaderDoc apiHeader) {
        return null;
    }

    @Override
    public T visit(ApiPermissionDoc apiPermission) {
        return null;
    }

    @Override
    public T visit(ApiMethodDoc apiMethod) {
        return null;
    }

    @Override
    public T visit(ApiObjectDoc apiObject) {
        return null;
    }

    @Override
    public T visit(ApiObjectFieldDoc apiObjectField) {
        return null;
    }

    @Override
    public T visit(ApiParamDoc apiParam) {
        return null;
    }

    @Override
    public T visit(ApiResponseObjectDoc apiResponseObject) {
        return null;
    }

    @Override
    public T visit(ApiVersionDoc apiVersion) {
        return null;
    }

    @Override
    public T visit(JSONDoc doc) {
        return null;
    }
}
