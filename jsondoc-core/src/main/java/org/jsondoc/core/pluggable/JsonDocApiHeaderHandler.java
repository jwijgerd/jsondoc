package org.jsondoc.core.pluggable;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiHeader;
import org.jsondoc.core.annotation.ApiHeaders;
import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiHeaderHandler implements ApiMethodAnnotationHandler{

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return candidate instanceof Method && annotation instanceof ApiHeaders;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {
        ApiHeaders annotation = element.getAnnotation(ApiHeaders.class);
        for (ApiHeader apiHeader : annotation.headers()) {
            // TODO: take care of duplicates.
            doc.getHeaders().add(new ApiHeaderDoc(apiHeader.name(), apiHeader.description()));
        }
    }
}
