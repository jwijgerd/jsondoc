package org.jsondoc.core.pluggable.jsondoc;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.pluggable.ApiAnnotationHandler;
import org.jsondoc.core.pojo.ApiDoc;

/**
 * @author Daniel Ostermeier
 */
public class JsonDocApiHandler implements ApiAnnotationHandler {

    @Override
    public boolean canHandle(AnnotatedElement candidate, Annotation annotation) {
        return candidate instanceof Class && annotation instanceof Api;
    }

    @Override
    public void handle(AnnotatedElement element, ApiDoc doc) {
        Api annotation = element.getAnnotation(Api.class);
        doc.setName(annotation.name());
        doc.setDescription(annotation.description());
    }
}
