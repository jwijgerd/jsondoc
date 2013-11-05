package org.jsondoc.core.pluggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiHeader;
import org.jsondoc.core.annotation.ApiHeaders;
import org.jsondoc.core.annotation.ApiPermission;
import org.jsondoc.core.annotation.ApiPermissions;
import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiPermissionHandler  implements ApiMethodAnnotationHandler{

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return candidate instanceof Method && annotation instanceof ApiPermissions;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {
        ApiPermissions annotation = element.getAnnotation(ApiPermissions.class);
        for (ApiPermission apiHeader : annotation.permissions()) {
            // TODO: take care of duplicates.
            doc.getHeaders().add(new ApiHeaderDoc(apiHeader.name(), apiHeader.description()));
        }
    }
}

