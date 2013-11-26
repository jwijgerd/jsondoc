package org.jsondoc.core.pluggable.jsondoc;

import static org.jsondoc.core.util.StringUtils.hasText;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pojo.ApiMethodDoc;
import org.jsondoc.core.pojo.ApiStatus;
import org.jsondoc.core.pojo.ApiVerb;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiMethodHandler implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof ApiMethod && candidate instanceof Method;
    }

    @Override
    public void handle(AnnotatedElement method, ApiMethodDoc doc) {
        ApiMethod annotation = method.getAnnotation(ApiMethod.class);
        if (hasText(annotation.path())) {
            doc.setPath(annotation.path());
        }
        if (hasText(annotation.description())) {
            doc.setDescription(annotation.description());
        }
        if (annotation.verb() != ApiVerb.UNDEFINED) {
            doc.setVerb(annotation.verb());
        }
        if (annotation.status() != ApiStatus.UNDEFINED) {
            doc.setStatus(annotation.status());
        }
        doc.setResponseStatus(annotation.responseStatus());
        doc.addConsumes(annotation.consumes());
        doc.addProduces(annotation.produces());
    }
}
