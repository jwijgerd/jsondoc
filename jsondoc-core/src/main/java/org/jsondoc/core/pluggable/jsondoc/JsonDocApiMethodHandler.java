package org.jsondoc.core.pluggable.jsondoc;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pluggable.ApiMethodAnnotationHandler;
import org.jsondoc.core.pojo.ApiMethodDoc;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiMethodHandler implements ApiMethodAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return annotation instanceof ApiMethod && candidate instanceof Method;
    }

    public void handle(AnnotatedElement method, ApiMethodDoc doc) {
        ApiMethod annotation = method.getAnnotation(ApiMethod.class);
        doc.setPath(annotation.path());
        doc.setDescription(annotation.description());
        doc.setVerb(annotation.verb());
        doc.setConsumes(Arrays.asList(annotation.consumes()));
        doc.setProduces(Arrays.asList(annotation.produces()));
    }
}
