package org.jsondoc.core.visitor;

import org.jsondoc.core.pojo.ApiBodyObjectDoc;
import org.jsondoc.core.pojo.ApiDoc;
import org.jsondoc.core.pojo.ApiErrorDoc;
import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiObjectDoc;
import org.jsondoc.core.pojo.ApiObjectFieldDoc;
import org.jsondoc.core.pojo.ApiParamDoc;
import org.jsondoc.core.pojo.ApiResponseObjectDoc;
import org.jsondoc.core.pojo.ApiVersionDoc;
import org.jsondoc.core.pojo.JSONDoc;

/**
 * @author Daniel Ostermeier
 */
public interface Visitor<T> {

    T visit(ApiBodyObjectDoc apiBodyObject);
    T visit(ApiDoc api);
    T visit(ApiErrorDoc apiError);
    T visit(ApiHeaderDoc apiHeader);
    T visit(ApiMethodDoc apiMethod);
    T visit(ApiObjectDoc apiObject);
    T visit(ApiObjectFieldDoc apiObjectField);
    T visit(ApiParamDoc apiParam);
    T visit(ApiResponseObjectDoc apiResponseObject);
    T visit(ApiVersionDoc apiVersion);
    T visit(JSONDoc doc);
}