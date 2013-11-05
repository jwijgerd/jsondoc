package org.jsondoc.core.pluggable.jsondoc;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.annotation.ApiError;
import org.jsondoc.core.annotation.ApiErrors;
import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pojo.ApiErrorDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiErrorHandler implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof ApiErrors;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {
        ApiErrors annotation = element.getAnnotation(ApiErrors.class);
        for (ApiError apiError : annotation.apierrors()) {
            doc.getApierrors().add(new ApiErrorDoc(apiError.code(), apiError.description()));
        }
    }
}
