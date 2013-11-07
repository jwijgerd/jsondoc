package org.jsondoc.core.pluggable.jsondoc;

import static org.jsondoc.core.util.StringUtils.hasText;

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
        ApiErrors errorsAnnotation = element.getAnnotation(ApiErrors.class);
        for (ApiError errorAnnotation : errorsAnnotation.apierrors()) {
            ApiErrorDoc errorDoc = doc.getError(errorAnnotation.code());
            if (errorDoc == null) {
                errorDoc = new ApiErrorDoc();
                errorDoc.setCode(errorAnnotation.code());
                doc.getApierrors().add(errorDoc);
            }
            if (hasText(errorAnnotation.description())) {
                errorDoc.setDescription(errorAnnotation.description());
            }
        }
    }
}
