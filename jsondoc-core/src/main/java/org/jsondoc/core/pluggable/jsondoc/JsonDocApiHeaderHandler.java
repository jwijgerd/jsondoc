package org.jsondoc.core.pluggable.jsondoc;

import static org.jsondoc.core.util.StringUtils.hasText;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiHeader;
import org.jsondoc.core.annotation.ApiHeaders;
import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pojo.ApiHeaderDoc;
import org.jsondoc.core.pojo.ApiMethodDoc;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiHeaderHandler implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return candidate instanceof Method && annotation instanceof ApiHeaders;
    }

    @Override
    public void handle(AnnotatedElement element, ApiMethodDoc doc) {
        ApiHeaders headersAnnotation = element.getAnnotation(ApiHeaders.class);
        for (ApiHeader headerAnnotation : headersAnnotation.headers()) {
            ApiHeaderDoc headerDoc = doc.getHeader(headerAnnotation.name());
            if (headerDoc == null) {
                headerDoc = new ApiHeaderDoc();
                headerDoc.setName(headerAnnotation.name());
                doc.getHeaders().add(headerDoc);
            }
            if (hasText(headerAnnotation.description())) {
                headerDoc.setDescription(headerAnnotation.description());
            }
        }
    }
}
